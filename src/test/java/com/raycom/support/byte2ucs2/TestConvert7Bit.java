package com.raycom.support.byte2ucs2;

import com.raycom.support.HexConvert;

/**
 * User: server
 * Date: 13-7-16
 * Time: 下午6:23
 */
public class TestConvert7Bit {
    public static byte[] inData = new byte[] {
            (byte)0x11, (byte)0x11, (byte)0x11, (byte)0x11, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x11, (byte)0x11, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF,
    };

    public static void main(String[] args) {


        byte[] outData = new Convert7Bit().coding(inData);
        System.out.println("Coding....");
        System.out.println("In data:  " + HexConvert.byte2String(inData));
        System.out.println("Out data: " + HexConvert.byte2String(outData));

        inData = new Convert7Bit().decoding(outData);
        System.out.println("Decoding....");
        System.out.println("In data:  " + HexConvert.byte2String(outData));
        System.out.println("Out data: " + HexConvert.byte2String(inData));
    }
}
