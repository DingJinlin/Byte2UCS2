package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;

/**
 * User: server
 * Date: 13-7-16
 * Time: 上午9:52
 */
public class Convert7Bit {
    byte convertByte;

    public Convert7Bit() {
        convertByte = 0x00;
    }

    /**
     * 填充转换区
     */
    public byte[] coding(byte[] data) {
        int bufLen = data.length + (data.length + 6 ) / 7 ;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        for(int i = 0; i != data.length; i++) {
            int right = (i % 7) + 1;
            int left = 7 - right;

            byte value = (byte)((data[i] & 0xFF) >> right);
            value |= convertByte;
            buf.put(value);

            convertByte = (byte)((data[i]<< left) & 0x7F);

            if(i % 7 == 6 || i == data.length - 1) {
                buf.put(convertByte);
                convertByte = 0x00;
            }
        }


        return buf.array();
    }

    /**
     * 获取还原数据
     * @return
     */
    public byte[] decoding(byte[] data) {
        int bufLen = data.length - (data.length + 7 ) / 8 ;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        for(int i = 0; i != data.length; i++) {
            if((i + 1) % 8 == 0 || i + 1 == data.length) {
                continue;
            } else {
                convertByte = data[i + 1];
                int left = (i % 8) + 1;
                int right = 7 - left;

                byte value = (byte)((data[i] & 0xFF) << left);
                convertByte = (byte)((data[i] & 0xFF) >> right);
                value |= convertByte;
                buf.put(value);
            }
        }

        return buf.array();
    }
}
