package com.orange.demo_zhihu.controller;

import com.orange.demo_zhihu.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 * 主页
 */
@Controller
public class indexController {
    @RequestMapping(path = {"/index", "/"})
    @ResponseBody
    public String index() {

        return "Hello demo_zhihu";
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
                           HttpSession httpSession){
StringBuffer sb=new StringBuffer();
sb.append(request.getMethod()+"<br>");
sb.append(request.getQueryString()+"<br>");
sb.append(request.getPathInfo()+"<br>");
sb.append(request.getRequestURL()+"<br>");
return sb.toString();


    }
}
