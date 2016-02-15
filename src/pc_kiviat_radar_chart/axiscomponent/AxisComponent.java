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
import javax.swing.event.EventListenerList;

/**
 * Cette classe est un composant slider orienté sur un angle précisé
 * On peut contrôler sa valeur directement
 * @author bouillys
 */
public class AxisComponent extends JComponent {
    
    
    /* **********************************************************
     *
     *  Constants
     *
     * ***********************************************************/
    // <editor-fold defaultstate="collapsed" desc="Constants">   
    /**
     * Default point size for the slider
     */
    private static final int DEFAULT_POINT_SIZE = 10;
    
    /**
     * Default space needed to print label
     */
    private static final int DEFAULT_LABEL_SIZE = 25;
    
    /**
     * Default space needed in the center to see points better
     */
    private static final int DEFAULT_CENTER_SIZE = 20;
    // </editor-fold>
    
    
    
    /* **********************************************************
     *
     *  Attributes
     *
     * ***********************************************************/
    // <editor-fold defaultstate="collapsed" desc="Attributes">  
    /**
     * Orientation of the axis in radians
     */
    private double angle;
        
    /**
     * The point on the axis
     */
    private Ellipse2D.Double point;
    
    /**
     * The line of the axis
     */
    private Line2D.Double line;

    /**
     * Boolean indicating if the mouse is hovering on the cursor
     */
    private boolean hover = false;
    // </editor-fold>
    
    
    /**
     * Allows to create an axis component with given values
     * @param angle
     * @param name
     * @param value
     * @param min
     * @param max
     */
    public AxisComponent(double angle, String name, int value, int min, int max) {
        this.angle = angle;
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        
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
            
            // Setting the new value
            int value = pointToValue(approximatePoint(e.getX(), e.getY()));
            
            setValue(value);
            repaint();
            
            // Telling the listeners that value changed
            AxisEvent event = new AxisEvent(this, AxisComponent.this, AxisEvent.VALUE_CHANGED);
            fireAxisChanged(event);
        }

        @Override
        public void mouseMoved(MouseEvent e) { }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
                        
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
    public void setBounds(int x, int y, int width, int height) {
        // FIXME : décallage entre le point et la ligne
        super.setBounds(0, 0, width, height); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    // TODO : this is not working to detect if user is clicking on the axis
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
        return new Point((int) valueToPoint(value).x,
                (int) valueToPoint(value).y);
    }  
    
    /**
     * Sets the angle to specified value
     * @param angle 
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    /* **********************************************************
     *
     *  Specific methods to build the axis
     *
     * ***********************************************************/ 
    // <editor-fold defaultstate="collapsed" desc="Building methods">
    /**
     * Gets the value of the axis and returns the corresponding point
     * @param value
     * @return a point containing the coordinates
     */
    private Point2D.Double valueToPoint(int value) {
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
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
        double dist = (getWidth()/2) - DEFAULT_LABEL_SIZE;
        
        // value = (max-min)*(x-x1)/(x2-x1)
        int calculatedValue;
        if(Math.cos(angle) == 0) {
            calculatedValue = (int) ((max-min)*(coordinates.x - centerX));
        } else {
            calculatedValue = (int) ((max-min)*(coordinates.x - centerX)/(dist*Math.cos(angle)));
        }
        
        return calculatedValue;
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
    
    // </editor-fold>
    
    
    /* **********************************************************
     *
     *  Axis events listening and triggering
     *
     * ***********************************************************/ 
    // <editor-fold defaultstate="collapsed" desc="Axis events">    
    /**
     * List of listeners that listens to the changes on this axis
     */
    private final EventListenerList eventListenerList = new EventListenerList();

    /**
     * Adds a listener on this axis' changes
     * @param listener 
     */
    public void addAxisListener(AxisListener listener) {
        eventListenerList.add(AxisListener.class, listener);
    }

    /**
     * Removes a listener on this axis
     * @param listener 
     */
    public void removeAxisListener(AxisListener listener) {
        eventListenerList.remove(AxisListener.class, listener);
    }
    
    /**
     * Tells all the listeners that this axis has changed
     * @param event 
     */
    private void fireAxisChanged(AxisEvent event) {
        for (AxisListener listener : eventListenerList.getListeners(AxisListener.class)) {
            listener.axisChanged(event);
        }
    }
    // </editor-fold>
    
    
    /* **********************************************************
     *
     *  Model of the Axis
     *
     * ***********************************************************/ 
    // <editor-fold defaultstate="collapsed" desc="Model of the axis">     
    /**
     * Name of this axis
     */
    private String name;
    
    /**
     * Value of this axis
     */
    private int value;
    
    /**
     * Minimum value of this axis
     */
    private int min;
    
    /**
     * Maximum value of this axis
     */
    private int max; 

    /**
     * Gets the name of the axis
     * @return a string name of the axis
     */
    public String getAxisName() {
        return name;
    }

    /**
     * Sets the name of the axis
     * @param name 
     */
    public void setAxisName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the axis
     * @return the int value of the axis
     */
    public int getValue() {
        return value;
    }

    /**
     * Checks and sets the value of the axis
     * @param value 
     */
    public void setValue(int value) {
        this.value = this.checkValue(value);
    }

    /**
     * Checks the value and returns a valid one
     * @param value
     * @return a valid value
     */
    private int checkValue(int value) {
        if(value < this.min) {
            return this.min;
        }
        
        if(value > this.max) {
            return this.max;
        }
        
        return value;
    }
    
    /**
     * Gets the minimum value of the axis
     * @return a int minimum value of the axis
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the axis
     * @param min 
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the maximum value of the axis
     * @return a int maximum value of the axis
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the axis
     * @param max 
     */
    public void setMax(int max) {
        this.max = max;
    }
    // </editor-fold>
}
