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

}
