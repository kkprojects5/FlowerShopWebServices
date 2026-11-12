package webFlowerService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@WebService
@SOAPBinding(style = Style.RPC)
public class WebFlowerService {

    // same Customer.in used by WebFlowerRESTProject
    private final String CUSTOMER_FILE =
        "C:\\Users\\Administrator\\eclipse-workspace2\\WebFlowerRESTProject\\Customer.in";

    // load customers from shared file
    private List<Customer> loadCustomers() throws Exception {

        List<Customer> list = new ArrayList<>();

        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) return list;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] data = line.split("\\t+");

            if (data.length >= 5) {

                Customer c = new Customer();
                c.setCustomerId(data[0]);
                c.setName(data[1]);
                c.setEmail(data[2]);
                c.setPhone(data[3]);
                c.setPassword(data[4]);

                list.add(c);
            }
        }

        sc.close();
        return list;
    }

    @WebMethod(operationName = "loginCustomer")
    public String loginCustomer(@WebParam(name = "customerId") String customerId,
                                 @WebParam(name = "password") String password) throws Exception {

        List<Customer> customers = loadCustomers();

        for (Customer c : customers) {

            if (c.getCustomerId().equals(customerId)) {

                if (c.getPassword() != null && c.getPassword().equals(password)) {
                    return "Login Successful. Welcome " + c.getName();
                } else {
                    return "Login Failed: Incorrect Password";
                }
            }
        }

        return "Login Failed: Customer Not Found";
    }

    @WebMethod(operationName = "searchCustomerInfo")
    public Customer searchCustomerInfo(@WebParam(name = "customerId") String customerId) throws Exception {

        List<Customer> customers = loadCustomers();

        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }

        return null;
    }
}