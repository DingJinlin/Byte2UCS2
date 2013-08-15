package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 */
public class UCS22Bytes extends UCS2Convert {
    public UCS22Bytes(byte[] data) throws Exception {
        super(data);
    }

    @Override
    public byte[] convert() {
        ByteBuffer inBuf = ByteBuffer.wrap(data);
        inBuf.order(ByteOrder.BIG_ENDIAN);
        inBuf.clear();

        ByteBuffer dataBuf = ByteBuffer.allocate(data.length);
        dataBuf.order(ByteOrder.BIG_ENDIAN);
        dataBuf.clear();

        while (inBuf.hasRemaining()) {
            short value = inBuf.getShort();
            if(value == ESCAPE_CHARACTER ) {
               value = (short)(inBuf.getShort() ^ XOR_CHARACTER);
            }

            dataBuf.putShort(value);
        }

        dataBuf.flip();
        byte[] outData = new byte[dataBuf.limit()];
        dataBuf.get(outData);

        return outData;
    }
}
