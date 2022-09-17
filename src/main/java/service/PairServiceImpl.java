package service;

import org.springframework.stereotype.Service;
import entity.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PairServiceImpl implements PairService {

    private final List<Pair> pairs = new ArrayList<>();

    public void addAll(List<Pair> pairs) {
        this.pairs.addAll(pairs);
    }

    @Override
    public List<Pair> getAll() {
        return this.pairs;
    }

    @Override
    public void delete(Map<String, String> map) {
        this.pairs.removeIf(pair -> map.containsKey(pair.first()));
        this.pairs.removeIf(pair -> map.containsValue(pair.second()));
    }

    @Override
    public boolean isEmpty() {
        return this.pairs.isEmpty();
    }
}
