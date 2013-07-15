package com.raycom.support.byte2ucs2;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
public class Byte2UCS2 {
    // 填充字节
    final byte FILL_BYTE = (byte)0xFF;

    // 替换时对待替换的字节进行与运算
    final byte REP_BYTE = (byte)0x7F;

    // 还原时对待替换的字节进行或运算
    final byte REV_BYTE = (byte)0xD0;


    final int GROUP_LEN = 32;
    final int GROUP_WORD_LEN = GROUP_LEN / 2;

    // 一包所包含的组数
    final int PACK_GROUP_COUNT = 16;


    byte[] byteData = new byte[0];
    byte[] ucs2Data = new byte[0];

    // 替换区Map
    Map<Integer, Set<Byte>> replacementArea;

    // 数据内容缓存
    byte[] dataBuf;
    /**
     * 替换数据内容
     * @return
     */
    public void replaceData() {
        // 数据内容缓存长度
        int dataBufLen = (byteData.length + 1) / 2 * 2;

        // 数据内容缓存
        dataBuf = new byte[dataBufLen];
        dataBuf[dataBufLen - 1] = FILL_BYTE;

        System.arraycopy(byteData, 0, dataBuf, 0, byteData.length);

        // 计算组数
        int groupCount = (dataBufLen + GROUP_LEN - 1) / GROUP_LEN;

        // 计算包数
        int packCount = (groupCount + PACK_GROUP_COUNT - 1) / PACK_GROUP_COUNT;

        // 将内容进行替换
        for (int groupSeq = 0; groupSeq != groupCount; groupSeq++) {
            Set<Byte> positionSet = new HashSet<Byte>();
            for (int position = 0; position != GROUP_WORD_LEN; position++) {
                int dataBufPosition = (groupSeq * GROUP_LEN) + (position * 2);
                if (dataBuf[dataBufPosition] > 0xD7 && dataBuf[dataBufPosition] < 0xE0) {
                    dataBuf[dataBufPosition] &= REP_BYTE;

                    // 记录替换位置
                    positionSet.add((byte)position);
                }
            }

            if(positionSet.size() != 0) {
                // 记录替换分组序号
                replacementArea.put(groupSeq, positionSet);
            }
        }
    }

    /**
     * 组包替换区内容
     * @return
     */
    public byte[] createReplacementAreaBuf() {
        byte head = 0x00;

        // 替换区缓存
        ByteBuffer replacementAreaBuf = ByteBuffer.allocate(256);
        replacementAreaBuf.clear();

        // 小于16组的最后一组序号
        int lastGroupSeq = 0;
        Set<Integer> groupSeqSet = replacementArea.keySet();
        boolean firstGroup = true;
        for (int groupSeq : groupSeqSet) {
            // 记录总组数
            putReplacementData(replacementAreaBuf, (byte)groupSeqSet.size());

            // 记录当前组序号
            putReplacementData(replacementAreaBuf, (byte)groupSeq);

            Set<Byte> positionSet =  replacementArea.get(groupSeq);
            // 记录替换的位数
            putReplacementData(replacementAreaBuf, (byte)positionSet.size());

            // 记录替换位置
            for(byte position : positionSet) {
                putReplacementData(replacementAreaBuf, position);
            }
        }

        int len = replacementAreaBuf.position();
        byte[] replacementArea = new byte[len];
        replacementAreaBuf.flip();
        replacementAreaBuf.get(replacementArea);

        return replacementArea;
    }

    // 现需要填充是高位还是低位
    boolean isReplacementAreaBufPointHighBit = true;
    byte putData;
    private void putReplacementData(ByteBuffer buf, byte data) {
        if(isReplacementAreaBufPointHighBit) {
            putData = (byte)0x00;
            putData &= (data << 4);
        } else {
            putData &= (data & 0x0F);
            buf.put(putData);
        }
    }
}
