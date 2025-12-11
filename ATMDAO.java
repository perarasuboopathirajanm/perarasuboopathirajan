package atmproject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMDAO {

    public ATM getAccount(int pin) {
        ATM acc = null;
        try (Connection con = DBConnect.getConnection()) {
            if (con == null) return null;

            String query = "SELECT * FROM accounts WHERE pin=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, pin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                acc = new ATM(rs.getInt("pin"), rs.getString("name"), rs.getDouble("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public boolean updateBalance(int pin, double newBalance) {
        try (Connection con = DBConnect.getConnection()) {
            if (con == null) return false;

            String query = "UPDATE accounts SET balance=? WHERE pin=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, newBalance);
            ps.setInt(2, pin);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePin(int oldPin, int newPin) {
        try (Connection con = DBConnect.getConnection()) {
            if (con == null) return false;

            // Check if new PIN exists
            String checkQuery = "SELECT * FROM accounts WHERE pin=?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setInt(1, newPin);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                System.out.println("New PIN already exists!");
                return false;
            }

            String query = "UPDATE accounts SET pin=? WHERE pin=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newPin);
            ps.setInt(2, oldPin);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
