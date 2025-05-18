package vn.minhtung.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.minhtung.laptopshop.domain.Order;
import vn.minhtung.laptopshop.domain.User;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order>  findById(long id);

    List<Order> findByUser(User user);

    
}
