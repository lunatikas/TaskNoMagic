package lt.tomas.test.model;


public class Room {
    private String roomNr;
    private Person person;

    public Room() {
    }

    public Room(String roomNr) {
        this.roomNr = roomNr;
    }

    public Room(String roomNr, Person person) {
        this.roomNr = roomNr;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRoomNr() {
        return roomNr;
    }

    public boolean hasPerson() {
        boolean notEmpty = false;
        if (person != null) {
            if (person.getFirstName() != null && !person.getFirstName().equals("") && !person.getFirstName().equals("Empty")) {
                notEmpty = true;
            }
        }
        return notEmpty;
    }
}