package beans;

import interceptors.LogInterceptor;
import persist.Order;
import persist.OrderRepository;
import persist.Product;
import services.OrderService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderBean implements Serializable, OrderService {

    @EJB
    private OrderRepository orderRepository;
    private Order order;

    public OrderBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        orderRepository = (OrderRepository) context.getAttribute("orderRepository");
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String createOrder() {
        this.order = new Order();
        return "/order.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String editOrder(Order order) {
        this.order = order;
        return "/order.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order);
    }

    @Override

    public String saveOrder() {
        orderRepository.merge(this.order);
        return "/orders.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public boolean existById(int id) {
        return orderRepository.existById(id);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
