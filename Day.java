package HometaskAboutRegex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Day {

    private double duration;
    private int durationLecture;
    private int durationBreak;
    private int durationPractice;
    private int durationExam;
    private List<ActiveAction> activeForDay;
}
