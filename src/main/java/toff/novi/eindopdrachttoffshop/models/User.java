//package toff.novi.eindopdrachttoffshop.models;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//
//import java.util.Set;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "password")
//    private String password;
//
////    @ManyToMany(fetch = FetchType.EAGER)
////    @JoinTable(
////            name = "user_roles",
////            joinColumns = @JoinColumn(name = "user_id"),
////            inverseJoinColumns = @JoinColumn(name = "role_name")
////    )
////    private Set<Role> roles;
//
//
//
//    public User() {
//    }
//
//    public User(int id, String name, String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//
//
//    public User(String name, @Email String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPassword() { return password; } // TOEGEVOEGD
//
////    public Set<Role> getRoles() { return roles; } // TOEGEVOEGD
////
////    public void setRoles(Set<Role> roles) { this.roles = roles; } // TOEGEVOEGD
//
//    public String getUsername() { return email; } // TOEGEVOEGD, email als username
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//
//                '}';
//    }
//
//    public Integer getId() {
//        return id;
//
//    }
//
//   }
//

package toff.novi.eindopdrachttoffshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "USER"; // Default role

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_name")
//    )
//    private Set<Role> roles;

    public User() {
    }

    public User(int id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public User(String name, @Email String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() { return password; } // TOEGEVOEGD

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

//    public Set<Role> getRoles() { return roles; } // TOEGEVOEGD
//
//    public void setRoles(Set<Role> roles) { this.roles = roles; } // TOEGEVOEGD

    public String getUsername() { return email; } // TOEGEVOEGD, email als username

    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
}