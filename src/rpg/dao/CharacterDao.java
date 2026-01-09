package rpg.dao;

import rpg.decorator.AbilityFactory;
import rpg.model.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDao {

    public void save(Character c) throws Exception {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT OR REPLACE INTO characters " +
                             "(name, strength, intelligence, agility, abilities) " +
                             "VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, c.getName());
            ps.setInt(2, c.getStrength());
            ps.setInt(3, c.getIntelligence());
            ps.setInt(4, c.getAgility());
            ps.setString(5, String.join(",", c.getAbilities()));

            ps.executeUpdate();
        }
    }

    public Character findByName(String name) throws Exception {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM characters WHERE name = ?")) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            return mapRowToCharacter(rs);
        }
    }

    public List<Character> findAll() throws Exception {
        List<Character> characters = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM characters");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                characters.add(mapRowToCharacter(rs));
            }
        }
        return characters;
    }

    private Character mapRowToCharacter(ResultSet rs) throws Exception {
        Character character = new Character(
                rs.getString("name"),
                rs.getInt("strength"),
                rs.getInt("intelligence"),
                rs.getInt("agility")
        );

        String abilities = rs.getString("abilities");
        if (abilities != null && !abilities.isBlank()) {
            for (String ability : abilities.split(",")) {
                character = AbilityFactory.apply(character, ability);
            }
        }
        return character;
    }
}
