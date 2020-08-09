package com.cz.zfy.vhr.config;

import com.cz.zfy.vhr.model.Hr;
import com.cz.zfy.vhr.model.RespBean;
import com.cz.zfy.vhr.service.HrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sun.security.util.Password;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .usernameParameter("username")
                    .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login").successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                Hr hr = (Hr)authentication.getPrincipal();
                RespBean ok = RespBean.ok("登录成功",hr);
                String s = new ObjectMapper().writeValueAsString(ok);
                out.write(s);
                out.flush();
                out.close();
            }
        }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req,
                                                HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                RespBean respBean = RespBean.error("登陆失败！");
                if(e instanceof LockedException){
                    respBean.setMsg("账户被锁定，请联系管理员");
                }else if(e instanceof CredentialsExpiredException){
                    respBean.setMsg("密码过期，请联系管理员");
                }else if(e instanceof AccountExpiredException){
                    respBean.setMsg("账户过期，请联系管理员");
                }else if(e instanceof DisabledException){
                    respBean.setMsg("账户被禁用，请联系管理员");
                }else if(e instanceof BadCredentialsException){
                    respBean.setMsg("用户名或者密码错误，请联系管理员");
                }
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();
            }
        })
        .permitAll()
        .and()
        .logout()
        .logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest req,
                                        HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                out.flush();
                out.close();
            }
        })
        .permitAll()
        .and()
        .csrf().disable();
    }
}
