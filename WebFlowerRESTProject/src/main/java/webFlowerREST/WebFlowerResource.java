package webFlowerREST;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.*;
import java.util.*;

@Path("WebFlower")
public class WebFlowerResource {

    private final String FILE_PATH =
        "C:\\Users\\Administrator\\eclipse-workspace2\\WebFlowerRESTProject\\Flower.in";

    // load flowwers
    private HashMap<String, Flower> loadFlowers() throws Exception {

        HashMap<String, Flower> map = new HashMap<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return map;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] data = line.split("\\t+");

            if (data.length == 5) {

                Flower f = new Flower(
                        data[0],
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Integer.parseInt(data[4])
                );

                map.put(f.getFlower_id(), f);
            }
        }

        sc.close();
        return map;
    }

    // save flowers
    private void saveFlowers(HashMap<String, Flower> map) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false));

        for (Flower f : map.values()) {

            bw.write(
                f.getFlower_id() + "\t" +
                f.getName() + "\t" +
                f.getColor() + "\t" +
                f.getPrice() + "\t" +
                f.getStock()
            );

            bw.newLine();
        }

        bw.close();
    }

    // inventory html
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String displayHTML() throws Exception {

        HashMap<String, Flower> map = loadFlowers();

        StringBuilder sb = new StringBuilder();

        sb.append("<html><body>");
        sb.append("<h2>Flower Inventory</h2>");

        sb.append("<table border='1'>");
        sb.append("<tr><th>ID</th><th>Name</th><th>Color</th><th>Price</th><th>Stock</th><th>Total</th></tr>");

        double total = 0;

        for (Flower f : map.values()) {

            double value = f.calculateTotalValue();
            total += value;

            sb.append("<tr>")
              .append("<td>").append(f.getFlower_id()).append("</td>")
              .append("<td>").append(f.getName()).append("</td>")
              .append("<td>").append(f.getColor()).append("</td>")
              .append("<td>").append(f.getPrice()).append("</td>")
              .append("<td>").append(f.getStock()).append("</td>")
              .append("<td>").append(value).append("</td>")
              .append("</tr>");
        }

        sb.append("</table>");
        sb.append("<h3>Total Inventory Value: ").append(total).append("</h3>");
        sb.append("</body></html>");

        return sb.toString();
    }

    // json inventory
    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Flower> getJSON() throws Exception {
        return new ArrayList<>(loadFlowers().values());
    }

    // search
    @GET
    @Path("/searchFlower/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Flower search(@PathParam("id") String id) throws Exception {
        return loadFlowers().get(id);
    }

    // crud - add (create
    @POST
    @Path("/addFlower")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String addFlower(@FormParam("flowerId") String id,
                             @FormParam("name") String name,
                             @FormParam("color") String color,
                             @FormParam("price") double price,
                             @FormParam("stock") int stock) throws Exception {

        HashMap<String, Flower> map = loadFlowers();

        map.put(id, new Flower(id, name, color, price, stock));

        saveFlowers(map);

        return "<h2>Flower Added</h2>";
    }
    
    //search
    @GET
    @Path("/searchFlower")
    @Produces(MediaType.TEXT_HTML)
    public String searchFlower(@QueryParam("flowerId") String flowerId) throws Exception {

        HashMap<String, Flower> flowers = loadFlowers();

        Flower f = flowers.get(flowerId);

        if (f == null) {
            return "<h2>Flower not found</h2>";
        }

        return "<h2>Flower Details</h2>"
             + "<p>ID: " + flowerId + "</p>"
             + "<p>Name: " + f.getName() + "</p>"
             + "<p>Color: " + f.getColor() + "</p>"
             + "<p>Price: $" + f.getPrice() + "</p>"
             + "<p>Stock: " + f.getStock() + "</p>";
    }

    // update
    @PUT
    @Path("/updateFlower")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String updateFlower(@FormParam("flowerId") String id,
                               @FormParam("price") double price,
                               @FormParam("stock") int stock) throws Exception {

        HashMap<String, Flower> map = loadFlowers();

        Flower f = map.get(id);

        if (f == null) {
            return "<h2>Flower Not Found</h2>";
        }

        // UPD data
        f.setPrice(price);
        f.setStock(stock);

        saveFlowers(map);

        return "<h2>Flower Updated Successfully (PUT)</h2>"
             + "<p>ID: " + id + "</p>"
             + "<p>Price: $" + price + "</p>"
             + "<p>Stock: " + stock + "</p>";
    }

    // del flower
    
    @DELETE
    @Path("/deleteFlower/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteFlower(@PathParam("id") String id) throws Exception {

        HashMap<String, Flower> map = loadFlowers();

        if (map.remove(id) == null) {
            return "<h2>Flower Not Found</h2>";
        }

        saveFlowers(map);

        return "<h2>Flower Deleted Successfully</h2>";
    }
 // register customer
    private final String CUSTOMER_FILE =
    	    "C:\\Users\\Administrator\\eclipse-workspace2\\WebFlowerRESTProject\\Customer.in";

    	@POST
    	@Path("/registerCustomer")
    	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    	@Produces(MediaType.TEXT_HTML)
    	public String registerCustomer(@FormParam("name") String name,
    	                               @FormParam("email") String email,
    	                               @FormParam("phone") String phone,
    	                               @FormParam("password") String password) throws Exception {

    	    // gener cust id
    	    int count = 1000;

    	    File file = new File(CUSTOMER_FILE);

    	    if (file.exists()) {

    	        BufferedReader br = new BufferedReader(
    	            new FileReader(file)
    	        );

    	        while (br.readLine() != null) {
    	            count++;
    	        }

    	        br.close();
    	    }

    	    String id = "C" + (count + 1);

    	    // save cust
    	    BufferedWriter bw = new BufferedWriter(
    	        new FileWriter(CUSTOMER_FILE, true)
    	    );

    	    bw.write(id + "\t" + name + "\t" + email + "\t" + phone + "\t" + password);
    	    bw.newLine();
    	    bw.close();

    	    return "<h2>Customer Registered Successfully</h2>"
    	         + "<p>Customer ID: " + id + "</p>"
    	         + "<p>Name: " + name + "</p>"
    	         + "<p>Email: " + email + "</p>"
    	         + "<p>Phone: " + phone + "</p>";
    	}
    // place order
    private final String ORDER_FILE =
    	    "C:\\Users\\Administrator\\eclipse-workspace2\\WebFlowerRESTProject\\Order.in";
    
    	// simple order counter
    	private static int orderCounter = 100;

    	@POST
    	@Path("/placeOrder")
    	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    	@Produces(MediaType.TEXT_HTML)
    	public String placeOrder(@FormParam("flowerId") String flowerId,
    	                         @FormParam("quantity") int quantity) throws Exception {

    	    HashMap<String, Flower> flowers = loadFlowers();

    	    Flower f = flowers.get(flowerId);

    	    if (f == null) {
    	        return "<h2>Flower not found</h2>";
    	    }

    	    if (quantity <= 0) {
    	        return "<h2>Invalid quantity</h2>";
    	    }

    	    if (f.getStock() < quantity) {
    	        return "<h2>Not enough stock available</h2>";
    	    }

    	    // calc total
    	    double total = f.getPrice() * quantity;

    	    // reduce stock
    	    f.setStock(f.getStock() - quantity);
    	    saveFlowers(flowers);

    	    // simple orderid
    	    String orderId = "O" + (++orderCounter);

    	    // save to file
    	    BufferedWriter bw = new BufferedWriter(
    	        new FileWriter(ORDER_FILE, true)
    	    );

    	    bw.write(orderId + "\t" + flowerId + "\t" + quantity + "\t" + total);
    	    bw.newLine();
    	    bw.close();

    	    // response
    	    return "<h2>Order Placed Successfully</h2>"
    	         + "<p>Order ID: " + orderId + "</p>"
    	         + "<p>Flower ID: " + flowerId + "</p>"
    	         + "<p>Quantity: " + quantity + "</p>"
    	         + "<p>Unit Price: $" + f.getPrice() + "</p>"
    	         + "<p>Total Price: $" + total + "</p>";
    	}
    // make payment - order based


