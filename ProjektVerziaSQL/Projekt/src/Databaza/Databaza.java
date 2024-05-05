package Databaza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Databaza {
    
    private static Connection conn = null;

    private Databaza() {}

    public static Connection getConnection(String filePath) {
        if (conn == null) {
            synchronized (Databaza.class) {
                if (conn == null) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                        conn = DriverManager.getConnection("jdbc:sqlite:" + filePath);
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();  
                    }
                }
            }
        }
        return conn;
    }

    
    public static void initDb() throws SQLException {
        String sqlCreateRomany =
            "CREATE TABLE IF NOT EXISTS Romany (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nazov TEXT, " +
            "autor TEXT, " +
            "rok INTEGER, " +
            "pozicana BOOLEAN, " +
            "zaner TEXT);";

        String sqlCreateUcebnice =
            "CREATE TABLE IF NOT EXISTS Ucebnice (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nazov TEXT, " +
            "autor TEXT, " +
            "rok INTEGER, " +
            "typ TEXT, " +
            "pozicana BOOLEAN, " +
            "vek_kategoria INTEGER);";

        try (PreparedStatement stmtRomany = conn.prepareStatement(sqlCreateRomany);
             PreparedStatement stmtUcebnice = conn.prepareStatement(sqlCreateUcebnice)) {
            stmtRomany.execute();
            stmtUcebnice.execute();
        }
    }

    public static void pridatRoman(Roman roman) throws SQLException {
        String sql = "INSERT INTO Romany (nazov, autor, rok, pozicana, zaner) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roman.nazov);
            stmt.setString(2, roman.autor);
            stmt.setInt(3, roman.rok);
            stmt.setBoolean(4, roman.jePozicana);
            stmt.setString(5, roman.getZaner());
            stmt.executeUpdate();
        }
    }

    public static void pridatUcebnicu(Ucebnica ucebnica) throws SQLException {
        String sql = "INSERT INTO Ucebnice (nazov, autor, rok, vek_kategoria, pozicana) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ucebnica.nazov);
            stmt.setString(2, ucebnica.autor);
            stmt.setInt(3, ucebnica.rok);
            stmt.setInt(4, ucebnica.getVek_kategoria());
            stmt.setBoolean(5, ucebnica.jePozicana);
            stmt.executeUpdate();
        }
    }

    public static List<Kniha> ziskatVsetkyKnihy() throws SQLException {
        List<Kniha> knihy = new ArrayList<>();

        knihy.addAll(nacitatKnihy("SELECT nazov, autor, rok, pozicana, zaner FROM Romany", true));
        knihy.addAll(nacitatKnihy("SELECT nazov, autor, rok, pozicana, vek_kategoria FROM Ucebnice", false));

        return knihy;
    }

    private static List<Kniha> nacitatKnihy(String sql, boolean jeRoman) throws SQLException {
        List<Kniha> knihy = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (jeRoman) {
                    Roman roman = new Roman(
                        rs.getString("nazov"), 
                        rs.getString("autor"), 
                        rs.getInt("rok"), 
                        rs.getString("zaner")
                    );
                    roman.jePozicana = rs.getBoolean("pozicana");
                    knihy.add(roman);
                } else {
                    Ucebnica ucebnica = new Ucebnica(
                        rs.getString("nazov"), 
                        rs.getString("autor"), 
                        rs.getInt("rok"), 
                        rs.getInt("vek_kategoria")
                    );
                    ucebnica.jePozicana = rs.getBoolean("pozicana");
                    knihy.add(ucebnica);
                }
            }
        }
        return knihy;
    }

    public static void uploadVsetkyKnihy(List<Kniha> knihy) throws SQLException {
        conn.setAutoCommit(false);
        try {
            for (Kniha kniha : knihy) {
                if (kniha instanceof Roman) {
                    pridatRoman((Roman) kniha);
                } else if (kniha instanceof Ucebnica) {
                    pridatUcebnicu((Ucebnica) kniha);
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void odpojitSa() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
