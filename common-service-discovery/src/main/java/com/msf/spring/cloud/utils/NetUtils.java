package com.msf.spring.cloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author wencheng
 * @since 2018/6/5
 */
public class NetUtils {
    private static Logger logger = LoggerFactory.getLogger(NetUtils.class);

    private static volatile InetAddress LOCAL_ADDRESS = null;


    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static final String ANYHOST = "0.0.0.0";

    private static final String LOCALHOST = "127.0.0.1";

    /**
     * 遍历本地网卡，返回内网IP。
     *
     * @return 本地网卡IP
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    /**
     * 获取内网IP
     *
     * @return
     */
    public static String getLocalHost() {
        return getLocalAddress().getHostAddress();
    }

    /**
     * 是否有效的IP地址，有效指内网Ip或者外网IP
     *
     * @param address
     * @return
     */
    public static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && IP_PATTERN.matcher(name).matches());
    }

    /**
     * 是否有效的内网IP
     *
     * @param address
     * @return
     */
    public static boolean isLocalAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && isValidAddress(address));
    }


    private static InetAddress getLocalAddress0() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            if (isLocalAddress(address)) {
                return address;
            }
        } catch (Throwable e) {
            logger.warn("Failed to retriving ip address", e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface network = interfaces.nextElement();
                Enumeration<InetAddress> addresses = network.getInetAddresses();
                if (addresses == null) {
                    continue;
                }
                while (addresses.hasMoreElements()) {
                    address = addresses.nextElement();
                    if (isLocalAddress(address)) {
                        return address;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Could not get local host ip address!");
    }

}
