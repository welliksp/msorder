package br.com.wsp.msorder.repository;

import br.com.wsp.msorder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
