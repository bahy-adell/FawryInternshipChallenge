package FawryChallenge.ecommerceSystem;

import FawryChallenge.ecommerceSystem.interfaces.Shippable;
import FawryChallenge.ecommerceSystem.models.Product;
import FawryChallenge.ecommerceSystem.services.ShippingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ECommerceSystem {
    private List<Product> products;
    private List<Customer> customers;
    private ShippingService shippingService;
    private static final double SHIPPING_RATE_PER_KG = 30.0;

    public ECommerceSystem() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.shippingService = new ShippingService();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public Product getProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Customer getCustomerByName(String name) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty. Nothing to checkout.");
            return;
        }

        double subtotal = 0;
        List<Shippable> itemsToShip = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            Product product = getProductByName(entry.getKey().getName());
            int quantityInCart = entry.getValue();

            if (product == null) {
                System.out.println("Error: Product " + entry.getKey().getName() + " not found in system.");
                return;
            }
            if (product.isExpired()) { 
                System.out.println("Error: " + product.getName() + " is expired and cannot be checked out.");
                return;
            }
            if (product.getQuantity() < quantityInCart) {
                System.out.println("Error: " + product.getName() + " is out of stock. Available: " + product.getQuantity());
                return;
            }

            subtotal += product.getPrice() * quantityInCart;

            if (product.isShippable()) {
                for (int i = 0; i < quantityInCart; i++) {
                    final String itemName = product.getName();
                    final double itemWeight = product.getWeight();
                    itemsToShip.add(new Shippable() {
                        @Override
                        public String getName() {
                            return itemName;
                        }

                        @Override
                        public double getWeight() {
                            return itemWeight;
                        }
                    });
                }
            }
        }

        double shippingFees = calculateShippingFees(itemsToShip);
        double totalAmount = subtotal + shippingFees;

        if (customer.getBalance() < totalAmount) {
            System.out.println("Error: Insufficient balance for " + customer.getName() + ". Required: " + totalAmount + ", Available: " + customer.getBalance()); // [cite: 20]
            return;
        }

        
        try {
            customer.debitBalance(totalAmount);
            for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
                Product product = getProductByName(entry.getKey().getName());
                product.decreaseQuantity(entry.getValue());
            }

            
            shippingService.sendForShipping(itemsToShip);

            System.out.println("***************************");
            System.out.println("\n** Checkout receipt **");
            cart.getCartItems().forEach((product, quantity) ->
                    System.out.println(quantity + "x " + product.getName() + "  " + String.format("%.0f", product.getPrice() * quantity)) // [cite: 40, 41, 42, 43]
            );
            System.out.println("***************************");
            System.out.println("Subtotal  " + String.format("%.0f", subtotal));
            System.out.println("Shipping  " + String.format("%.0f", shippingFees));
            System.out.println("Amount    " + String.format("%.0f", totalAmount));
            System.out.println("Customer balance after payment: " + String.format("%.0f", customer.getBalance()));
            cart.clear();

        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }

    private double calculateShippingFees(List<Shippable> items) {
        double totalWeightKg = items.stream().mapToDouble(Shippable::getWeight).sum();
        return Math.ceil(totalWeightKg) * SHIPPING_RATE_PER_KG;
    }
}
