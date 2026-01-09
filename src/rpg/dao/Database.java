package rpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private static final String DB_PATH;

    static {
        try {
            String projectDir = System.getProperty("user.dir");
            DB_PATH = projectDir + File.separator + "data" + File.separator + "characters.db";

            File dataDir = new File(projectDir, "data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
                 Statement s = c.createStatement()) {

                s.execute("""
                    CREATE TABLE IF NOT EXISTS characters (
                        name TEXT PRIMARY KEY,
                        strength INTEGER,
                        intelligence INTEGER,
                        agility INTEGER,
                        abilities TEXT
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
