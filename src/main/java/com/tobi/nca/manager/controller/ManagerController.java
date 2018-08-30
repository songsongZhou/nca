package com.tobi.nca.manager.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.generator.pro.entity.Category;
import com.generator.pro.entity.User;
import com.tobi.nca.manager.services.ManagerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("后台管理")
@RestController
//@CrossOrigin
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    //账号的增删改查
    @PostMapping("/login")
    public ApiResult login(String loginName, String password) {
        return managerService.login(loginName,password);
    }
    @PostMapping("/addOrUpdateUser")
    public ApiResult addOrUpdateUser(User user) {
        return managerService.addOrUpdateUser(user);
    }

    @PostMapping("/delUser")
    public ApiResult delUserById(int id) {
        return managerService.delUserById(id);
    }
    @GetMapping("getUser")
    public ApiResult getUserById(int id) {
        return managerService.getUserById(id);
    }
    @GetMapping("getUsers")
    public IPage<User> getUsers(int current) {
        return managerService.getUsers(current);
    }
    //账号的增删改查


    //分类管理类
    @GetMapping("getCategorys")
    public IPage<Category> getCategorys(int current){
        return managerService.getCategorys(current);
    }

    @GetMapping("getCategory")
    public Category getCategoryById(int id){
        return managerService.getCategoryById(id);
    }
    @PostMapping("/addOrUpdateCategory")
    public ApiResult addCategory(Category category) {
        return managerService.addCategory(category);
    }

    //分类管理类

}
