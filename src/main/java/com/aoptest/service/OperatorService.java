package com.aoptest.service;

import com.aoptest.inter.OperatorIService;
import org.springframework.stereotype.Component;

@Component
public class OperatorService implements OperatorIService {

    public String getOwe(String str) {
        System.out.println("----------" + str);
        //float i = 1/0;
        return str + "end";
    }

    @Override
    public String getAcct(String str) {
        System.out.println("--------------Acct------------------");
        return str + " " + "acct_end";
    }
}
