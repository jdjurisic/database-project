package database;

import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
import java.util.List;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);

    void executeQuery(String query, List<String> lst);
}
