package vn.minhtung.laptopshop.controller.client;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class ItemController {
    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        return "client/product/detail";
    }

}
