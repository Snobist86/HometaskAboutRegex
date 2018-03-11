package HometaskAboutRegex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum KindOfActivity {

    LECTURE("Лекции", Arrays.asList("Введение", "Скаляры", "Массивы", "Углубленное изучение массивов", "Условные операторы",
            "Тернары", "Циклы", "Углубленное изучение циклов", "Коллекции", "Потоки", "Сериализация", "Заключение")),
    PRACTICE("Практика", Arrays.asList("Упражнения", "Решения")),
    BREAK("Перерыв", Arrays.asList("Перерыв", "Обеденный перерыв")),
    EXAM("Экзамен", Arrays.asList("Экзамен", "Выставление баллов"));

    private String description;
    private List<String> listAction;

    public static KindOfActivity identifyTypeOfActivity(String activityName) {

        return Arrays.stream(KindOfActivity.values())
                .filter(type -> type.getListAction().contains(activityName))
                .findFirst()
                .orElse(LECTURE);
    }
}
