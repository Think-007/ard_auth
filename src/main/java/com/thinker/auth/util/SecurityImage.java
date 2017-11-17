///*      						
// * Copyright 2010 Beijing Xinwei, Inc. All rights reserved.
// * 
// * History:
// * ------------------------------------------------------------------------------
// * Date    	|  Who  		|  What  
// * 2017年2月23日	| lipengfeia 	| 	create the file                       
// */
//
//package com.thinker.auth.util;
//
///**
// * 
// * 类简要描述
// * 
// * <p>
// * 类详细描述
// * </p> 
// * 
// * @author lipengfeia
// * 
// */
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Random;
//import com.sun.image.codec.jpeg.ImageFormatException;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//public class SecurityImage {
//
//	public static BufferedImage createImage(String securityCode) {
//
//		// 验证码长度
//		int codeLength = securityCode.length();
//		// 字体大小
//		int fSize = 15;
//		int fWidth = fSize + 1;
//		// 图片宽度
//		int width = codeLength * fWidth + 6;
//		// 图片高度
//		int height = fSize * 2 + 1;
//
//		// 图片
//		BufferedImage image = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.createGraphics();
//
//		// 设置背景色
//		g.setColor(Color.WHITE);
//		// 填充背景
//		g.fillRect(0, 0, width, height);
//
//		// 设置边框颜色
//		g.setColor(Color.LIGHT_GRAY);
//		// 边框字体样式
//		g.setFont(new Font("Arial", Font.BOLD, height - 2));
//		// 绘制边框
//		g.drawRect(0, 0, width - 1, height - 1);
//
//		// 绘制噪点
//		Random rand = new Random();
//		for (int i = 0; i < codeLength * 6; i++) {
//			int xs = rand.nextInt(width);
//			int ys = rand.nextInt(height);
//			// 绘制1*1大小的矩形
//			randColor(g, rand);
//			g.drawRect(xs, ys, 1, 1);
//
//		}
//
//		// 干扰线
//		for (int i = 0; i < 10; i++) {
//			int xs = rand.nextInt(width);
//			int ys = rand.nextInt(height);
//			int xe = xs + 10;
//			int ye = ys + 10;
//			randColor(g, rand);
//			g.drawLine(xs, ys, xe, ye);
//		}
//
//		// 绘制验证码
//		int codeY = height - 10;
//		// 设置字体颜色和样式
//		g.setFont(new Font("Georgia", Font.BOLD, 20));
//		for (int i = 0; i < codeLength; i++) {
//			randColor(g, rand);
//			g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5,
//					codeY);
//		}
//		// 关闭资源
//		g.dispose();
//
//		return image;
//	}
//
//	/**
//	 * 随机色
//	 * 
//	 * @param g
//	 * @param rand
//	 */
//	private static void randColor(Graphics g, Random rand) {
//		int red = 0, green = 0, blue = 0;
//		red = rand.nextInt(255);
//		green = rand.nextInt(255);
//		blue = rand.nextInt(255);
//		g.setColor(new Color(red, green, blue));
//	}
//
//	public static ByteArrayInputStream getImageAsInputStream(String securityCode) {
//
//		BufferedImage image = createImage(securityCode);
//		return convertImageToStream(image);
//	}
//
//	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
//
//		ByteArrayInputStream inputStream = null;
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
//		try {
//			jpeg.encode(image);
//			byte[] bts = bos.toByteArray();
//			inputStream = new ByteArrayInputStream(bts);
//		} catch (ImageFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return inputStream;
//	}
//}
