package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import toff.novi.eindopdrachttoffshop.models.User;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {


}


////dob er nog niet in!!
//public interface UserRepository extends JpaRepository<User, Integer> {
//     List<User> findByCreatedDobAfter(LocalDate date);
//}
