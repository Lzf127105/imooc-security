package com.imooc.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryCondition {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户年龄起始值")
    private Integer age;
    @ApiModelProperty("用户年龄终止值")
    private Integer ageTo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }
}
