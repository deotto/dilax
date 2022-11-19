package dilax.codekata;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://codekata.com/kata/kata09-back-to-the-checkout/
 */
public class CheckOut {

    private final PricingRules pricingRules;

    private final ConcurrentMap<Character, AtomicInteger> shoppingCart = new ConcurrentHashMap<>();

    public CheckOut(final PricingRules pricingRules) {
        this.pricingRules = pricingRules;
    }

    public void scan(char sku) {
        shoppingCart.computeIfAbsent(sku, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int total() {
        return shoppingCart.entrySet().stream()
                .mapToInt(entry -> pricingRules.compute(entry.getKey(), entry.getValue().get()))
                .sum();
    }
}
