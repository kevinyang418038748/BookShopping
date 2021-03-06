package com.shxt.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shxt.admin.dao.UserListDAO;
import com.shxt.model.UserInfo;

public class GetUserList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		UserListDAO uld = new UserListDAO();
		String id = request.getParameter("id");
		if(id != null){
			uld.delete(Integer.parseInt(id));
		}
		int pageNow = 1;
		String pagenow = request.getParameter("pageNow");
		if(pagenow != null){
			pageNow = Integer.parseInt(pagenow);
		}
		int pageCount = uld.getPageCount(5);
		List<UserInfo> userInfo = new ArrayList<UserInfo>();

		String type = request.getParameter("type");
		if(type != null){
			uld.deleteAll(pageNow);
		}
		if(type == null){
			userInfo = uld.getPageUserList(pageNow);
		}else{
				if(pageNow == pageCount){
					userInfo = uld.getPageUserList(pageNow - 1);
				}else{
					userInfo = uld.getPageUserList(pageNow + 1);
				}
		}
		
		
		request.setAttribute("userInfo", userInfo);
		request.setAttribute("pageNow", pageNow);
		request.setAttribute("pageCount", pageCount);
		
		request.getRequestDispatcher("admin/memberlist.jsp").forward(request,response);

		out.flush();
		out.close();
	}

}
