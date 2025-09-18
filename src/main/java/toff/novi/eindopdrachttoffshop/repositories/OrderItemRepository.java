package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.enums.OrderItemStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByCartId(Integer cartId);

    List<OrderItem> findByCartIdAndStatus(Integer cartId, OrderItemStatus status);

    Optional<OrderItem> findByCartIdAndProductNameAndStatus(Integer cartId, String productName, OrderItemStatus status);

    List<OrderItem> findByOrder_Id(Integer orderId);

    List<OrderItem> findByStatus(OrderItemStatus status);
}
