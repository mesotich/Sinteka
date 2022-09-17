package executor;

import executor.strategy.EqualsStringsSimilarityStrategy;
import executor.strategy.MaxWordsCoefficientSimilarityStrategy;
import executor.strategy.SameWordsSimilarityStrategy;
import executor.strategy.SimilarityStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import service.CheckService;
import service.PairService;
import service.ResultService;

import java.util.List;

@Component
public class AllStrategiesExecutor implements Executor {

    private final PairService pairService;
    private final ResultService resultService;
    private final CheckService checkService;
    private final List<SimilarityStrategy> allStrategies;

    public AllStrategiesExecutor(PairService pairService,
                                 ResultService resultService,
                                 CheckService checkService,
                                 List<SimilarityStrategy> allStrategies) {
        this.pairService = pairService;
        this.resultService = resultService;
        this.checkService = checkService;
        this.allStrategies = allStrategies;
    }

    @Override
    public void execute() {
        allStrategies.forEach(strategy -> {
            if (pairService.isEmpty())
                return;
            var map = strategy.findResult(pairService.getAll());
            resultService.add(map);
            pairService.delete(map);
        });
        if (!pairService.isEmpty())
            throw new RuntimeException("Обработаны не все строки");
        checkService.check();
    }

}
