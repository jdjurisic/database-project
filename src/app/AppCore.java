package app;

import actions.ActionManager;
import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import lombok.Data;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.implementation.InformationResource;
import utils.Constants;

import java.util.HashMap;
import java.util.List;

@Data
public class AppCore extends PublisherImplementation {

    private Database database;
    private Settings settings;
    private TableModel tableModel;


    public AppCore() {

        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        tableModel = new TableModel();

    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }


    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }



    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        //Zasto ova linija moze da ostane zakomentarisana?
        //this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }

    public TableModel readTableModelFromTable(String fromTable){
        TableModel tableModel = new TableModel();
        tableModel.setRows(this.database.readDataFromTable(fromTable));
        return tableModel;
    }

    public void executeQuery(String query, List<String> lst, TableModel tableModel, String tablename){
        getDatabase().executeQuery(query, lst);
        tableModel.setRows(this.database.readDataFromTable(tablename));
    }

    public void addToTable(HashMap<String, String> hashMap, TableModel tableModel, String tablename){
        getDatabase().addInTable(hashMap, tablename);
        tableModel.setRows(this.database.readDataFromTable(tablename));
    }

    public void deleteToTable(HashMap<String, Object> hashMap, TableModel tableModel, String tablename){
        getDatabase().deleteInTable(hashMap, tablename);
        tableModel.setRows(this.database.readDataFromTable(tablename));
    }

    public void updateToTable(HashMap<String, Object> oldHashMap, HashMap<String, Object> newHashMap,  TableModel tableModel, String tablename){
        getDatabase().updateInTable(oldHashMap, newHashMap, tablename);
        tableModel.setRows(this.database.readDataFromTable(tablename));
    }

   /* public TableModel filterAndSort(HashMap<String, String> hashMap, String columns, String tableName){


        TableModel tableModel = new TableModel();
        tableModel.setRows(getDatabase().filterAndSortInTable(hashMap, columns, tableName));
        return tableModel;

    }*/

    public void filterAndSort(HashMap<String, String> hashMap, String columns, String tableName, TableModel tableModel){
        //getDatabase().filterAndSortInTable(hashMap, columns, tableName);
        tableModel.setRows(getDatabase().filterAndSortInTable(hashMap, columns, tableName));

    }

    public  TableModel clickOnRow(String query,  String tableName){
        TableModel tableModel = new TableModel();
        tableModel.setRows(getDatabase().clickOnTable(query, tableName));
        return tableModel;
    }

    public void removeFilter(String fromTable, TableModel tableModel){
        tableModel.setRows(this.database.readDataFromTable(fromTable));

    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }


}
