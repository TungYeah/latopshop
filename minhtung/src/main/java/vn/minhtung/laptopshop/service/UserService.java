package vn.minhtung.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.minhtung.laptopshop.domain.Role;
import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.domain.dto.RegisterDTO;
import vn.minhtung.laptopshop.repository.OrderRepository;
import vn.minhtung.laptopshop.repository.ProductRepository;
import vn.minhtung.laptopshop.repository.RoleRepository;
import vn.minhtung.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productrRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
        this.productrRepository = productRepository;

    }

    public String handleHello() {
        return "helloFromService";
    }

    public Page<User> getAllUsers(Pageable page) {
        return this.userRepository.findAll(page);
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user) {
        User minhtung = this.userRepository.save(user);
        return minhtung;
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }

    public long countProducts() {
        return this.productrRepository.count();
    }
}
