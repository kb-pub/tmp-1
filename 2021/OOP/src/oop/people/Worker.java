package oop.people;

public class Worker extends Employee {
    public Worker(String name, String surname) {
        super(name, surname, Post.WORKER);
    }

    private void handle(Task task) {
        System.out.println(this + " handling " + task);
    }

    @Override
    public String toString() {
        return "worker " + getFullName();
    }

    @Override
    public String sayWhoAmI() {
        return "i am a worker, " + getFullName();
    }
}
