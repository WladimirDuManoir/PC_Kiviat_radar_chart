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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

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
    
    // TODO : These values are stoked here temporarly 
    private final int min;
    private final int max; 
    private final int value;
    private final String name;

    
    /**
     * Allows to create an axis component with given values
     * @param angle
     * @param min
     * @param max
     * @param value 
     * @param name 
     */
    public AxisComponent(double angle, int min, int max, int value, String name) {
        
        // TODO : check that angle is in radians
        this.angle = angle;
        
        // TODO : find another way to get those values
        this.min = min;
        this.max = max;
        this.value = value;
        this.name = name;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Painting the axis
        double centerX = getWidth()/2;
        double centerY = getHeight()/2;
        double totaldist = getWidth()/2;
        line = new Line2D.Double(centerX, centerY, 
                centerX + totaldist*Math.cos(angle),
                centerY + totaldist*Math.sin(angle));
        g2.draw(line);
        
        // Painting the point representing the value
        // TODO : gérer plus proprement la position : l'arrondi en int décale
        // le point par rapport à l'axe
        double dist =  totaldist*(value-min)/(max-min);
        point = new Ellipse2D.Double(
                centerX + dist*Math.cos(angle),
                centerY + dist*Math.sin(angle),
                DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE);
        g2.setColor(Color.red);
        g2.fill(point);
        
        // Painting the name of the axis
        // TODO : gérer plus proprement la position : le texte sort du cadre
        g2.drawString(name, 
                (float) (centerX + totaldist*Math.cos(angle)), 
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
