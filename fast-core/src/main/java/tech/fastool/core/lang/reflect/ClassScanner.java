package tech.fastool.core.lang.reflect;

import tech.fastool.core.exceptions.GenericRuntimeException;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.filter.Filter;
import tech.fastool.core.lang.*;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * 类扫描器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ClassScanner {

    /**
     * 包名
     */
    private final String packageName;

    /**
     * 包名，最后跟一个点，表示包名，避免在检查前缀时的歧义
     */
    private final String packageNameWithDot;

    /**
     * 包路径，用于文件中对路径操作
     */
    private final String packageDirName;

    /**
     * 包路径，用于jar中对路径操作，在Linux下与packageDirName一致
     */
    private final String packagePath;

    /**
     * 过滤器
     */
    private final Filter<Class<?>> classFilter;

    /**
     * 编码
     */
    private final Charset encoding;

    /**
     * 扫描结果集
     */
    private final Set<Class<?>> classes = new HashSet<>();

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 是否初始化类
     */
    private boolean initialize;

    /**
     * 扫描该包路径下所有class文件
     *
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage() {
        return scanPackage(StringUtil.EMPTY_STRING, null);
    }

    /**
     * 扫描指定包路径下的所有class集合
     *
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String packageName) {
        return scanPackage(packageName, null);
    }

    /**
     * 扫描指定包路径下的满足条件的类
     *
     * @param packageName 包名
     * @param classFilter 过滤器，符合条件的
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String packageName, Filter<Class<?>> classFilter) {
        return new ClassScanner(packageName, classFilter).scan();
    }

    /**
     * 扫码指定包路径下包含指定注解的类
     *
     * @param packageName     包名
     * @param annotationClass 注解类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return scanPackage(packageName, clazz -> clazz.isAnnotationPresent(annotationClass));
    }

    /**
     * 构造，默认UTF-8编码
     */
    public ClassScanner() {
        this(null);
    }

    /**
     * 构造，默认UTF-8编码
     *
     * @param packageName 包名，所有包传入""或者null
     */
    public ClassScanner(String packageName) {
        this(packageName, null);
    }

    /**
     * 构造，默认UTF-8编码
     *
     * @param packageName 包名，所有包传入""或者null
     * @param classFilter 过滤器，无需传入null
     */
    public ClassScanner(String packageName, Filter<Class<?>> classFilter) {
        this(packageName, classFilter, CharsetUtil.UTF_8);
    }

    /**
     * 构造
     *
     * @param packageName 包名，所有包传入""或者null
     * @param classFilter 过滤器，无需传入null
     * @param encoding    编码
     */
    public ClassScanner(String packageName, Filter<Class<?>> classFilter, Charset encoding) {
        this.packageName = packageName == null ? StringUtil.EMPTY_STRING : packageName;
        this.packageNameWithDot = StringUtil.addSuffixIfNot(this.packageName, StringUtil.DOT);
        this.packageDirName = this.packageName.replace(CharUtil.DOT, File.separatorChar);
        this.packagePath = this.packageName.replace(CharUtil.DOT, CharUtil.SLASH);
        this.classFilter = classFilter;
        this.encoding = encoding;
    }

    /**
     * 设置是否在扫描到类时初始化类
     *
     * @param initialize 是否初始化类
     */
    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

    /**
     * 设置自定义的类加载器
     *
     * @param classLoader 类加载器
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 扫描包路径下满足class过滤器条件的所有class文件
     *
     * @return 类集合
     */
    public Set<Class<?>> scan() {
        Enumeration<URL> enumerationUrls;
        try {
            enumerationUrls = ClassLoaderUtil.getContextClassLoader().getResources(packagePath);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
        while (enumerationUrls.hasMoreElements()) {
            URL url = enumerationUrls.nextElement();
            if (ObjectUtil.equals("file", url.getProtocol())) {
                scanFile(new File(UrlUtil.decode(url.getFile(), encoding)), null);
            } else if (ObjectUtil.equals("jar", url.getProtocol())) {
                scanJar(UrlUtil.getJarFile(url));
            }
        }
        if (CollectionUtil.isEmpty(classes)) {
            scanJavaClassPaths();
        }
        return Collections.unmodifiableSet(classes);
    }

    /**
     * 扫描Java指定的ClassPath路径
     */
    private void scanJavaClassPaths() {
        String[] javaClassPaths = ClassUtil.getJavaClassPaths();
        for (String javaClassPath : javaClassPaths) {
            // bug修复，由于路径中空格和中文导致的Jar找不到
            javaClassPath = UrlUtil.decode(javaClassPath, CharsetUtil.systemCharset());
            scanFile(new File(javaClassPath), null);
        }
    }

    /**
     * 扫描jar包
     *
     * @param jarFile jar包
     */
    private void scanJar(JarFile jarFile) {
        jarFile.stream().forEach(jarEntry -> {
            String name = StringUtil.deletePrefix(jarEntry.getName(), StringUtil.SLASH);
            if (name.startsWith(packageName)) {
                if (name.endsWith(".class") && !jarEntry.isDirectory()) {
                    String className = name.substring(0, name.length() - 6).replace(CharUtil.SLASH, CharUtil.DOT);
                    addIfAccept(loadClass(className));
                }
            }
        });
    }

    /**
     * 扫描文件或目录中的类
     *
     * @param file    文件或目录
     * @param rootDir 包名对应classpath绝对路径
     */
    private void scanFile(File file, String rootDir) {
        if (file.isFile()) {
            String absolutePath = file.getAbsolutePath();
            if (absolutePath.endsWith(".class")) {
                String className = absolutePath.substring(rootDir.length(), absolutePath.length() - 6)
                        .replace(File.separatorChar, CharUtil.DOT);
                addIfAccept(className);
            } else if (absolutePath.endsWith(".jar")) {
                try {
                    scanJar(new JarFile(file));
                } catch (IOException e) {
                    throw new IoRuntimeException(e);
                }
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (ArrayUtil.isNotEmpty(files)) {
                for (File subFile : files) {
                    scanFile(subFile, (null == rootDir) ? subPathBeforePackage(file) : rootDir);
                }
            }
        }
    }

    /**
     * 通过过滤器，是否满足接受此类的条件
     *
     * @param className 类名
     */
    private void addIfAccept(String className) {
        if (StringUtil.isEmpty(className)) {
            return;
        }
        final int classLength = className.length();
        final int packageLength = packageName.length();
        if (classLength == packageLength) {
            //类名和包名长度一致，用户可能传入的包名是类名
            if (className.equals(packageName)) {
                addIfAccept(loadClass(className));
            }
        } else if (classLength > packageLength) {
            //检查类名是否以指定包名为前缀，包名后加.（避免类似于com.ijiagoushi.chillies.A和com.ijiagoushi.chilies.ATest这类类名引起的歧义）
            if (className.startsWith(packageNameWithDot)) {
                addIfAccept(loadClass(className));
            }
        }
    }

    /**
     * 通过过滤器，是否满足接受此类的条件
     *
     * @param clazz 类
     */
    private void addIfAccept(Class<?> clazz) {
        if (clazz != null) {
            if (classFilter == null || classFilter.accept(clazz)) {
                this.classes.add(clazz);
            }
        }
    }

    /**
     * 加载类
     *
     * @param className 类名
     * @return 加载的类
     */
    private Class<?> loadClass(String className) {
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getDefaultClassLoader();
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, this.initialize, classLoader);
        } catch (NoClassDefFoundError error) {
            // 由于依赖库导致的类无法加载，直接跳过此类
        } catch (UnsupportedClassVersionError e) {
            // 版本导致的不兼容的类，跳过
        } catch (Exception e) {
            throw new GenericRuntimeException(e);
        }
        return clazz;
    }

    /**
     * 截取文件绝对路径中包名之前的部分
     *
     * @param file 文件
     * @return 包名之前的部分
     */
    private String subPathBeforePackage(File file) {
        String absolutePath = file.getAbsolutePath();
        if (StringUtil.hasLength(packageDirName)) {
            absolutePath = StringUtil.substrBefore(absolutePath, packageDirName, true);
        }
        return StringUtil.addSuffixIfNot(absolutePath, File.separator);
    }

}
