package com.example.snsauthenticationapp.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * LINE認証リダイレクトURLへリダイレクト
 * CallBackハンドリング
 * */

@Controller
public class LoginController {

    final LineAuthenticationService lineAuthenticationService;

    public LoginController(LineAuthenticationService lineAuthenticationService){
        this.lineAuthenticationService = lineAuthenticationService;
    }

    /** LINE認証用リダイレクト */
    @GetMapping("/login")
    public RedirectView login() {
        String redirectUrl = lineAuthenticationService.createRedirectURL();
        return new RedirectView(redirectUrl);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String getToken(@RequestParam("code") String code,
                           HttpServletRequest req, HttpServletResponse res) throws Exception{
//        LineToken responseToken = loginRestClient.getToken(code);
//        String accessTK = responseToken.getAccessTK();
//        String refreshTK = responseToken.getRefreshTK();
//        String idTK = responseToken.getIdTK();
//
//        Cookie accessTKCookie = new Cookie("access_token", accessTK);
//        accessTKCookie.setSecure(true);
//        Cookie refreshTKCookie = new Cookie("refresh_token", refreshTK);
//        refreshTKCookie.setSecure(true);
//        Cookie idTKCookie = new Cookie("id_token", idTK);
//        idTKCookie.setSecure(true);
//
//        res.addCookie(accessTKCookie);
//        res.addCookie(refreshTKCookie);
//        res.addCookie(idTKCookie);

        return "redirect:/";
    }
}
