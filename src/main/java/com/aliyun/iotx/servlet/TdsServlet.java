package com.aliyun.iotx.servlet;

import com.alibaba.fastjson.JSON;
import com.aliyun.iotx.pojo.Tds;
import com.aliyun.iotx.pojo.Wl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tds")
public class TdsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tds> tdsList = new SelectServlet().getTdsList();
        String jsonString = JSON.toJSONString(tdsList);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
