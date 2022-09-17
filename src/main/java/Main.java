import configuration.SpringConfig;
import data.Downloader;
import data.Uploader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import executor.Executor;

public class Main {

    private static final String INPUT_PATH = "input.txt";
    private static final String OUTPUT_PATH = "output.txt";

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        var downloader = context.getBean("fileDownloader", Downloader.class);
        downloader.download(INPUT_PATH);
        var plan = context.getBean("allStrategiesExecutor", Executor.class);
        plan.execute();
        var uploader = context.getBean("fileUploader", Uploader.class);
        uploader.upload(OUTPUT_PATH);


    }
}
