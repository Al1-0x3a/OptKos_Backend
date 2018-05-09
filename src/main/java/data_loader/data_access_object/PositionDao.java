package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Position> positionList = new ArrayList<>();

    private PositionDao() {
    }

    public static List<Position> getAllPositionsFromDb() {
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.POSITION";
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    positionList.add(new Position(rs.getString("POSITIONID"),
                            rs.getString("NAME"), rs.getString("DESCRIPTION"),
                            rs.getString("ANNOTATION")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionList;
    }

    public static Position getPositionByPositionId(String positionId){
        if(positionList == null ){
            positionList = getAllPositionsFromDb();
        }
        Position tmp = null;
        for (Position p : positionList) {
            if (p.getPositionId() == positionId) {
                tmp = p;
            }
        }
        return tmp;
    }

    public static void createPosition(Position position) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.POSITION(POSITIONID, NAME, DESCRIPTION, ANNOTATION) VALUES (?,?,?,?)");
            preparedStmt.setString(1, position.getPositionId());
            preparedStmt.setString(2, position.getName());
            preparedStmt.setString(3, position.getDescription());
            preparedStmt.setString(4, position.getNote());

            positionList.add(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
