package pl.javastart.couponcalc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @BeforeEach
    void init() {
        priceCalculator = new PriceCalculator();
    }

    @Test
    public void shouldReturnZeroForNoProducts() {
        // given

        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79, withPrecision(0.01));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneNotMatchingCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.CAR, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(5.99, withPrecision(0.01));
    }

    @Test
    public void shouldReturnPriceForMultipleProductsAndOneNotMatchingCoupon() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Terminator DVD", 9.99, Category.ENTERTAINMENT));
        products.add(new Product("Skrobaczka do szyb", 7.99, Category.CAR));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.HOME, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(23.97, withPrecision(0.01));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndMultipleCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        coupons.add(new Coupon(null, 30));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.19, withPrecision(0.01));
    }

    @Test
    public void shouldReturnPriceForMultipleProductsAndMultipleCoupons() {

        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Terminator DVD", 9.99, Category.ENTERTAINMENT));
        products.add(new Product("Skrobaczka do szyb", 7.99, Category.CAR));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.ENTERTAINMENT, 50));
        coupons.add(new Coupon(Category.CAR, 40));
        coupons.add(new Coupon(null, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(18.98, withPrecision(0.01));
    }
}