package Databaza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Databaza {
    private Connection conn;

    public Databaza(String url, String pouzivatel, String heslo) throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, pouzivatel, heslo);
            if (conn == null){
                System.err.println("Pripojenie s databazou zlyhalo");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void pridatRoman(Roman roman) throws SQLException {
        String sql = "INSERT INTO Romany (nazov, autor, rok, pozicana, zaner) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roman.nazov);
            stmt.setString(2, roman.autor);
            stmt.setInt(3, roman.rok);
            stmt.setBoolean(4, roman.jePozicana);
            stmt.setString(5, roman.zaner);
            stmt.executeUpdate();
        }
    }

    public void pridatUcebnicu(Ucebnica ucbnica) throws SQLException {
        String sql = "INSERT INTO Ucebnice (nazov, autor, rok, typ, pozicana, vek_kategoria) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ucbnica.nazov);
            stmt.setString(2, ucbnica.autor);
            stmt.setInt(3, ucbnica.rok);
            stmt.setBoolean(4, ucbnica.jePozicana);
            stmt.setInt(5, ucbnica.vek_kategoria);
            stmt.executeUpdate();
        }

    }

    public List<Kniha> ziskatVsetkyKnihy() throws SQLException {
        List<Kniha> knihy = new ArrayList<>();
        String sql = "SELECT * FROM Romany";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nazov = rs.getString("nazov");
                String autor = rs.getString("autor");
                int rok = rs.getInt("rok");
                boolean pozicana = rs.getBoolean("pozicana");
                String zaner = rs.getString("zaner");
                Roman r = new Roman(nazov, autor, rok, zaner);
                r.jePozicana = pozicana;
                knihy.add(r);
            }
        }
        sql = "SELECT * FROM Ucebnice";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nazov = rs.getString("nazov");
                String autor = rs.getString("autor");
                int rok = rs.getInt("rok");
                boolean pozicana = rs.getBoolean("pozicana");
                int vek_kategoria = rs.getInt("vek_kategoria");
                Ucebnica u = new Ucebnica(nazov, autor, rok, vek_kategoria);
                knihy.add(u);
            }
        }
        return knihy;
    }

    public void uploadVsetkyKnihy(List<Kniha> knihy) throws SQLException {
        for (Kniha kniha : knihy) {
            if (kniha instanceof Roman){
                pridatRoman((Roman) kniha);
            }
            else if (kniha instanceof Ucebnica){
                pridatUcebnicu((Ucebnica) kniha);
            }
        }
    }

    public void odpojitSa() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
