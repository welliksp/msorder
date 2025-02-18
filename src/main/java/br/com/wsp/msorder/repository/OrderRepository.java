package br.com.wsp.msorder.repository;

import br.com.wsp.msorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
