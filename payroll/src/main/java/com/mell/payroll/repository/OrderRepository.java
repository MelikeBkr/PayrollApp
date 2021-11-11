package com.mell.payroll.repository;

import com.mell.payroll.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

//NOTE: Domain type -> Order, Id type -> Long
public interface OrderRepository extends JpaRepository<Order, Long>
{
}
