package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.sql.PreparedStatement;
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

}
