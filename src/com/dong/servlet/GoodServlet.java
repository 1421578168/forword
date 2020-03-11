package com.dong.servlet;

import com.dong.entry.Good;
import com.dong.service.GoodService;
import com.dong.service.impl.GoodServiceImpl;
import com.dong.util.PageBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@WebServlet("/qiantai/goodSvl")
public class GoodServlet extends HttpServlet {
    private GoodService goodService = new GoodServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //		创建 FileItem 对象的工厂   
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件需要上传到的路径   
        String path = getServletContext().getRealPath("/WEB-INF/upload");
        //指定临时文件目录
        factory.setRepository(new File(path));
        //设置内存缓冲区的大小
        factory.setSizeThreshold(1024 * 1024);
        //负责处理上传的文件数据，并将表单中每个输入项封装成一个 FileItem 对象中
        ServletFileUpload upload = new ServletFileUpload(factory);
        //ProgressListener显示上传进度
        ProgressListener progressListener = new ProgressListener() {
            public void update(long pBytesRead, long pContentLength, int pItems) {
                /*System.out.println("到现在为止,  " + pBytesRead + " 字节已上传，总大小为 "
                        + pContentLength);*/
            }
        };
        upload.setProgressListener(progressListener);

        List<FileItem> list;
        try {
            //调用Upload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
            list = (List<FileItem>) upload.parseRequest(request);
//			对list进行迭代，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
            Good good = new Good();
            for (FileItem item : list) {
                String name = item.getFieldName();
                if (item.isFormField()) {//为普通表单字段
                    String value = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
                    if (name.equals("pname")) {
                        good.setGname(value);
                    } else if (name.equals("ptype")) {
                        good.setGtype(value);
                    } else if (name.equals("price")) {
                        good.setPrice(Double.parseDouble(value));
                    }

                } else {//为上传文件，则调用item.write方法写文件
                    String value = item.getName();
                    int start = value.lastIndexOf("\\");
                    String filename = value.substring(start + 1);
                    good.setPic(filename);

                    item.write(new File(path, filename));

                }
            }
            boolean b = goodService.addGood(good);
            if (b) {
                response.sendRedirect("addnewproduct.jsp");
            } else {
                request.getSession().setAttribute("info", "添加商品失败");
                request.getRequestDispatcher("addnewproduct.jsp").forward(request, response);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void queryGood(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String gtype = request.getParameter("gtype");
        List<String> allGtype = goodService.findAllGtype();
        if(gtype==null||gtype.isEmpty()){
            gtype = allGtype.get(1);
        }
        String str = request.getParameter("pageNum");
        Integer pageNum = 1;
        if (str != null){
            pageNum = Integer.valueOf(str);
        }
        PageBean<Good> pb = new PageBean<>();
        pb.setPageNum(pageNum);
        goodService.getPageBean(gtype,pb);
        request.getSession().setAttribute("pb",pb);
        request.getSession().setAttribute("allGtype",allGtype);
        response.sendRedirect("main.jsp");
    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("pid"));
        String gname = request.getParameter("pname");
        String gtype = request.getParameter("ptype");
        Double price = Double.valueOf(request.getParameter("pprice"));
        Good good = new Good(id,gname,gtype,price,null);
        boolean b = goodService.updateGood(good);
        if (b){
            response.sendRedirect("goodSvl?method=queryGood");
        }else {
            request.getSession().setAttribute("info","修改失败!");
            request.getRequestDispatcher("updateproduct.jsp").forward(request,response);
        }
    }
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        boolean b = goodService.removeGood(id);
        if (b){
            response.sendRedirect("goodSvl?method=queryGood");
        }else {
            response.getWriter().print("<script>\n" +
                    "        window.alert(\"删除失败!\");\n" +
                    "        window.history.back();\n" +
                    "    </script>");
        }
    }
}