package com.thinker.test.test;

import com.thinker.security.Base64;
import com.thinker.security.RSAEncrypt;
import com.thinker.security.RSASignature;

public class TestRsa {

	public static void main(String[] args) {

		/*
		 * try { String filepath = "D:/tmp/";
		 * 
		 * RSAEncrypt.genKeyPair(filepath);
		 * 
		 * System.out.println("--------------公钥加密私钥解密过程-------------------");
		 * String plainText = "ihep_公钥加密私钥解密"; // 公钥加密过程 byte[] cipherData =
		 * RSAEncrypt.encrypt(RSAEncrypt .loadPublicKeyByStr(RSAEncrypt
		 * .loadPublicKeyByFile(filepath)), plainText .getBytes()); String
		 * cipher = Base64.encode(cipherData); // 私钥解密过程 byte[] res =
		 * RSAEncrypt.decrypt(RSAEncrypt .loadPrivateKeyByStr(RSAEncrypt
		 * .loadPrivateKeyByFile(filepath)), Base64 .decode(cipher)); String
		 * restr = new String(res); System.out.println("原文：" + plainText);
		 * System.out.println("加密：" + cipher); System.out.println("解密：" +
		 * restr); System.out.println();
		 * 
		 * System.out.println("--------------私钥加密公钥解密过程-------------------");
		 * plainText = "ihep_私钥加密公钥解密"; // 私钥加密过程 cipherData =
		 * RSAEncrypt.encrypt(RSAEncrypt .loadPrivateKeyByStr(RSAEncrypt
		 * .loadPrivateKeyByFile(filepath)), plainText .getBytes()); cipher =
		 * Base64.encode(cipherData); // 公钥解密过程 res =
		 * RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt
		 * .loadPublicKeyByFile(filepath)), Base64.decode(cipher)); restr = new
		 * String(res); System.out.println("原文：" + plainText);
		 * System.out.println("加密：" + cipher); System.out.println("解密：" +
		 * restr); System.out.println();
		 * 
		 * System.out.println("---------------私钥签名过程------------------"); String
		 * content = "ihep_这是用于签名的原始数据"; String signstr =
		 * RSASignature.sign(content,
		 * RSAEncrypt.loadPrivateKeyByFile(filepath));
		 * System.out.println("签名原串：" + content); System.out.println("签名串：" +
		 * signstr); System.out.println();
		 * 
		 * System.out.println("---------------公钥校验签名------------------");
		 * System.out.println("签名原串：" + content); System.out.println("签名串：" +
		 * signstr);
		 * 
		 * System.out.println("验签结果：" + RSASignature.doCheck(content, signstr,
		 * RSAEncrypt.loadPublicKeyByFile(filepath))); System.out.println(); }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		try {
			//

			String[] keypai = RSAEncrypt.genKeyPair();
//			String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALIvy2rOKsKYUpcHWYqlUKxnsDAXt9I41c3ynk0wJcbGGM/WfZP6N15L5h9QeAJv/Dn2G/eSmkQ1k7Qfe1JbDgGHl2gqAfe/zJt+3oeQBQRkLVxbEWH5LNIVxBeb9TYC4niy9o1bRYAf6qjztFU8IjQphjEFmLiuguaPJEJR+MJzAgMBAAECgYBhd1NFcNBi8P78LcEE3bhrFayCIfcivoP0yylb/2IqgGFCDolSUCPJKKsPpex/KNJGwiA6I67OcKACftXuDnBx6etKaBjKLfJSWEIUz2TDdkIZiuUE03E/UKLr+jToBkKD/p4qpKj9x2YVonHTi7jlMzXn6NahJUKuA8pO7azBgQJBAPTTqGvigs1xGQrIHoqG2x9+Jkmjftsr4UX6Y2Z/d/STEhmL6X6kmPJtPBGGG+Ks6AIUQEQ8z5CEmQB92Qk3ZzkCQQC6UZJ4r92PmsAis+G6EeEXZczpu+3MF8XkvRo04d+u096QfSQn5mm3mlq2XZlnFKBy5ErqY41IA4kZBFBRhusLAkB9flTRYBz+I5/bd/K3vSJqxSXpDyZCP/7L+Omkq2gYdJ6Ne7snEEXZ1gY5UU3P5iNSlYDPuYAHqwAmUVvDrqN5AkAI6l5X6BJyDLWk83aZsMFUnUpw5M08NucBSws1/Jj3hKvhvhs8zYnBf2FX2KK+i/4hgUsUbC8WgxTMXah6PjqBAkAdnRBLCbZbjgq26w6sXrUkNRmrQPJMciKzNYOCECLluEyOcVqXeZqWfvDV/G0V8TFheysj+3KPFQK9KDw5ekE/";
//			String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyL8tqzirCmFKXB1mKpVCsZ7AwF7fSONXN8p5NMCXGxhjP1n2T+jdeS+YfUHgCb/w59hv3kppENZO0H3tSWw4Bh5doKgH3v8ybft6HkAUEZC1cWxFh+SzSFcQXm/U2AuJ4svaNW0WAH+qo87RVPCI0KYYxBZi4roLmjyRCUfjCcwIDAQAB";

			String publicKey = keypai[0];
			String privateKey = keypai[1];
			String suer = "13011836133_123456_呵呵哒";

			String s = Base64.encode(RSAEncrypt.encrypt(
					RSAEncrypt.loadPublicKeyByStr(publicKey), suer.getBytes()));
			
			String en = "c4TUERMkt6DnjEPEXb44ht73tv5tossjAf1i6s7rNadjCbj4H195Qm2xFytTjKw1NpFKcczhFGPt1UEuMVEXvFoDJztNWMBernubO5gXp/uokqGh7Xq8Hs7+PgFRc78m1NoFgvV5QKfMfuDZdtFY5Gf1Fzjc1o/eUuaDf9PkzqA=";

			// String encryptStr =
			// "n0gn/Tdb1eHSIresdmfWzSvyacIJOPeJr4UYbv86yEx/X2dUA7+ZndamvhsD4UwAnMfe8tq7i71wrbF0M3I1qOxVvO36Am4VG0n+3jDWgIPDjgnMr7PDsrtZRgoJb4vM3XfSFTt5PzVECk35rB7Q3DGsslQfaOKEd58Oq4dJYRE=";
			String userInfoStr = new String(RSAEncrypt.decrypt(
					RSAEncrypt.loadPrivateKeyByStr(privateKey),
					Base64.decode(en)));
			System.out.println("密文" + s);
			System.out.println("解析" + userInfoStr);

			//
			// String[] keypai = RSAEncrypt.genKeyPair();
			//
			//
			// System.out.println("--------------公钥加密私钥解密过程-------------------");
			// String plainText = "ihep_公钥加密私钥解密";
			// // 公钥加密过程
			// byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt
			// .loadPublicKeyByStr(keypai[0]), plainText
			// .getBytes());
			// String cipher = Base64.encode(cipherData);
			// // 私钥解密过程
			// byte[] res = RSAEncrypt.decrypt(RSAEncrypt
			// .loadPrivateKeyByStr(keypai[1]), Base64
			// .decode(cipher));
			// String restr = new String(res);
			// System.out.println("原文：" + plainText);
			// System.out.println("加密：" + cipher);
			// System.out.println("解密：" + restr);
			// System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
