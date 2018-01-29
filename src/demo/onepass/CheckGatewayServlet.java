/**
 * @(#)CheckGatewayServlet.JAVA, 2017年11月08日.
 *
 * Copyright 2017 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo.onepass;

import demo.config.GeetestConfig;
import org.json.JSONException;
import org.json.JSONObject;
import sdk.GmessageLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuquan@geetest.com
 */
public class CheckGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        // 首先初始化GmessageLib类，传入参数ID和KEY，其值从Onepass管理后台获取
        GmessageLib gtmessagesdk = new GmessageLib(
            GeetestConfig.getOnepass_id(), GeetestConfig.getOnepass_key());
        // 从客户端获取到的参数手机号，用以进行网关校验
        String phone = request.getParameter("phone");
        // 从客户端获取到的onepass流水号，用以进行网关校验
        String process_id = request.getParameter("process_id");
        // 从客户端获取到的运营商token号，用以进行短信校验
        String accesscode = request.getParameter("accesscode");

        // 调用Gmessagelib的对象的checkGateway方法，并将参数以固定顺序传入。返回值为1则代表成功，返回值为0则代表失败
        int result = gtmessagesdk.checkGateway(phone, process_id, accesscode,false);
        try {
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            // result=1代表验证结果成功，返回0
            data.put("result", result == 1 ? 0 : 1);
            out.println(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
