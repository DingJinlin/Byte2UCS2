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
    short currentData;

    public Convert15Bit() {
        convertData = 0x00;
    }

    /**
     * 编码
     */
    public byte[] coding(byte[] data) {
        byte[] inData;
        // 处理奇数字节
        if(data.length % 2 != 0) {
            inData = new byte[data.length + 1];
            inData[inData.length -1] = 0x00;
            System.arraycopy(data, 0, inData, 0, data.length);
        } else {
            inData = data;
        }

        int bufLen = inData.length + (inData.length + 29 ) / 30 * 2 ;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        ByteBuffer inDataBuf = ByteBuffer.wrap(inData);
        inDataBuf.clear();
        inDataBuf.order(ByteOrder.BIG_ENDIAN);

        for(int i = 0; i != inData.length / 2; i++) {
            short convertBit = BitTable.getShortBit(14 - (i % 15));
            currentData = inDataBuf.getShort();
            short value = (short)(currentData & convertBit);
            value |= convertData;
            buf.putShort(value);

            convertData = (short)(currentData & (~convertBit));
            convertData = (short)((convertData & 0xFFFF) >> 1);

            if(i % 15 == 14 || i == inData.length / 2 - 1) {
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
    public byte[] decoding(byte[] data) {
        int bufLen = data.length - (data.length + 31) / 32 * 2;
        ByteBuffer buf = ByteBuffer.allocate(bufLen);

        ByteBuffer inDataBuf = ByteBuffer.wrap(data);
        for(int i = 0; i != data.length / 2; i++) {
            if(i % 16 == 15 || i == data.length / 2 - 1) {
                currentData = inDataBuf.getShort();
                continue;
            } else {
                currentData = inDataBuf.getShort();
                convertData = inDataBuf.getShort();
                inDataBuf.position(inDataBuf.position() - 2);

                short convertBit = BitTable.getShortBit(14 - (i % 16));
                currentData &= convertBit;
                convertBit = BitTable.getShortBit(13 - (i % 16));

                convertData = (short)(convertData & (~convertBit));
                convertData = (short)(convertData << 1);

                short value = (short)(currentData |convertData);
                buf.putShort(value);
            }
        }
        return buf.array();
    }
}
