package lt.tomas.test.controller;

import lt.tomas.test.model.Person;
import lt.tomas.test.model.Room;
import lt.tomas.test.model.SqliteCommands;
import lt.tomas.test.model.sqlclasses.HistoryElement;

import java.util.List;

public class ControllerCommands {

    public ControllerCommands() {
    }

    public boolean putClient(Person person) {
        HistoryElement hs;
        SqliteCommands sq = new SqliteCommands();
        boolean roomFound = false;

        for (Room room : sq.getRooms()) {
            if (roomFound) {
                break;
            }
            if (!room.hasPerson()) {
                room.setPerson(person);
                hs = new HistoryElement(room, "check-in");
                sq.insertHistory(hs);
                sq.updateRoom(room);
                roomFound = true;
            }
        }
        return roomFound;
    }

    public boolean checkOut(Person person) {
        boolean deleted = false;
        HistoryElement hs;
        SqliteCommands sq = new SqliteCommands();
        for (Room room : sq.getRooms()) {
            if (deleted) {
                break;
            }
            if (room.getPerson() != null) {
                if (room.getPerson().getFirstName().equals(person.getFirstName()) && room.getPerson().getLastName().equals(person.getLastName())) {
                    hs = new HistoryElement(room, "check-out");
                    sq.insertHistory(hs);
                    sq.removePerson(room);
                    deleted = true;
                }
            }
        }
        return deleted;
    }

    public List<Room> getAllRooms() {
        SqliteCommands obj = new SqliteCommands();
        return obj.getRooms();
    }

    public List<HistoryElement> getRoomInfo(Room room) {
        SqliteCommands obj = new SqliteCommands();
        return obj.getRoomHistory(room);
    }
}
