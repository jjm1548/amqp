package com.aliyun.iotx.servlet;

import com.alibaba.fastjson.JSON;
import com.aliyun.iotx.pojo.Tds;
import com.aliyun.iotx.pojo.Vol;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/vol")
public class VolServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vol> volList = new SelectServlet().getVolList();
        String jsonString = JSON.toJSONString(volList);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
