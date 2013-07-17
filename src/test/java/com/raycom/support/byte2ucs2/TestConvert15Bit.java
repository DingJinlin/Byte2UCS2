package com.raycom.support.byte2ucs2;

import com.raycom.support.HexConvert;

/**
 * User: server
 * Date: 13-7-16
 * Time: 下午6:23
 */
public class TestConvert15Bit {
    public static byte[] inData = new byte[] {
            (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88,
            (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88,
            (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88,
            (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11,
            (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11,
            (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11,
            (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11,
    };

    public static void main(String[] args) {
        byte[] outData = new Convert15Bit().coding(TestUCS2Convert.inData);
        System.out.println("Coding....");
        System.out.println("In data:  " + HexConvert.byte2String(TestUCS2Convert.inData));
        System.out.println("Out data: " + HexConvert.byte2String(outData));

        inData = new Convert15Bit().decoding(outData);
        System.out.println("Decoding....");
        System.out.println("In data:  " + HexConvert.byte2String(outData));
        System.out.println("Out data: " + HexConvert.byte2String(inData));
    }
}
