package com.tobi.nca.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.generator.pro.entity.ShopCart;
import com.tobi.nca.api.services.WeiXinService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api("微信接口")
@RestController
@RequestMapping("weixin")
public class WeiXinController {

    @Autowired
    private WeiXinService weiXinService;
    //首页


    //分类
    @GetMapping("/getCategorys")
    public ApiResult getCategorys(){
        return weiXinService.getCategorys();
    }
    @PostMapping("/getGoodsByCId")
    public IPage getGoodsByCId(int cId,int current){
        return weiXinService.getGoodsByCId(cId,current);
    }
    //分类

    //购物车
    @PostMapping("/getCart")
    public ApiResult getCartById(int userId){
        return weiXinService.getCartById(userId);
    }

    @PostMapping("/addCart")
    public ApiResult addCart(int userId, ShopCart cart){
        return weiXinService.addCart(userId,cart);
    }

    @PostMapping("/delCart")
    public ApiResult delCart(int userId, String cartIds){
        return weiXinService.delCart(userId,cartIds);
    }

    @PostMapping("/editCart")
    public ApiResult editCart(String carts){
        return weiXinService.editCart(carts);
    }
    //购物车
}
