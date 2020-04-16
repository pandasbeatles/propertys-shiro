package com.xiaochen.beatles.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.xiaochen.beatles.config.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/onpay")
public class LPayController {

    @RequestMapping("/pay")
    public void mypay(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sumCost = Integer.parseInt(map.get("sumCost"));
        int costId = Integer.parseInt(map.get("costId"));
        int userId = Integer.parseInt(map.get("userId"));
        System.out.println(sumCost);
        System.out.println(costId);
        System.out.println(userId);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 返给用户的响应地址,回调结果
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        String outTradeNo = "panda" + System.currentTimeMillis() + (long) (Math.random() * 10000000L);
        String tmpe = "物业费";
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + outTradeNo + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + sumCost + "," +
                "    \"subject\":panda 1号," +
                "    \"body\":" + tmpe + "," +
                "    \"passback_params\":merchantBizType%3d3C%26merchantBizNo%3d2016010101111," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088102177718202\"" +
                "    }" +
                "  }");//填充业务参数
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //直接将完整的表单html输出到页面
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
        System.out.println(alipayRequest.toString());

    }

    /**
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝同步通知页面
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     * @author sihai
     * @date 2017年8月23日 下午8:51:01
     * @version V1.0
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        System.out.println("支付成功, 进入同步通知接口*******");
        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] SET = (String[]) requestParams.get(name);
            String SETtr = "";
            for (int i = 0; i < SET.length; i++) {
                SETtr = (i == SET.length - 1) ? SETtr + SET[i]
                        : SETtr + SET[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            SETtr = new String(SETtr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, SETtr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE); //调用SDK验证签名

        ModelAndView mv = new ModelAndView("alipaySuccess");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
            //orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);

            System.out.println("支付宝同步通知 :  " + "商户订单号 :" + out_trade_no + "trade_no :" + trade_no + " 付款金额 :" + total_amount);
            // Orders order = orderService.getOrderById(out_trade_no);

            // Product product = productService.getProductById(order.getProductId());


            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            //log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");
            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
            //mv.addObject("productName", product.getName());
            return mv;
        } else {
            log.info("支付, 验签失败...");
            return null;
        }

    }

    /**
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝异步 通知页面
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     * @author sihai
     * @date 2017年8月23日 下午8:51:13
     * @version V1.0
     */
    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] SET = (String[]) requestParams.get(name);
            String SETtr = "";
            for (int i = 0; i < SET.length; i++) {
                SETtr = (i == SET.length - 1) ? SETtr + SET[i]
                        : SETtr + SET[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			SETtr = new String(SETtr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, SETtr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                // orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
                System.out.println("支付宝异步通知 :  " + signVerified);
                // Orders order = orderService.getOrderById(out_trade_no);

                //  Product product = productService.getProductById(order.getProductId());


                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                // log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");
            }
            log.info("支付成功...");

        } else {//验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }

    /**
     * 查询交易
     *
     * @param outTradeNo
     * @param tradeNo
     * @return
     */
    @RequestMapping("/alipayTradeQuery")
    @ResponseBody
    public String alipayTradeQuery(String outTradeNo, String tradeNo) {
        System.out.println(outTradeNo);
        System.out.println(tradeNo);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = null;
        //支付宝交易号
        String trade_no = null;
        try {
            //商户订单号，商户网站订单系统中唯一订单号
            out_trade_no = new String(outTradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            trade_no = new String(tradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //请二选一设置
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
            System.out.println(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(result);
        String alipay_trade_query_response = jsonObject.getString("alipay_trade_query_response");
        JSONObject jsonObjects = JSON.parseObject(alipay_trade_query_response);
        String code = jsonObjects.getString("code");
        String msg = jsonObjects.getString("msg");
        String tradeStatus = jsonObjects.getString("trade_status");
        System.out.println(tradeStatus);
        if (code.equals("10000") && msg.equals("Success") && tradeStatus.equals("TRADE_SUCCESS")) {
            return result;
        }

        return null;
    }

    /**
     * 退款交易
     *
     * @param outTradeNo
     * @param tradeNo
     * @param refundAmount
     * @param refundReason
     * @param outRequestNo
     * @return
     */
    @RequestMapping("/alipayTradeRefund")
    @ResponseBody
    public String alipayTradeRefund(String outTradeNo, String tradeNo, String refundAmount, String refundReason, String outRequestNo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = null;
        //支付宝交易号
        String trade_no = null;
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = null;
        //退款的原因说明
        String refund_reason = null;
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = null;
        try {
            //商户订单号，商户网站订单系统中唯一订单号
            out_trade_no = new String(outTradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            trade_no = new String(tradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //请二选一设置
            //需要退款的金额，该金额不能大于订单金额，必填
            refund_amount = new String(refundAmount.getBytes("ISO-8859-1"), "UTF-8");
            //退款的原因说明
            refund_reason = new String(refundReason.getBytes("ISO-8859-1"), "UTF-8");
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
            out_request_no = new String(outRequestNo.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"trade_no\":\"" + trade_no + "\","
                + "\"refund_amount\":\"" + refund_amount + "\","
                + "\"refund_reason\":\"" + refund_reason + "\","
                + "\"out_request_no\":\"" + out_request_no + "\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //输出
        return result;
    }

    /**
     * 退款查询
     *
     * @param outTradeNo
     * @param tradeNo
     * @param outRequestNo
     * @return
     */
    @RequestMapping("/alipayTradeFastpayRefundQuery")
    @ResponseBody
    public String alipayTradeFastpayRefundQuery(String outTradeNo, String tradeNo, String outRequestNo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = null;
        //支付宝交易号
        String trade_no = null;
        //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
        String out_request_no = null;
        try {
            //商户订单号，商户网站订单系统中唯一订单号
            out_trade_no = new String(outTradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            trade_no = new String(tradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //请二选一设置
            //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
            out_request_no = new String(outRequestNo.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"trade_no\":\"" + trade_no + "\","
                + "\"out_request_no\":\"" + out_request_no + "\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //输出
        return result;
    }

    /**
     * 交易关闭
     *
     * @param outTradeNo
     * @param tradeNo
     */
    @RequestMapping("/alipayTradeClose")
    @ResponseBody
    public String alipayTradeClose(String outTradeNo, String tradeNo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();


        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = null;
        //支付宝交易号
        String trade_no = null;
        try {
            //商户订单号，商户网站订单系统中唯一订单号
            out_trade_no = new String(outTradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            trade_no = new String(tradeNo.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //输出

        return result;
    }

    /**
     * @param outTradeNo
     * @param tradeNo
     * @return
     */
    @RequestMapping("/alipayQuerypays")
    @ResponseBody
    public ModelAndView alipayQuerypays(String outTradeNo, String tradeNo) {
        //定义返回视图
        ModelAndView mv = new ModelAndView("statics/html/pays");
        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = null;
        //支付宝交易号
        String trade_no = null;
        try {
            //商户订单号，商户网站订单系统中唯一订单号
            out_trade_no = new String(outTradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            trade_no = new String(tradeNo.getBytes("ISO-8859-1"), "UTF-8");
            //请二选一设置
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mv.addObject("out_trade_no", out_trade_no);
        mv.addObject("trade_no", trade_no);
        if (outTradeNo.equals(null) && tradeNo.equals(null)) {
            return null;
        }
        return mv;
    }
}

