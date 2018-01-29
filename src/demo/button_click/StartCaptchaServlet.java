/**
 * @(#)StartCaptchaServlet.JAVA, 2017年11月08日.
 *
 * Copyright 2017 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo.button_click;

import demo.config.GeetestConfig;
import sdk.GeetestLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Get请求，register阶段，又称API1接口，验证初始化，返回利用key加密的challenge
 * 
 * @author liuquan@geetest.com
 */
public class StartCaptchaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getButton_click_id(),
            GeetestConfig.getButton_click_key(),
            GeetestConfig.getNewfailback());

        // 自定义参数,可选择添加
        String userid = "test";
        HashMap<String, String> param = new HashMap<String, String>();
        // 网站用户id
        param.put("user_id", userid);
        // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("client_type", "web");
        // 传输用户请求验证时所携带的IP
        param.put("ip_address", "127.0.0.1");

        // 进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        // 将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey,
            gtServerStatus);
        // 将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        String resStr = gtSdk.getResponseStr();
        PrintWriter out = response.getWriter();
        out.println(resStr);
    }

}
