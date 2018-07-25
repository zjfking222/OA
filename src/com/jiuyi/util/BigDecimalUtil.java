package com.jiuyi.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
	// 相加
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    // 相减
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    // 相乘
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }


     /**    
      *   相除,做除法的时候相对的麻烦一点,涉及的多一些
      *   提供精确的小数位四舍五入处理。    
      *   @param   v1   需要四舍五入的数字
      *   @param   v2   需要四舍五入的数字        
      *   @param   scale   小数点后保留几位    
      *   @return   四舍五入后的结果    
      */     
    public static double round(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("此参数错误");
        }
        BigDecimal one = new BigDecimal(Double.toString(v1));
        BigDecimal two = new BigDecimal(Double.toString(v2));
        return one.divide(two, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
}
