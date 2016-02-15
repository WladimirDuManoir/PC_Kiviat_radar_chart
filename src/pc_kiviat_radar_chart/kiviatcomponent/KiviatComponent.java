/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.kiviatcomponent;

import pc_kiviat_radar_chart.models.MyTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import pc_kiviat_radar_chart.axiscomponent.AxisComponent;

/**
 * Radar Chart made of Axis Components for graphical 
 * radar chart representation of a table.
 * @author rooty
 */
public final class KiviatComponent extends JComponent {
    
    private final ArrayList<AxisComponent> axes = new ArrayList<>();
    
    public KiviatComponent (MyTableModel data) {
        int dataRealSize = 0;
         
        for (int i = 0; i < data.getRowCount() ; i++) {
            if (data.getValueAt(i,0) != null
                    && data.getValueAt(i,1) != null
                    && data.getValueAt(i,2) != null
                    && data.getValueAt(i,3) != null) {
                dataRealSize++;
            }
        }
        Double angle = dataRealSize == 0 ? 0 :  Math.toRadians(360)/dataRealSize;
        int real = 0;
        for (int i = 0; i < dataRealSize ; i++) {
            if (data.getValueAt(i,0) != null
                    && data.getValueAt(i,1) != null
                    && data.getValueAt(i,2) != null
                    && data.getValueAt(i,3) != null) {
                axes.add(new AxisComponent(real*angle, data, real));
                        
                this.add(axes.get(real));
                real++;
            }
        }
        
        this.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
    
    @Override
    public void setBounds (int x, int y, int width, int height) {
         super.setBounds(x, y, width, height);
         axes.stream().forEach((axe) -> {
             axe.setBounds(x, y, width, height);
        });
    }

    @Override
    public void paint(Graphics g) {
    
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
        
        
        // Drawing the axes etc
        super.paint(g);
        
    }
}
