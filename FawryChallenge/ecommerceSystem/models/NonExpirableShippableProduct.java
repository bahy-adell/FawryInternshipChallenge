package FawryChallenge.ecommerceSystem.models;

import FawryChallenge.ecommerceSystem.interfaces.Shippable;

public class NonExpirableShippableProduct extends Product implements Shippable {
    private double weight;

    public NonExpirableShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}