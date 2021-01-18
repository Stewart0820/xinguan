package com.stewart.system.service;

import com.stewart.system.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Stewart
 * @since 2021-01-03
 */
public interface DepartmentService extends IService<Department> {

    List<Department> findDeptAndCount();

}
