/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.fms.util;

//---------------------------------------------------------------------------
//Filename:        EncryptUtils.java
//Date:            2012-02-23
//Author:          
//Function:        鍔犲瘑
//History:
//
//---------------------------------------------------------------------------

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <strong>Internal class, do not use directly.</strong>
 *
 * String encryption utility methods.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class EncryptUtils {

	/**
	 * Encrypt byte array.
	 */
	private final static byte[] encrypt(byte[] source, String algorithm)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.reset();
		md.update(source);
		return md.digest();
	}

	/**
	 * Encrypt string
	 */
	public final static String encrypt(String source, String algorithm)
			throws NoSuchAlgorithmException {
		byte[] resByteArray = encrypt(source.getBytes(), algorithm);
		return StringUtil.toHexString(resByteArray);
	}

	/**
	 * Encrypt string using MD5 algorithm
	 */
	public final static String encryptMD5(String source) {
		if (source == null) {
			source = "";
		}

		String result = "";
		try {
			result = encrypt(source, "MD5");
		} catch (NoSuchAlgorithmException ex) {
			// this should never happen
			throw new RuntimeException(ex);
		}
		return result;
	}

	/**
	 * Encrypt string using SHA algorithm
	 */
	@SuppressWarnings("unused")
	public final static String encryptSHA(String source) {
		if (source == null) {
			source = "";
		}

		String result = "";
		try {
			result = encrypt(source, "SHA");
		} catch (NoSuchAlgorithmException ex) {
			// this should never happen
			throw new RuntimeException(ex);
		}
		return result;
	}



	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String THREE_DES = "DESede";


	/**
	 * 解密函数
	 * @param src 密文的字节数组
	 * @return
	 */
	public static byte[] decryptThreeDES(byte[] src,String secretkey) {
		try {
			SecretKey deskey = new SecretKeySpec(build3DesKey(secretkey), THREE_DES);
			Cipher c1 = Cipher.getInstance(THREE_DES);
			c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密方法
	 * @param src 源数据的字节数组
	 * @return
	 */
	public static byte[] encryptThreeDES(byte[] src,String secretkey) {
		try {
			SecretKey deskey = new SecretKeySpec(build3DesKey(secretkey), THREE_DES);    //生成密钥
			Cipher c1 = Cipher.getInstance(THREE_DES);    //实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}


	/*
     * 根据字符串生成密钥字节数组
     * @param keyStr 密钥字符串
     * @return
     * @throws UnsupportedEncodingException
     */
	public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组

        /*
         * 执行数组拷贝
         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
		if(key.length > temp.length){
			//如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		}else{
			//如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**
	 * MAC算法可选以下多种算法
	 *
	 * <pre>
	 * HmacMD5
	 * HmacSHA1
	 * HmacSHA256
	 * HmacSHA384
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}



	/**
	 * 生成签名数据
	 *
	 * @param data 待加密的数据
	 * @param key  加密使用的key
	 * @return 生成MD5编码的字符串
	 */
	public static String encryptHMAC_SHA1(byte[] data, byte[] key) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data);
		return new String(encryptMD5(rawHmac));
	}



}
