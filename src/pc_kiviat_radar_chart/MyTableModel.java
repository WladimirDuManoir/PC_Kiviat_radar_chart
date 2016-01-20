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
class MyTableModel extends AbstractTableModel {

    private final String[] columnNames = { "Name",
        "Value",
        "Vmin",
        "Vmax" };
  
    private Object[][] data; 
    
    public MyTableModel() {
        data = new Object[][] {
        { "utilisation", 1, 0, 100},
        { "efficiency", 2, -100, 100},
        { "buty", 20, 0, 1000},
        { "quality", 50, 0, 100},
        {null, null, null, null}};
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
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}

