package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 */
public class Bytes2UCS2 extends UCS2Convert {

    public Bytes2UCS2(byte[] data) throws Exception {
        super(data);
    }

    /**
     * 替换数据内容
     */
    @Override
    public byte[] convert() {
        ByteBuffer inBuf = ByteBuffer.wrap(data);
        inBuf.order(ByteOrder.BIG_ENDIAN);
        inBuf.clear();

        ByteBuffer dataBuf = ByteBuffer.allocate(data.length * 2);
        dataBuf.order(ByteOrder.BIG_ENDIAN);
        dataBuf.clear();

        while (inBuf.hasRemaining()) {
            short value = inBuf.getShort();
            if((value > REPLACE_CHARACTER_START && value < REPLACE_CHARACTER_END) || value == ESCAPE_CHARACTER ) {
                dataBuf.putShort(ESCAPE_CHARACTER);
                value ^= XOR_CHARACTER;
            }
            dataBuf.putShort(value);
        }

        dataBuf.flip();
        byte[] outData = new byte[dataBuf.limit()];
        dataBuf.get(outData);

        return outData;
    }
}
