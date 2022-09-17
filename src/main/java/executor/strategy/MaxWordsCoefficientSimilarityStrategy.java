package executor.strategy;

import entity.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class MaxWordsCoefficientSimilarityStrategy implements SimilarityStrategy {

    @Override
    public Map<String, String> findResult(List<Pair> pairs) {
        return pairs.stream()
                .sorted(Comparator.comparingDouble(this::maxSimilarity).reversed())
                .collect(Collectors.toMap(
                        Pair::first,
                        Pair::second,
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
    }

    private double maxSimilarity(Pair pair) {
        return Stream.of(pair.first().toLowerCase().split(" "))
                .flatMap(s1 -> Stream.of(pair.second().toLowerCase().split(" "))
                        .map(s2 -> similarity(s1, s2)))
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
    }

    private double similarity(String s1, String s2) {
        if (s1 == null || s2 == null)
            throw new IllegalArgumentException("The string must not be null");
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0 || s1.equals(s2))
            return 1.0;
        if (s1.length() == 0 || s2.length() == 0)
            return 0.0;
        return (maxLen - distance(s1, s2)) / (double) maxLen;
    }

    private int distance(String s1, String s2) {
        int[][] matrix = new int[s1.length() + 1][s2.length() + 1];
        range(0, matrix.length)
                .forEach(row -> range(0, matrix[0].length)
                        .forEach(col -> matrix[row][col] = minDiv(row, col, s1, s2, matrix)));
        return matrix[matrix.length - 1][matrix[0].length - 1];
    }

    private int minDiv(int i, int j, String s1, String s2, int[][] matrix) {
        if (i == 0 || j == 0) return Math.max(Math.max(i, 0), j);
        int m = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
        return Math.min(
                Math.min(matrix[i][j - 1] + 1, matrix[i - 1][j] + 1),
                matrix[i - 1][j - 1] + m);
    }
}
