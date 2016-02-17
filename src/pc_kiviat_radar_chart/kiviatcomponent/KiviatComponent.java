/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.kiviatcomponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import pc_kiviat_radar_chart.axiscomponent.AxisComponent;
import pc_kiviat_radar_chart.axiscomponent.AxisEvent;
import pc_kiviat_radar_chart.axiscomponent.AxisListener;

/**
 * Radar Chart made of Axis Components for graphical radar chart representation
 * of a table.
 *
 * @author rooty
 */
public final class KiviatComponent extends JComponent {

    /**
     * List of the axes of the Kiviat
     */
    private final ArrayList<AxisComponent> axes = new ArrayList<>();
    
    /**
     * Model of the Kiviat
     */
    private DefaultTableModel model;

    /**
     * The listener on the model events
     */
    private final TableModelListener modelListener = new KiviatTableModelListener();
    
    /**
     * The listener on each axis
     */
    private final AxisListener axisListener = new KiviatAxisListener();
    
    /**
     * Sets the model and updates axis
     * @param model 
     */
    public void setModel(DefaultTableModel model) {
        this.model = model;
        this.model.addTableModelListener(modelListener);

        for (int i = 0; i < this.model.getRowCount(); i++) {
            AxisComponent axis = new AxisComponent(i * Math.toRadians(360) / this.model.getRowCount(),
                (String) this.model.getValueAt(i, 0),
                (int) this.model.getValueAt(i, 1),
                (int) this.model.getValueAt(i, 2),
                (int) this.model.getValueAt(i, 3));
            this.addAxis(axis);
        }
        
        repaint();
    }
    
    /**
     * Adding an axis to the Kiviat
     * @param axis 
     */
    private void addAxis(AxisComponent axis) {
        axis.addAxisListener(axisListener);
        this.add(axis);
        axes.add(axis);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        axes.stream().forEach((axe) -> {
            axe.setBounds(0, 0, width, height);
        });
    }

    @Override
    public void paint(Graphics g) {
        this.setBounds(this.getBounds());
        
        // Drawing the lines between the points
        Polygon lines = new Polygon();

        axes.stream().forEach((axis) -> {
            lines.addPoint(axis.getValueCoordinates().x, axis.getValueCoordinates().y);
        });

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color light_orange = new Color(230, 162, 140);
        g2.setColor(light_orange);
        g2.fill(lines);

        g2.setColor(Color.BLACK);
        g2.draw(lines);
        super.paint(g);
        
    }

    /**
     * Custom TableModelListener to update axes
     */
    private class KiviatTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            switch (e.getType()) {
                case TableModelEvent.DELETE:
                    tableRowDeleted(e);
                    break;
                case TableModelEvent.INSERT:
                    tableRowInserted(e);
                    break;
                case TableModelEvent.UPDATE:
                    tableCellUpdated(e);
                    break;
            }
        }

        /**
         * Removes deleted rows from the axes
         * @param e 
         */
        private void tableRowDeleted(TableModelEvent e) {
            // Adding the axis
            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                remove(axes.get(i));
                axes.remove(i);
            }
            
            // Updating angles
            for(AxisComponent axis : axes) {
                axis.setAngle(axes.indexOf(axis) * Math.toRadians(360) / axes.size());
            }
            
            repaint();
        }

        /**
         * Inserts new rows in the axes
         * @param e 
         */
        private void tableRowInserted(TableModelEvent e) {
            // Adding the axis
            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                AxisComponent newAxis = new AxisComponent(0,
                        (String) model.getValueAt(i, 0),
                        (int) model.getValueAt(i, 1),
                        (int) model.getValueAt(i, 2),
                        (int) model.getValueAt(i, 3));
                
                addAxis(newAxis);
            }
            
            // Updating angles
            for(AxisComponent axis : axes) {
                axis.setAngle(axes.indexOf(axis) * Math.toRadians(360) / axes.size());
            }
            
            repaint();
        }

        /**
         * Updates corresponding values of the model in the axes
         * @param e 
         */
        private void tableCellUpdated(TableModelEvent e) {
            if (e.getFirstRow() != TableModelEvent.HEADER_ROW) {
                for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                    if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
                        axes.get(i).setAxisName((String) model.getValueAt(i, 0));
                        axes.get(i).setValue((int) model.getValueAt(i, 1));
                        axes.get(i).setMin((int) model.getValueAt(i, 2));
                        axes.get(i).setMax((int) model.getValueAt(i, 3));
                    } else {
                        switch(e.getColumn()) {
                            case 0 :
                                axes.get(i).setAxisName((String) model.getValueAt(i, 0));
                                break;
                            case 1 : 
                                axes.get(i).setValue((int) model.getValueAt(i, 1));
                                break;
                            case 2 :
                                axes.get(i).setMin((int) model.getValueAt(i, 2));
                                break;
                            case 3 :
                                axes.get(i).setMax((int) model.getValueAt(i, 3)); 
                                break;
                        }
                    }
                    
                    axes.get(i).repaint();
                }
            }
        }
    }

    /**
     * Custom AxisListener to update model according to axes
     */
    private class KiviatAxisListener implements AxisListener {

        @Override
        public void axisChanged(AxisEvent e) {
            int row = axes.indexOf(e.getAxis());
            
            switch(e.getType()) {
                case AxisEvent.NAME_CHANGED :
                    model.setValueAt(axes.get(row).getAxisName(), row, 0);
                    break;
                case AxisEvent.VALUE_CHANGED :
                    model.setValueAt(axes.get(row).getValue(), row, 1); 
                    break;
                case AxisEvent.MIN_CHANGED :
                    model.setValueAt(axes.get(row).getMin(), row, 2);
                    break;
                case AxisEvent.MAX_CHANGED :
                    model.setValueAt(axes.get(row).getMax(), row, 3);
                    break;
            }
        }
        
    }
}
