package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;

/**
 * User: server
 * Date: 13-7-16
 * Time: 上午9:52
 */
public class Convert4BitBuffer {
    public static final int BUF_LEN = 1024;

    // 现需要填充是高位还是低位
    boolean isReplacementAreaBufPointHighBit;
    byte convertData;
    ByteBuffer buf;

    /**
     * 转换时使用的构造函数
     */
    public Convert4BitBuffer() {
        isReplacementAreaBufPointHighBit = true;
        ByteBuffer buf = ByteBuffer.allocate(BUF_LEN);
        buf.clear();
    }

    /**
     * 还原时使用的构造函数
     * @param dataBuf
     */
    public Convert4BitBuffer(byte[] dataBuf) {
        isReplacementAreaBufPointHighBit = true;
        buf = ByteBuffer.wrap(dataBuf);
        buf.clear();
    }

    /**
     * 填充转换区
     * @param data
     */
    public void putReplacementData(byte data) {
        if (isReplacementAreaBufPointHighBit) {
            convertData = (byte) 0x00;
            convertData &= (data << 4);
        } else {
            convertData &= (data & 0x0F);
            buf.put(convertData);
        }

        isReplacementAreaBufPointHighBit = !isReplacementAreaBufPointHighBit;
    }

    /**
     * 获取还原数据
     * @return
     */
    public byte getReplacementData() {
        byte data;

        if(isReplacementAreaBufPointHighBit) {
            convertData = buf.get();
            data = (byte)(convertData >> 4);
        } else {
            data = (byte)(convertData & 0x0F);
        }
        isReplacementAreaBufPointHighBit = !isReplacementAreaBufPointHighBit;

        return data;
    }

    /**
     * 获取转换结果
     * @return
     */
    public byte[] getConvertResult() {
        int len = buf.position();
        buf.flip();
        byte[] data = new byte[len];
        buf.get(data);

        return data;
    }

    /**
     * 获取BUF
     * @return
     */
    public ByteBuffer getBuf() {
        return buf;
    }
}
