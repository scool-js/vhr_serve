package com.cz.zfy.vhr.service;

import com.cz.zfy.vhr.mapper.MenuMapper;
import com.cz.zfy.vhr.model.Hr;
import com.cz.zfy.vhr.model.Menu;
import com.cz.zfy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getMenuByHrId(){
        return menuMapper.getMenuByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

//    @Cacheable
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }
}
