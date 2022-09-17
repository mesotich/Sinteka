package executor.strategy;

import entity.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SameWordsSimilarityStrategy implements SimilarityStrategy {

    @Override
    public Map<String, String> findResult(List<Pair> pairs) {
        return pairs.stream()
                .sorted(Comparator.comparingInt(this::sameWordsQuantity).reversed())
                .filter(pair -> sameWordsQuantity(pair) > 0)
                .collect(Collectors.toMap(
                        Pair::first,
                        Pair::second,
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
    }

    private int sameWordsQuantity(Pair pair) {
        return (int) Stream.of(pair.first().toLowerCase().split(" "))
                .filter(List.of(pair.second().toLowerCase().split(" "))::contains)
                .count();
    }
}
