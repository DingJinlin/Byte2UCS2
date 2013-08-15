package com.raycom.support.byte2ucs2;

/**
 * User: server
 * Date: 13-7-15
 * Time: 下午1:21
 */
public abstract class UCS2Convert {
    // 转义符
    final short ESCAPE_CHARACTER = (short)0xD7D7;

    // 替换数据起始
    final short REPLACE_CHARACTER_START = (short)0xD7FF;

    // 替换数据结束
    final short REPLACE_CHARACTER_END = (short)0xE000;

    // 替换符,替换时与待替换数据进行异或运算
    final short XOR_CHARACTER = (short)0x8000;

    // 数据内容缓存
    byte[] data;

    public UCS2Convert(byte[] data) throws Exception {
        if(data.length % 2 != 0) {
            throw new Exception("data length % 2 != 0");
        }
        this.data = data;
    }

    abstract public byte[] convert();
}
