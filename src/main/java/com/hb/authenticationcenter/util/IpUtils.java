package com.hb.authenticationcenter.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Ip Utils:
 * resolve the ip information in the request.
 * @author admin
 * @version 1.0
 * @description IP Utils
 * @date 2023/1/8
 */
public class IpUtils {

    public static String LOOPBACK_ADDRESS_V4 = "127.0.0.1";

    public static String LOOPBACK_ADDRESS_ALL_V6 = "::1";

    public static String UNKNOWN = "unknown";

    /**
     * get ip from http request.
     * @param request HttpServletRequest
     * @return current request ip info
     */
    public static String resolveRequestIp(HttpServletRequest request) {
        Assert.notNull(request, "request can not null.");
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0) {
            ipAddress = request.getRemoteAddr();
            if(LOOPBACK_ADDRESS_V4.equals(ipAddress)|| LOOPBACK_ADDRESS_ALL_V6.equals(ipAddress)){
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    ipAddress = UNKNOWN;
                }
            }
        }
        if(ipAddress != null && ipAddress.length() > 15){
            if(ipAddress.indexOf(",") > 0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
