package com.bootx.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.entity.Order;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberRankService;
import com.bootx.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

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

    @Value("${certPath}")
    private String certPath;
    @Value("${alipayPublicCertPath}")
    private String alipayPublicCertPath;
    @Value("${rootCertPath}")
    private String rootCertPath;

    @PostMapping("/create")
    public Result create(HttpServletRequest request, @CurrentUser Member member, Integer type, Long memberRankId) throws AlipayApiException {
        MemberRank memberRank = memberRankService.find(memberRankId);
        if(memberRank==null){
            return Result.error("非法请求");
        }
        String token = request.getHeader("token");
        String deviceId = request.getHeader("deviceId");
        String ip = request.getHeader("ip");
        if(StringUtils.isBlank(token) || StringUtils.isBlank(deviceId) || StringUtils.isBlank(ip)||type==null){
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
        payRequest.setNotifyUrl("https://965z2991e.oicp.vip/api/payment/callback");
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
        certAlipayRequest.setCertPath(new File(certPath).getAbsolutePath());
        certAlipayRequest.setAlipayPublicCertPath(new File(alipayPublicCertPath).getAbsolutePath());
        certAlipayRequest.setRootCertPath(new File(rootCertPath).getAbsolutePath());
        return certAlipayRequest;
    }

    @PostMapping("/callback")
    public String callback(HttpServletRequest request){
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
