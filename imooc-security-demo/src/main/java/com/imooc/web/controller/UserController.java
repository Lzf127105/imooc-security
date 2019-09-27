package com.imooc.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.exception.UserNotExistException;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private List<User> getThreeEmptyUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        return userList;
    }

    // 无参数查询
    @RequestMapping(value = "query1", method = RequestMethod.GET)
    public List<User> query() {
        return getThreeEmptyUsers();
    }

    // 根据用户名查询 @RequestParam指定参数信息
    @RequestMapping(value = "query2", method = RequestMethod.GET)
    public List<User> query2(@RequestParam(name = "username", required = false, defaultValue = "leo")
                                         String username) {
        System.out.println(username);
        return getThreeEmptyUsers();
    }

    // 将查询参数组装到对象中, @PageableDefault封装分页参数
    @RequestMapping(value = "query3", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询服务") // swagger生成api文档的方法的备注说明
    public List<User> query3(UserQueryCondition userQueryCondition,
                             @PageableDefault(page = 1, size = 10, sort = "username,asc age,desc")
                                     Pageable pageable) {
        System.out.println(
                ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(
                ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));

        return getThreeEmptyUsers();
    }

    // 使用@JsonView控制json输出内容,详情返回密码,列表不返回
    @RequestMapping(value = "query4", method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> query4() {
        return getThreeEmptyUsers();
    }

    // @PathVariable映射url片段到java方法的参数
    // 在url声明中使用正则表达式,来验证入参（:\\d+表示只能是数字）
    // 使用@JsonView控制json输出内容,详情返回密码,列表不返回
    // 测试Spring Boot异常  /user/2
    // 测试自定义异常 /user/0
    @RequestMapping(value = "{id:\\d+}", method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam("用户ID") @PathVariable(name = "id") String id) {
        if ("0".equals(id)) {
            throw new UserNotExistException(id);
        } else if ("2".equals(id)) {
            throw new RuntimeException("模拟runtime exception");
        }

        System.out.println("id:" + id);
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    // 使用@RequesBody映射请求体到java方法参数
    // 使用常用注解验证入参 @Valid注解表示使用实体类内的注解验证参数(也包含自定的)
    // 自定义注解 MyConstraint
    // 对日期类型的处理,传递传时间戳
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream()
                    .forEach(e -> System.out.println("controller errors:" + e.getDefaultMessage()));
        }
        System.out.println("controller user:"
                + ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        user.setId(1);
        return user;
    }

    // 数据校验
    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable String id, @Valid @RequestBody User user, BindingResult errors) {
        System.out.println(id);
        if (errors.hasErrors()) {
            errors.getAllErrors().stream()
                    .forEach(e -> {
                        FieldError fieldError = (FieldError) e;
                        System.out.println(fieldError.getField());
                        System.out.println(e.getDefaultMessage());
                    });
        }
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        user.setId(1);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println("id:" + id);
    }

    @GetMapping("/me")
    public Object me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/me2")
    public Object me2(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/me3")
    public Object me3(@AuthenticationPrincipal UserDetails user) {
        return user;
    }
}
