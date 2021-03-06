package database;

import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);

    void executeQuery(String query, List<String> lst);

    void addInTable(HashMap<String, String> hashMap, String tableName);

    void deleteInTable(HashMap<String, Object> hashMap, String table);

    void updateInTable(HashMap<String, Object> oldHashMap, HashMap<String, Object> newHashMap,String table);

    List<Row> filterAndSortInTable(HashMap<String, String> hashMap, String columns, String tableName);

    List<Row> clickOnTable(String query,  String tableName);

    List<Row> countOnTable(String tableName, String columnCount, ArrayList<String> groupBy);

    List<Row> avgOnTable(String tableName, String avgColumn, ArrayList<String> groupBy);
}
