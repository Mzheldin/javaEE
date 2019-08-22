package beans;

import persist.Order;
import persist.OrderRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class OrderBean implements Serializable {

    @Inject
    private OrderRepository orderRepository;
    private Order order;

    public OrderBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        orderRepository = (OrderRepository) context.getAttribute("orderRepository");
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public String createOrder() {
        this.order = new Order();
        return "/order.xhtml?faces-redirect=true";
    }

    public String editOrder(Order order) {
        this.order = order;
        return "/order.xhtml?faces-redirect=true";
    }

    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order);
    }

    public String saveOrder() {
        orderRepository.merge(this.order);
        return "/orders.xhtml?faces-redirect=true";
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
