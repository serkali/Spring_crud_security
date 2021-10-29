package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<User> listProducts = userRepository.findAll();
        model.addAttribute("users", listProducts);

        return "/index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "new_user";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("user") User user) {
        userRepository.save(user);

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit_user");
        Optional<User> user = userRepository.findById(id);
        modelAndView.addObject("user", user);

        return modelAndView;

    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }


/*    @GetMapping("/")
    public String showUser(ModelMap model) {
        User user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "/";
    }*/
}

