package com.tobi.nca.api.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.generator.pro.entity.*;
import com.google.gson.Gson;
import com.tobi.nca.config.ErrorCode;
import com.tobi.nca.utils.KeyTools;
import com.tobi.nca.utils.PageConfig;
import com.tobi.nca.utils.weixin.AccessTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeiXinService {

    private static final String APP_ID="wx3341de2d2e80d241";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResult shareSignature(String url) {
        //获取签名signature
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature=AccessTokenUtil.getSignature(noncestr,timestamp,url);

        Map<String,Object> map=new HashMap<>();
        map.put("appId",APP_ID);
        map.put("timestamp",timestamp);
        map.put("nonceStr",noncestr);
        map.put("signature",signature);

        System.out.println("appId>>"+APP_ID);
        System.out.println("timestamp>>"+timestamp);
        System.out.println("nonceStr>>"+noncestr);
        System.out.println("signature>>"+signature);
        return ApiResult.ok(map);
    }

    public ApiResult login(String userName, String password) {
        ApiAssert.notNull(ErrorCode.EMPTY, userName, password);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_name", userName);
        Customer user = new Customer().selectOne(qw);

        if (user == null) {
            return ApiResult.failed("该用户没有注册");
        }

        if(user.getDel()==0){
            return ApiResult.failed("用户账号已停用");
        }

        if (user.getPassword().equals(KeyTools.MD5(password))) {
            return ApiResult.ok(user);
        }
        return ApiResult.failed("密码错误");
    }

    public ApiResult register(Customer customer) {
        ApiAssert.notNull(ErrorCode.EMPTY, customer);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("user_name",customer.getUserName());
        Customer customer1=new Customer().selectOne(qw);
        if(customer1!=null){
            return ApiResult.failed("该用户名已被注册,是否去登录");
        }

        customer.setCreateTime(LocalDateTime.now());
        if(customer.insert()){
            return ApiResult.ok(customer);
        }
        return ApiResult.failed("注册失败，请稍后重试");
    }

    public ApiResult getUserById(int userId){
        ApiAssert.notNull(ErrorCode.EMPTY, userId);

        Customer user = new Customer().selectById();
        return ApiResult.ok(user);
    }

    public ApiResult getBanner(){
        QueryWrapper qw=new QueryWrapper();
        List<Banner> banners=new Banner().selectList(qw);
        return ApiResult.ok(banners);
    }

    public ApiResult getModuleGoods() {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("status",1);
        List<Module> module=new Module().selectList(qw);
        if(module.size()<=0){
            return ApiResult.failed("没有活动");
        }


        List<Map> list=new ArrayList<>();
        for (int i = 0; i < module.size(); i++) {
            Map map=new HashMap();
            String sql="SELECT g.id,g.goods_name goodsName,g.goods_image goodsImage,g.price FROM goods g left join" +
                    " module_goods m on g.id=m.goods_id where m.module_id="+module.get(i).getId();
            map.put("module",module.get(i));
            map.put("goods",jdbcTemplate.queryForList(sql));
            list.add(map);
        }
        return ApiResult.ok(list);
    }

    public ApiResult getCategorys() {
        List<Category> category=new Category().selectAll();
        return ApiResult.ok(category);
    }


    public IPage getGoodsByCId(int cId,int current) {
        ApiAssert.notNull(ErrorCode.EMPTY, cId);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("category_id",cId);
        qw.eq("del",1);
        return new Goods().selectPage(new Page<>(current, PageConfig.PAGE_SIZE), qw);
    }

    public ApiResult getCartById(int userId) {
        QueryWrapper qw=new QueryWrapper();
        String sql="SELECT cart.id,cart.num,sku.id skuId,sku.sku_name skuName,sku.sku_price skuPrice," +
                "sku.sku_image skuImage FROM shop_cart cart,goods_sku sku " +
                "where cart.user_id="+userId+" and cart.sku_id=sku.id";

        return ApiResult.ok(jdbcTemplate.queryForList(sql));
    }

    public ApiResult addCart(int userId, ShopCart cart) {
        ApiAssert.notNull(ErrorCode.EMPTY,userId,cart);
        LocalDateTime nowDateTime=LocalDateTime.now();
        cart.setCreateTime(nowDateTime);
        cart.setUpdateTime(nowDateTime);
        if(cart.insert()){
            return ApiResult.ok("加入购物车成功");
        }
        return ApiResult.failed("加入购物车失败");
    }

    @Transactional
    public ApiResult delCart(int userId, String cartIds) {
        ApiAssert.notNull(ErrorCode.EMPTY,userId,cartIds);
        QueryWrapper qw=new QueryWrapper();
        qw.in("id",cartIds);
        qw.eq("user_id",userId);
        boolean isDel=new ShopCart().delete(qw);
        if(isDel){
            return ApiResult.ok("删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @Transactional
    public ApiResult editCart(String carts) {
        ApiAssert.notNull(ErrorCode.EMPTY,carts);
        Map<String,Object> listMap=new Gson().fromJson(carts,Map.class);
        List<ShopCart> cartList= (List<ShopCart>) listMap.get("carts");
        for (int i = 0; i < cartList.size(); i++) {
            ShopCart cart=cartList.get(i);
            cart.setUpdateTime(LocalDateTime.now());
            cart.updateById();
        }
        return ApiResult.failed("修改失败");
    }


    public ApiResult getGoodsDetail(int gId) {
        ApiAssert.notNull(ErrorCode.EMPTY,gId);
        Goods goods=new Goods().selectById(gId);
        if(goods==null){
            return ApiResult.failed("商品不存在");
        }
        QueryWrapper qw=new QueryWrapper();
        qw.eq("goods_id",gId);
        qw.eq("del",1);
        List<GoodsSku> goodsSkus=new GoodsSku().selectList(qw);
        Map map=new HashMap();
        map.put("goods",goods);
        map.put("goodsSku",goodsSkus);
        return ApiResult.ok(map);
    }



}
