package rpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class ArmyDao {

    public void saveArmy(String name) throws Exception {
        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("INSERT OR IGNORE INTO armies(name) VALUES(?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void addGroupToArmy(String army, String group) throws Exception {
        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "INSERT INTO army_groups VALUES(?, ?)")) {
            ps.setString(1, army);
            ps.setString(2, group);
            ps.executeUpdate();
        }
    }

    public Map<String, List<String>> loadArmyGroups() throws Exception {
        Map<String, List<String>> map = new HashMap<>();

        try (Connection c = Database.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(
                             "SELECT army_name, group_name FROM army_groups");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.computeIfAbsent(
                        rs.getString("army_name"),
                        k -> new ArrayList<>()
                ).add(rs.getString("group_name"));
            }
        }
        return map;
    }
}
