package com.thinker.test.test;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author bohua 由于非对称加密算法的开销很大,所以如果直接以非对称技术来加密发送的消息效率会很差.
 */
public class RSA {

	public static final int KEYSIZE = 512;

	private KeyPair keyPair;
	private Key publicKey;
	private Key privateKey;

	/**
	 * 生成秘钥对
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom();
		pairgen.initialize(RSA.KEYSIZE, random);
		this.keyPair = pairgen.generateKeyPair();
		return this.keyPair;
	}

	/**
	 * 加密秘钥
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 */
	public byte[] wrapKey(Key key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.WRAP_MODE, this.privateKey);
		byte[] wrappedKey = cipher.wrap(key);
		return wrappedKey;
	}

	/**
	 * 解密秘钥
	 * 
	 * @param wrapedKeyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public Key unwrapKey(byte[] wrapedKeyBytes)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.UNWRAP_MODE, this.publicKey);
		Key key = cipher.unwrap(wrapedKeyBytes, "AES", Cipher.SECRET_KEY);
		return key;
	}

	public Key getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(Key publicKey) {
		this.publicKey = publicKey;
	}

	public Key getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(Key privateKey) {
		this.privateKey = privateKey;
	}
}