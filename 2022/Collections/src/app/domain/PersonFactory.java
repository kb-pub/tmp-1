package app.domain;

import java.time.LocalDate;
import java.util.Random;

public class PersonFactory {
    public static final String[]
            NAMES = {"Иван", "Николай", "Васисуалий", "Егор", "Акакий"},
            SURNAMES = {"Иванов", "Николаев", "Васисуалиев", "Егоров", "Акакиев"};

    private static final Random RANDOM = new Random(42);

    public static Person get() {
        return new Person(
                getRandomName(),
                getRandomSurname(),
                getRandomBirthdate());
    }

    private static String getRandomName() {
        return NAMES[RANDOM.nextInt(NAMES.length)];
    }

    private static String getRandomSurname() {
        return SURNAMES[RANDOM.nextInt(SURNAMES.length)];
    }

//    private static LocalDate getRandomBirthdate() {
//        return LocalDate.of(2022, 10, 5);
//    }

    private static LocalDate getRandomBirthdate() {
        return LocalDate.of(
                RANDOM.nextInt(1990, 2000),
                RANDOM.nextInt(1, 13),
                RANDOM.nextInt(1, 29));
    }
}
