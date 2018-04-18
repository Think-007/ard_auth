package com.thinker.test.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFile {

	public static void main(String[] args) {
		
		
		String filePath = "D:/upload/users/imgs/";
		File originfile = new File(filePath+ "/origin/" + "s.jpg");
		
		System.out.println(originfile.getAbsolutePath());

		if (!originfile.exists()) {
			try {
				originfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
