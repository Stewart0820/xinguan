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
import com.stewart.vo.system.UserEditVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        selectUserName(username);
        selectDepartExist(departmentId);
        // 加盐加密
        String salt  = UUID.randomUUID().toString().substring(0,32);
        user.setSalt(salt);
        // 使用springsecurity 自带的
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());
        user.setDeleted(0);
        this.baseMapper.insert(user);
    }

    /**
     * 修改用户信息
     * @param id
     * @param userEditVO
     */
    @Override
    public void updateUser(Long id, UserEditVO userEditVO) {
        Long departmentId = userEditVO.getDepartmentId();
//        String username = userEditVO.getUsername();
        User selectById = this.baseMapper.selectById(id);
        if (selectById==null){
            throw new BusinessException(ResultCode.USER_NOT_EXIT.getCode(),
                    ResultCode.USER_NOT_EXIT.getMessage());
        }
        selectDepartExist(departmentId);
//        selectUserName(username);

        User user = new User();
        // userEditVO转换为user
        BeanUtils.copyProperties(userEditVO,user);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        this.baseMapper.update(user,wrapper);
    }

    @Override
    public UserEditVO editUser(Long id) {
        selectUserId(id);
        User user = this.baseMapper.selectById(id);
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user,userEditVO);
        return userEditVO;
    }

    /**
     * 根据部门id查询该部门是否存在
     * @param id
     */
    private void selectDepartExist(Long id){
        Department department = departmentMapper.selectById(id);
        if (department==null){
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),
                    ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }
    }


    /**
     * 根据username查询是否有对应的username
     * @param username
     */
    private void selectUserName(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Integer integer = this.baseMapper.selectCount(wrapper);
        if(integer!=0){
            throw new BusinessException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(),
                    ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    /**
     * 根据id查询userId是否存在
     * @param id
     */
    private void selectUserId(Long id){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Integer integer = this.baseMapper.selectCount(wrapper);
        if(integer==0){
            throw new BusinessException(ResultCode.USER_NOT_EXIT.getCode(),
                    ResultCode.USER_NOT_EXIT.getMessage());
        }
    }



}



















