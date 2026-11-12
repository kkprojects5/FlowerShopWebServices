package webFlowerREST;

public class Order {

    private String orderId;
    private String customerId;
    private String flowerId;
    private int quantity;
    private double totalAmount;

    public Order() {}

    public Order(String orderId, String customerId, String flowerId, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.flowerId = flowerId;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

   
    public void calculateTotal(double flowerPrice) {
        this.totalAmount = flowerPrice * quantity;
    }
}