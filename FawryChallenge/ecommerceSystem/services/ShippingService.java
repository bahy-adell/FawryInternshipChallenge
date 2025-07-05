package FawryChallenge.ecommerceSystem.services;

import FawryChallenge.ecommerceSystem.interfaces.Shippable;

import java.util.List;

public class ShippingService {
    public void sendForShipping(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }
        System.out.println("\n** Shipment notice **");
        double totalWeight = 0;
        for (Shippable item : shippableItems) {
            System.out.println(shippableItems.stream().filter(s -> s.equals(item)).count() + "x " + item.getName() + "  " + String.format("%.0fg", item.getWeight() * 1000));
            totalWeight += item.getWeight();
        }
        System.out.println("Total package weight " + String.format("%.1fkg", totalWeight));
    }
}
