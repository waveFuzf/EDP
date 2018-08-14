package com.zust.EDP.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class ImgUp {

	public String UpImg(String userno, String image, String path) throws IOException {

		String filepath = null;
		File directory = new File("");// 设定为当前文件夹
		try {
			System.out.println(directory.getCanonicalPath());// 获取标准的路径
			System.out.println(directory.getAbsolutePath());// 获取绝对路径
		} catch (Exception e) {
		}
		filepath = path + "\\avatar\\avatar_" + userno + ".jpg";

		System.out.println("userno=" + userno);

		if (userno != null && !userno.equals("")) {
			System.out.print(image);
			String img[] = image.split(",");
			boolean a = GenerateImage(img[1], userno, filepath);
			if (a) {
				System.out.println("上传成功");
				return "true";
			} else {
				return "uploadError";
			}
		} else {
			System.out.println("请先登陆");
			return "loginBeforupload";
		}

	}

	public static boolean GenerateImage(String imgStr, String userno, String path) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
