package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lzf
 * @create 2019-09-27 8:59
 * @Description:
 */
@SpringBootApplication
@RestController
public class DemoAppliation {
    public static void main(String[] args) {
        SpringApplication.run(DemoAppliation.class,args);
    }

    @GetMapping(value="/getList")
    @ResponseBody
    public Object getList(){
        return "hello";
    }
}
