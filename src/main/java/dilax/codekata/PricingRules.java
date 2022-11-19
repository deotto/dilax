package dilax.codekata;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://codekata.com/kata/kata09-back-to-the-checkout/
 */
public class PricingRules {

    record PriceRecord(int unitCount, int price) {
    }

    private final Map<Character, List<PriceRecord>> ruleMap = Map.of(
            'A', List.of(new PriceRecord(3, 130), new PriceRecord(1, 50)),
            'B', List.of(new PriceRecord(2, 45), new PriceRecord(1, 30)),
            'C', List.of(new PriceRecord(1, 20)),
            'D', List.of(new PriceRecord(1, 15))
    );


    int compute(final char sku, final int amount) {
        final AtomicInteger remainder = new AtomicInteger(amount);
        return ruleMap.get(sku).stream()
                .mapToInt(priceRecord -> priceRecord.price * (remainder
                        .getAndSet(remainder.get() % priceRecord.unitCount) / priceRecord.unitCount))
                .sum();
    }

    int compute_3rd(final char sku, final int amount) {
        final AtomicInteger remainder = new AtomicInteger(amount);
        return ruleMap.get(sku).stream()
                .mapToInt(priceRecord -> {
                    final int subTotal = priceRecord.price * (remainder.get() / priceRecord.unitCount);
                    remainder.set(remainder.get() % priceRecord.unitCount);
                    return subTotal;
                })
                .sum();
    }

    int compute_2nd(final char sku, final int amount) {

        final List<PriceRecord> priceRecords = ruleMap.get(sku);

        int remainder = amount;
        int result = 0;

        for (PriceRecord priceRecord : priceRecords) {
            result += priceRecord.price * (remainder / priceRecord.unitCount);
            remainder %= priceRecord.unitCount;
        }

        return result;
    }

    int compute_1st(final char sku, final int amount) {

        final List<PriceRecord> priceRecords = ruleMap.get(sku);

        int remainder = amount;
        int result = 0;

        for (PriceRecord priceRecord : priceRecords) {
            while (priceRecord.unitCount <= remainder) {
                result += priceRecord.price;
                remainder -= priceRecord.unitCount;
            }
        }

        return result;
    }
}
