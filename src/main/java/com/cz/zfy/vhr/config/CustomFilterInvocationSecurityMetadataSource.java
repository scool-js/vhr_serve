package com.cz.zfy.vhr.config;

import com.cz.zfy.vhr.model.Menu;
import com.cz.zfy.vhr.model.Role;
import com.cz.zfy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/***
 * 根据用户传来的请求地址分析请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuServicel;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuServicel.getAllMenusWithRole();
        for(Menu menu:menus){
            if(antPathMatcher.match(menu.getUrl(), requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for(int i =0;i<roles.size();i++){
                    str[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
