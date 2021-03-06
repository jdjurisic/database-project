package database;

import database.settings.Settings;
import gui.MainFrame;
import lombok.Data;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);

                while (columns.next()){

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    String isNullable = columns.getString("IS_NULLABLE");
                    //System.out.println("KOLONA "+ columnName +" isnullable :"+isNullable);
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    if(isNullable.equals("NO"))attribute.addChild(new AttributeConstraint(ConstraintType.NOT_NULL.toString(),attribute,ConstraintType.NOT_NULL));
                    newTable.addChild(attribute);
                    //System.out.println(attribute.toString());
                }

                while(primaryKeys.next()){
                    //System.out.println("Za tab:"+tableName+" primarni kljuc :" + primaryKeys.getString("COLUMN_NAME"));
                    String up = primaryKeys.getString("COLUMN_NAME");
                    Attribute a =(Attribute) newTable.getChildByName(up);
                    a.addChild(new AttributeConstraint(ConstraintType.PRIMARY_KEY.toString(),a,ConstraintType.PRIMARY_KEY));
                }


            }

            //System.out.println("NOVO");
            ResultSet tables1 = metaData.getTables(connection.getCatalog(),"dbo",null,tableType);


            String fkTableName;
            String fkColumnName;
            String pkColumnName;

            while(tables1.next()){
                String tableName1 = tables1.getString("TABLE_NAME");
                ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName1);
                //System.out.println(tableName1);
                while(foreignKeys.next()){

                    fkTableName = foreignKeys.getString("PKTABLE_NAME");
                    fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                    pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                    if(fkTableName.equals(tableName1)){
                        Entity tabela = (Entity)ir.getChildByName(tableName1);
                        Attribute atr = (Attribute)tabela.getChildByName(fkColumnName);
                        Attribute atrVeza = (Attribute) tabela.getChildByName(pkColumnName);
                        atr.setInRelationWith(atrVeza);
                        atr.addChild(new AttributeConstraint(ConstraintType.FOREIGN_KEY.toString(),atr,ConstraintType.FOREIGN_KEY));

                        break;
                    }
                    //System.out.println("Tabela:"+tableName1 +"strani kljuc iz tabele:"+fkTableName +" "+ fkColumnName);
                    Entity tabela = (Entity)ir.getChildByName(tableName1);
                    Attribute atr = (Attribute)tabela.getChildByName(fkColumnName);
                    //System.out.println("TABELA:"+tabela);
                    //System.out.println("ATR:"+atr);
                    //System.out.println("ISPIS "+ fkTableName + fkColumnName);
                    Entity tabelaVeza = (Entity) ir.getChildByName(fkTableName);
                    //System.out.println("POKUSAJ "+tabelaVeza);
                    Attribute atrVeza = (Attribute) tabelaVeza.getChildByName(fkColumnName);
                    //System.out.println("POKUSAJ2 "+atrVeza);
                    atr.setInRelationWith(atrVeza);
                    atr.addChild(new AttributeConstraint(ConstraintType.FOREIGN_KEY.toString(),atr,ConstraintType.FOREIGN_KEY));
                    //System.out.println("NOVA KOLONA:"+tabela);
                }
            }

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
            //System.out.println("UPIT :" +query);
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

    @Override
    public void executeQuery(String query, List<String> lst) {
        System.out.println("Ispis iz msqlrepo:"+query +" lista "+lst);

        try {
            this.initConnection();
            PreparedStatement st = connection.prepareStatement(query);

            if(lst !=null){
                for(int i=0;i<lst.size();i++){
                    st.setString(i+1,lst.get(i));
                }
            }


            int j = st.executeUpdate();
            System.out.println("Izvrsavanje komadne:"+j);
            st.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
            JOptionPane.showMessageDialog(null, throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();

        }
    }

    @Override
    public void addInTable(HashMap<String, String> hashMap, String tableName) {

        try {
            this.initConnection();
            //String query = "INSERT INTO " + tableName + "(";

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INSERT INTO ");
            stringBuilder.append(tableName);
            //System.out.println(hashMap.size());
            stringBuilder.append(" ( ");
            int z=1;
            for (Map.Entry<String, String> entry: hashMap.entrySet()) {

                if(z == hashMap.size()){
                    stringBuilder.append(entry.getKey().toString()+" ) VALUES (");
                }else stringBuilder.append(entry.getKey().toString()+", ");
                z++;
            }


            for(int i=0;i<hashMap.size();i++){
                if(i+1 == hashMap.size()){
                    stringBuilder.append("? )");
                }else stringBuilder.append(" ? ,");
            }
            //System.out.println(stringBuilder.toString());
            //System.out.println(hashMap);
            PreparedStatement st = connection.prepareStatement(stringBuilder.toString());
           // int i = 1;
            /*for (Map.Entry<String, String> entry: hashMap.entrySet()) {
                st.setString(i,entry.getKey().toString());
                System.out.println(i+ "  "+ entry.getKey());
                i++;

            }*/
            int i = 1;
            for (Map.Entry<String, String> entry: hashMap.entrySet()) {
                st.setString(i,entry.getValue());
                //System.out.println(i+ "  "+ entry.getValue());
                i++;
            }
            //System.out.println(st.toString());
            st.executeUpdate();
            st.close();


        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            //System.out.println(throwables.getMessage());
            JOptionPane.showMessageDialog(null, throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            this.closeConnection();

        }




    }

    @Override
    public void deleteInTable(HashMap<String, Object> hashMap, String table) {


        try {
            this.initConnection();


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM ");
            stringBuilder.append(table);
            stringBuilder.append(" WHERE ");
            boolean firstTime=false;
            for(Map.Entry<String,Object> entry : hashMap.entrySet()){
                if(firstTime==false){
                    firstTime=true;

                }else{
                    if(entry.getValue() != null)stringBuilder.append(" AND ");
                }
                if(entry.getValue() != null){
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("='");
                    stringBuilder.append(entry.getValue().toString());
                    stringBuilder.append("'");
                }

            }
            stringBuilder.append(";");
           // System.out.println(stringBuilder.toString());
            PreparedStatement st = connection.prepareStatement(stringBuilder.toString());
            st.executeUpdate();

            st.close();


        } catch (SQLException throwables) {
            //throwables.printStackTrace();
           // System.out.println(throwables.getMessage());
            JOptionPane.showMessageDialog(null, throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            this.closeConnection();

        }

    }

    @Override
    public void updateInTable(HashMap<String, Object> oldHashMap, HashMap<String, Object> newHashMap, String table) {

        try {
            this.initConnection();

            StringBuilder newInput = new StringBuilder();
            for (Map.Entry<String,Object> entry : newHashMap.entrySet()){
                newInput.append(entry.getKey());
                newInput.append(" ='");
                newInput.append(entry.getValue());
                newInput.append("' ,");
            }
            newInput.deleteCharAt(newInput.length()-1);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE ");
            stringBuilder.append(table);
            stringBuilder.append(" SET ");
            // nova polja dodaj
            stringBuilder.append(newInput);

            stringBuilder.append(" WHERE ");
            boolean firstTime=false;
            for(Map.Entry<String,Object> entry : oldHashMap.entrySet()){
                if(firstTime==false){
                    firstTime=true;

                }else{
                    if(entry.getValue() != null) stringBuilder.append(" AND ");
                }
                if(entry.getValue() != null){
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("='");
                    stringBuilder.append(entry.getValue().toString());
                    stringBuilder.append("'");
                }
            }
            stringBuilder.append(";");
            //System.out.println(stringBuilder);



            PreparedStatement st = connection.prepareStatement(stringBuilder.toString());
            st.executeUpdate();

            st.close();


        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            // System.out.println(throwables.getMessage());
            JOptionPane.showMessageDialog(null, throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            this.closeConnection();

        }



    }

    @Override
    public List<Row> filterAndSortInTable(HashMap<String, String> hashMap, String columns, String tableName) {

        List<Row> rows = new ArrayList<>();
        StringBuilder sortCondition = new StringBuilder();
        sortCondition.append(" ORDER BY ");
        for(Map.Entry<String,String> entry : hashMap.entrySet()){
            //System.out.println("iz f-je:"+entry);
            sortCondition.append(entry.getKey());
            sortCondition.append(" ");
            sortCondition.append(entry.getValue());
            sortCondition.append(" , ");
        }
        sortCondition.deleteCharAt(sortCondition.lastIndexOf(","));
        sortCondition.append(";");

        try{
            this.initConnection();

            String queryToSend = "SELECT " + columns + " FROM " + tableName + sortCondition.toString();
            //System.out.println(queryToSend);
            PreparedStatement preparedStatement = connection.prepareStatement(queryToSend);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(tableName);

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
        //System.out.println(rows);
        return rows;

    }

    @Override
    public List<Row> clickOnTable(String query, String tableName) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String queryToSend = "SELECT * FROM " + tableName + " WHERE "+ query;
            //System.out.println(queryToSend);
            PreparedStatement preparedStatement = connection.prepareStatement(queryToSend);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(tableName);

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
        //System.out.println(rows);
        return rows;

    }

    @Override
    public List<Row> countOnTable(String tableName, String columnCount, ArrayList<String> groupBy) {

        List<Row> rows = new ArrayList<>();
        StringBuilder groupCondition = new StringBuilder();
        for(String str:groupBy){
            groupCondition.append(str);
            groupCondition.append(" , ");

        }
        if(groupCondition.lastIndexOf(",")>0)groupCondition.deleteCharAt(groupCondition.lastIndexOf(","));


        try{
            this.initConnection();

            StringBuilder queryToSend = new StringBuilder();
            queryToSend.append("SELECT COUNT(");
            queryToSend.append(columnCount);
            queryToSend.append(") as count_");
            queryToSend.append(columnCount);
            if(groupCondition.length()>0){
                queryToSend.append(",");
                queryToSend.append(groupCondition);
            }
            queryToSend.append(" FROM ");
            queryToSend.append(tableName);
            if(groupCondition.length()>0){
                queryToSend.append(" GROUP BY ");
                queryToSend.append(groupCondition);
            }
            queryToSend.append(";");
            //System.out.println(queryToSend);

            PreparedStatement preparedStatement = connection.prepareStatement(queryToSend.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(tableName);

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
        //System.out.println(rows);
        return rows;

    }


    @Override
    public List<Row> avgOnTable(String tableName, String avgColumn, ArrayList<String> groupBy) {

        List<Row> rows = new ArrayList<>();
        StringBuilder groupCondition = new StringBuilder();
        //groupCondition.append(" GROUP BY ");
        for(String str:groupBy){
            groupCondition.append(str);
            groupCondition.append(" , ");

        }
        if(groupCondition.lastIndexOf(",")>0)groupCondition.deleteCharAt(groupCondition.lastIndexOf(","));


        try{
            this.initConnection();

            StringBuilder queryToSend = new StringBuilder();
            queryToSend.append("SELECT AVG(");
            queryToSend.append(avgColumn);
            queryToSend.append(") as average_");
            queryToSend.append(avgColumn);
            if(groupCondition.length()>0){
                queryToSend.append(",");
                queryToSend.append(groupCondition);
            }
            queryToSend.append(" FROM ");
            queryToSend.append(tableName);
            if(groupCondition.length()>0){
                queryToSend.append(" GROUP BY ");
                queryToSend.append(groupCondition);
            }
            queryToSend.append(";");
            //System.out.println(queryToSend);

            PreparedStatement preparedStatement = connection.prepareStatement(queryToSend.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(tableName);

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
        //System.out.println(rows);
        return rows;

    }

}