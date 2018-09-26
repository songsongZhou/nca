package com.tobi.nca.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.generator.pro.entity.Customer;
import com.generator.pro.entity.ShopCart;
import com.tobi.nca.api.services.WeiXinService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("微信接口")
@RestController
@RequestMapping("/weixin")
@CrossOrigin
public class WeiXinController {

    @Autowired
    private WeiXinService weiXinService;


    @PostMapping("/shareSignature")
    public ApiResult shareSignature(String url){
        return weiXinService.shareSignature(url);
    }

    //用户
    @PostMapping("/login")
    public ApiResult login(String userName,String password){
        return weiXinService.login(userName,password);
    }

    @PostMapping("/register")
    public ApiResult register(Customer customer){
        return weiXinService.register(customer);
    }

    @PostMapping("getUser")
    public ApiResult getUser(int userId){
        return weiXinService.getUserById(userId);
    }


    //用户


    //首页
    @GetMapping("/getBanner")
    public ApiResult getBanner(){
        return weiXinService.getBanner();
    }

    @GetMapping("/getModuleGoods")
    public ApiResult getModuleGoods(){
        return weiXinService.getModuleGoods();
    }

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

    //商品

    @PostMapping("/getGoodsDetail")
    public ApiResult getGoodsDetail(int gId){
        return weiXinService.getGoodsDetail(gId);
    }

    //商品



}
