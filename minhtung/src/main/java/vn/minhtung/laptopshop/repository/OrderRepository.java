package vn.minhtung.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.minhtung.laptopshop.domain.Order;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order>  findById(long id);

}
