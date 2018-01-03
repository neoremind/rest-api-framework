package com.hulu.ap.apiframework.util;


import org.apache.commons.lang3.StringUtils;

import java.net.*;
import java.util.Collections;

public final class AddressUtils {

    private AddressUtils() {
    }

    public static String getLocalIPv4Address() throws SocketException {
        String address = null;
        int addressIndex = Integer.MAX_VALUE;

        for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            if (networkInterface.isLoopback() || networkInterface.isVirtual()) {
                continue;
            }

            for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                if (inetAddress.isLoopbackAddress() || !(inetAddress instanceof Inet4Address) ||
                        inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress()) {
                    continue;
                }

                if (networkInterface.getIndex() <= addressIndex) {
                    address = inetAddress.getHostAddress();
                    addressIndex = networkInterface.getIndex();
                }
            }
        }

        return address;
    }

    public static String getShortHostname() throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        int firstDot = hostname.indexOf('.');
        if (firstDot > 0) {
            hostname = hostname.substring(0, firstDot);
        }
        return hostname;
    }

    private static String hostname = null;

    public static String getShortHostnameDefaultUnknown() {
        if (hostname != null) {
            return hostname;
        }

        String shortHostname = "UNKNOWN";

        try {
            shortHostname = StringUtils.split(InetAddress.getLocalHost().getHostName(), ".")[0];
        } catch (Exception e) {
            // ignore this exception
        }

        if (shortHostname != null) {
            hostname = shortHostname;
        }

        return shortHostname;
    }

    /*
     * From http://stackoverflow.com/questions/4256438/calculate-whether-an-ip-is-in-a-specified-range-in-java
     *
     * An IPv4 address will fit into an 32bit int, but for easy comparison, we use a 64-bit int instead. Unfortunately,
     * java doesn't have unsigned ints, so we would need to account for some IPs mapping to negative integers when
     * comparing them.
     */
    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        if (octets.length != 4) {
            // We would overflow the result if we try to pack an IPv6 address into a long, soo.. throw an Exception.
            throw new IllegalArgumentException("You must specify an IPv4 address");
        }

        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
}

