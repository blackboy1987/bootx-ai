package com.bootx.controller;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.entity.Order;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberRankService;
import com.bootx.service.MemberService;
import com.bootx.service.OrderService;
import com.bootx.service.impl.MemberRankServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Resource
    private MemberRankService memberRankService;
    @Resource
    private OrderService orderService;


    @PostMapping("/create")
    public Result create(HttpServletRequest request, @CurrentUser Member member, Integer type, Long memberRankId) throws AlipayApiException {
        MemberRank memberRank = memberRankService.find(memberRankId);
        if(memberRank==null){
            return Result.error("非法请求");
        }
        String token = request.getHeader("token");
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(token) || StringUtils.isBlank(deviceId)||type==null){
            return Result.error("非法请求");
        }
        // 这里要判断，当前会员拥有的会员等级是否高于需要升级的。如果高于，提示不支持升级
        if(member.getMemberRank()!=null && member.getMemberRank().getPrice().compareTo(memberRank.getPrice())>0){
            return Result.error("当前会员等级高于"+memberRank.getName()+",暂不支持升级");
        }




        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
        AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        if(type == 1){
            // 升级会员
            model.setBody("会员升级");
            Order order = orderService.create(member,memberRank);
            model.setTotalAmount(order.getAmount()+"");
            model.setTimeoutExpress("30m");
            model.setSubject(order.getMemo());
            model.setProductCode(order.getSn());
            model.setOutTradeNo(order.getSn());
        }else{
            return Result.error("非法请求");
        }
        payRequest.setBizModel(model);
        payRequest.setNotifyUrl("http://ai.igomall.xin/api/payment/callback");
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(payRequest);
            System.out.println(response.getBody());
            return Result.success(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.error("参数错误");
    }

    private CertAlipayRequest getAlipayConfig() {
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAOPwdPcdyREOMLac4zMKJ1OIjy13LniZN3StbnOhX3RzX92+Jta99Qq8D/XwzdKlaHAY09uw+LwcSm6w2ZwPGk6cCpZjwmO6E3SoqUMG4bXwzX2QmrCuxeq7h+S3g1P9E36lnSX29FzJBdyRTV62SXRgPmH5k+sP1zvx9HtxWPU6a6VCbtqAjIoXAJ4sUTaPiI6wc08XGy+iJmI25zSK97QBmH6j5vr06VqUj+s1dsDSGOumgHN7fpJ5kQ2gxqVz7fgU30nar7854QpoN36TumDU+T3nRtZTjsQ2fLybdNJhaetwlkXGnt79HtXnvGy/rk0PXOZ7mwntIvsGnNfBrAgMBAAECggEAYgKs+/EQNlDlzQ3Mh8dEYLUAEtpNnOoLX3NSxBlhJGBYvuBdOsdNGBNT0ln9iRuAo+dN+0hPdozd/CRQc9k0cqLEg2pvSGGt2JNxupy2JzJRQLeGx2TNlUEw0nx0zCoj4dKRG+l6GoLToGTguHAJthpA3RL0cl2YUplgOIfRZn0anffYQR/g/SnDMivPNCXT36OJXss8Q9PG9E09zgcODW0lajJSYVLGecKxv4VLACX5473q57gO1Zdod1ufY/MQzyW7qIFkXNRrlJDNOaEJvyUpJkx5wyr17YBZDCdZzkOdsNeDMp6FNoJOs3d/j5KjtFz1fPCb5pB+a0+LJTSZiQKBgQD/9HifgfbPnIwm7Xfq5WaofJDCg+Le72hTOOPl+iBg3BoYSCBSciTgKVhn4rc70R2oU4VVcKIjzAQlXeqMlYOkYsrGZRdUeXH7eVD+xv70vuteL9N90bAhnMD9hBQIgJHmU/7VpZvKcubwuuStUSYI78rC2xd2VBZwxRdECgvCTwKBgQCAPsKhCgJHRflhZNCUfwa/WoSqOmcU/fiykj6BOykdqRVSI3y56+pM8CiceAFSOanIsKX1X/SwrIU4IrGEfzxxUnoWeeilgUh3LzlOFCBEvx3KkyzHmItgDMF0SmJhvjWSDENfXKyp6iQ0qPEPduDSduRPSGDjW7gUvGDw14+1JQKBgQCtiYFnFGTUql1T31mYx2RtA9faKjVd1ZA0LLRHrujZAyImKHDRtVZPLXklJk/5nHSxNb+HCFDGWILPGvf1zUFt6RV2of/JZHXlfIFv3FfsdK18NIz+F3eFspQezrgOc614LqbvD9Oq1XDC8tQstoJFON+OcaFctvJoDxkRJoaGfQKBgHHlSz1ygiuCfcR9oLEBT6DKZ2A3li0SkbkmINTtEcsBy6mzUqZcWy4RbD/qZGH+TEeseve9TLdLwNTmGvotdiS+tycXBgNXGmCfVCl/vljP00mCBBcP51hae8Un+tAL+c/HjF45FC+jfpw2HkE5ttm0NXgkM8g4QPhJhV7gUwTxAoGABdCmkZ0/Qoj81yvE6wqMNODqyjLoY5LeX5NBaC7YalrI6YemyYrxUOK30a/J3LMtbtoctAKrLkYkH1DRU+UF1fizAEiZ8pvgWwT/5VCA9nb+klaz7a6EhL9oruRGI8W2Th5GamjwL4fFCQHpr4zQ7Mh//XKKOUA+PN0R9g1gK7w=";
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId("2021004156667072");
        certAlipayRequest.setPrivateKey(privateKey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(new File("/root/alipayfile/appCertPublicKey_2021004156667072.crt").getAbsolutePath());
        certAlipayRequest.setAlipayPublicCertPath(new File("/root/alipayfile/alipayCertPublicKey_RSA2.crt").getAbsolutePath());
        certAlipayRequest.setRootCertPath(new File("/root/alipayfile/alipayRootCert.crt").getAbsolutePath());
        return certAlipayRequest;
    }

    /**
     * =========================start=========================================
     * gmt_create:2024-07-26 09:29:48
     * charset:UTF-8
     * seller_email:1169794338@qq.com
     * subject:会员升级：终身会员
     * sign:nA5NxHsKE8uLAfwyYjz+SItp6OViyIJUkxna+zWxTQSsUi6+9v1eCe3s8DbuKNgdwfu7XeX2QACHu09bYZswHdcx0rJDcEcbdFChRuf05hB3HNXIGLgK1Imyn1TPv6KBYZYK1a9UzDinLZ177YKkDdY2XPAXXgG871jhekyZMnH909efDiE57EkWX/bzESmKry9BFG0NqmVuqJac2Hc+LtUFNbeuUKXOSt/cvyn0qUW6JDAo8yQAmAOxVkmpNWw43DDuoRUYWveRMqU3ky9s8zzQasGKe9l+mxpTDNnUUimh27FJE8+gGRj9IDLIHwxqIzWimDc5dhKVMaMarWQHdg==
     * body:会员升级
     * buyer_open_id:085MaAua7UaNHu9_R8n_UM5exKOBVVzSd2afxeWUgEzP6A7
     * invoice_amount:0.01
     * notify_id:2024072601222092950032851441399962
     * fund_bill_list:[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}]
     * notify_type:trade_status_sync
     * trade_status:TRADE_SUCCESS
     * receipt_amount:0.01
     * app_id:2021004156667072
     * buyer_pay_amount:0.01
     * sign_type:RSA2
     * seller_id:2088402666505772
     * gmt_payment:2024-07-26 09:29:49
     * notify_time:2024-07-26 09:29:50
     * merchant_app_id:2021004156667072
     * version:1.0
     * out_trade_no:1721957349555
     * total_amount:0.01
     * trade_no:2024072622001432851402069421
     * auth_app_id:2021004156667072
     * buyer_logon_id:bla***@163.com
     * point_amount:0.00
     * =========================end==========================================
     * @param request
     * @return
     */
    @PostMapping("/callback")
    public String callback(HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        System.out.println("=========================start=========================================");
        while (parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            System.out.println(key+":"+value);
        }
        System.out.println("=========================end==========================================");


        Order order = orderService.findBySn(request.getParameter("out_trade_no"));
        if(order.getStatus()==0){
            String tradeStatus = request.getParameter("trade_status");
            String totalAmount = request.getParameter("total_amount");
            BigDecimal amount = new BigDecimal(totalAmount);
            BigDecimal subtract = order.getAmount().subtract(amount);
            if(subtract.compareTo(new BigDecimal("0.01"))<=0 || subtract.compareTo(new BigDecimal("-0.01"))<=0){
                if(StringUtils.equalsAnyIgnoreCase("TRADE_SUCCESS",tradeStatus)){
                    // 支付成功
                    order.setStatus(2);
                    order.setCompleteDate(new Date());
                    orderService.update(order);
                    orderService.finish(order);
                }
            }
        }


        return "success";
    }
}
