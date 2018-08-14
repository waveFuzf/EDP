package com.zust.EDP.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class VerificationCode extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public VerificationCode() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// System.out.println("请求生成验证码");

		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();

		int width = 80;
		int height = 35;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = img.createGraphics();

		// 背景色
		g.setColor(new Color(247, 185, 131));
		// 图像形状和大小
		g.fillRect(0, 0, width, height);

		Random r = new Random();

		for (int i = 0; i < 50; i++) {
			g.setColor(getRandomColor(111, 175));
			int x = r.nextInt(width);
			int y = r.nextInt(height);

			int xl = r.nextInt(30);
			int yl = r.nextInt(24);

			g.drawLine(x, y, x + xl, y + yl);// 画线

			g.drawOval(x, y, 0, 0);// 画点

		}
		// 设置字体
		g.setFont(new Font("Quicksand", Font.PLAIN, 32));
		String code = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(r.nextInt(10));
			code += rand;
			g.setColor(getRandomColor(50, 113));
			g.drawString(rand, 13 * i + 10, 28);
		}

		HttpSession session = request.getSession();
		session.setAttribute("imageIcon", code);

		// System.out.println("随机数为："+code);

		ImageIO.write(img, "jpeg", out);

		out.close();
	}

	private Color getRandomColor(int x, int y) {
		Random rom = new Random();
		if (x > 255)
			x = 255;
		if (x < 0)
			x = 0;
		if (y > 255)
			y = 255;
		if (y < 0)
			y = 0;
		int r = x + rom.nextInt(y - x);
		int g = x + rom.nextInt(y - x);
		int b = x + rom.nextInt(y - x);
		return new Color(r, g, b);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
