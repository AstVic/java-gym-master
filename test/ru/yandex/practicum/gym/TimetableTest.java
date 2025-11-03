package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        int count = 0;
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> result = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        if (!result.isEmpty()) {
            for (TimeOfDay timeOfDay : result.navigableKeySet()) {
                count += result.get(timeOfDay).size();
            }
        }
        Assertions.assertEquals(1, count);

        //Проверить, что за вторник не вернулось занятий
        result = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        int count = 0;
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> result = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        if (!result.isEmpty()) {
            for (TimeOfDay timeOfDay : result.navigableKeySet()) {
                count += result.get(timeOfDay).size();
            }
        }
        Assertions.assertEquals(1, count);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        result = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Set<TimeOfDay> keys = new HashSet<>();
        keys.add(new TimeOfDay(13, 0));
        keys.add(new TimeOfDay(20, 0));
        Assertions.assertEquals(keys, result.keySet());

        // Проверить, что за вторник не вернулось занятий
        result = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, result.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertNull(result);
    }

    @Test
        // Test 1
    void testGetTrainingSessionsForDayEmptyTimetable() {
        Timetable timetable = new Timetable();
        Assertions.assertNotNull(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));

        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(12, 30)));
    }

    @Test
        // Test 2
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> result = new ArrayList<>();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        result.add(singleTrainingSession);

        group = new Group("Акробатика для взрослых", Age.ADULT, 60);
        coach = new Coach("Васильева", "Наталья", "Сергеевна");
        singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        result.add(singleTrainingSession);

        Assertions.assertEquals(result, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
    }

    @Test
        // Test 3
    void testGetTrainingSessionsForDayMultipleSessionsOrder() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession15 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(singleTrainingSession15);

        group = new Group("Акробатика для взрослых", Age.ADULT, 60);
        coach = new Coach("Васильева", "Наталья", "Сергеевна");
        TrainingSession singleTrainingSession13 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession13);

        List<TimeOfDay> expectedOrder = Arrays.asList(
                new TimeOfDay(13, 0),
                new TimeOfDay(15, 0)
        );

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> result = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(expectedOrder, new ArrayList<>(result.keySet()));

        Assertions.assertEquals(List.of(singleTrainingSession13), result.get(new TimeOfDay(13, 0)));
        Assertions.assertEquals(List.of(singleTrainingSession15), result.get(new TimeOfDay(15, 0)));

    }

    @Test
        // Test 1 for countOfTrainings
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();
        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
// Test 2 for countOfTrainings
    void testGetCountByCoachesSameCounts() {
        Timetable timetable = new Timetable();

        Coach coach2 = new Coach("Иванов", "Петр", "Сергеевич");
        Coach coach1 = new Coach("Сидорова", "Мария", "Игоревна");

        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 45);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group2, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach2, DayOfWeek.THURSDAY, new TimeOfDay(11, 0)));

        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(2, result.get(0).getCountOfTrainings());
        Assertions.assertEquals(2, result.get(1).getCountOfTrainings());
        Assertions.assertEquals(coach2, result.get(0).getCoach());
        Assertions.assertEquals(coach1, result.get(1).getCoach());
    }

    @Test
// Test 3 for countOfTrainings
    void testGetCountByCoachesMultipleCoachesDifferentCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Петр", "Сергеевич");
        Coach coach2 = new Coach("Сидорова", "Мария", "Игоревна");
        Coach coach3 = new Coach("Петров", "Алексей", "Викторович");

        Group group = new Group("Йога", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY, new TimeOfDay(11, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));

        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(3, result.get(0).getCountOfTrainings()); // coach3
        Assertions.assertEquals(2, result.get(1).getCountOfTrainings()); // coach2
        Assertions.assertEquals(1, result.get(2).getCountOfTrainings()); // coach1
    }
}
