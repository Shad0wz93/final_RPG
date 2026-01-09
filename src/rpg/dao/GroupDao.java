package rpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GroupDao {

    public void saveGroup(String name) throws Exception {
        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("INSERT OR IGNORE INTO groups(name) VALUES(?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void addCharacterToGroup(String group, String character) throws Exception {
        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "INSERT INTO group_characters VALUES(?, ?)")) {
            ps.setString(1, group);
            ps.setString(2, character);
            ps.executeUpdate();
        }
    }
}
