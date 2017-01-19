package com.zx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/MutilUploadServlet")
public class MutilUploadServlet extends HttpServlet {
		
	private static final long serialVersionUID = 1L;
    
    public MutilUploadServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 设置字符集
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 当表单中设置了enctype属性后，servlet通过getParameter接收参数会失效
		// 如果想实现上传文件，要借助以下几个类：FileItem、DiskFileItemFactory、ServletFileUpload

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 定义最大允许上传的文件尺寸
		upload.setFileSizeMax(5 * 1024 * 1024);
		
		// 获得文件保存路径
		String rootPath = request.getServletContext().getRealPath("/upload/");		
		System.out.println("rootPath ==" + rootPath);
		
		PrintWriter pw = response.getWriter(); 

		try {
			Map<String, List<FileItem>> map = upload.parseParameterMap(request);
			for (Map.Entry<String, List<FileItem>> entry : map.entrySet()) {
				List<FileItem> list = entry.getValue();
				for(FileItem item : list) {     
	                if(item.isFormField()) { 
	                	//如果获取的 表单信息是普通的文本信息          
	                	processFormField(item);
	                } else { 
	                	//对传入的文件 ，比如说二进制的 图片，电影这些   
	                	processUploadFile(rootPath, item, pw);
	                }  
	            }  
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		pw.close();
		
	}
	
	// 处理表单内容  
    private void processFormField(FileItem item) throws Exception {  
        String name = item.getFieldName();  
        if (name.equals("username") || name.equals("psw")) {
        	String value = item.getString(); 
            System.out.println("name = " + name);           
            System.out.println("value = " + value); 
        }          
    }  
      
    // 处理上传的文件  
    private void processUploadFile(String filePath, FileItem item, PrintWriter pw) throws Exception {  
        // 此时的文件名包含了完整的路径，得注意加工一下  
        String filename = item.getName();         
        System.out.println("完整的文件名：" + filename);  
        int index = filename.lastIndexOf("\\");  
        filename = filename.substring(index + 1, filename.length());  
 
        long fileSize = item.getSize();  
 
        if("".equals(filename) && fileSize == 0){             
            System.out.println("文件名为空 ...");  
            return;  
        }   
        
        File uploadFile = new File(filePath + File.separator + filename);  
        item.write(uploadFile);
        
        pw.write("上传文件"+filename+"成功");
    }  

}
