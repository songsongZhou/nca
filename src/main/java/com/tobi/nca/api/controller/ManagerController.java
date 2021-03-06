package com.tobi.nca.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.generator.pro.entity.*;
import com.tobi.nca.api.services.ManagerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("后台管理")
@RestController
@CrossOrigin
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



    // 微信模块
    @GetMapping("getCustomer")
    public IPage<Customer> getCustomer(int current) {
        return managerService.getCustomer(current);
    }

    @GetMapping("getModule")
    public ApiResult getModule(){
        return managerService.getModule();
    }

    @PostMapping("getModuleGoods")
    public ApiResult getModuleGoods(int moduleId){
        return managerService.getModuleGoods(moduleId);
    }

    @PostMapping("addModule")
    public ApiResult addModule(Module module){
        return managerService.addModule(module);
    }

    @PostMapping("addGoods2Module")
    public ApiResult addGoods2Module(ModuleGoods moduleGoods){
        return managerService.addGoods2Module(moduleGoods);
    }

    @GetMapping("getBanner")
    public ApiResult getBanner(){
        return managerService.getBanner();
    }

    @PostMapping("addBanner")
    public ApiResult addBanner(Banner banner){
        return managerService.addBanner(banner);
    }

    // 微信模块



    @PostMapping("uploadImage")
    public ApiResult uploadImage(MultipartFile file)throws Exception{
        return managerService.uploadImage(file);
    }

    @PostMapping("delImages")
    public ApiResult uploadImage(String images){
        return managerService.delImages(images);
    }
}
