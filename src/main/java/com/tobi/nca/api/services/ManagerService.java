package com.tobi.nca.api.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.generator.pro.entity.*;
import com.google.gson.Gson;
import com.tobi.nca.config.ErrorCode;
import com.tobi.nca.utils.GoodsVo;
import com.tobi.nca.utils.KeyTools;
import com.tobi.nca.utils.cos.FileUpload2COS;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Service
public class ManagerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResult login(String loginName, String password) {
        ApiAssert.notNull(ErrorCode.EMPTY, loginName, password);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_name", loginName);
        User user = new User().selectOne(qw);

        if (user == null) {
            return ApiResult.failed("该用户没有注册");
        }

        if (user.getDel() == 0) {
            return ApiResult.failed("用户账号已停用");
        }

        if (user.getPassword().equals(KeyTools.MD5(password))) {
            return ApiResult.ok(user);
        }
        return ApiResult.failed("密码错误");
    }

    public ApiResult addOrUpdateUser(User user) {

        if (user.getId() == null) {
            QueryWrapper qw = new QueryWrapper();
            qw.eq("user_name", user.getUserName());
            User oldUser = new User().selectOne(qw);
            if (oldUser != null) {
                return ApiResult.failed(user.getUserName() + "已被使用！");
            }
        }

        if (user.getCreateTime() == null) {
            user.setCreateTime(LocalDateTime.now());
        }
        user.setPassword(KeyTools.MD5(user.getPassword()));
        if (user.insertOrUpdate()) {
            return ApiResult.ok(user);
        }
        return ApiResult.failed("创建用户失败");
    }

    public ApiResult delUserById(int id) {
        ApiAssert.notNull(ErrorCode.EMPTY, id);
        boolean del = new User().deleteById(id);
        if (del) {
            return ApiResult.ok("删除成功");
        }
        return ApiResult.failed("更新失败");
    }

    public ApiResult getUserById(int id) {
        ApiAssert.notNull(ErrorCode.EMPTY, id);
        User user = new User().selectById(id);
        if (user.getDel() == 1) {
            return ApiResult.ok(user);
        }
        return ApiResult.failed("用户账号已停用");
    }

    public IPage<User> getUsers(int current) {
        ApiAssert.notNull(ErrorCode.EMPTY, current);
        return new User().selectPage(new Page<>(current, 12), null);
    }

    public IPage<Category> getCategorys(int current) {
        ApiAssert.notNull(ErrorCode.EMPTY, current);
        return new Category().selectPage(new Page<>(current, 12), null);
    }

    public Category getCategoryById(int id) {
        ApiAssert.notNull(ErrorCode.EMPTY, id);
        return new Category().selectById(id);
    }

    public ApiResult addCategory(Category category) {
        if (category.insertOrUpdate()) {
            return ApiResult.ok("操作成功");
        }
        return ApiResult.failed("操作失败");
    }

    public ApiResult delCategory(int id) {
        ApiAssert.notNull(ErrorCode.EMPTY, id);
        boolean isDel = new Category().deleteById(id);
        if (isDel) {
            return ApiResult.ok("删除分类成功！");
        }
        return ApiResult.failed("删除分类失败！");
    }

    public IPage<Goods> getGoods(int current) {
        ApiAssert.notNull(ErrorCode.EMPTY, current);
        return new Goods().selectPage(new Page<>(current, 12), null);
    }

    @Transactional
    public ApiResult addOrUpdateGoods(String goodsStr) {
        GoodsVo goodsVo = new Gson().fromJson(goodsStr, GoodsVo.class);
        Goods goods = goodsVo.getGoodsVo().getGoods();
        if (!goods.insertOrUpdate()) {
            return ApiResult.failed("操作失败");
        }
        for (int i = 0; i < goodsVo.getGoodsVo().getGoodsSkus().size(); i++) {
            GoodsSku sku = goodsVo.getGoodsVo().getGoodsSkus().get(i);
            if (goods.getId() != null) {
                sku.setGoodsId(goods.getId());
            }
            sku.insertOrUpdate();
        }

        return ApiResult.ok("操作成功");
    }

    public IPage<Customer> getCustomer(int current) {
        ApiAssert.notNull(ErrorCode.EMPTY, current);
        return new Customer().selectPage(new Page<>(current, 12), null);
    }

    public ApiResult addModule(Module module) {
        ApiAssert.notNull(ErrorCode.EMPTY, module);
        if (module.getId() == null) {
            module.setCreateTime(LocalDateTime.now());
        }
        if (module.insertOrUpdate()) {
            return ApiResult.ok("成功");
        }
        return ApiResult.failed("操作失败");
    }

    public ApiResult addGoods2Module(ModuleGoods moduleGoods) {
        ApiAssert.notNull(ErrorCode.EMPTY, moduleGoods);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("module_id", moduleGoods.getModuleId());
        qw.eq("goods_id", moduleGoods.getGoodsId());
        ModuleGoods moduleGoods1 = new ModuleGoods().selectOne(qw);
        if (moduleGoods1 != null) {
            return ApiResult.failed("该活动商品已存在");
        }

        if (moduleGoods.getId() == null) {
            moduleGoods.setCreateTime(LocalDateTime.now());
        }
        if (moduleGoods.insertOrUpdate()) {
            return ApiResult.ok("成功");
        }
        return ApiResult.failed("操作失败");

    }

    public ApiResult uploadImage(MultipartFile file)throws Exception {
        ApiAssert.notNull(ErrorCode.EMPTY, file);
        File f = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else{
            InputStream ins = file.getInputStream();
            f=new File(file.getOriginalFilename());
            inputStreamToFile(ins, f);
        }
        return ApiResult.ok( FileUpload2COS.uploadImage(f));
    }


    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
