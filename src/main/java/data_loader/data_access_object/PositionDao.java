package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDao {

    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private PositionDao() {
    }

    public static List<Position> getAllPositionsFromDb() {
        List<Position> positionList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.POSITION");
            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    positionList.add(new Position(rs.getString("POSITIONID"),
                            rs.getString("NAME"), rs.getString("DESCRIPTION"),
                            rs.getString("ANNOTATION")));
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionList;
    }

    public static Position getPositionByPositionId(String positionId){
        try {
            preparedStmt = con.prepareStatement("SELECT  * FROM OPTKOS.POSITION WHERE POSITIONID=?");
            preparedStmt.setString(1,positionId);
            ResultSet rs = preparedStmt.executeQuery();

            Position position = new Position();
            if(rs.next()) {
                position.setPositionId(rs.getString("POSITIONID"));
                position.setNote(rs.getString("ANNOTATION"));
                position.setDescription(rs.getString("DESCRIPTION"));
                position.setName(rs.getString("NAME"));
                return position;
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean createPosition(Position position) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.POSITION(POSITIONID, NAME, DESCRIPTION, ANNOTATION) VALUES (?,?,?,?)");
            preparedStmt.setString(1, position.getPositionId());
            preparedStmt.setString(2, position.getName());
            preparedStmt.setString(3, position.getDescription());
            preparedStmt.setString(4, position.getNote());

            preparedStmt.executeUpdate();
            preparedStmt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
