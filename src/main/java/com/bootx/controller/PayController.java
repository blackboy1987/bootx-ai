package com.bootx.controller;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.ExtUserInfo;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Value("${APP_CERT_PATH}")
    private String APP_CERT_PATH;

    @Value("${alipay_cert_path}")
    private String alipay_cert_path;

    @Value("${alipayRootCert}")
    private String alipayRootCert;

    @GetMapping("payment")
    public void payment(String[] args) throws AlipayApiException {

        // AES:X/5V3WQgG6T01NfSyNfBag==

        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        // 设置订单绝对超时时间
        model.setTimeExpire("2024-12-31 10:05:00");

        // 设置业务扩展参数
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(System.nanoTime()+"");
        extendParams.setHbFqSellerPercent("100");
        extendParams.setHbFqNum("3");
        extendParams.setIndustryRefluxInfo("{\"scene_code\":\"metro_tradeorder\",\"channel\":\"xxxx\",\"scene_data\":{\"asset_name\":\"ALIPAY\"}}");
        extendParams.setSpecifiedSellerName("XXX的跨境小铺");
        extendParams.setRoyaltyFreeze("true");
        extendParams.setCardType("S0JP0000");
        model.setExtendParams(extendParams);

        // 设置商户订单号
        model.setOutTradeNo("70501111111S001111119");

        // 设置外部指定买家
        ExtUserInfo extUserInfo = new ExtUserInfo();
        extUserInfo.setCertType("IDENTITY_CARD");
        extUserInfo.setCertNo("362334768769238881");
        extUserInfo.setName("李明");
        extUserInfo.setMobile("16587658765");
        extUserInfo.setMinAge("18");
        extUserInfo.setNeedCheckInfo("F");
        extUserInfo.setIdentityHash("27bfcd1dee4f22c8fe8a2374af9b660419d1361b1c207e9b41a754a113f38fcc");
        model.setExtUserInfo(extUserInfo);

        // 设置通知参数选项
        List<String> queryOptions = new ArrayList<String>();
        queryOptions.add("hyb_amount");
        queryOptions.add("enterprise_pay_info");
        model.setQueryOptions(queryOptions);

        // 设置订单总金额
        model.setTotalAmount("9.00");

        // 设置订单标题
        model.setSubject("大乐透");

        // 设置产品码
        model.setProductCode("QUICK_MSECURITY_PAY");

        // 设置公用回传参数
        model.setPassbackParams("merchantBizType%3d3C%26merchantBizNo%3d2016010101111");

        // 设置订单包含的商品列表信息
        List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
        GoodsDetail goodsDetail0 = new GoodsDetail();
        goodsDetail0.setGoodsName("ipad");
        goodsDetail0.setAlipayGoodsId("20010001");
        goodsDetail0.setQuantity(1L);
        goodsDetail0.setPrice("2000");
        goodsDetail0.setGoodsId("apple-01");
        goodsDetail0.setGoodsCategory("34543238");
        goodsDetail0.setCategoriesTree("124868003|126232002|126252004");
        goodsDetail0.setShowUrl("http://www.alipay.com/xxx.jpg");
        goodsDetail.add(goodsDetail0);
        model.setGoodsDetail(goodsDetail);

        // 设置商户的原始订单号
        model.setMerchantOrderNo("20161008001");

        request.setBizModel(model);
        request.setNeedEncrypt(true);
        request.setBizContent("{" +
                "\"merchant_app_id\":\"2021004156667072\"" +
                "  }");
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");


// 提交数据至支付宝时请使用
        AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.certificateExecute(request);


        String orderStr = alipayTradeAppPayResponse.getBody();
        System.out.println(orderStr);

        if (alipayTradeAppPayResponse.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }

    private CertAlipayRequest getAlipayConfig() {
        String privateKey  = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAOPwdPcdyREOMLac4zMKJ1OIjy13LniZN3StbnOhX3RzX92+Jta99Qq8D/XwzdKlaHAY09uw+LwcSm6w2ZwPGk6cCpZjwmO6E3SoqUMG4bXwzX2QmrCuxeq7h+S3g1P9E36lnSX29FzJBdyRTV62SXRgPmH5k+sP1zvx9HtxWPU6a6VCbtqAjIoXAJ4sUTaPiI6wc08XGy+iJmI25zSK97QBmH6j5vr06VqUj+s1dsDSGOumgHN7fpJ5kQ2gxqVz7fgU30nar7854QpoN36TumDU+T3nRtZTjsQ2fLybdNJhaetwlkXGnt79HtXnvGy/rk0PXOZ7mwntIvsGnNfBrAgMBAAECggEAYgKs+/EQNlDlzQ3Mh8dEYLUAEtpNnOoLX3NSxBlhJGBYvuBdOsdNGBNT0ln9iRuAo+dN+0hPdozd/CRQc9k0cqLEg2pvSGGt2JNxupy2JzJRQLeGx2TNlUEw0nx0zCoj4dKRG+l6GoLToGTguHAJthpA3RL0cl2YUplgOIfRZn0anffYQR/g/SnDMivPNCXT36OJXss8Q9PG9E09zgcODW0lajJSYVLGecKxv4VLACX5473q57gO1Zdod1ufY/MQzyW7qIFkXNRrlJDNOaEJvyUpJkx5wyr17YBZDCdZzkOdsNeDMp6FNoJOs3d/j5KjtFz1fPCb5pB+a0+LJTSZiQKBgQD/9HifgfbPnIwm7Xfq5WaofJDCg+Le72hTOOPl+iBg3BoYSCBSciTgKVhn4rc70R2oU4VVcKIjzAQlXeqMlYOkYsrGZRdUeXH7eVD+xv70vuteL9N90bAhnMD9hBQIgJHmU/7VpZvKcubwuuStUSYI78rC2xd2VBZwxRdECgvCTwKBgQCAPsKhCgJHRflhZNCUfwa/WoSqOmcU/fiykj6BOykdqRVSI3y56+pM8CiceAFSOanIsKX1X/SwrIU4IrGEfzxxUnoWeeilgUh3LzlOFCBEvx3KkyzHmItgDMF0SmJhvjWSDENfXKyp6iQ0qPEPduDSduRPSGDjW7gUvGDw14+1JQKBgQCtiYFnFGTUql1T31mYx2RtA9faKjVd1ZA0LLRHrujZAyImKHDRtVZPLXklJk/5nHSxNb+HCFDGWILPGvf1zUFt6RV2of/JZHXlfIFv3FfsdK18NIz+F3eFspQezrgOc614LqbvD9Oq1XDC8tQstoJFON+OcaFctvJoDxkRJoaGfQKBgHHlSz1ygiuCfcR9oLEBT6DKZ2A3li0SkbkmINTtEcsBy6mzUqZcWy4RbD/qZGH+TEeseve9TLdLwNTmGvotdiS+tycXBgNXGmCfVCl/vljP00mCBBcP51hae8Un+tAL+c/HjF45FC+jfpw2HkE5ttm0NXgkM8g4QPhJhV7gUwTxAoGABdCmkZ0/Qoj81yvE6wqMNODqyjLoY5LeX5NBaC7YalrI6YemyYrxUOK30a/J3LMtbtoctAKrLkYkH1DRU+UF1fizAEiZ8pvgWwT/5VCA9nb+klaz7a6EhL9oruRGI8W2Th5GamjwL4fFCQHpr4zQ7Mh//XKKOUA+PN0R9g1gK7w=";
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId("2021004156667072");
        certAlipayRequest.setPrivateKey(privateKey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(new File("D:/cert/appCertPublicKey_2021004156667072.crt").getAbsolutePath());
        certAlipayRequest.setAlipayPublicCertPath(new File("D:/cert/alipayCertPublicKey_RSA2.crt").getAbsolutePath());
        certAlipayRequest.setRootCertPath(new File("D:/cert/alipayRootCert.crt").getAbsolutePath());
        return certAlipayRequest;
    }
}
