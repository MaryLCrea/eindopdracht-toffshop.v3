package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.enums.OrderAndItemStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByCartId(Integer cartId);

    List<OrderItem> findByCart_IdAndStatus(Integer cartId, OrderAndItemStatus status);
    Optional<OrderItem> findByIdAndCart_Id(Integer id, Integer cartId);

    Optional<OrderItem> findByCartIdAndProductNameAndStatus(Integer cartId, String productName, OrderAndItemStatus status);

    Optional<OrderItem> findByCartIdAndSkuAndStatus(Integer cartId, String sku, OrderAndItemStatus status);

    List<OrderItem> findByOrder_Id(Integer orderId);

    List<OrderItem> findByStatus(OrderAndItemStatus status);
}
