package beans;

import interceptors.LogInterceptor;
import persist.Order;
import persist.Product;
import services.OrderService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.util.HashMap;
import java.util.Map;

@Stateful
public class CartBean {

    @EJB
    private OrderService orderService;

    private Map<Product, Integer> ordersLine = new HashMap<>();

    @Interceptors({LogInterceptor.class})
    public void addProduct(Product product, Integer count){
        if (!ordersLine.containsKey(product))
            ordersLine.put(product, count);
        else ordersLine.put(product, ordersLine.get(product) + count);
    }

    @Interceptors({LogInterceptor.class})
    public void removeProduct(Product product){
        ordersLine.remove(product);
    }

    @Interceptors({LogInterceptor.class})
    public void removeProduct(Product product, Integer count){
        if (ordersLine.containsKey(product))
            if (ordersLine.get(product) > count)
                ordersLine.put(product, ordersLine.get(product) - count);
            else ordersLine.remove(product);
    }

    @Interceptors({LogInterceptor.class})
    public void createOrder(){
        if (ordersLine.size() > 0){
            Order order = new Order();
            order.setProducts(formOrder());
            orderService.editOrder(order);
            orderService.saveOrder();
        }
    }

    private String formOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : ordersLine.entrySet())
            stringBuilder
                    .append("product: ")
                    .append(entry.getKey().getName())
                    .append("# count: ")
                    .append(entry.getValue())
                    .append("%");
        return stringBuilder.toString();
    }
}
