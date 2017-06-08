package com.orange.demo_zhihu.controller;

import com.orange.demo_zhihu.model.User;
import com.orange.demo_zhihu.service.demo_service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2017/6/2.
 * 主页
 */
@Controller
public class indexController {
    @Autowired
    demo_service demo;
    private static final Logger logger= LoggerFactory.getLogger(indexController.class);
    @RequestMapping(path = {"/index", "/"})
    @ResponseBody
    public String index(HttpSession httpSession) {
        logger.info("visit index");
        return "Hello demo_zhihu "+httpSession.getAttribute("msg")+demo.getMessage();
    }
    @RequestMapping(value = "/profile/{groupId}/{userId}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") int type,
                          @RequestParam(value="key",required = false) String key) {
        return String.format("Profile Page of %s %d %d %s",groupId,userId,type,key);
    }
    @RequestMapping(path = {"/vm"},method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1","vvvv");
        List<String> color= Arrays.asList(new String[]{"red","blue","black"});
        model.addAttribute("colors",color);
        Map<String,String>map=new HashMap<>();
        for (int i = 0; i < 3; i++) {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user",new User("Wang"));
        return "home";
    }
    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                           HttpServletResponse response,
                           HttpServletRequest request,
                           HttpSession httpSession
                         // @CookieValue("JSESSIONID") String sessionId
                         ){
        StringBuffer sb=new StringBuffer();
       // sb.append("cookieValue "+sessionId);// @CookieValue("JSESSIONID")必须有JSESSIONID才行，否则网页打不开
        Enumeration<String> headerName=request.getHeaderNames();
        while(headerName.hasMoreElements()){
            String name=headerName.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie:"+cookie.getName()+" value:"+cookie.getValue());
            }
        }
sb.append(request.getMethod()+"<br>");
sb.append(request.getQueryString()+"<br>");
sb.append(request.getPathInfo()+"<br>");
sb.append(request.getRequestURL()+"<br>");
response.addHeader("zhihu","hello");
response.addCookie(new Cookie("username","demo_zhihu"));
return sb.toString();

    }
    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code, HttpSession httpSession){
        httpSession.setAttribute("msg","from redirect");
        RedirectView red;
        red = new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;

    }
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam("key" )String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数错误");
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error"+e.getMessage();
    }



}
