package tech.fastool.core.io;

import org.junit.jupiter.api.Test;
import tech.fastool.core.lang.CharsetUtil;

import java.io.*;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain  IoUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-01
 */
public class IoUtilTest {

    @Test
    public void readBytes() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("bytes.txt");
        byte[] data = IoUtil.readBytes(in);
        assertNotNull(data);
        assertArrayEquals("bytes.txt".getBytes(), data);
    }

    @Test
    public void testReadBytes() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("bytes.txt");
        byte[] data = IoUtil.readBytes(in, 5);
        assertNotNull(data);
        assertArrayEquals("bytes".getBytes(), data);
    }

    @Test
    public void testReadBytes1() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        BufferedReader bufferedReader = IoUtil.getUtf8BufferedReader(in);
        assertNotNull(bufferedReader);
        String line = bufferedReader.readLine();
        assertNotNull(line);
        assertEquals("年龄越大，月学会了顺其自然。", line);
        bufferedReader.close();
    }

    @Test
    public void testReadBytes2() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        BufferedReader bufferedReader = IoUtil.getGbkBufferedReader(in);
        assertNotNull(bufferedReader);
        String line = bufferedReader.readLine();
        assertNotNull(line);
        assertNotEquals("年龄越大，月学会了顺其自然。", line);
        bufferedReader.close();
    }

    @Test
    public void testReadBytes3() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("gbk.txt");
        BufferedReader bufferedReader = IoUtil.getGbkBufferedReader(in);
        assertNotNull(bufferedReader);
        String line = bufferedReader.readLine();
        assertNotNull(line);
        assertEquals("灵笼", line);
        bufferedReader.close();
    }

    @Test
    public void readUtf8() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        String str = IoUtil.readUtf8(in);
        assertNotNull(str);
        assertEquals("年龄越大，月学会了顺其自然。", str);

        in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        str = IoUtil.read(in, CharsetUtil.GBK);
        assertNotEquals("年龄越大，月学会了顺其自然。", str);

    }

    @Test
    public void read() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        BufferedReader br = IoUtil.getUtf8BufferedReader(in);
        String str = IoUtil.read(br);
        assertNotNull(str);
        assertEquals("年龄越大，月学会了顺其自然。", str);
    }

    @Test
    public void copy() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("bytes.txt");
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        long fileSize = IoUtil.copy(in, out, IoUtil.DEFAULT_MIDDLE_BUFFER_SIZE);
        assertEquals(9, fileSize);
        byte[] data = out.toByteArray();
        assertNotNull(data);
        assertArrayEquals("bytes.txt".getBytes(), data);
    }

    @Test
    public void testCopy() {
        byte[] buffer = new byte[IoUtil.DEFAULT_LARGE_BUFFER_SIZE];
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("bytes.txt");
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        long fileSize = IoUtil.copy(in, out, buffer);
        assertEquals(9, fileSize);
        byte[] data = out.toByteArray();
        assertNotNull(data);
        assertArrayEquals("bytes.txt".getBytes(), data);
    }

    @Test
    public void testCopy1() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("bytes.txt");
        FileInputStream fis = new FileInputStream(new File(resource.toURI()));
        File tempFile = File.createTempFile("bytes", ".txt");
        FileOutputStream fos = new FileOutputStream(tempFile);
        long fileSize = IoUtil.copy(fis, fos);
        assertEquals(9, fileSize);
        assertEquals(9, tempFile.length());
        tempFile.delete();
    }

    @Test
    public void testCopy2() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("utf8.txt");
        BufferedReader br = IoUtil.getUtf8BufferedReader(in);

        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);

        long charCount = IoUtil.copy(br, osw);
         assertEquals(14, charCount);
        assertArrayEquals("年龄越大，月学会了顺其自然。".getBytes(), out.toByteArray());
    }

    @Test
    public void testCopy3() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("gbk.txt");
        BufferedReader br = IoUtil.getGbkBufferedReader(in);

        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);

        char[] buffer = new char[IoUtil.DEFAULT_BUFFER_SIZE];

        long fileSize = IoUtil.copy(br, osw, buffer);

        assertEquals(2, fileSize);
        String res = out.toString();
        assertNotNull(res);
        assertEquals("灵笼", res);

    }

    @Test
    public void testCopy111() {

    }

    @Test
    void testCopy13() {
    }

}