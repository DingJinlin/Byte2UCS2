package com.raycom.support;

/**
 * <p>byte[]与Hex String转换支持类</p>
 * @author Ding Jinlin
 */
public class HexConvert {
    private static char[] array = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        byte[] src = new byte[]{0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, 0x05, 0x06, 0x07}; 
        
        byte[] bytes = src;
        bytes = src;
        
        System.out.println("Src = " + new String(src));
        
        long start = System.nanoTime();
        String string0 = byte2String(bytes);
        long end = System.nanoTime();
        System.out.println("result = " + string0);
        System.out.println("time1 = " + (end-start));
        
        long start3 = System.nanoTime();
        bytes = string2Byte(string0);
        long end3 = System.nanoTime();
        System.out.println("result = " + new String(bytes));
        System.out.println("time1 = " + (end3-start3));
    }

    /**
     * 将byte[]转成以十六进制表示的字符串
     * <p>byte2String(byte[] bytes)</p>
     * @param bytes
     * @return
     */
    public static String byte2String(byte[] bytes) {
        int len = bytes.length;
        char[] hexString = new char[len * 2];
        int pos;
        int charPos;

        for(int i = 0; i != len; i++) {
            charPos = i * 2;
            
            pos = (bytes[i] & 0xF0) >> 4;
            hexString[charPos] = array[pos];
            
            pos = bytes[i] & 0x0F;
            hexString[charPos + 1] = array[pos];
        }
        
        return String.valueOf(hexString);
    }
    
    /**
     * 将以十六进制表示的字符串转换成byte[]<br>
     * <p>string2Byte(String hexString)</p>
     * @param hexString 以十六进制表示的字符串
     * @return
     */
    public static byte[] string2Byte(String hexString) {    
        char[] chars = hexString.toCharArray();
        int len = chars.length >> 1;
        byte[] bytes = new byte[len];
        for(int i = 0; i != len; i++) {
            int position = i << 1;
            int high = 0;
            int low = 0;
            
            if(chars[position]<'A') {
                high = chars[position]-0x30;
            } else {
                high = chars[position]-0x37;
            }
            if(chars[position+1]<'A') {
                low = chars[position+1]-0x30;
            } else {
                low = chars[position+1]-0x37;
            }
            high = high << 4;
            
            bytes[i] = (byte) (high + low);
        }
        
        return bytes;
    }

}
