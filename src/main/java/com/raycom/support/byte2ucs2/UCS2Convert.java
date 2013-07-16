package com.raycom.support.byte2ucs2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 */
public abstract class UCS2Convert {
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
    final int PACK_LEN = GROUP_LEN * PACK_GROUP_COUNT;

    // 替换区包Map
    Map<Integer, Map<Integer, Set<Byte>>> packs = new HashMap<Integer, Map<Integer, Set<Byte>>>();

    // 数据内容缓存
    byte[] dataBuf;

    abstract public byte[] convert();

}
