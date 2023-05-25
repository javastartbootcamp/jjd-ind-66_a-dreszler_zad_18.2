package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null) {
            return 0;
        }

        double minPrice = products.stream().map(Product::getPrice).reduce(0., Double::sum);

        if (coupons != null) {
            for (Coupon coupon : coupons) {
                double total = products.stream()
                        .map(p -> getDiscountedPrice(p, coupon))
                        .reduce(0., Double::sum);
                minPrice = Math.min(minPrice, total);
            }
        }

        return minPrice;
    }

    private double getDiscountedPrice(Product product, Coupon coupon) {
        if (coupon.getCategory() == null || product.getCategory() == coupon.getCategory()) {
            return product.getPrice() * (1 - coupon.getDiscountValueInPercents() / 100.0);
        }

        return product.getPrice();
    }
}