package com.example.snsauthenticationapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    /** トップページ */
    @GetMapping("")
    public String index(Model model) {
        return "index";
    }

    /** リダイレクト確認用実装 */
    @GetMapping("redirect")
    public String redirect() {
        return "redirect:/";
    }

}
