package rpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
