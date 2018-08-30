package com.tobi.nca.manager.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.generator.pro.entity.Category;
import com.generator.pro.entity.User;
import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;
import com.tobi.nca.config.ErrorCode;
import com.tobi.nca.utils.KeyTools;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ManagerService {

    public ApiResult login(String loginName, String password) {
        ApiAssert.notNull(ErrorCode.EMPTY, loginName, password);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_name", loginName);
        User user = new User().selectOne(qw);

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

    public ApiResult addOrUpdateUser(User user) {
        if(user.getCreateTime()==null){
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
        User user=new User().selectById(id);
        if (user.getDel()==1) {
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
        if(category.insertOrUpdate()){
            return ApiResult.ok("操作成功");
        }
        return ApiResult.failed("操作失败");
    }
}
