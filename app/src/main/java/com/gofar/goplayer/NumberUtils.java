package com.gofar.goplayer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author lcf
 * @date 8/8/2018 下午 2:45
 * @since 1.0
 */
public class NumberUtils {

    public static String formatByte(long b) {
        if (b <= 0) {
            return "0M";
        }
        double kByte = b / 2014;
        double mByte = kByte / 1024;
        if (mByte < 1) {
            BigDecimal result = new BigDecimal(Double.toString(kByte));
            return result.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gByte = mByte / 1024;
        if (gByte < 1) {
            BigDecimal result = new BigDecimal(Double.toString(mByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double tByte = gByte / 1024;
        if (tByte < 1) {
            BigDecimal result = new BigDecimal(Double.toString(gByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result = new BigDecimal(tByte);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    public static String formatDate(long m) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return format.format(new Date(m));
    }
}
