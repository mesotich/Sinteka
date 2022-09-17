package service;

import entity.Pair;

import java.util.List;
import java.util.Map;

public interface PairService {

    void addAll(List<Pair> pairs);

    List<Pair> getAll();

    void delete(Map<String, String> map);

    boolean isEmpty();
}
