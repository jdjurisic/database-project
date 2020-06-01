package database;

import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

public interface Database{

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);

    void executeQuery(String query,List<String> lst);

    void addInTable(HashMap<String, String> hashMap, String tableName);

    void deleteInTable(HashMap<String, Object> hashMap, String table);

    void updateInTable(HashMap<String, Object> oldHashMap, HashMap<String, Object> newHashMap,String table);

    List<Row> filterAndSortInTable(HashMap<String, String> hashMap, String columns, String tableName);

    List<Row> clickOnTable(String query,  String tableName);


}
