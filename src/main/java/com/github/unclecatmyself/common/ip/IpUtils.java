package com.github.unclecatmyself.common.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class IpUtils {

    /***
     * 获取外网IP
     * @return
     */
    public static String internetIp() {
        try {

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress = null;
            Enumeration<InetAddress> inetAddresses = null;
            while (networks.hasMoreElements()) {
                inetAddresses = networks.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null
                            && inetAddress instanceof Inet4Address
                            && !inetAddress.isSiteLocalAddress()
                            && !inetAddress.isLoopbackAddress()
                            && inetAddress.getHostAddress().indexOf(":") == -1) {
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
