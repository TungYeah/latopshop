package vn.minhtung.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.minhtung.laptopshop.domain.Cart;
import vn.minhtung.laptopshop.domain.User;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(long id);

    Cart findByUser(User user);
}
