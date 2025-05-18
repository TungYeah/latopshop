package vn.minhtung.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.minhtung.laptopshop.domain.Product;
import vn.minhtung.laptopshop.service.ProductService;
import vn.minhtung.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
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
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> products = this.productService.getAllProducts(pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("products1", listProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", products.getTotalPages());
        return "admin/product/show";

    }

    @GetMapping("/admin/product/create")
    public String getCreatProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Product minhtung,
            BindingResult newProductbindingResult, @RequestParam("minhtungFile") MultipartFile file) {
        List<FieldError> errors = newProductbindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductbindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String img = this.uploadService.handleUploadFile(file, "product");
        minhtung.setImage(img);
        this.productService.handleSaveProduct(minhtung);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProducts(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProducts(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update/{id}")
    public String postUpdateProducts(Model model,
            @ModelAttribute("newProduct") @Valid Product minhtung,
            BindingResult result,
            @RequestParam("minhtungFile") MultipartFile file) {

        if (result.hasErrors()) {
            return "admin/product/update";
        }

        Product product = this.productService.getProductById(minhtung.getId()).get();
        if (product != null) {
            product.setName(minhtung.getName());
            product.setPrice(minhtung.getPrice());
            product.setDetailDesc(minhtung.getDetailDesc());
            product.setShortDesc(minhtung.getShortDesc());
            product.setFactory(minhtung.getFactory());
            product.setTarget(minhtung.getTarget());
            product.setQuantity(minhtung.getQuantity());

            if (!file.isEmpty()) {
                String img = this.uploadService.handleUploadFile(file, "product");
                product.setImage(img);
            }
        }

        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProducts(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String postDeleteProducts(Model model, @ModelAttribute("newProduct") Product minhtung) {
        this.productService.deleteProduct(minhtung.getId());
        return "redirect:/admin/product";
    }
}
