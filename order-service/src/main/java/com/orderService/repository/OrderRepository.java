package com.orderService.repository;

import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Order SET orderStatus = :status WHERE id = :id")
    void changeStatus(@Param("id") Long id, @Param("status") EnumOrderStatus orderStatus);

    Order findByIdempotencyKey(String key);
}
