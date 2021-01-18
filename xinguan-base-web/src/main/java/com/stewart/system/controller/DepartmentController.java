package com.stewart.system.controller;


import com.stewart.handle.BusinessException;
import com.stewart.response.Result;
import com.stewart.response.ResultCode;
import com.stewart.system.entity.Department;
import com.stewart.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Stewart
 * @since 2021-01-03
 */
@Api(value = "系统部门模块",tags = "系统部门接口")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/findDeptAndCount")
    @ApiOperation(value = "查询部门及人数",notes = "从部门表中分组查询用户人数")
    public Result findDeptAndCount(){
        List<Department> departments = departmentService.findDeptAndCount();
        if (departments.size()==0){
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),
                                        ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }
        return Result.ok().data("departments",departments);
    }
}

