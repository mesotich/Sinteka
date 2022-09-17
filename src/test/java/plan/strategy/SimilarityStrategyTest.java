package plan.strategy;

import executor.strategy.EqualsStringsSimilarityStrategy;
import executor.strategy.SameWordsSimilarityStrategy;
import executor.strategy.SimilarityStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import entity.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimilarityStrategyTest {

    private static List<Pair> pairs1;
    private static List<Pair> pairs2;
    private static List<Pair> pairs3;
    private static SimilarityStrategy strategy;

    @BeforeAll
    void prepare() {
        pairs1 = List.of(new Pair[]{new Pair("Бетон с присадкой", "присадка для бетона"),
                new Pair("Бетон с присадкой", "доставка")});
        pairs2 = List.of(new Pair[]{new Pair("Бетон с присадкой", "Цемент")});
        var listN = List.of(new String[]{"гвоздь", "шуруп", "краска синяя", "ведро для воды"});
        var listM = List.of(new String[]{"краска", "корыто для воды", "шуруп 3х1.5"});
        pairs3 = listN.stream()
                .flatMap(s1 -> listM.stream()
                        .map(s2 -> new Pair(s1, s2)))
                .collect(Collectors.toList());
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class EqualsStringsSimilarityStrategyTest {

        @BeforeAll
        void inject() {
            strategy = new EqualsStringsSimilarityStrategy();
        }

        @ParameterizedTest(name = "{arguments} test")
        @MethodSource(value = "plan.strategy.SimilarityStrategyTest#getPairsForArgumentTest")
        void returnEmptyMapForFindResult(List<Pair> pairs) {
            assertThat(Collections.emptyMap())
                    .isEqualTo(strategy.findResult(pairs));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SameWordsSimilarityStrategyTest {

        @BeforeAll
        void inject() {
            strategy = new SameWordsSimilarityStrategy();
        }

        @ParameterizedTest(name = "{arguments} test")
        @MethodSource(value = "plan.strategy.SimilarityStrategyTest#getPairsForArgumentTest")
        void returnEmptyMapForFindResult(List<Pair> pairs) {
            if (pairs.equals(pairs3))
                return;
            assertThat(strategy.findResult(pairs)).isEmpty();
        }
    }

    static Stream<Arguments> getPairsForArgumentTest() {
        return Stream.of(
                Arguments.of(pairs1),
                Arguments.of(pairs2),
                Arguments.of(pairs3)
        );
    }
}

