/**
 * @(#)Config.JAVA, 2017年11月08日.
 *
 * Copyright 2017 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * 公共资源配置类，读取properties资源
 *
 * @author liuquan@geetest.com
 */
public class GeetestConfig {

    // Test-Button服务 大图模式的测试账户ID
    private static String button_click_id = "";

    // Test-Button服务 大图模式的测试账户KEY
    private static String button_click_key = "";

    // Test-Button服务 滑动模式的测试账户ID
    private static String button_slide_id = "";

    // Test-Button服务 滑动模式的测试账户KEY
    private static String button_slide_key = "";

    // One-Pass服务的测试账户ID
    private static String onepass_id = "";

    // One-Pass服务的测试账户KEY
    private static String onepass_key = "";

    // Test-Button服务请求失败时，failback策略的标志
    private static boolean newfailback = true;

    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("/demo/config/config.properties");
            Properties prop = new Properties();
            prop.load(in);
            button_click_id = prop.getProperty("button_click_id");
            button_click_key = prop.getProperty("button_click_key");
            button_slide_id = prop.getProperty("button_slide_id");
            button_slide_key = prop.getProperty("button_slide_key");
            onepass_id = prop.getProperty("onepass_id");
            onepass_key = prop.getProperty("onepass_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getButton_click_id() {
        return button_click_id;
    }

    public static String getButton_click_key() {
        return button_click_key;
    }

    public static String getButton_slide_id() {
        return button_slide_id;
    }

    public static String getButton_slide_key() {
        return button_slide_key;
    }

    public static String getOnepass_id() {
        return onepass_id;
    }

    public static String getOnepass_key() {
        return onepass_key;
    }

    public static boolean getNewfailback() {
        return newfailback;
    }
}
