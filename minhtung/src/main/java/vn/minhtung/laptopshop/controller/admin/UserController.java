package vn.minhtung.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.service.UploadService;
import vn.minhtung.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/admin/user")
    public String getCreateUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 1);
        Page<User> usersPage = this.userService.getAllUsers(pageable);
        List<User> users = usersPage.getContent();
        model.addAttribute("users1", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getPageUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User minhtung, BindingResult newUserBindingResult,
            @RequestParam("minhtungFile") MultipartFile file) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = this.uploadService.handleUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(minhtung.getPassword());
        minhtung.setAvatar(avatar);
        minhtung.setPassword(hashPassword);
        minhtung.setRole(this.userService.getRoleByName(minhtung.getRole().getName()));

        this.userService.handleSaveUser(minhtung);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
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
            if (minhtung.getRole() != null && minhtung.getRole().getName() != null) {
                currentUser.setRole(this.userService.getRoleByName(minhtung.getRole().getName()));
            }

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
