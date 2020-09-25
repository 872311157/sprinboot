package com.example.springboot.demo.controller;

import com.example.springboot.demo.entity.BootUser;
import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.service.BootUserService;
import com.example.springboot.demo.util.configUtil.ConfigProperties;
import com.example.springboot.demo.util.ajaxUtil.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * TODO
 *
 * @author 韩路路
 * @date 2020-8-31 15:17
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private BootUserService bootUserService;
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("")
    public String login() {

        return "login";
    }

    /**
     * 错误界面
     * @return
     */
    @RequestMapping("error")
    public String error() {
        return "error";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("submit")
    public ModelAndView submit(@RequestParam Map<String, String> user, ModelAndView model, HttpServletRequest request) {
        String username = user.get("username");
        String password = user.get("password");
        System.out.println(username + "--" + password);
        BootUser bootUser = this.bootUserService.login(username, password);
        if (null != bootUser)
        {
            model = new ModelAndView("redirect:main");//重定向主页面
            //使用session传递用户数据
            HttpSession session = request.getSession();
            session.setAttribute("user",bootUser);
        }else {
            model.setViewName("redirect:error");
        }
        return model;
    }

    /**
     * @Author hanlulu
     * @Description 重定向主页面
     * @Date  2020-9-24
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("main")
    public String main() {
        return "main";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("regist")
    @ResponseBody
    public ResultEntity<UserInfo> regist(@RequestParam Map<String, String> user) {
        ResultEntity<UserInfo> results = ResultEntity.SUCCESS_RESULT;
        String username = user.get("username");
        String password = user.get("password");
        String email = user.get("email");

        System.out.println(this.configProperties.getClassName());

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email))
        {
            results = ResultEntity.ERROR_RESULT;
        }else{
            BootUser bootUser = new BootUser();
            bootUser.setUsername(username);
            bootUser.setPasswords(password);
            bootUser.setEmail(email);
            this.bootUserService.insert(bootUser);
        }
        return results;
    }

    /**
     * 返回模板
     * @param model
     * @return
     */
    @RequestMapping("/getUser")
    public String getUser(Model model) {
        model.addAttribute("userName","小明");
        model.addAttribute("phone","123456");

        List<UserInfo> res = new ArrayList<UserInfo>();
        UserInfo user1 = new UserInfo("张三", "男", 21, "2340140201");
        UserInfo user2 = new UserInfo("李四", "男", 25, "2340140202");
        res.add(user1);
        res.add(user2);
        model.addAttribute("userList",res);
        return "test/user";
    }

}
