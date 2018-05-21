package lt.tomas.test.model.sqlclasses;

import lt.tomas.test.model.Room;

import java.util.Date;

public class HistoryElement {

    private Date time ;
    private String action;
    private Room room;

    public HistoryElement(Room room,String action) {
        this.room=room;
        this.action=action;
        time = new Date();
    }

    public HistoryElement(Date time, String action, Room room) {
        this.time = time;
        this.action = action;
        this.room = room;
    }

    //region getter
    public Date getTime() {
        return time;
    }

    public String getAction() {
        return action;
    }

    public Room getRoom() {
        return room;
    }
    //endregion
}
