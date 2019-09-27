package com.imooc.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * UserController测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(get("/user/query1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) // 返回状态码为200
                .andExpect(jsonPath("$.length()").value(3));  // 返回数据的集合长度是3
    }

    @Test
    public void whenQuerySuccess2() throws Exception {
        mockMvc.perform(get("/user/query2")
                .param("username", "alex")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void whenQuerySuccess3() throws Exception {
        mockMvc.perform(get("/user/query3")
                .param("username", "alex")
                .param("age", "18")
                .param("ageTo", "22")
                .param("size", "15")
                .param("page", "3")
                .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()) // 返回状态码为200
                .andExpect(jsonPath("$.length()").value(3));  // 返回数据的集合长度是3
    }

    @Test
    public void whenQuerySuccess4() throws Exception {
        String result = mockMvc.perform(get("/user/query4")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tom"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

    // 测试创建用户
    @Test
    public void whenCreateSuccess() throws Exception {
        Date birthday = new Date();
        System.out.println("test birthday_timestamp:" + birthday.getTime());
        String content = "{\"username\":\"zhangsan\", \"password\":\"zhangsan-password\",\"birthday\": "
                + birthday.getTime() + "}";
        String result = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("test result:" + result);
    }

    // 测试校验
    @Test
    public void whenCreateFailByValid() throws Exception {
        Date birthday = new Date();
        System.out.println(birthday.getTime());
        String content = "{\"username\":\"zhangsan\", \"password\":null,\"birthday\": "
                + birthday.getTime() + "}";
        String result = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        Date birthday = new Date(LocalDateTime.now()
                .plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(birthday.getTime());
        String content = "{\"username\":\"zhangsan\", \"password\":null,\"birthday\": "
                + birthday.getTime() + "}";
        String result = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception{
        String result = mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile("file",
                        "text.txt",
                        "multipart/form-data",
                        "hello update".getBytes("UTF-8"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
