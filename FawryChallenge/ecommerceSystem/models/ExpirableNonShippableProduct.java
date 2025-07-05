package FawryChallenge.ecommerceSystem.models;

import FawryChallenge.ecommerceSystem.interfaces.Expirable;

import java.time.LocalDate;

public class ExpirableNonShippableProduct extends Product implements Expirable {
    private LocalDate expirationDate; 

    public ExpirableNonShippableProduct(String name, double price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isShippable() {
        return false;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
