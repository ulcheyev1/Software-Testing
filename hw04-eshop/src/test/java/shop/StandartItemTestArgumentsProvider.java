package shop;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import shop.StandardItem;

import java.util.stream.Stream;

public class StandartItemTestArgumentsProvider implements ArgumentsProvider{
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new StandardItem(5, "Drink", 15, "Food", 5),
                        new StandardItem(5, "Drink", 15, "Food", 5), true));
    }
}



