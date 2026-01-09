package rpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private static final String DB_PATH;

    static {
        try {
            String dir = System.getProperty("user.dir");
            DB_PATH = dir + File.separator + "data" + File.separator + "characters.db";

            new File(dir + File.separator + "data").mkdirs();

            try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
                 Statement s = c.createStatement()) {

                s.execute("""
                    CREATE TABLE IF NOT EXISTS characters(
                        name TEXT PRIMARY KEY,
                        strength INTEGER,
                        intelligence INTEGER,
                        agility INTEGER,
                        abilities TEXT
                    )
                """);

                s.execute("""
                    CREATE TABLE IF NOT EXISTS groups(name TEXT PRIMARY KEY)
                """);

                s.execute("""
                    CREATE TABLE IF NOT EXISTS armies(name TEXT PRIMARY KEY)
                """);

                s.execute("""
                    CREATE TABLE IF NOT EXISTS group_characters(
                        group_name TEXT,
                        character_name TEXT
                    )
                """);

                s.execute("""
                    CREATE TABLE IF NOT EXISTS army_groups(
                        army_name TEXT,
                        group_name TEXT
                    )
                """);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }
}
