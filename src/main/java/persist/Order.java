package persist;

public class Order {

    private int id;
    private String products;

    public Order(int id, String products) {
        this.id = id;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
