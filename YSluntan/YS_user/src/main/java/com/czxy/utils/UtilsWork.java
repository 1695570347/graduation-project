package com.czxy.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import utils.IdWorker;

@Component
public class UtilsWork {
    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }
}
