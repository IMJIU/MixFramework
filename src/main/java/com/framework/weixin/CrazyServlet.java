package com.framework.weixin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 核心请求处理类

 <servlet>
    <servlet-name>CrazyServlet</servlet-name>
    <servlet-class>com.javen.course.servlet.CrazyServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>CrazyServlet</servlet-name>
    <url-pattern>/Javen</url-pattern>
  </servlet-mapping>
 * 
 */
public class CrazyServlet extends HttpServlet {

	private static final long serialVersionUID = -5021188348833856475L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 消息的接收、处理、响应
	}
}