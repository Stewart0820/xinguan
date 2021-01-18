package com.stewart.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stewart.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Stewart
 * @since 2021-01-02
 */
public interface UserService extends IService<User> {

    IPage<User> findUserPage(Page<User> page,  QueryWrapper<User> wrapper);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);
}
