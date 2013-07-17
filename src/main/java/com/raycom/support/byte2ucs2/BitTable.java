package com.raycom.support.byte2ucs2;

/**
 * User: server
 * Date: 13-7-17
 * Time: 上午11:22
 */
public class BitTable {
    private static final int[] bitTable = {
        0x00000001, 0x00000003, 0x00000007, 0x0000000F,
        0x0000001F, 0x0000003F, 0x0000007F, 0x000000FF,
        0x000001FF, 0x000003FF, 0x000007FF, 0x00000FFF,
        0x00001FFF, 0x00003FFF, 0x00007FFF, 0x0000FFFF,
        0x0001FFFF, 0x0003FFFF, 0x0007FFFF, 0x000FFFFF,
        0x001FFFFF, 0x003FFFFF, 0x007FFFFF, 0x00FFFFFF,
        0x01FFFFFF, 0x03FFFFFF, 0x07FFFFFF, 0x0FFFFFFF,
        0x1FFFFFFF, 0x3FFFFFFF, 0x7FFFFFFF, 0xFFFFFFFF
    };

    static byte getByteBit(int position) {
        if(position == -1) {
            return 0;
        }
        return (byte)bitTable[position];
    }

    static short getShortBit(int position) {
        if(position == -1) {
            return 0;
        }
        return (short)bitTable[position];
    }

    static int getIntBit(int position) {
        if(position == -1) {
            return 0;
        }
        return bitTable[position];
    }
}
