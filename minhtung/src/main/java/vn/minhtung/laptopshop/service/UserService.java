package vn.minhtung.laptopshop.service;

import java.util.List;



import org.springframework.stereotype.Service;

import vn.minhtung.laptopshop.domain.Role;
import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.repository.RoleRepository;
import vn.minhtung.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final  RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }
    public String handleHello(){
        return "helloFromService";
    }
    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User handleSaveUser(User user){
        User minhtung  =  this.userRepository.save(user);
        return minhtung;
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }
    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }
    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }
}
