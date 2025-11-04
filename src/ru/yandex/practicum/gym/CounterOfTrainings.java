package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int countOfTrainings;

    public CounterOfTrainings(Coach coach, int countOfTrainings) {
        this.coach = coach;
        this.countOfTrainings = countOfTrainings;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        if (o.countOfTrainings == countOfTrainings) {
            return coach.compareTo(o.getCoach());
        }
        return countOfTrainings - o.countOfTrainings;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) object;
        return Objects.equals(coach, that.coach) && Objects.equals(countOfTrainings, that.countOfTrainings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, countOfTrainings);
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", countOfTrainings=" + countOfTrainings +
                '}';
    }

    public int getCountOfTrainings() {
        return countOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }
}
