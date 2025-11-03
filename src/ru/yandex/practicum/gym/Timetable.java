package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        for (DayOfWeek dayOfWeek: DayOfWeek.values()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> treeMap = new TreeMap<>();
            timetable.put(dayOfWeek, treeMap);
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        if (!timetable.get(day).containsKey(time)) { // если день в расписании есть, но в его TreeMap пока нет нужного времени
            ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
            timetable.get(day).put(time, trainingSessions);
        }
        timetable.get(day).get(time).add(trainingSession); // get(day) и get(time), благодаря проверкам выше,
        // работают всегда правильно
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
       return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> countOfTrainingsForAllCoaches = new HashMap<>();
        for (DayOfWeek keyDay: timetable.keySet()) {
            for (TimeOfDay keyTime: timetable.get(keyDay).keySet()) {
                for (TrainingSession trainingSession: timetable.get(keyDay).get(keyTime)) {
                    Coach coach = trainingSession.getCoach();
                    if (!countOfTrainingsForAllCoaches.containsKey(coach)) {
                        countOfTrainingsForAllCoaches.put(coach, 1);
                    } else {
                        countOfTrainingsForAllCoaches.put(coach, countOfTrainingsForAllCoaches.get(coach) + 1);
                    }
                }
            }
        }
        ArrayList<CounterOfTrainings> arrayList = new ArrayList<>();
        for (Coach coach: countOfTrainingsForAllCoaches.keySet()) {
            arrayList.add(new CounterOfTrainings(coach, countOfTrainingsForAllCoaches.get(coach)));
        }
        arrayList.sort(Comparator.reverseOrder());
        return arrayList;
    }
}
