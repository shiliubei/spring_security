package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowLoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {

        return "plain-login";

    }
}
