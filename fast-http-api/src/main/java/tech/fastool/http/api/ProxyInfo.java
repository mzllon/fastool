package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Http代理信息
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ProxyInfo {

    private final String hostOrIp;

    private final int port;

    private final ProxyType type;

    private String username;

    private String password;

    ProxyInfo(String hostOrIp, int port, ProxyType type) {
        this.hostOrIp = hostOrIp;
        this.port = port;
        this.type = type;
    }

    ProxyInfo(ProxyType type, String hostOrIp, int port, String username, String password) {
        this.hostOrIp = hostOrIp;
        this.port = port;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public Proxy.Type toJdkType() {
        switch (type) {
            case HTTP:
                return Proxy.Type.HTTP;
            case SOCKS:
                return Proxy.Type.SOCKS;
            case DIRECT:
                return Proxy.Type.DIRECT;
            default:
                throw new IllegalArgumentException("Invalid ProxyType:" + type);
        }
    }

    public Proxy toJdkProxy() {
        return new Proxy(toJdkType(), new InetSocketAddress(hostOrIp, port));
    }

    public String hostOrIp() {
        return hostOrIp;
    }

    public int port() {
        return port;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public ProxyType type() {
        return type;
    }

    public static ProxyInfo direct() {
        return new ProxyInfo(ProxyType.NONE, null, 0, null, null);
    }

    public static ProxyInfo http(@NotNull String hostOrIp, int port) {
        return new ProxyInfo(ProxyType.HTTP, hostOrIp, port, null, null);
    }

    public static ProxyInfo http(@NotNull String hostOrIp, int port, String username, String pwd) {
        return new ProxyInfo(ProxyType.HTTP, hostOrIp, port, username, pwd);
    }

    public static ProxyInfo socks(@NotNull String hostOrIp, int port) {
        return new ProxyInfo(ProxyType.SOCKS, hostOrIp, port, null, null);
    }

    public static ProxyInfo socks(@NotNull String hostOrIp, int port, String username, String pwd) {
        return new ProxyInfo(ProxyType.SOCKS, hostOrIp, port, username, pwd);
    }

    /**
     * Represents the proxy type.
     */
    public enum ProxyType {

        /**
         * No Proxy
         */
        NONE,
        /**
         * Represents a direct connection, or the absence of a proxy.
         */
        DIRECT,
        /**
         * Represents proxy for high level protocols such as HTTP or FTP.
         */
        HTTP,
        /**
         * Represents a SOCKS (V4 or V5) proxy.
         */
        SOCKS,

    }

}
