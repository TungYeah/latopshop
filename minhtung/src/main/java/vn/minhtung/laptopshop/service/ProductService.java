package vn.minhtung.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.minhtung.laptopshop.domain.Cart;
import vn.minhtung.laptopshop.domain.CartDetail;
import vn.minhtung.laptopshop.domain.Product;
import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.repository.CartDetailRepository;
import vn.minhtung.laptopshop.repository.CartRepository;
import vn.minhtung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product) {
        Product minhtung = this.productRepository.save(product);
        return minhtung;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public Optional<Product> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }

            Optional<Product> pr = this.productRepository.findById(productId);
            if (pr.isPresent()) {
                Product p = pr.get();

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, p);
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(p);
                    cd.setPrice(p.getPrice());
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);

                    cart.setSum(cart.getSum() + 1);
                    cart = this.cartRepository.save(cart);
                    session.setAttribute("sum", cart.getSum());
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }
}
