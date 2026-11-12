package clientFlowerREST;

import java.util.Scanner;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;

public class ClientFlowerREST {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        String baseUrl =
            "http://localhost:8080/WebFlowerRESTProject/rest/WebFlower";

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target;

        System.out.println(" Flower Client App Started");
        

        // =========================
        // 1. VIEW INVENTORY (GET)
        // =========================
        target = client.target(baseUrl);

        String response = target.request()
                .accept(MediaType.TEXT_HTML)
                .get(String.class);

        System.out.println("\n--- INVENTORY ---");
        System.out.println(response);

        // =========================
        // 2. ADD FLOWER (POST)
        // =========================
        System.out.println("\nEnter Flower ID:");
        String id = console.next();

        System.out.println("Enter Name:");
        String name = console.next();

        System.out.println("Enter Color:");
        String color = console.next();

        System.out.println("Enter Price:");
        String price = console.next();

        System.out.println("Enter Stock:");
        String stock = console.next();

        target = client.target(baseUrl).path("addFlower");

        Form form = new Form();
        form.param("flowerId", id);
        form.param("name", name);
        form.param("color", color);
        form.param("price", price);
        form.param("stock", stock);

        response = target.request()
                .accept(MediaType.TEXT_HTML)
                .post(Entity.form(form), String.class);

        System.out.println("\n--- ADD FLOWER RESPONSE ---");
        System.out.println(response);

        // =========================
        // 3. SEARCH FLOWER (GET)
        // =========================
        System.out.println("\nEnter Flower ID to search:");
        String searchId = console.next();

        target = client.target(baseUrl)
                .path("searchFlower")
                .queryParam("flowerId", searchId);

        response = target.request()
                .accept(MediaType.TEXT_HTML)
                .get(String.class);

        System.out.println("\n--- SEARCH RESULT ---");
        System.out.println(response);
    }
}