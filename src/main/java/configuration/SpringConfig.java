package configuration;

import executor.strategy.EqualsStringsSimilarityStrategy;
import executor.strategy.MaxWordsCoefficientSimilarityStrategy;
import executor.strategy.SameWordsSimilarityStrategy;
import executor.strategy.SimilarityStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"service", "data", "executor"})
public class SpringConfig {

    @Bean
    public List<SimilarityStrategy> allStrategies() {
        return List.of(new SimilarityStrategy[]{
                new EqualsStringsSimilarityStrategy(),
                new SameWordsSimilarityStrategy(),
                new MaxWordsCoefficientSimilarityStrategy()});
    }
}
