package HometaskAboutRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public final class WriteUtil {

    private WriteUtil() {
    }

    public static void writingReportOne(Collection<String> collection) {
        try {
            Files.write(Paths.get("resources", "reportOne.txt"), collection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writingReportTwo(Collection<String> collection) {
        try {
            Files.write(Paths.get("resources", "reportTwo.txt"), collection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
