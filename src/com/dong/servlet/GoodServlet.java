package com.dong.servlet;

import com.dong.entry.Good;
import com.dong.service.GoodService;
import com.dong.service.impl.GoodServiceImpl;
import com.dong.util.Car;
import com.dong.util.CarItem;
import com.dong.util.PageBean;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
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
    

    protected void queryGood(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String gtype = request.getParameter("gtype");
        List<String> allGtype = goodService.findAllGtype();
        if(gtype==null||gtype.isEmpty()){
            gtype = allGtype.get(0);
        }
        String str = request.getParameter("pageNum");
        Integer pageNum = 1;
        if (str != null){
            pageNum = Integer.valueOf(str);
        }
        PageBean<Good> pb = new PageBean<>();
        pb.setPageSize(10);
        pb.setPageNum(pageNum);
        goodService.getPageBean(gtype,pb);
        request.getSession().setAttribute("pb",pb);
        request.getSession().setAttribute("allGtype",allGtype);
        response.sendRedirect("main.jsp");
    }
    protected void downImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filename = request.getParameter("filename");
        String path = request.getServletContext().getRealPath("WEB-INF/upload"+filename);
        FileInputStream fis =  new FileInputStream(path);;
        ServletOutputStream sos = response.getOutputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes))!=-1){
            sos.write(bytes,0,len);
        }
        fis.close();
        sos.close();
    }
    protected void addCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        String quantityStr = request.getParameter("quantity");
        Integer quantity ;
        if (quantityStr==null){
            quantity = 1;
        }else {
            quantity = Integer.valueOf(quantityStr);
        }
        HttpSession session = request.getSession();
        Car car = (Car) session.getAttribute("car");
        if (car == null){
            car = new Car();
        }
        CarItem carItem = new CarItem();
        Good good = goodService.findGoodById(id);
        carItem.setGood(good);
        carItem.setQuantity(quantity);
        car.addGood(carItem);
        session.setAttribute("car",car);
        request.getRequestDispatcher("flow.jsp").forward(request,response);
    }
}