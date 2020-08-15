package com.cz.zfy.vhr.mapper;

import com.cz.zfy.vhr.model.Menu;
import com.cz.zfy.vhr.model.Role;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getMenuByHrId(Integer hrid);

    List<Menu> getAllMenusWithRole();
}