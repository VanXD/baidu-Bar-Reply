package com.vanxd.baidubar.start;

import com.vanxd.baidubar.component.MessagePool;
import com.vanxd.baidubar.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wyd on 2017-03-03.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.vanxd.baidubar")
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }

    @Bean("messagePool")
    public MessagePool getMessagePool() {
        MessagePool messagePool = new MessagePool();
        String[] sentences = FileUtil.textInLine("a.txt").split("。");
        MessagePool.setSentences(sentences);
        MessagePool.setCount(0);
        return messagePool;
    }

}
