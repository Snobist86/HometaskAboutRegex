package HometaskAboutRegex;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static HometaskAboutRegex.KindOfActivity.PRACTICE;

public class Report {

    private final static String TIME = "(\\d+:\\d+)";
    private final static String ACTIVITY = "[А-Яа-я\\s]+";
    private static final int SECONDS_FOR_MINUTE = 60;
    private static final String END_DAY = "конец дня\n";
    private static final int ONE_HUNDRED_PERCENT = 100;
    private static final String SEPARATOR_OF_DAY = "";

    public static void makeReportToFile(File file, List<Day> course) {
        List<String> timeTable = new ArrayList<>();
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)))) {

            while (scanner.hasNextLine()) {
                String presentString = scanner.nextLine();

                if (presentString.trim().length() == 0) {
                    Day day = makeDayReport(timeTable);
                    calculateTimeForReport(day);
                    course.add(day);
                    timeTable.clear();
                } else {
                    timeTable.add(presentString);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Day makeDayReport(List<String> list) {
        Day day = new Day();
        List<ActiveAction> listActivity = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            ActiveAction action = new ActiveAction();
            action.setStartTime(getSubString(list.get(i), TIME));
            action.setFinishTime(getSubString(list.get(i + 1), TIME));
            action.setActivity(getSubString(list.get(i), ACTIVITY));
            listActivity.add(action);
        }
        day.setActiveForDay(listActivity);
        String startDay = listActivity.get(0).getStartTime();
        String finishDay = listActivity.get(listActivity.size() - 1).getFinishTime();
        day.setDuration(calculateDurationActivity(startDay, finishDay));

        return day;
    }

    private static String getSubString(String line, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(line);
        String changeString = null;
        if (matcher.find()) {
            changeString = matcher.group();
        }

        return changeString;
    }

    private static int calculateDurationActivity(String current, String nextString) {
        LocalTime start = LocalTime.parse(current);
        LocalTime end = LocalTime.parse(nextString);
        Long timeActivity = Duration.between(start, end).getSeconds() / SECONDS_FOR_MINUTE;

        return timeActivity.intValue();
    }

    private static void calculateTimeForReport(Day day) {
        int durationLecture = 0;
        int durationBreak = 0;
        int durationPractice = 0;
        int durationExam = 0;

        for (ActiveAction action : day.getActiveForDay()) {
            switch (KindOfActivity.identifyTypeOfActivity(action.getActivity().trim())) {
                case LECTURE:
                    durationLecture += calculateDurationActivity(action.getStartTime(), action.getFinishTime());
                    break;
                case PRACTICE:
                    durationPractice += calculateDurationActivity(action.getStartTime(), action.getFinishTime());
                    break;
                case BREAK:
                    durationBreak += calculateDurationActivity(action.getStartTime(), action.getFinishTime());
                    break;
                case EXAM:
                    durationExam += calculateDurationActivity(action.getStartTime(), action.getFinishTime());
                    break;
            }
        }
        day.setDurationLecture(durationLecture);
        day.setDurationPractice(durationPractice);
        day.setDurationBreak(durationBreak);
        day.setDurationExam(durationExam);
    }

    public static void makeReportOne(List<Day> course, List<String> list) {
        course.forEach(day -> {
            list.addAll(day.getActiveForDay()
                    .stream()
                    .map(ActiveAction::toString)
                    .collect(Collectors.toList()));
            list.add(END_DAY);
        }
        );
    }

    public static void makeReportTwo(List<Day> course, List<String> reportTwo) {
        double timeAllLecture = 0;

        for (Day day : course) {
            timeAllLecture += day.getDurationLecture();
        }
        makeReportTwoPathOne(course, reportTwo);
        makeReportTwoPathTwo(course, reportTwo, timeAllLecture);

    }

    private static void makeReportTwoPathOne(List<Day> course, List<String> reportTwo) {
        for (Day day : course) {
            for (KindOfActivity kindOfActivity : KindOfActivity.values()) {
                String kind = kindOfActivity.getDescription();
                double[] valueAndPercent = getValueDurationActivityAndPercentOfDay(day, kind);
                String articleOfReportTwoPathOne = String.format("%s: %.0f минут %.2f%%", kind, valueAndPercent[0], valueAndPercent[1]);
                reportTwo.add(articleOfReportTwoPathOne);
            }
            reportTwo.add(SEPARATOR_OF_DAY);
        }
    }

    private static double[] getValueDurationActivityAndPercentOfDay(Day day, String activity) {
        double[] valueAndPercent = new double[2];
        switch (activity) {
            case "Лекции":
                valueAndPercent[0] = day.getDurationLecture();
                valueAndPercent[1] = day.getDurationLecture() * ONE_HUNDRED_PERCENT / day.getDuration();
                break;
            case "Практика":
                valueAndPercent[0] = day.getDurationPractice();
                valueAndPercent[1] = day.getDurationPractice() * ONE_HUNDRED_PERCENT / day.getDuration();
                break;
            case "Перерыв":
                valueAndPercent[0] = day.getDurationBreak();
                valueAndPercent[1] = day.getDurationBreak() * ONE_HUNDRED_PERCENT / day.getDuration();
                break;
            case "Экзамен":
                valueAndPercent[0] = day.getDurationExam();
                valueAndPercent[1] = day.getDurationExam() * ONE_HUNDRED_PERCENT / day.getDuration();
                break;
        }

        return valueAndPercent;
    }

    private static void makeReportTwoPathTwo(List<Day> course, List<String> reportTwo, double timeAllLecture) {
        reportTwo.add(String.format("%s:", KindOfActivity.LECTURE.getDescription()));

        for (Day day : course) {
            for (ActiveAction activeAction : day.getActiveForDay()) {
                String action = activeAction.getActivity().trim();
                if (KindOfActivity.LECTURE.getListAction().contains(action)) {
                    int durationLecture = calculateDurationActivity(activeAction.getStartTime(), activeAction.getFinishTime());
                    double percentTimeLectureOfAllLecture = durationLecture * ONE_HUNDRED_PERCENT / timeAllLecture;
                    String articleOfReportTwoPathTwo = String.format("%s: %d минут %.2f%%", action, durationLecture, percentTimeLectureOfAllLecture);
                    reportTwo.add(articleOfReportTwoPathTwo);
                }
            }
        }
    }
}
