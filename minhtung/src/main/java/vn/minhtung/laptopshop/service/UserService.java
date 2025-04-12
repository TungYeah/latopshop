package vn.minhtung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.minhtung.laptopshop.domain.User;
import vn.minhtung.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
