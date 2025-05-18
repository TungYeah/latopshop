package vn.minhtung.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.minhtung.laptopshop.domain.Cart;
import vn.minhtung.laptopshop.domain.CartDetail;
import vn.minhtung.laptopshop.domain.Order;
import vn.minhtung.laptopshop.domain.OrderDetail;
import vn.minhtung.laptopshop.domain.Product;
import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session, 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id);

        Cart cart = this.productService.fetchByUser(user);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();

        double totalPrice = 0;

        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDeatail(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @GetMapping("/thanks")
    public String getThanksPage(Model model) {
        return "client/cart/thanks";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long id = (long) session.getAttribute("id");
        user.setId(id);
        this.productService.handlePlaceOrder(user, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String addProductFromViewToCart(HttpServletRequest request,
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getProductPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
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
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Product> prs = this.productService.getAllProducts(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/show";
    }
}
