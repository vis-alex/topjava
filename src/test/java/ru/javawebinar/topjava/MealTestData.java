package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID1 = START_SEQ + 2;
    public static final int MEAL_ID2 = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL_ID1, LocalDateTime.parse("1991-07-01T13:12:57"), "Hola", 1001);
    public static final Meal meal2 = new Meal(MEAL_ID2, LocalDateTime.parse("2008-07-02T03:12:21"), "Its later", 501);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("1941-07-01T13:12:57"), "New", 10);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.parse("1991-07-01T13:12:57"));
        updated.setDescription("Hola");
        updated.setCalories(1001);
        return updated;
    }

}
