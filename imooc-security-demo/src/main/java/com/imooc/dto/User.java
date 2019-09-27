package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class User {

    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {};

    @JsonView(UserSimpleView.class)
    private Integer id;

    @JsonView(UserSimpleView.class)
    @MyConstraint(message = "用户名重复")//这是自定义的验证器对用户名的验证
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    public User() {
        this.username = "fake username";
        this.password = "fake password";
    }
}
