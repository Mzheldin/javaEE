package beans;

import persist.Order;
import persist.OrderRepository;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@ManagedBean
public class OrderBean {

    private OrderRepository orderRepository;
    private Order order;

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        orderRepository = (OrderRepository) context.getAttribute("orderRepository");
    }

    public List<Order> getAllOrders() throws SQLException {
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

    public String deleteOrder(Order order) throws SQLException{
        orderRepository.deleteOrder(order);
        return "/orders.xhtml?faces-redirect=true";
    }

    public String saveOrder() throws SQLException {
        orderRepository.save(this.order);
        return "/orders.xhtml?faces-redirect=true";
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
