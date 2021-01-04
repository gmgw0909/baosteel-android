package com.meetutech.baosteel.utils;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.utils
// Author: culm at 2017-05-02
//*********************************************************



    import android.annotation.SuppressLint;
    import java.io.UnsupportedEncodingException;
    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import javax.crypto.Mac;
    import javax.crypto.SecretKey;
    import javax.crypto.spec.SecretKeySpec;

@SuppressLint("DefaultLocale")
public class Base64Utils {

  private static final String ENCODE = "UTF-8";

  // Mapping table from 6-bit nibbles to Base64 characters.
  private static char[] map1 = new char[64];

  // Mapping table from Base64 characters to 6-bit nibbles.
  private static byte[] map2 = new byte[128];

  private static MessageDigest sha1MD;

  static {
    int i = 0;
    for (char c = 'A'; c <= 'Z'; c++)
      map1[i++] = c;
    for (char c = 'a'; c <= 'z'; c++)
      map1[i++] = c;
    for (char c = '0'; c <= '9'; c++)
      map1[i++] = c;
    map1[i++] = '+';
    map1[i++] = '/';
  }

  static {
    for (int i = 0; i < map2.length; i++)
      map2[i] = -1;
    for (int i = 0; i < 64; i++)
      map2[map1[i]] = (byte) i;
  }

  @SuppressLint("DefaultLocale")
  public static String byte2hex(byte[] b)
  // 二行制\u8F6C字符串
  {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
			/*
			 * if (n < b.length - 1) { hs = hs; }
			 */
    }
    return hs.toUpperCase();
  }

  /**
   * 解码一个字节数组从Base64格式，没有空格或换行符被允许在Base64编码的数据.
   *
   * @param in
   *            一个字符数组中的Base64编码的数据.
   * @return 一个数组中的解码数据字节.
   */
  public static byte[] decode(char[] in) {
    int iLen = in.length;
    if (iLen % 4 != 0)
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    while (iLen > 0 && in[iLen - 1] == '=')
      iLen--;
    int oLen = (iLen * 3) / 4;
    byte[] out = new byte[oLen];
    int ip = 0;
    int op = 0;
    while (ip < iLen) {
      int i0 = in[ip++];
      int i1 = in[ip++];
      int i2 = ip < iLen ? in[ip++] : 'A';
      int i3 = ip < iLen ? in[ip++] : 'A';
      if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      int b0 = map2[i0];
      int b1 = map2[i1];
      int b2 = map2[i2];
      int b3 = map2[i3];
      if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      int o0 = (b0 << 2) | (b1 >>> 4);
      int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
      int o2 = ((b2 & 3) << 6) | b3;
      out[op++] = (byte) o0;
      if (op < oLen)
        out[op++] = (byte) o1;
      if (op < oLen)
        out[op++] = (byte) o2;
    }
    return out;
  }

  /**
   * 解码一个字节数组从Base64格式.
   *
   * @param s
   *            一个Base64的字串被解码.
   * @return 一个数组中的解码数据字节.
   * @throws 如果输入的不是有效的Base64编码的数据则会IllegalArgumentException.
   */
  public static byte[] decode(String s) {
    return decode(s.toCharArray());
  }

  /**
   * 解码一个字符串从Base64格式.
   *
   * @param s
   *            一个Base64的字串被解码.
   * @return 一个字符串, 该字符串包含解码数据.
   * @throws 如果输入的不是有效的Base64编码的数据则会IllegalArgumentException.
   */
  public static String decodeString(String s) {
    return new String(decode(s));
  }

  /**
   * 一个字节数组编码成Base64格式。没有空格或换行插入.
   *
   * @param in
   *            一个数组中的数据字节编码.
   * @return 一个字符数组的Base64编码的数据.
   */
  public static char[] encode(byte[] in) {
    return encode(in, in.length);
  }

  /**
   * 一个字节数组编码成Base64格式。没有空格或换行插入.
   *
   * @param in
   *            一个数组中的数据字节编码.
   * @param iLen
   *            过程的字节数<code>in</code>.
   * @return 一个字符数组的Base64编码的数据.
   */
  public static char[] encode(byte[] in, int iLen) {
    int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
    int oLen = ((iLen + 2) / 3) * 4; // output length including padding
    char[] out = new char[oLen];
    int ip = 0;
    int op = 0;
    while (ip < iLen) {
      int i0 = in[ip++] & 0xff;
      int i1 = ip < iLen ? in[ip++] & 0xff : 0;
      int i2 = ip < iLen ? in[ip++] & 0xff : 0;
      int o0 = i0 >>> 2;
      int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
      int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
      int o3 = i2 & 0x3F;
      out[op++] = map1[o0];
      out[op++] = map1[o1];
      out[op] = op < oDataLen ? map1[o2] : '=';
      op++;
      out[op] = op < oDataLen ? map1[o3] : '=';
      op++;
    }
    return out;
  }

  /**
   * 一个字节数组编码成Base64格式。没有空格或换行插入.
   *
   * @param s
   *            一个字符串进行编码.
   * @return 一个字符数组的Base64编码的数据.
   */
  public static String encodeString(String s) {
    return new String(encode(s.getBytes()));
  }

  @SuppressLint("DefaultLocale")
  public static String hmac_sha1(String key, String datas) {
    String reString = "";

    try {
      byte[] data = key.getBytes("UTF-8");
      // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
      SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
      // 生成一个指定 Mac 算法 的 Mac 对象
      Mac mac = Mac.getInstance("HmacSHA1");
      // 用给定密钥初始化 Mac 对象
      mac.init(secretKey);

      byte[] text = datas.getBytes("UTF-8");
      // 完成 Mac 操作
      byte[] text1 = mac.doFinal(text);

      reString = byte2hex(text1);

    } catch (Exception e) {
    }
    return reString.toLowerCase();
  }

  public static String SHA1(String text) {

    if (null == sha1MD) {
      try {
        sha1MD = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException e) {
        return null;
      }
    }
    try {
      sha1MD.update(text.getBytes(ENCODE), 0, text.length());
    } catch (UnsupportedEncodingException e) {
      sha1MD.update(text.getBytes(), 0, text.length());
    }
    return toHexString(sha1MD.digest());
  }

  private static String toHexString(byte[] hashs) {
    StringBuffer sBuffer = new StringBuffer();
    for (byte hash : hashs) {
      sBuffer.append(Integer.toString((hash & 0xFF) + 0x100, 16).substring(1));
    }
    return sBuffer.toString();
  }

  public static String toHexString(String s) {
    String str = "";
    for (int i = 0; i < s.length(); i++) {
      int ch = (int) s.charAt(i);
      String s4 = Integer.toHexString(ch);
      str = str + s4;
    }
    return str;
  }
}