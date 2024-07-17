package com.bootx.controller.admin;

import com.bootx.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author black
 */
@Controller("AdminIndexController")
@RequestMapping("/admin")
public class IndexController extends BaseController {

    @GetMapping("/**")
    public String index(){
        return "/admin/index";
    }
}