private final String PAYMENT_FILE =
    "C:\\Users\\Administrator\\eclipse-workspace2\\WebFlowerRESTProject\\Payment.in";

@POST
@Path("/makePayment")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public String makePayment(@FormParam("orderId") String orderId,
                          @FormParam("paymentMethod") String method) throws Exception {

    if (orderId == null || orderId.isEmpty()) {
        return "<h2>Order ID required</h2>";
    }

    // find order in file
    BufferedReader br = new BufferedReader(new FileReader(ORDER_FILE));

    String line;
    double amount = -1;
    boolean found = false;

    while ((line = br.readLine()) != null) {

        String[] data = line.split("\\t+");

        if (data[0].equals(orderId)) {
            amount = Double.parseDouble(data[3]); // TOTAL PRICE
            found = true;
            break;
        }
    }

    br.close();

    if (!found) {
        return "<h2>Order not found</h2>";
    }

    // gen order id
    String paymentId = "P" + System.currentTimeMillis();

    // save oayment
    BufferedWriter bw = new BufferedWriter(
        new FileWriter(PAYMENT_FILE, true)
    );

    bw.write(paymentId + "\t" + orderId + "\t" + amount + "\t" + method);
    bw.newLine();
    bw.close();

    // response
    return "<h2>Payment Successful</h2>"
         + "<p>Payment ID: " + paymentId + "</p>"
         + "<p>Order ID: " + orderId + "</p>"
         + "<p>Amount Paid: $" + amount + "</p>"
         + "<p>Method: " + method + "</p>";
}
}
