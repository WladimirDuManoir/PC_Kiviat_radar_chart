/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.models;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rooty Model for our Table representing the differents criteria.
 */
public class MyTableModel extends DefaultTableModel {

    private static final String[] columnNames = {"Name",
        "Value",
        "Vmin",
        "Vmax"};

    private static final Object[][][] dataset = {
        new Object[][]{
            {"utilisation", 1, 0, 100},
            {"efficiency", 2, -100, 100},
            {"buty", 20, 0, 1000},
            {"quality", 50, 0, 100}},
        new Object[][]{
            {"sdgrg", 1, 0, 100},
            {"efficiergency", 4, -50, 6},
            {"buersgerty", 0, 0, 1000},
            {"gdsfdfgfd", 40, 0, 1055555},
            {"gdsfdfgfd", 40, 0, 1055555},
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
    
    public MyTableModel() {
        super(dataset[1], columnNames);
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
    public void setValueAt(Object value, int row, int col) {
        super.setValueAt(checkValue(value, row, col), row, col);
        fireTableCellUpdated(row, col);
    }

    /**
     * Check Values for the table.
     *
     * @param data is the value tested.
     * @param row is the row in the table.
     * @param column is column of the table.
     * @return if the valid value to be inserted in the row, column cell.
     */
    private int checkValue(Object data, int row, int column) {
        switch (column) {
            case 0: // Name
                break;
            case 1: // Value
                if (data instanceof Integer) {
                    int value = (int) data;
                    if (getValueAt(row, 2) != null && value < (int) getValueAt(row, 2)) {
                        System.err.println("Error : Value too low. ");
                        return (int) getValueAt(row, 2);
                    }
                    
                    if(getValueAt(row, 3) != null && value > (int) getValueAt(row, 3)) {
                        System.err.println("Error : Value too high. ");
                        return (int) getValueAt(row, 3);
                    }
                    
                    return value;
                } else {
                    System.err.println("Error : Value not an Integer. ");
                    return (int) getValueAt(row, 1);
                }
                
            case 2: // Vmin
                if (data instanceof Integer) {
                    int value = (int) data;
                    if(getValueAt(row, 1) != null && value > (int) getValueAt(row, 1)) {
                        System.err.println("Error : Min higher than value");
                        return (int) getValueAt(row, 1);
                    }
                    
                    if (getValueAt(row, 3) != null && value > (int) getValueAt(row, 3)) {
                        System.err.println("Error : Min higher than max. ");
                        return (int) getValueAt(row, 3);
                    }
                    
                    return value;
                    
                } else {
                    System.err.println("Error : Min not an Integer. ");
                    return (int) getValueAt(row, 2);
                }
                
            case 3: // Vmax 
                if (data instanceof Integer) {
                    int value = (int) data;
                    if (getValueAt(row, 1) != null && value < (int) getValueAt(row, 1)) {
                        System.err.println("Error : Max lower than value. ");
                        return (int) getValueAt(row, 1);
                    }
                    
                    if (getValueAt(row, 2) != null && value < (int) getValueAt(row, 2)) {
                        System.err.println("Error : Max lower than min. ");
                        return (int) getValueAt(row, 2);
                    }
                    
                    return value;
                } else {
                    System.err.println("Error : Max not an Integer. ");
                    return (int) getValueAt(row, 3);
                }
            default:
                System.err.println("Error : in the column number. ");
                break;
        }
        
        return (int) getValueAt(row, 1);
    }
}
