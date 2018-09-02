package com.tobi.nca.manager.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.generator.pro.entity.Category;
import com.generator.pro.entity.Goods;
import com.generator.pro.entity.User;
import com.tobi.nca.manager.services.ManagerService;
import com.tobi.nca.utils.GoodsVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("delCategory")
    public ApiResult delCategory(int id){
        return managerService.delCategory(id);
    }


    //分类管理类


    //商品管理
    @GetMapping("getGoods")
    public IPage<Goods> getGoods(int current){
        return managerService.getGoods(current);
    }

    @PostMapping("addOrUpdateGoods")
    public ApiResult addOrUpdateGoods(@RequestBody String goodsVo){
        return managerService.addOrUpdateGoods(goodsVo);
    }

    //商品管理

}
