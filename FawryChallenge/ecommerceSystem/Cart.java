package FawryChallenge.ecommerceSystem;


import  FawryChallenge.ecommerceSystem.models.Product; 

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void add(Product product, int quantity) {
        if (product.isExpired()) {
            System.out.println("Error: " + product.getName() + " is expired and cannot be added to the cart.");
            return;
        }
        if (product.getQuantity() < quantity) {
            System.out.println("Error: Not enough stock for " + product.getName() + ". Available: " + product.getQuantity());
            return;
        }
        items.put(product, items.getOrDefault(product, 0) + quantity);
        System.out.println(quantity + "x " + product.getName() + " added to cart.");
    }

    public Map<Product, Integer> getCartItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
