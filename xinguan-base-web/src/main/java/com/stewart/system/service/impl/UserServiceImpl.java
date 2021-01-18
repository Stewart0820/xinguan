package com.stewart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stewart.handle.BusinessException;
import com.stewart.response.ResultCode;
import com.stewart.system.entity.Department;
import com.stewart.system.entity.User;
import com.stewart.system.enums.UserStatusEnum;
import com.stewart.system.enums.UserTypeEnum;
import com.stewart.system.mapper.DepartmentMapper;
import com.stewart.system.mapper.UserMapper;
import com.stewart.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Stewart
 * @since 2021-01-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public IPage<User> findUserPage(Page<User> page, QueryWrapper<User> wrapper) {
        return this.baseMapper.findUserPage(page,wrapper);
    }

    @Override
    public void addUser(User user) {

        // 判断是否增加了同一个
        String username = user.getUsername();


        // 获取部门id
        Long departmentId = user.getDepartmentId();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Integer integer = this.baseMapper.selectCount(wrapper);
        if(integer!=0){
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(),
                    ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }

        Department department = departmentMapper.selectById(departmentId);
        if (department==null){
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),
                    ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }
        String salt  = UUID.randomUUID().toString().substring(0,32);
        user.setSalt(salt);
        // 使用springsecurity 自带的
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());
        user.setDeleted(0);
        this.baseMapper.insert(user);
    }
}



















