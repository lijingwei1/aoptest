package com.aoptest;

import com.aoptest.inter.OperatorIService;
import com.aoptest.service.OperatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AoptestApplicationTests {
    @Autowired
    public OperatorIService operatorIService;
    @Test
    public void contextLoads(){
        try{
            System.out.println("==============");
            operatorIService.getOwe("select * from acct_owe-");
            //operatorIService.getAcct("ACCT/SELECT:");
            //operatorIService.getOwe("1");
            System.out.println("==============");
        }catch (Exception e){
            System.out.println("3333333333333333333333333333");
            System.out.println(e);
            System.out.println(e.getCause());
        }
    }

}
