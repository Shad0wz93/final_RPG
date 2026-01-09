package rpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

    public Map<String, List<String>> loadGroupCharacters() throws Exception {
        Map<String, List<String>> map = new HashMap<>();

        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "SELECT group_name, character_name FROM group_characters");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.computeIfAbsent(
                        rs.getString("group_name"),
                        k -> new ArrayList<>()
                ).add(rs.getString("character_name"));
            }
        }
        return map;
    }
}
