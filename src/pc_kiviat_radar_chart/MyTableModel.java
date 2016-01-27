/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rooty
 * Model for our Table representing the differents criteria. 
 */
public class MyTableModel extends AbstractTableModel {

    private final String[] columnNames = { "Name",
        "Value",
        "Vmin",
        "Vmax" };
  
    private final Object[][] data; 
    private final Object [][][] dataset = {
        // (!) all data are not coherent 
        new Object[][] {
        { "utilisation", 1, 0, 100},
        { "efficiency", 2, -100, 100},
        { "buty", 20, 0, 1000},
        { "quality", 50, 0, 100},
        {null, null, null, null}},
        
        new Object[][] {
        { "sdgrg", 1, 0, 100},
        { "efficiergency", 4, -50, 6},
        { "buersgerty", 0, 0, 1000},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        { "gdsfdfgfd", 40, 0, 10555550},
        {null, null, null, null}},
        
        new Object[][] {
        { "utilisation", 5555555, 0, 100},
        { "efficiedfgdfgfdgdfgfdgfdgdfgdfgdfgdfgdfgfdncy", 2, -10, 100},
        { "buty", 20, 0, 1000},
        { "quality", 50, 0, 4},
        {null, null, null, null}},
        
        new Object[][] {
        { "utilisafdgdftion", 1, 0, 100},
        { "efficiency", 2, -10000000, 100},
        { "buty", 20, 0, 51000},
        { "quagsdfgdfglity", 50, 0, 100},
        {null, null, null, null}},
    };
    
    public MyTableModel() {
        data = dataset [1];
        
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
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
        if (checkValue(value, row, col)){
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
    
    /**
     * Check Values for the table. 
     * @param data is the value tested.
     * @param row is the row in the table.
     * @param column is column of the table.
     * @return if the value valide to be inserted in the row, column cell.
     */
    private boolean checkValue(Object data, int row, int column) {
            switch (column) {
            case 0: // Name
                break;
            case 1: // Value
               if (data instanceof Integer) {
                   int value = (int) data; 
                   if (getValueAt(row, column + 1) != null 
                           && value >= (int)getValueAt(row, column + 1)
                           && getValueAt(row, column + 2) != null 
                           && value <= (int)getValueAt(row, column + 2)
                           ) {
                      return true;
                   } else {
                     System.err.println("Error : Value not in range. ");
                   }
                } else {
                     System.err.println("Error : Value not an Integer. ");
                   }
                break;
            case 2: // Vmin
                if (data instanceof Integer) {
                   int value = (int) data; 
                   if (getValueAt(row, column + 1) != null 
                           && value <= (int)getValueAt(row, column + 1)) {
                       if ( value > (int)getValueAt(row, column - 1)) {
                           // Value in tab is no more valide (set to minimum).
                           setValueAt(value, row, column - 1);
                       }
                       return true;
                   } else {
                     System.err.println("Error : Value not in range. ");
                   }
                } else {
                     System.err.println("Error : Value not an Integer. ");
                   }
                break;
            case 3 : // Vmax 
                if (data instanceof Integer) {
                   int value = (int) data; 
                   if (getValueAt(row, column - 1) != null 
                           && value >= (int)getValueAt(row, column - 1)) {
                       if ( value < (int)getValueAt(row, column - 2)) {
                           // Value in tab is no more valide (set to maximum). 
                           setValueAt(value, row, column - 2);
                       }
                       return true;
                   } else {
                     System.err.println("Error : Value not in range. ");
                   }
                } else {
                     System.err.println("Error : Value not an Integer. ");
                   }
                break;
            default : 
                System.err.println("Error : in the colmum number. ");
                break;
        }  
        return false;
    }
}

