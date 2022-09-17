package executor.strategy;

import entity.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EqualsStringsSimilarityStrategy implements SimilarityStrategy {

    @Override
    public Map<String, String> findResult(List<Pair> pairs) {
        return pairs.stream()
                .filter(pair -> pair.first().equals(pair.second().toLowerCase()))
                .collect(Collectors.toMap(Pair::first, Pair::second));
    }
}
