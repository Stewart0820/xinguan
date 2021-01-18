package com.stewart.system.service.impl;

import com.stewart.system.entity.Menu;
import com.stewart.system.mapper.MenuMapper;
import com.stewart.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Stewart
 * @since 2021-01-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
