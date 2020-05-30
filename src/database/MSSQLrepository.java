package database;

import database.settings.Settings;
import lombok.Data;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class MSSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MSSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }


    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("RAF_BP_Primer");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);

            while (tables.next()){

                String tableName = tables.getString("TABLE_NAME");
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                //Koje atribute imaja ova tabela?

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()){

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);

                    newTable.addChild(attribute);
                    System.out.println(attribute.toString());

                }

            }

            System.out.println("NOVO");
            ResultSet tables1 = metaData.getTables(connection.getCatalog(),"dbo",null,tableType);


            String fkTableName = null;
            String fkColumnName = null;

            while(tables1.next()){
                String tableName1 = tables1.getString("TABLE_NAME");
                ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName1);
                System.out.println(tableName1);
                while(foreignKeys.next()){

                    fkTableName = foreignKeys.getString("PKTABLE_NAME");
                    fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                    if(fkTableName.equals(tableName1))break;
                    System.out.println("Tabela:"+tableName1 +"strani kljuc iz tabele:"+fkTableName +" "+ fkColumnName);
                    Entity tabela = (Entity)ir.getChildByName(tableName1);
                    Attribute atr = (Attribute)tabela.getChildByName(fkColumnName);
                    System.out.println("TABELA:"+tabela);
                    System.out.println("ATR:"+atr);
                    System.out.println("ISPIS "+ fkTableName + fkColumnName);
                    Entity tabelaVeza = (Entity) ir.getChildByName(fkTableName);
                    System.out.println("POKUSAJ "+tabelaVeza);
                    Attribute atrVeza = (Attribute) tabelaVeza.getChildByName(fkColumnName);
                    System.out.println("POKUSAJ2 "+atrVeza);
                    atr.setInRelationWith(atrVeza);
                    System.out.println("NOVA KOLONA:"+tabela);
                }
            }
//moje

//            System.out.println(" --- ");

//            System.out.println(" --- ");
            //moje

            //TODO Ogranicenja nad kolonama? Relacije?

            //System.out.println(ir);
            return ir;
            // String isNullable = columns.getString("IS_NULLABLE");
            //ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, newTable.getName());
            // ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, table.getName());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }
}