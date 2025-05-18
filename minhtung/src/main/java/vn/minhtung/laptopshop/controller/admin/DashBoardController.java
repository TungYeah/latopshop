package vn.minhtung.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.minhtung.laptopshop.service.UserService;

@Controller
public class DashBoardController {

    private final UserService  userService;
    public DashBoardController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashBoard(Model model) {
        model.addAttribute("countUsers", userService.countUsers());
        model.addAttribute("countProducts", userService.countProducts());
        model.addAttribute("countOrders", userService.countOrders());
        return "admin/dashboard/show";
    }
}
