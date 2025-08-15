package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
