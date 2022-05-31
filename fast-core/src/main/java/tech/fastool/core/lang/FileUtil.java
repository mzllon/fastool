package tech.fastool.core.lang;

import java.io.File;

/**
 * 文件工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
public class FileUtil {

    /**
     * 1KB
     */
    public static final long ONE_KB = 1024;

    /**
     * 1MB
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * Linux路径分隔符
     */
    public static final char UNIX_SEPARATOR_CHAR = CharUtil.SLASH;

    /**
     * Linux路径分隔符
     */
    public static final String UNIX_SEPARATOR = StringUtil.EMPTY_STRING + CharUtil.SLASH;

    /**
     * Windows路径分隔符
     */
    public static final char WINDOWS_SEPARATOR_CHAR = CharUtil.BACKSLASH;

    /**
     * Windows路径分隔符
     */
    public static final String WINDOWS_SEPARATOR = StringUtil.EMPTY_STRING + CharUtil.BACKSLASH;

    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     */
    public static boolean isWindows() {
        return WINDOWS_SEPARATOR_CHAR == File.separatorChar;
    }

}
