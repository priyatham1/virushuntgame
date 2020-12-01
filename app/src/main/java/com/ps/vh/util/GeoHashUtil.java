package com.ps.vh.util;


/**
 * Utilities for encoding and decoding geohashes. Based on
 * http://en.wikipedia.org/wiki/Geohash.
 */
// LUCENE MONITOR: monitor against spatial package
// replaced with native DECODE_MAP
public class GeoHashUtil {

    private static final char[] BASE_32 = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final int PRECISION = 12;
    private static final int[] BITS = {16, 8, 4, 2, 1};

    private GeoHashUtil() {
    }

    public static String encode(double latitude, double longitude) {
        return encode(latitude, longitude, PRECISION);
    }

    /**
     * Encodes the given latitude and longitude into a geohash
     *
     * @param latitude  Latitude to encode
     * @param longitude Longitude to encode
     * @return Geohash encoding of the longitude and latitude
     */
    public static String encode(double latitude, double longitude, int precision) {
//        double[] latInterval = {-90.0, 90.0};
//        double[] lngInterval = {-180.0, 180.0};
        double latInterval0 = -90.0;
        double latInterval1 = 90.0;
        double lngInterval0 = -180.0;
        double lngInterval1 = 180.0;

        final StringBuilder geohash = new StringBuilder();
        boolean isEven = true;

        int bit = 0;
        int ch = 0;

        while (geohash.length() < precision) {
            double mid = 0.0;
            if (isEven) {
//                mid = (lngInterval[0] + lngInterval[1]) / 2D;
                mid = (lngInterval0 + lngInterval1) / 2D;
                if (longitude > mid) {
                    ch |= BITS[bit];
//                    lngInterval[0] = mid;
                    lngInterval0 = mid;
                } else {
//                    lngInterval[1] = mid;
                    lngInterval1 = mid;
                }
            } else {
//                mid = (latInterval[0] + latInterval[1]) / 2D;
                mid = (latInterval0 + latInterval1) / 2D;
                if (latitude > mid) {
                    ch |= BITS[bit];
//                    latInterval[0] = mid;
                    latInterval0 = mid;
                } else {
//                    latInterval[1] = mid;
                    latInterval1 = mid;
                }
            }

            isEven = !isEven;

            if (bit < 4) {
                bit++;
            } else {
                geohash.append(BASE_32[ch]);
                bit = 0;
                ch = 0;
            }
        }

        return geohash.toString();
    }

    private static final char encode(int x, int y) {
        return BASE_32[((x & 1) + ((y & 1) * 2) + ((x & 2) * 2) + ((y & 2) * 4) + ((x & 4) * 4)) % 32];
    }


    //========== long-based encodings for geohashes ========================================


    /**
     * Encodes latitude and longitude information into a single long with variable precision.
     * Up to 12 levels of precision are supported which should offer sub-metre resolution.
     *
     * @param latitude
     * @param longitude
     * @param precision The required precision between 1 and 12
     * @return A single long where 4 bits are used for holding the precision and the remaining
     * 60 bits are reserved for 5 bit cell identifiers giving up to 12 layers.
     */
    public static long encodeAsLong(double latitude, double longitude, int precision) {
        if((precision>12)||(precision<1))
        {
            throw new IllegalArgumentException("Illegal precision length of "+precision+
                    ". Long-based geohashes only support precisions between 1 and 12");
        }
        double latInterval0 = -90.0;
        double latInterval1 = 90.0;
        double lngInterval0 = -180.0;
        double lngInterval1 = 180.0;

        long geohash = 0l;
        boolean isEven = true;

        int bit = 0;
        int ch = 0;

        int geohashLength=0;
        while (geohashLength < precision) {
            double mid = 0.0;
            if (isEven) {
                mid = (lngInterval0 + lngInterval1) / 2D;
                if (longitude > mid) {
                    ch |= BITS[bit];
                    lngInterval0 = mid;
                } else {
                    lngInterval1 = mid;
                }
            } else {
                mid = (latInterval0 + latInterval1) / 2D;
                if (latitude > mid) {
                    ch |= BITS[bit];
                    latInterval0 = mid;
                } else {
                    latInterval1 = mid;
                }
            }

            isEven = !isEven;

            if (bit < 4) {
                bit++;
            } else {
                geohashLength++;
                geohash|=ch;
                if(geohashLength<precision){
                    geohash<<=5;
                }
                bit = 0;
                ch = 0;
            }
        }
        geohash<<=4;
        geohash|=precision;
        return geohash;
    }

    /**
     * Formats a geohash held as a long as a more conventional
     * String-based geohash
     * @param geohashAsLong a geohash encoded as a long
     * @return A traditional base32-based String representation of a geohash
     */
    public static String toString(long geohashAsLong)
    {
        int precision = (int) (geohashAsLong&15);
        char[] chars = new char[precision];
        geohashAsLong >>= 4;
        for (int i = precision - 1; i >= 0 ; i--) {
            chars[i] =  BASE_32[(int) (geohashAsLong & 31)];
            geohashAsLong >>= 5;
        }
        return new String(chars);
    }


}
