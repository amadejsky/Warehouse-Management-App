import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) {
}

public class MoneyParser {
    private static final Pattern MONEY_FORMAT_PATTERN = Pattern.compile("(?<amount>\\d+(?:\\.\\d{1,2})?) ?(?<currencyCode>[A-Z]{3})");

    public Money parse(String originalInput) {
        return Optional.ofNullable(originalInput)
                .map(MONEY_FORMAT_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(it -> new Money(
                        new BigDecimal(it.group("amount")),
                        Currency.getInstance(it.group("currencyCode"))
                ))
                .orElseThrow(() -> new IllegalArgumentException("Could not match: %s".formatted(originalInput)));
    }
}
