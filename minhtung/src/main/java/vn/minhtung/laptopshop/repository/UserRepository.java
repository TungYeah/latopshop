package vn.minhtung.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.minhtung.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findOneByEmail(String email);

    // List<User> findAll();

    User findById(long id);

    void deleteById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
