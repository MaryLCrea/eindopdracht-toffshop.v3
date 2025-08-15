package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
