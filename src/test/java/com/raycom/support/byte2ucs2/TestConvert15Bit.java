package com.raycom.support.byte2ucs2;

import com.raycom.support.HexConvert;

/**
 * User: server
 * Date: 13-7-16
 * Time: 下午6:23
 */
public class TestConvert15Bit {
    public static void main(String[] args) {
        byte[] inData = new byte[] {
                (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
                (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
        };

        byte[] outData = new Convert15Bit(inData).coding();
        System.out.println("Coding....");
        System.out.println("In data: " + HexConvert.byte2String(inData));
        System.out.println("Out data: " + HexConvert.byte2String(outData));

        inData = new Convert15Bit(outData).decoding();
        System.out.println("Decoding....");
        System.out.println("In data: " + HexConvert.byte2String(outData));
        System.out.println("Out data: " + HexConvert.byte2String(inData));
    }
}
