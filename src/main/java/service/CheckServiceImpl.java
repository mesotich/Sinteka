package service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    private List<String> listN = new ArrayList<>();
    private List<String> listM = new ArrayList<>();
    private final ResultService resultService;

    public CheckServiceImpl(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public void addLists(List<String> listN, List<String> listM) {
        this.listN = listN;
        this.listM = listM;
    }

    @Override
    public void check() {
        var resultMap = resultService.getAll();
        for (String line : listN)
            resultMap.putIfAbsent(line, "?");
        for (String line : listM
        ) {
            if (!resultMap.containsValue(line))
                resultMap.put(line, "?");
        }
        resultService.add(resultMap);
    }
}
