package com.saswat.ShopPal.order.repository;

import com.saswat.ShopPal.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
