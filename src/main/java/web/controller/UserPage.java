package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserPage {

private final UserRepository userRepository;
@Autowired
    public UserPage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(value = "/list")
    public String getUserPage2(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userRepository.getUserByName(principal.getName()));
        return "list";
    }



}
