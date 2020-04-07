package com.imooc.cofig;

import com.imooc.service.OrderStatusService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

@Component
public class AutoTask {

    @Autowired
    OrderStatusService orderStatusService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrderTask(){
        System.out.println("定时任务执行开始，执行时间："+ DateUtil.dateToString(new Date(),DateUtil.DATETIME_PATTERN));
        orderStatusService.closeOrder();
        System.out.println("定时任务执行结束，执行时间："+ DateUtil.dateToString(new Date(),DateUtil.DATETIME_PATTERN));
    }
}
