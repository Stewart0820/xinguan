package com.stewart.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stewart.response.Result;
import com.stewart.system.entity.User;
import com.stewart.system.service.UserService;
import com.stewart.vo.system.UserEditVO;
import com.stewart.vo.system.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Stewart
 * @since 2021-01-02
 */
@RestController
@RequestMapping("/user")
@Api(value = "系统用户模块",tags = "系统用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findUserList")
    @ApiOperation(value = "用户列表",notes = "查询所有用户信息")
    public Result findUserList(@RequestParam(required = true,defaultValue = "1")Integer current,
                               @RequestParam(required = true,defaultValue = "6")Integer size){
        Page<User> page = new Page<>(current, size);
        //单表的时候其实这个方法是非常好用的
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Page<User> userPage= userService.page(page);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();
        return Result.ok().data("total",total).data("records",records);
    }

    /**
     * 根据用户条件进行分页查询
     *
     * @param current
     * @param size
     * @param userVO
     * @return
     */
    @ApiOperation(value="条件查询",notes="用户条件查询")
    @PostMapping("/findUserPage")
    public Result findUserPage(@RequestParam(required = true, defaultValue = "1") Integer current,
                               @RequestParam(required = true, defaultValue = "6") Integer size,
                               @RequestBody UserVO userVO) {
        //对用户进行分页,泛型中注入的就是用户实体类
        Page<User> page = new Page<>(current, size);
        //单表的时候其实这个方法是非常好用的
        QueryWrapper<User> wrapper = getWrapper(userVO);
        IPage<User> userPage = userService.findUserPage(page, wrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();
        return Result.ok().data("total", total).data("records", records);
    }
    /**
     * 封装查询条件
     *
     * @param userVO
     * @return
     */
    private QueryWrapper<User> getWrapper(UserVO userVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userVO != null) {
            if (!StringUtils.isEmpty(userVO.getDepartmentId())) {
                queryWrapper.eq("department_id", userVO.getDepartmentId());
            }
            if (!StringUtils.isEmpty(userVO.getUsername())) {
                queryWrapper.eq("username", userVO.getUsername());
            }
            if (!StringUtils.isEmpty(userVO.getEmail())) {
                queryWrapper.eq("email", userVO.getEmail());
            }
            if (!StringUtils.isEmpty(userVO.getSex())) {
                queryWrapper.eq("sex", userVO.getSex());
            }
            if (!StringUtils.isEmpty(userVO.getNickname())) {
                queryWrapper.eq("nickname", userVO.getNickname());
            }
        }
        return queryWrapper;
    }

    @ApiOperation(value="添加用户",notes="添加用户信息")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
        try{
            userService.addUser(user);
            return Result.ok();
        }catch(Exception e){
            return Result.error();
        }
    }

    @ApiOperation(value="修改用户",notes="修改用户信息")
    @PostMapping("/editUser")
    public Result editUser(@RequestBody UserEditVO userEditVO){
        try{
            userService.updateUser(userEditVO.getId(),userEditVO);
            return Result.ok();
        }catch(Exception e){
            return Result.error();
        }
    }

    @ApiOperation(value="编辑用户",notes="获取用户的详情，编辑用户信息")
    @GetMapping("/editUser")
    public Result selectUserById(@RequestParam(required=true) Long id){
        if(id!=null){
            UserEditVO userEditVO = userService.editUser(id);
            return Result.ok().data("userInfo",userEditVO);
        }else{
            return Result.error();
        }
    }
}

