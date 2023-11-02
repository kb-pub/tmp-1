package app.domain;

import java.util.function.Predicate;

public class NotBoryaPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return person.getName().equals("Боря");
    }
}
