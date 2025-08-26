//package toff.novi.eindopdrachttoffshop.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import toff.novi.eindopdrachttoffshop.models.User;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Integer> {
//    Optional<User> findByEmail(String email);
//
//    boolean existsByEmail(String email);


package toff.novi.eindopdrachttoffshop.repositories;

import toff.novi.eindopdrachttoffshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
    public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByEmail(String email);

        // Since we use email as username, we can make this method delegate to findByEmail
        default Optional<User> findByUsernameOrEmail(String password) {
            return findByEmail(password);
        }

        boolean existsByEmail(String email);

    }


