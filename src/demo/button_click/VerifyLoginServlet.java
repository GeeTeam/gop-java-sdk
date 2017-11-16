/**
 * @(#)VerifyLoginServlet.JAVA, 2017年11月08日.
 *
 * Copyright 2017 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo.button_click;

import demo.config.GeetestConfig;
import org.json.JSONException;
import org.json.JSONObject;
import sdk.GeetestLib;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * POST请求，validate阶段，又称API2接口，二次校验, request表单中必须包含challenge, validate, seccode
 *
 * @author liuquan@geetest.com
 */
public class VerifyLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getButton_click_id(),
            GeetestConfig.getButton_click_key(),
            GeetestConfig.getNewfailback());

        String challenge = request
            .getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        // 从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession()
            .getAttribute(gtSdk.gtServerStatusSessionKey);

        // 从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        // 自定义参数，可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        // 网站用户id
        param.put("user_id", userid);
        // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("client_type", "web");
        // 传输用户请求验证时所携带的IP
        param.put("ip_address", "127.0.0.1");

        // 校验结果标识
        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate,
                seccode, param);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out
                .println("failback: use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate,
                seccode);
        }

        try {
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            data.put("version", gtSdk.getVersionInfo());
            // gtResult=1代表校验成功
            data.put("status", gtResult == 1 ? "success" : "fail");
            out.println(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
