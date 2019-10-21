package com.github.unclecatmyself.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class UniqueIpUtils {

    /***
     * 获取外网IP
     * @return
     */
    public static String internetIp() {
        try {

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress;
            Enumeration<InetAddress> netAddresses;
            while (networks.hasMoreElements()) {
                netAddresses = networks.nextElement().getInetAddresses();
                while (netAddresses.hasMoreElements()) {
                    inetAddress = netAddresses.nextElement();
                    if (Objects.nonNull(inetAddress)
                            && inetAddress instanceof Inet4Address
                            && !inetAddress.isSiteLocalAddress()
                            && !inetAddress.isLoopbackAddress()
                            && !inetAddress.getHostAddress().contains(":")) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

            return null;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 获取内网IP
     *
     * @return
     */
    public static String intranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取服务启动host
     * @return
     */
    public static String getHost(){
        return internetIp()==null?intranetIp():internetIp();
    }

}
