package com.aliyun.iotx.servlet;

import com.alibaba.fastjson.JSON;
import com.aliyun.iotx.pojo.Tds;
import com.aliyun.iotx.pojo.Vol;
import com.aliyun.iotx.pojo.Wl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@WebServlet("/selectAll")
public class SelectServlet extends HttpServlet {
    private static long location = 0;

    private static List<Wl> wlList = new ArrayList<>();
    private static List<Tds> tdsList = new ArrayList<>();
    private static List<Vol> volList = new ArrayList<>();

    public List<Wl> getWlList() {
        return wlList;
    }

    public List<Tds> getTdsList() {
        return tdsList;
    }

    public List<Vol> getVolList() {
        return volList;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File("output.txt");
        RandomAccessFile r = new RandomAccessFile(file, "r");
        r.seek(location);
        //记录数据是否有异常
        boolean wl_error = false;
        boolean tds_error = false;
        boolean vol_error = false;
        try {
            String tempString = null;
            while ((tempString = r.readLine()) != null) {
                String[] strings = tempString.split("=");
                if (Objects.equals(strings[0], "wl")) {
                    double wl = Double.parseDouble(strings[1]);
                    if (wl > 6) {
                        wl_error = true;
                    }
                    wlList.add(new Wl(wl));
                } else if (Objects.equals(strings[0], "tds")) {
                    double tds = Double.parseDouble(strings[1]);
                    if (tds > 250) {
                        tds_error = true;
                    }
                    tdsList.add(new Tds(tds));
                } else if (Objects.equals(strings[0], "vol")) {
                    int vol = Integer.parseInt(strings[1]);
                    if (vol > 4000) {
                        vol_error = true;
                    }
                    volList.add(new Vol(vol));
                } else {
                    continue;
                }
            }
            location = r.getFilePointer();
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        if(wl_error||tds_error||vol_error){
            String msg = "";
            if(wl_error){
                msg+="水位出现异常！\n";
            }
            if(tds_error){
                msg+="水质出现异常！\n";
            }
            if(vol_error){
                msg+="电压出现异常！\n";
            }
            response.getWriter().write(msg);
        }
        else response.getWriter().write("success");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
