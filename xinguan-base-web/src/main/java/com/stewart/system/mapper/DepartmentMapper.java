package com.stewart.system.mapper;

import com.stewart.system.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Stewart
 * @since 2021-01-03
 */
public interface DepartmentMapper extends BaseMapper<Department> {
    List<Department> findDeptAndCount();


}
