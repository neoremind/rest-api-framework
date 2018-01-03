package com.neoremind.apiframework;

import java.util.Random;

/**
 * @author xu.zhang
 */
public class RandomPortUtil {

    private static final Random RANDOM = new Random();

    /**
     * Get number in [begin,end]
     *
     * @param begin
     * @param end
     * @return random number
     */
    public static int getPort(int begin, int end) {
        if (end < begin) {
            throw new IllegalArgumentException("end must not smaller than begin");
        }

        int minus = RANDOM.nextInt(end - begin + 1);
        return (begin + minus);
    }

}
