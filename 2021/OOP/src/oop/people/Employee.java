package oop.people;

abstract public class Employee extends Person {
    private Post post;

    public Employee(String name, String surname, Post post) {
        super(name, surname);
        this.post = post;
    }

    public String sayWhoAmI() {
        return "i an employee " + getFullName();
    }

    public Post getPost() {
        return post;
    }
}
