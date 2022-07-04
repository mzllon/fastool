package tech.fastool.core.digest;

import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.exceptions.NoSuchAlgorithmRuntimeException;
import tech.fastool.core.io.IOes;
import tech.fastool.core.lang.Charsets;
import tech.fastool.core.lang.Files;
import tech.fastool.core.lang.Strings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/**
 * {@linkplain MessageDigest}创建
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class MessageDigestCreator {

    /**
     * {@linkplain MessageDigest}实例
     */
    private final MessageDigest md;

    public MessageDigestCreator(MessageDigestAlgorithm algorithm) {
        this(algorithm, null);
    }

    public MessageDigestCreator(MessageDigestAlgorithm algorithm, Provider provider) {
        this(algorithm.getValue(), provider);
    }

    public MessageDigestCreator(String algorithm) {
        this(algorithm, null);
    }

    public MessageDigestCreator(String algorithm, Provider provider) {
        try {
            if (provider == null) {
                this.md = MessageDigest.getInstance(algorithm);
            } else {
                this.md = MessageDigest.getInstance(algorithm, provider);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        }
    }

    public byte[] digest(byte[] data) {
        return this.md.digest(data);
    }

    public byte[] digest(String data) {
        return digest(data, Charsets.UTF_8);
    }

    public byte[] digest(String data, Charset encoding) {
        return digest(Strings.bytes(data, encoding));
    }

    public byte[] digest(File input) {
        InputStream in = null;
        try {
            in = Files.getBufferedInputStream(input);
            return digest(in);
        } finally {
            IOes.closeQuietly(in);
        }
    }

    /**
     * 生成摘要，使用默认缓存大小，见 {@link IOes#DEFAULT_BUFFER_SIZE}
     *
     * @param data {@link InputStream} 数据流
     * @return 摘要bytes
     */
    public byte[] digest(InputStream data) {
        return digest(data, IOes.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 生成摘要
     *
     * @param data         {@link InputStream} 数据流
     * @param bufferLength 缓存长度，不足1使用 {@link IOes#DEFAULT_BUFFER_SIZE} 做为默认值
     * @return 摘要bytes
     * @throws IoRuntimeException IO异常
     */
    public byte[] digest(InputStream data, int bufferLength) throws IoRuntimeException {
        if (bufferLength < 1) {
            bufferLength = IOes.DEFAULT_BUFFER_SIZE;
        }
        byte[] buffer = new byte[bufferLength];
        int read;
        try {
            while ((read = data.read(buffer, 0, bufferLength)) > -1) {
                this.md.update(buffer, 0, read);
            }
            return this.md.digest();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
