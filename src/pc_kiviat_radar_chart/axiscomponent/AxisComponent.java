/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.axiscomponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 * Cette classe est un composant slider orienté sur un angle précisé
 * On peut contrôler sa valeur directement
 * @author bouillys
 */
public class AxisComponent extends JComponent {
    
    /**
     * Default point size for the slider
     */
    private static final int DEFAULT_POINT_SIZE = 10;
    
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
     * Boolean indicating if the mouse is hovering on the cursor
     */
    private boolean hover = false;
    
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
        this.addMouseMotionListener(new AxisComponentMouseMotionListener());
    }

    /**
     * Inner class to implement MouseListener to detect and move cursor
     */
    private class AxisComponentMouseListener implements MouseListener {

        public AxisComponentMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {
            hover = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hover = false;
            repaint();
        }
    }
    
    /**
     *  Inner class to implement MouseMotionListener to detect and move cursor
     */
    private class AxisComponentMouseMotionListener implements MouseMotionListener {

        public AxisComponentMouseMotionListener() { }

        @Override
        public void mouseDragged(MouseEvent e) {
            
            Point2D.Double pointOnAxis = approximatePoint(e.getX(), e.getY());
            int value = pointToValue(pointOnAxis);
            model.setValueAt(value, rowIndex, 1);
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) { }
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.draw(line);
        
        // Painting the point representing the value
        double distpoint =  dist*(value-min)/(max-min);
        point = new Ellipse2D.Double(
                    getValueCoordinates().x - DEFAULT_POINT_SIZE/2, 
                    getValueCoordinates().y - DEFAULT_POINT_SIZE/2,
                    DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE); 
        
        if(hover) {
            g2.setColor(Color.RED); // TODO : set color properly with a constant
        } else {
            g2.setColor(Color.BLACK);
        }
        
        g2.fill(point);
        
        // Painting the name of the axis
        double totaldist = getWidth()/2;
        g2.setColor(Color.BLACK);
        g2.drawString(name, 
                (float) (centerX - (g.getFontMetrics().stringWidth(name)/2) + totaldist*Math.cos(angle)), 
                (float) (centerY + totaldist*Math.sin(angle)));
        
        // Painting the value if hovered
        if(hover) {
            g2.setColor(Color.BLACK);
            g2.drawString("Value : " + value, 
                    getValueCoordinates().x, 
                    getValueCoordinates().y);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return point != null && line != null
                && (point.contains(x, y) || line.contains(x, y));
    }
    
    /**
     * Checks wether the point is on the cursor or not
     * @param x
     * @param y
     * @return boolean
     */
    public boolean cursorContains(int x, int y) {
        return point != null && point.contains(x, y);
    }
    
    /**
     * Returns the coordinates of the value on this axis
     * @return a point
     */
    public Point getValueCoordinates() {
        int value = (int) model.getValueAt(rowIndex, 1);
        return new Point((int) valueToPoint(value).x,
                (int) valueToPoint(value).y);
    }  
    
    /**
     * Gets the value of the axis and returns the corresponding point
     * @param value
     * @return a point containing the coordinates
     */
    private Point2D.Double valueToPoint(int value) {
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int min = (int) model.getValueAt(rowIndex, 2);
        int max = (int) model.getValueAt(rowIndex, 3); 
        int distpoint = ((getWidth()/2) - DEFAULT_LABEL_SIZE)*(value-min)/(max-min);
        
        Point2D.Double retour = new Point2D.Double(
            centerX + distpoint*Math.cos(angle),
            centerY + distpoint*Math.sin(angle));  
        
        return retour;
    }
    
    /**
     * Gets a point and returns the corresponding value of the axis
     * @param coordinates
     * @return 
     */
    private int pointToValue(Point2D.Double coordinates) {
        int centerX = getWidth()/2;
        int min = (int) model.getValueAt(rowIndex, 2);
        int max = (int) model.getValueAt(rowIndex, 3);
        double dist = (getWidth()/2) - DEFAULT_LABEL_SIZE;
        
        // value = (max-min)*(x-x1)/(x2-x1)
        int value;
        if(Math.cos(angle) == 0) {
            value = (int) ((max-min)*(coordinates.x - centerX));
        } else {
            value = (int) ((max-min)*(coordinates.x - centerX)/(dist*Math.cos(angle)));
        }
        
        return value;
    }
    
    /**
     * Given the coordinates x and y, returns the most close coordinates on the axis
     * @param x
     * @param y
     * @return 
     */
    private Point2D.Double approximatePoint(int x, int y) {
        double xDelta = line.getX2() - line.getX1();
        double yDelta = line.getY2() - line.getY1();

        if ((xDelta == 0) && (yDelta == 0)) {
          throw new IllegalArgumentException("Segment start equals segment end");
        }
        

        double u = ((x - line.getX1()) * xDelta + (y - line.getY1()) * yDelta)
                / (xDelta * xDelta + yDelta * yDelta);
        Point2D.Double closestPoint;

        if (u > 1) {
          closestPoint = new Point2D.Double(line.getX2(), line.getY2());
        } else {
          closestPoint = new Point2D.Double(
                  Math.round(line.getX1() + u * xDelta),
                  Math.round(line.getY1() + u * yDelta));
        }

        return closestPoint;
    }
}
