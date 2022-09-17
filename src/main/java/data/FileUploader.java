package data;

import org.springframework.stereotype.Component;
import service.ResultService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
public class FileUploader implements Uploader {

    private final ResultService resultService;

    public FileUploader(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public void upload(String target) {
        try (var bw = Files.newBufferedWriter(Path.of(target))) {
            for (Map.Entry<String, String> entry : resultService.getAll().entrySet())
                bw.write(entry.getKey() + ":" + entry.getValue() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
