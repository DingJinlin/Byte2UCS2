package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: server
 * Date: 13-7-16
 * Time: 上午9:52
 */
public class Convert15Bit {
    short convertData;
    byte[] data;

    /**
     * @param data
     */
    public Convert15Bit(byte[] data) {
        if(data.length % 2 != 0) {
            this.data = new byte[data.length + 1];
            this.data[data.length] = 0x00;
            System.arraycopy(data, 0, this.data, 0, data.length);
        }
        this.data = data;
        convertData = 0x00;
    }

    /**
     * 编码
     */
    public byte[] coding() {
        int bufLen = data.length + (data.length + 29 ) / 30 * 2 ;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        ByteBuffer inDataBuf = ByteBuffer.wrap(data);
        inDataBuf.clear();
        inDataBuf.order(ByteOrder.BIG_ENDIAN);

        for(int i = 0; i != data.length / 2; i++) {
            int right = (i % 15) + 1;
            int left = 15 - right;

            short value = (short)((data[i] & 0xFFFF) >> right);
            value |= convertData;
            buf.putShort(value);

            convertData = (short)((data[i]<< left) & 0x7FFF);

            if(i % 15 == 14 || i == data.length / 2 - 1) {
                buf.putShort(convertData);
                convertData = 0x0000;
            }
        }


        return buf.array();
    }

    /**
     * 获取还原数据
     * @return
     */
    public byte[] decoding() {
        int bufLen = data.length - (data.length + 29 ) / 30 * 2 ;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        ByteBuffer inDataBuf = ByteBuffer.wrap(data);
        for(int i = 0; i != data.length / 2; i++) {
            short currentData = inDataBuf.getShort();
            convertData = inDataBuf.getShort();
            inDataBuf.position(inDataBuf.position() - 2);

            int left = (i % 15) + 1;
            int right = 15 - left;

            short value = (byte)((currentData & 0xFFFF) << left);
            convertData = (byte)((convertData & 0xFFFF) >> right);
            value |= convertData;
            buf.putShort(value);
        }

        /*for(int i = 0; i != data.length / 2; i++) {
            short currentData = inDataBuf.getShort();
            if(i % 15 != 14 && i != data.length / 2 - 1) {
                convertData = inDataBuf.getShort();
                inDataBuf.position(inDataBuf.position() - 2);
            } else {
                continue;
            }

            int left = (i % 16) + 1;
            int right = 15 - left;

            short value = (byte)((currentData & 0xFFFF) << left);
            convertData = (byte)((convertData & 0xFFFF) >> right);
            value |= convertData;
            buf.putShort(value);
        }*/

        return buf.array();
    }
}
