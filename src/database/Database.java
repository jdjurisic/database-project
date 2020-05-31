package database;

import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
import java.util.List;

public interface Database{

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);

    void executeQuery(String query,List<String> lst);

}
