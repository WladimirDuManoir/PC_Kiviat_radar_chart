/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.axiscomponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Cette classe est un composant slider orienté sur un angle précisé
 * On peut contrôler sa valeur directement
 * @author bouillys
 */
public class AxisComponent extends JComponent {
    
    /**
     * Default point size for the slider
     */
    private static final int DEFAULT_POINT_SIZE = 7;
    
    /**
     * Default space needed to print label
     */
    private static final int DEFAULT_LABEL_SIZE = 15;
    
    /**
     * Orientation of the axis in radians
     */
    private final double angle;
        
    /**
     * The point on the axis
     */
    private Ellipse2D.Double point;
    
    /**
     * The line of the axis
     */
    private Line2D.Double line;
    
    /**
     * The model containing the values of the axis
     */
    private final AbstractTableModel model;
    
    /**
     * The index of the row of the model for this axis
     */
    private final int rowIndex;

    
    /**
     * Allows to create an axis component with given values
     * @param angle
     * @param model
     * @param rowIndex
     */
    public AxisComponent(double angle, AbstractTableModel model, int rowIndex) {
        this.angle = angle;
        this.model = model;
        this.rowIndex = rowIndex;
        
        this.addMouseListener(new AxisComponentMouseListener());
    }

    /**
     * Inner class to implement MouseListener to detect and move cursor
     */
    private static class AxisComponentMouseListener implements MouseListener {

        public AxisComponentMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Getting values from the model
        String name = (String) model.getValueAt(rowIndex, 0);
        int value = (int) model.getValueAt(rowIndex, 1);
        int min = (int) model.getValueAt(rowIndex, 2);
        int max = (int) model.getValueAt(rowIndex, 3); 
                
        // Painting the axis
        double centerX = getWidth()/2;
        double centerY = getHeight()/2;
        double dist = (getWidth()/2) - DEFAULT_LABEL_SIZE ;
        line = new Line2D.Double(centerX, centerY, 
                centerX + dist*Math.cos(angle),
                centerY + dist*Math.sin(angle));
        g2.draw(line);
        
        // Painting the point representing the value
        double distpoint =  dist*(value-min)/(max-min);
        point = new Ellipse2D.Double(
                centerX + distpoint*Math.cos(angle) - DEFAULT_POINT_SIZE/2,
                centerY + distpoint*Math.sin(angle) - DEFAULT_POINT_SIZE/2,
                DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE);
        g2.setColor(Color.red); // TODO : set color properly
        g2.fill(point);
        
        // Painting the name of the axis
        double totaldist = getWidth()/2;
        g2.setColor(Color.BLACK);
        g2.drawString(name, 
                (float) (centerX - (g.getFontMetrics().stringWidth(name)/2) + totaldist*Math.cos(angle)), 
                (float) (centerY + totaldist*Math.sin(angle)));
    }
    
    /**
     * Returns the coordinates of the value on this axis
     * @return a point
     */
    public Point getValueCoordinates() {
        return new Point((int) point.getX(), (int) point.getY());
    }
    
    
    
}
