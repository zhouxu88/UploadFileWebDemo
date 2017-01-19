package com.zx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class RegistServlet
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
    public RegistServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//处理客户端post请求
		request.setCharacterEncoding("utf-8"); //设置请求编码格式
		String username = request.getParameter("username");
		String psw = request.getParameter("psw");
		System.out.println("username = " + username);
		System.out.println("psw = " + psw);
		
		//设置返回信息
		ResultEntity entity = new ResultEntity();		
		//假设电话为13888888888已经注册
		if (username.equals("13888888888")) {// 已经注册
			entity.setResultCode(0);
			entity.setResultMsg("fail");
		} else {
			entity.setResultCode(1);
			entity.setResultMsg("success");
		}
		HashMap<String, ResultEntity> map = new HashMap<String,ResultEntity>();
		map.put("result", entity);			
		
		//String jsonStr = JSONSerializer.toJSON(map).toString();
		String jsonStr = "用户名："+ username + "密码："+psw;
		//处理客户的响应
		response.setCharacterEncoding("utf-8");//设置响应编码格式
		response.setContentType("text/html;charset=utf-8"); // 设置响应内容的数据类型和字符集
		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
		writer.flush();
		writer.close();
	}

}
