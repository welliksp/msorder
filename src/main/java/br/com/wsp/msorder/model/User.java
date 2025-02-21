package br.com.wsp.msorder.model;

import br.com.wsp.msorder.dto.LoginRequest;
import br.com.wsp.msorder.dto.UserRequest;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Transactional
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username is required")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "FirstName is required")
    @Size(max = 30, message = "FirstName must be up to 30 characters")
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @NotBlank(message = "LastName is required")
    @Size(max = 30, message = "LastName must be up to 30 characters")
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Birthdate is required")
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Role is required")
    @Column(name = "role", nullable = false)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean passwordEncoder(LoginRequest request, PasswordEncoder passwordEncoder) {
        
       return passwordEncoder.matches(request.password(), this.password);
    }
}