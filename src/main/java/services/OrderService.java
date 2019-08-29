package services;

import persist.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {
    List<Order> getAllOrders();
    String createOrder();
    String editOrder(Order order);
    void deleteOrder(Order order);
    String saveOrder();
    boolean existById(int id);
    Order findById(int id);
}
