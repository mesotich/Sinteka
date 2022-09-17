package data;

import org.springframework.stereotype.Component;
import entity.Pair;
import service.CheckService;
import service.PairService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

@Component
public class FileDownloader implements Downloader {

    private final PairService pairService;
    private final CheckService checkService;

    public FileDownloader(PairService pairService, CheckService checkService) {
        this.pairService = pairService;
        this.checkService = checkService;
    }

    @Override
    public void download(String resource) {
        List<String> listN;
        List<String> listM;
        try (var scanner = new Scanner(Path.of(resource))) {
            listN = readLinesToList(scanner);
            listM = readLinesToList(scanner);
        } catch (IOException e) {
            throw new RuntimeException("File reading exception. Path: " + resource);
        }
        checkService.addLists(listN, listM);
        pairService.addAll(listN.stream()
                .flatMap(s1 -> listM.stream()
                        .map(s2 -> new Pair(s1, s2)))
                .collect(Collectors.toList()));
    }

    private static List<String> readLinesToList(Scanner scanner) {
        int n = scanner.nextInt();
        scanner.nextLine();
        return range(0, n)
                .mapToObj(i -> scanner.nextLine())
                .collect(Collectors.toList());
    }
}
