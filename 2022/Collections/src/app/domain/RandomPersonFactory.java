package app.domain;

import java.time.LocalDate;
import java.util.Random;

public class RandomPersonFactory {
    private final static String[]
            NAMES = new String[]{"Вася", "Петя", "Боря"},
            SURNAMES = new String[]{"Васильев", "Петров", "Сидоров"};
    private final Random random = new Random(42);

    public Person get() {
        return new Person(
                NAMES[random.nextInt(NAMES.length)],
                SURNAMES[random.nextInt(SURNAMES.length)],
                LocalDate.of(
                        random.nextInt(1990, 2020),
                        random.nextInt(1, 13),
                        random.nextInt(1, 29)
                ));
    }
}
