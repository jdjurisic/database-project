package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;


    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

    @Override
    public void executeQuery(String query, List<String> lst) {
            repository.executeQuery(query,lst);
    }

    @Override
    public void addInTable(HashMap<String, String> hashMap, String tableName) {
        repository.addInTable(hashMap, tableName);
    }

    @Override
    public void deleteInTable(HashMap<String, Object> hashMap, String table) {
        repository.deleteInTable(hashMap, table);
    }

    @Override
    public void updateInTable(HashMap<String, Object> oldHashMap, HashMap<String, Object> newHashMap, String table) {
        repository.updateInTable(oldHashMap, newHashMap, table);
    }

    @Override
    public void filterAndSortInTable(HashMap<String, String> hashMap,String columns, String tableName) {
        repository.filterAndSortInTable(hashMap, columns, tableName);
    }


    @Override
    public List<Row> clickOnTable(String query, String tableName) {
        return repository.clickOnTable(query, tableName);
    }

}
