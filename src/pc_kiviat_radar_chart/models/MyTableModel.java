/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.models;

import javax.swing.table.DefaultTableModel;

/**
 * Custom TableModel for our JTable representing the differents criteria.
 * @author Wladimir Dumanoir
 */
public class MyTableModel extends DefaultTableModel {

    /**
     * Default name
     */
    private static final String DEFAULT_NAME = "Default";
    
    /**
     * Default value
     */
    private static final int DEFAULT_VALUE = 5;
    
    /**
     * Default min
     */
    private static final int DEFAULT_MIN = 0;
    
    /**
     * Default max
     */
    private static final int DEFAULT_MAX = 10;
    
    /**
     * Static columnnames
     */
    private static final String[] columnNames = {"Name",
        "Value",
        "Vmin",
        "Vmax"};

    /**
     * Sets of data available
     */
    private static final Object[][][] dataset = {
        new Object[][]{
            {"utilisation", 1, 0, 100},
            {"efficiency", 2, -100, 100},
            {"buty", 20, 0, 1000},
            {"quality", 50, 0, 100},
            {null, null, null, null}},
        new Object[][]{
            {"sdgrg", 1, 0, 100},
            {"efficiergency", 4, -50, 6},
            {"buersgerty", 0, 0, 1000},
            {"gdsfdfgfd", 4, 0, 10},
            {"gdsfdfgfd", 40, 0, 50},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555}},
        new Object[][]{
            {"utilisation", 5555555, 0, 100},
            {"efficiedfgdfgfdgdfgfdgfdgdfgdfgdfgdfgdfgfdncy", 2, -10, 100},
            {"buty", 20, 0, 1000},
            {"quality", 50, 0, 4}},
        new Object[][]{
            {"utilisafdgdftion", 1, 0, 100},
            {"efficiency", 2, -10000000, 100},
            {"buty", 20, 0, 51000},
            {"quagsdfgdfglity", 50, 0, 100}}};
    
    /**
     * Checks values and initialises a Table Model with the specified model
     * @param number
     */
    public MyTableModel(int number) {      
        super(dataset[number], columnNames);
        
        for(int i = 0 ; i < this.getRowCount() ; i++) {
            for(int j= 0 ; j < this.getColumnCount() ; j ++) {
                this.setValueAt(this.getValueAt(i, j), i, j);
            }
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public final void setValueAt(Object value, int row, int col) {
        super.setValueAt(checkValue(value, row, col), row, col);
        fireTableCellUpdated(row, col);
    }

    /**
     * Check Values for the specified row and column and returns a valid value
     * @param data is the value tested.
     * @param row is the row in the table.
     * @param column is column of the table.
     * @return if the valid value to be inserted in the row, column cell.
     */
    private Object checkValue(Object data, int row, int column) {
        switch (column) {
            case 0: // Name
                if(data == null) {
                    System.err.println("Error : Name is null.");
                    data = DEFAULT_NAME;
                }
                
                if(!(data instanceof String)) {
                    System.err.println("Error : Name not a string.");
                    return getValueAt(row, 0);
                }
                
                String nom = (String) data;
                if(nom.length() > 10) {
                    System.err.println("Error : Name too long.");
                    return nom.substring(0, 10);
                }
                
                if(nom.length() <= 0) {
                    System.err.println("Error : Name too short.");
                    return getValueAt(row, 0);
                }
                
                return nom;
            case 1: // Value
                if(data == null) {
                    System.err.println("Error : Value is null.");
                    data = DEFAULT_VALUE;
                }
                
                if (data instanceof Integer) {
                    int value = (int) data;
                    if (getValueAt(row, 2) != null && value < (int) getValueAt(row, 2)) {
                        System.err.println("Error : Value too low. ");
                        return getValueAt(row, 2);
                    }
                    
                    if(getValueAt(row, 3) != null && value > (int) getValueAt(row, 3)) {
                        System.err.println("Error : Value too high. ");
                        return getValueAt(row, 3);
                    }
                    
                    return data;
                } else {
                    System.err.println("Error : Value not an Integer. ");
                    return getValueAt(row, 1);
                }
                
            case 2: // Vmin
                if(data == null) {
                    System.err.println("Error : Min is null.");
                    data = DEFAULT_MIN;
                }
                
                if (data instanceof Integer) {
                    int value = (int) data;
                    if(getValueAt(row, 1) != null && value > (int) getValueAt(row, 1)) {
                        System.err.println("Error : Min higher than value");
                        return getValueAt(row, 1);
                    }
                    
                    if (getValueAt(row, 3) != null && value > (int) getValueAt(row, 3)) {
                        System.err.println("Error : Min higher than max. ");
                        return getValueAt(row, 3);
                    }
                    
                    return data;
                    
                } else {
                    System.err.println("Error : Min not an Integer. ");
                    return getValueAt(row, 2);
                }
                
            case 3: // Vmax 
                if(data == null) {
                    System.err.println("Error : Max is null.");
                    data = DEFAULT_MAX;
                }
                
                if (data instanceof Integer) {
                    int value = (int) data;
                    if (getValueAt(row, 1) != null && value < (int) getValueAt(row, 1)) {
                        System.err.println("Error : Max lower than value. ");
                        return getValueAt(row, 1);
                    }
                    
                    if (getValueAt(row, 2) != null && value < (int) getValueAt(row, 2)) {
                        System.err.println("Error : Max lower than min. ");
                        return getValueAt(row, 2);
                    }
                    
                    return value;
                } else {
                    System.err.println("Error : Max not an Integer. ");
                    return getValueAt(row, 3);
                }
            default:
                System.err.println("Error : in the column number. ");
                break;
        }
        
        return getValueAt(row, column);
    }
}
