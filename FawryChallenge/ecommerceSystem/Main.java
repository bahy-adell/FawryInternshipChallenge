package FawryChallenge.ecommerceSystem;

import FawryChallenge.ecommerceSystem.models.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ECommerceSystem system = new ECommerceSystem();

    
        Product cheese = new ExpirableShippableProduct("Cheese", 100.0, 10, 0.2, LocalDate.now().plusDays(5)); // [cite: 6, 8, 9]
        Product tv = new NonExpirableShippableProduct("TV", 1500.0, 3, 15.0); // 15kg [cite: 7, 8, 9]
        Product mobileScratchCard = new NonExpirableNonShippableProduct("Mobile Scratch Card", 50.0, 20); // No expiration, no shipping 

        system.addProduct(cheese);
     
        system.addProduct(tv);
        system.addProduct(mobileScratchCard);
      

       
        Customer customer1 = new Customer("Bahy", 5000.0);
        Customer customer2 = new Customer("Adel", 100.0);
        system.addCustomer(customer1);
        system.addCustomer(customer2);


        System.out.println("--- Test Case 1: Successful Checkout ---");
        Cart cart1 = new Cart();
        cart1.add(system.getProductByName("Cheese"), 2);
        cart1.add(system.getProductByName("TV"), 1);
        cart1.add(system.getProductByName("Mobile Scratch Card"), 1);
        system.checkout(customer1, cart1);
        System.out.println("Customer Alice balance after Test Case 1: " + customer1.getBalance());

     

    }
}