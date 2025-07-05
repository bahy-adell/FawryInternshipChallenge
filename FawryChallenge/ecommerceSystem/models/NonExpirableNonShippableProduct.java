package FawryChallenge.ecommerceSystem.models;

public class NonExpirableNonShippableProduct extends Product {
    public NonExpirableNonShippableProduct(String name, double price, int quantity) {
        super(name, price, quantity);
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
        return false;
    }
}
