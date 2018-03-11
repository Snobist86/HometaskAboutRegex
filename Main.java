package HometaskAboutRegex;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.join;

public class Main {

    public static void main(String[] args) {
        List<Day> course = new LinkedList<>();
        List<String> reportOne = new LinkedList<>();
        List<String> reportTwo = new LinkedList<>();

        File initialFile = new File(join(File.separator, "resources", "log_file.txt"));

//        ReportDuration reportDuration = new ReportDuration();
        ReportDuration.makeReportToFile(initialFile, course);

        ReportDuration.makeReportOne(course, reportOne);
        ReportDuration.makeReportTwo(course, reportTwo);

        WriteUtil.writingReportOne(reportOne);
        WriteUtil.writingReportTwo(reportTwo);

    }
}

