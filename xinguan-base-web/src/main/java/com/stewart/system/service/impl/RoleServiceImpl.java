package com.stewart.system.service.impl;

import com.stewart.system.entity.Role;
import com.stewart.system.mapper.RoleMapper;
import com.stewart.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Stewart
 * @since 2021-01-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
