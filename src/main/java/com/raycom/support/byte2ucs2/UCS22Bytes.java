package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 */
public class UCS22Bytes extends UCS2Convert {
    byte[] data;

    // 实际数据长度
    int dataLen;

    public UCS22Bytes(byte[] data) throws Exception {
        this.data = data;
    }

    /**
     * 还原数据内容
     *
     * @return
     */
    private byte[] revertData() {
        for(Integer groupSeq : groups.keySet()) {
            Set<Byte> positionSet = groups.get(groupSeq);
            for(byte position : positionSet) {
                int dataBufPosition = (groupSeq * GROUP_LEN) + (position * 2);
                dataBuf[dataBufPosition] |= REV_BYTE;
            }
        }

        byte[] revertData = new byte[dataLen];
        System.arraycopy(dataBuf, 0, revertData, 0, revertData.length);

        return revertData;
    }

    /**
     * 获取替换组内容
     *
     * @return
     */
    private Map<Integer, Set<Byte>> revertReplacementGroupBuf(byte data[]) {
        Map<Integer, Set<Byte>> groups = new HashMap<Integer, Set<Byte>>();

        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.clear();

        //获取总组数
        int groupNum = buffer.get();
        for(int i = 0; i != groupNum; i++) {
            int groupSeq = buffer.get();

            // 获取替换数
            byte[] convertNumBuf = new byte[1];
            buffer.get(convertNumBuf);
            buffer.position(buffer.position() - 1);
            Convert4Bit convert4Bit = new Convert4Bit(convertNumBuf);
            int convertNum = convert4Bit.decoding();

            // 获取替换位置
            byte[] convertPositionBuf = new byte[(convertNum + 2) / 2];
            buffer.get(convertPositionBuf);
            convert4Bit = new Convert4Bit(convertPositionBuf);
            convert4Bit.decoding(); // 首个高4位用于记录替换数,前面已获取过

            Set<Byte> positionSet = new HashSet<Byte>();
            for(int j = 0; j != convertNum; j++) {
                byte position = convert4Bit.decoding();
                positionSet.add(position);
            }

            if(!positionSet.isEmpty()) {
                groups.put(groupSeq, positionSet);
            }
        }

         return groups;
    }

    @Override
    public byte[] convert() {
        ByteBuffer buf = ByteBuffer.wrap(data);
        buf.clear();
        buf.order(ByteOrder.BIG_ENDIAN);

        // 获取实际数据长度
        dataLen = buf.getShort();

        int dataBufLen = (dataLen + 1) / 2 * 2;
        dataBuf = new byte[dataBufLen];
        int replacementAreaLen = data.length - 2 - dataBufLen;
        byte[] replacementArea = new byte[replacementAreaLen];

        buf.get(replacementArea);
        buf.get(dataBuf);

        // 获取替换区内容
        groups = revertReplacementGroupBuf(replacementArea);

        // 还原数据区
        return revertData();
    }
}
