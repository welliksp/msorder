package br.com.wsp.msorder.repository;

import br.com.wsp.msorder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long > {
}
