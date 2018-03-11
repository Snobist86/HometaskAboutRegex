package HometaskAboutRegex;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAction {

    private String startTime;
    private String finishTime;
    private String activity;

    @Override
    public String toString() {

        return String.format("%s - %s %s", startTime, finishTime, activity);
    }
}
