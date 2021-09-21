package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.services.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        return "index";
    }

    @RequestMapping("/new")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit_user");
        User user = userService.get(id);
        modelAndView.addObject("user", user);
        return modelAndView;

    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") long id) {
        userService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/user")
    public ModelAndView showUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
