package tech.fastool.core.io;

import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.lang.ArrayUtil;
import tech.fastool.core.lang.CharsetUtil;
import tech.fastool.core.lang.StringUtil;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * IO工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-01
 */
public final class IoUtil {

    private IoUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    /**
     * 文件结束标记
     */
    private static final int EOF = -1;

    /**
     * 默认缓存大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 默认中等缓存大小
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 4096;

    /**
     * 默认大缓存大小
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 8192;

    /**
     * 关闭<code>Closeable</code>,该方法等效于{@linkplain Closeable#close()}
     * <p>
     * 该方法主要用于finally块中，并且忽略所有的异常
     * </p>
     * Example code:
     * <pre>
     *   Closeable closeable = null;
     *   try {
     *       closeable = new FileReader("foo.txt");
     *       // process closeable
     *       closeable.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(closeable);
     *   }
     * </pre>
     *
     * @param closeable the object to close, may be null or already closed
     */
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }


    // region read bytes

    /**
     * 将输入流转为字节数组，并且自动关闭输入流
     *
     * @param in 输入流
     * @return 如果转换成功则返回字节数组，否则返回{@code null}
     */
    public static byte[] readBytes(final InputStream in) {
        return readBytes(in, true);
    }

    /**
     * 将输入流转为字节数组
     *
     * @param in        输入流
     * @param autoClose 关闭流
     * @return 如果转换成功则返回字节数组，否则返回{@code null}
     */
    public static byte[] readBytes(final InputStream in, boolean autoClose) {
        if (in == null) {
            return null;
        }
        try {
            FastByteArrayOutputStream out = new FastByteArrayOutputStream();
            copy(in, out);
            return out.toByteArray();
        } finally {
            if (autoClose) {
                closeQuietly(in);
            }
        }
    }

    /**
     * 从输入流中读取指定长度的字节数组
     *
     * @param in   输入流
     * @param size 读取长度，不能小于0
     * @return 返回读取的数据
     */
    public static byte[] readBytes(final InputStream in, final int size) {
        if (in == null) {
            return null;
        }
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }
        if (size == 0) {
            return new byte[0];
        }

        byte[] data = new byte[size];
        int readLength;

        try {
            readLength = in.read(data);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }

        if (readLength > 0 && readLength < size) {
            byte[] buf = new byte[size];
            System.arraycopy(data, 0, buf, 0, readLength);
            return buf;
        }
        return data;
    }

    // endregion

    // region read string

    /**
     * 从输入流转为字符串，采用{@code UTF-8}编码
     *
     * @param in 输入流
     * @return 字符串
     */
    public static String readUtf8(InputStream in) {
        return read(in, CharsetUtil.UTF_8_VALUE);
    }

    /**
     * 从输入流转为字符串，采用指定编码
     *
     * @param in      输入流
     * @param charset 自定义编码，可以为空
     * @return 字符串
     * @see #read(InputStream, Charset)
     */
    public static String read(InputStream in, String charset) {
        Charset encoding = CharsetUtil.forName(charset, null);
        return read(in, encoding);
    }

    /**
     * 从输入流转为字符串，采用指定编码
     * 编码为空采用系统默认编码
     *
     * @param in       输入流
     * @param encoding 自定义编码，可以为空
     * @return 字符串
     */
    public static String read(InputStream in, Charset encoding) {
        byte[] data = readBytes(in);
        return StringUtil.str(data, encoding);
    }

    /**
     * 将<code>Reader</code>的内容转为字符串
     *
     * @param reader read from
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    public static String read(final Reader reader) {
        return read(reader, true);
    }

    /**
     * 将<code>Reader</code>的内容转为字符串
     *
     * @param reader    read from
     * @param autoClose 是否自动关闭输入流
     * @return 转换异常时返回{@code null}，否则返回字节数组
     */
    public static String read(final Reader reader, final boolean autoClose) {
        if (reader == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        try {
            while (EOF != (reader.read(buffer))) {
                sb.append(buffer.flip());
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(reader);
            }
        }
        return sb.toString();
    }

    // endregion


    //region InputStream to OutputStream

    /**
     * 流拷贝
     * 自动关闭输入流和输出流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败或流过大均返回-1
     */
    public static long copy(final InputStream in, final OutputStream out) {
        return copy(in, out, true);
    }

    /**
     * 流拷贝
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败或流过大均返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final boolean autoClose) {
        return copy(in, out, DEFAULT_LARGE_BUFFER_SIZE, autoClose);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1。
     * 自动关闭输入流和输出流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final int bufferSize) {
        return copy(in, out, bufferSize, true);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final int bufferSize, final boolean autoClose) {
        return copy(in, out, new byte[bufferSize], autoClose);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1。
     * 自动关闭输入流和输出流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final byte[] buffer) {
        return copy(in, out, buffer, true);
    }

    /**
     * 流的拷贝，如果拷贝流失败则返回-1.
     *
     * @param in     输入流
     * @param out    输出流
     * @param buffer 缓冲区
     * @return 返回流大小，如果拷贝失败则返回-1
     */
    public static long copy(final InputStream in, final OutputStream out, final byte[] buffer, final boolean autoClose) {
        if (in == null) {
            return -1;
        }
        if (out == null) {
            return -1;
        }
        if (ArrayUtil.isEmpty(buffer)) {
            return -1;
        }
        long count = 0;
        int n;
        try {
            while (EOF != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
            return count;
        } catch (IOException e) {
            throw new IoRuntimeException("Copy bytes from a large InputStream to an OutputStream error", e);
        } finally {
            if (autoClose) {
                closeQuietly(out);
                closeQuietly(in);
            }
        }
    }

    /**
     * 拷贝文件流，使用NIO
     * 自动关闭输入流和输出流
     *
     * @param in  文件输入流
     * @param out 文件输出流
     * @return 拷贝的文件大小
     */
    public static long copy(final FileInputStream in, final FileOutputStream out) {
        return copy(in, out, true);
    }

    /**
     * 拷贝文件流，使用NIO
     *
     * @param in        文件输入流
     * @param out       文件输出流
     * @param autoClose 拷贝完毕后是否关闭输入/输出流
     * @return 拷贝的文件大小
     */
    public static long copy(final FileInputStream in, final FileOutputStream out, final boolean autoClose) {
        if (in == null) {
            return -1;
        }
        if (out == null) {
            return -1;
        }

        FileChannel inFileChannel = in.getChannel();
        FileChannel outFileChannel = out.getChannel();

        try {
            return inFileChannel.transferTo(0, inFileChannel.size(), outFileChannel);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(outFileChannel);
                closeQuietly(out);
                closeQuietly(inFileChannel);
                closeQuietly(in);
            }
        }
    }

    //endregion


    //region ================ Reader to Writer ================

    /**
     * 将字符输入流转换为字符输出流
     * 流复制完成后会自动关闭输入流和输出流
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 返回字符个数
     */
    public static long copy(final Reader reader, final Writer writer) {
        return copy(reader, writer, true);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 返回字符个数
     */
    public static long copy(final Reader reader, final Writer writer, final boolean autoClose) {
        return copy(reader, writer, DEFAULT_LARGE_BUFFER_SIZE, autoClose);
    }

    /**
     * 将字符输入流转换为字符输出流
     * 流复制完成后会自动关闭输入流和输出流
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 返回字符个数
     */
    public static long copy(final Reader reader, final Writer writer, final int bufferSize, final boolean autoClose) {
        return copy(reader, writer, new char[bufferSize], autoClose);
    }

    /**
     * 将字符输入流转换为字符输出流，如果字符输入流的大小超过2GB，则返回-1
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 返回字符个数
     */
    public static long copy(final Reader reader, final Writer writer, final char[] buffer) {
        return copy(reader, writer, buffer, false);
    }

    /**
     * 字符流的拷贝，支持大字符流(超过2GB)拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 返回字符个数
     */
    public static long copy(final Reader reader, final Writer writer, final char[] buffer, final boolean autoClose) {
        long charTotal = 0;
        int size;
        try {
            while (EOF != (size = reader.read(buffer))) {
                writer.write(buffer, 0, size);
                charTotal += size;
            }
            writer.flush();
            return charTotal;
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            if (autoClose) {
                closeQuietly(writer);
                closeQuietly(reader);
            }
        }
    }

    //endregion

    // region Reader

    /**
     * 将输入流转换为{@linkplain  BufferedReader}
     *
     * @param in       输入流
     * @param encoding 编码，可以为空
     * @return {@linkplain  BufferedReader}
     */
    public static BufferedReader getBufferedReader(InputStream in, Charset encoding) {
        if (in == null) {
            return null;
        }
        InputStreamReader isr = (encoding == null) ? (new InputStreamReader(in)) : (new InputStreamReader(in, encoding));
        return new BufferedReader(isr);
    }

    /**
     * 将输入流转换为{@linkplain  BufferedReader}
     *
     * @param in       输入流
     * @param encoding 编码，可以为空
     * @return {@linkplain  BufferedReader}
     */
    public static BufferedReader getBufferedReader(InputStream in, String encoding) {
        Charset cs = CharsetUtil.forName(encoding, null);
        return getBufferedReader(in, cs);
    }

    /**
     * 将输入流转换为{@linkplain  BufferedReader}
     *
     * @param in 输入流
     * @return {@linkplain  BufferedReader}
     */
    public static BufferedReader getUtf8BufferedReader(InputStream in) {
        return getBufferedReader(in, CharsetUtil.UTF_8);
    }

    /**
     * 将输入流转换为{@linkplain  BufferedReader}
     *
     * @param in 输入流
     * @return {@linkplain  BufferedReader}
     */
    public static BufferedReader getGbkBufferedReader(InputStream in) {
        return getBufferedReader(in, CharsetUtil.GBK_VALUE);
    }

    // endregion


}
