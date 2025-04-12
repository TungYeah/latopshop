package vn.minhtung.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import vn.minhtung.laptopshop.domain.User;

import vn.minhtung.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUser = this.userService.getAllUserByEmail("minhtung2108@gmail.com");
        System.out.println(arrUser);
        model.addAttribute("tung", "test");
        model.addAttribute("minhtung", "fromController");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getCreateUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create")
    public String getPageUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String creatUserPage(Model model, @ModelAttribute("newUser") User minhtung) {
        this.userService.handleSaveUser(minhtung);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }


    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currenUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currenUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User minhtung) {
        User currentUser = this.userService.getUserById(minhtung.getId());
        if (currentUser != null) {
            currentUser.setAddress(minhtung.getAddress());
            currentUser.setFullName(minhtung.getFullName());
            currentUser.setPhone(minhtung.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User minhtung) {
        this.userService.deleteAUser(minhtung.getId());
        return "redirect:/admin/user";
    }
}
