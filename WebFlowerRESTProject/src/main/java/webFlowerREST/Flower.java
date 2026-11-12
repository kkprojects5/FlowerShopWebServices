package webFlowerREST;

public class Flower {

    private String flower_id;
    private String name;
    private String color;
    private double price;
    private int stock;

    public Flower() {}

    public Flower(String flower_id,
                  String name,
                  String color,
                  double price,
                  int stock) {

        this.flower_id = flower_id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.stock = stock;
    }

    public String getFlower_id() {
        return flower_id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setFlower_id(String flower_id) {
        this.flower_id = flower_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    
    public double calculateTotalValue() {
        return price * stock;
    }
}