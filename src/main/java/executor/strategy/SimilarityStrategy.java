package executor.strategy;

import entity.Pair;

import java.util.List;
import java.util.Map;

public interface SimilarityStrategy {

    Map<String, String> findResult(List<Pair> pairs);
}
