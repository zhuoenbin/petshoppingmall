package com.ispan.dogland.service;

import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

import java.util.UUID;

@Service
public class OrderService {

    public String ecpayCheckout(String price, String url) {

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        AllInOne all = new AllInOne("");

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2017/01/01 08:05:23");
        obj.setTotalAmount(price);
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        obj.setReturnURL("<http://211.23.128.214:5000>");
        obj.setClientBackURL("http://localhost:5173/" + url);
        obj.setNeedExtraPaidInfo("N");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
}
