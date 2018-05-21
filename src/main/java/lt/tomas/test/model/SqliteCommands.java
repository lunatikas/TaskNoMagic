package lt.tomas.test.model;

import lt.tomas.test.model.connection.SQLiteJDBCDriverConnection;
import lt.tomas.test.model.sqlclasses.HistoryElement;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SqliteCommands {
    public SqliteCommands() {
    }

    SQLiteJDBCDriverConnection connect;

    public void insertHistory(HistoryElement hs) {
        connect = new SQLiteJDBCDriverConnection();
        String sql = "INSERT INTO 'History'(id,roomnr, name, lastname,action,datetime) VALUES(?,?,?,?,?,?)";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, null);
            pstmt.setString(2, hs.getRoom().getRoomNr());
            pstmt.setString(3, hs.getRoom().getPerson().getFirstName());
            pstmt.setString(4, hs.getRoom().getPerson().getLastName());
            pstmt.setString(5, hs.getAction());
            pstmt.setString(6, hs.getTime().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
        connect.closeConnection(conn);
    }

    public List<HistoryElement> getRoomHistory(Room room) {
        List<HistoryElement> lis = new ArrayList<>();
        HistoryElement hs;
        Room rm;
        Person pr;

        connect = new SQLiteJDBCDriverConnection();
        String sql = "SELECT * FROM 'History' WHERE roomnr =" + room.getRoomNr() + "";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pr = new Person(rs.getString("name"), rs.getString("lastname"));
                rm = new Room(rs.getString("roomnr"), pr);
                hs = new HistoryElement(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(rs.getString("datetime")), rs.getString("action"), rm);
                lis.add(hs);
            }
        } catch (SQLException e) {
        } catch (ParseException p) {
        }
        connect.closeConnection(conn);
        return lis;
    }

    public void insertRoom(Room rm) {
        connect = new SQLiteJDBCDriverConnection();
        String sql = "INSERT INTO 'Room'(id,name,lastname) VALUES(?,?,?)";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, null);
            pstmt.setString(2, rm.getPerson().getFirstName());
            pstmt.setString(3, rm.getPerson().getLastName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
        connect.closeConnection(conn);
    }

    public void updateRoom(Room rm) {
        connect = new SQLiteJDBCDriverConnection();
        String sql = "UPDATE Room SET name = '" + rm.getPerson().getFirstName() + "', lastname = '" + rm.getPerson().getLastName() + "'  WHERE ID = '" + Integer.parseInt(rm.getRoomNr()) + "' ";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
        connect.closeConnection(conn);
    }

    public void removePerson(Room rm) {
        connect = new SQLiteJDBCDriverConnection();
        String sql = "UPDATE Room SET name = '', lastname = ''  WHERE name= '" + rm.getPerson().getFirstName() + "' AND lastname = '" + rm.getPerson().getLastName() + "'";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
        connect.closeConnection(conn);
    }

    public List<Room> getRooms() {
        List<Room> lis = new ArrayList<>();
        HistoryElement hs;
        Room rm;
        Person pr;
        connect = new SQLiteJDBCDriverConnection();
        String sql = "SELECT * FROM 'Room'";
        Connection conn = this.connect.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pr = new Person(rs.getString("name"), rs.getString("lastname"));
                rm = new Room(rs.getString("id"), pr);
                if (!rm.hasPerson()) {
                    rm.getPerson().setFirstName("Empty");
                }
                lis.add(rm);
            }
        } catch (SQLException e) {
            connect.closeConnection(conn);
        }
        return lis;
    }
}

