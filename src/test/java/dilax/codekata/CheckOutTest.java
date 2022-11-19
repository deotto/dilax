package dilax.codekata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckOutTest {
    
    private final PricingRules pricingRules = new PricingRules();
    
    private int price(final String goods) {
        final CheckOut co = new CheckOut(pricingRules);
        goods.chars()
                .mapToObj(sku -> (char) sku)
                .forEach(sku -> co.scan(sku));
        return co.total();
    }

    @Test
    void scan() {
        assertEquals(0, price(""));
        assertEquals(50, price("A"));
        assertEquals(80, price("AB"));
        assertEquals(115, price("CDBA"));

        assertEquals(100, price("AA"));
        assertEquals(130, price("AAA"));
        assertEquals(180, price("AAAA"));
        assertEquals(230, price("AAAAA"));
        assertEquals(260, price("AAAAAA"));

        assertEquals(160, price("AAAB"));
        assertEquals(175, price("AAABB"));
        assertEquals(190, price("AAABBD"));
        assertEquals(190, price("DABABA"));
    }

        @Test
    void total() {
        final CheckOut co = new CheckOut(pricingRules);
        assertEquals(0, co.total());

        co.scan('A');
        assertEquals(50, co.total());

        co.scan('B');
        assertEquals(80, co.total());

        co.scan('A');
        assertEquals(130, co.total());

        co.scan('A');
        assertEquals(160, co.total());

        co.scan('B');
        assertEquals(175, co.total());
    }
}