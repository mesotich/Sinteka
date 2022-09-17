package service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {

    private final Map<String, String> resultMap = new HashMap<>();

    @Override
    public void add(Map<String, String> map) {
        resultMap.putAll(map);
    }

    @Override
    public Map<String, String> getAll() {
        return resultMap;
    }
}
