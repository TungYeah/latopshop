package vn.minhtung.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.minhtung.laptopshop.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
