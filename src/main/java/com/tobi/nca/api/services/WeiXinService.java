package com.tobi.nca.api.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.generator.pro.entity.Category;
import com.generator.pro.entity.Goods;
import com.generator.pro.entity.GoodsSku;
import com.generator.pro.entity.ShopCart;
import com.google.gson.Gson;
import com.tobi.nca.config.ErrorCode;
import com.tobi.nca.utils.PageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeiXinService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResult login(String username, String password) {
        return ApiResult.failed("登录失败");
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
