package com.framework.zookeeper.t02_config.server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tc/dd") 
public class WebControll {

	@RequestMapping("test.do")
	public void testM(HttpServletResponse response) {
		outputData(response,"测试成功");
		System.out.println("----------test.do-----------");
	}
	@RequestMapping("testtwo.do")
	public void sample(HttpServletResponse response) {
		outputData(response,"测试成功testtwo.do");
		System.out.println("--------testtwo.do-------------");
	}
	@RequestMapping("testthree.do")
	public void three(HttpServletResponse response) {
		outputData(response,"测试成功testthree.do");
		System.out.println("--------testthree.do-------------");
	}
	
	public void outputData(HttpServletResponse response, String data) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(data);
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
