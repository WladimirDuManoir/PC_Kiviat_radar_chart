/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart;

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
public final class RadarChartComponent extends JComponent {
    
    private final ArrayList<AxisComponent> axes = new ArrayList<>();
    
    public RadarChartComponent (MyTableModel data) {
        setPreferredSize(new Dimension(20000, 20000));
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
                // Create axe 
                axes.add(new AxisComponent(real*angle, data, real));
                
                // TO DELETE FIXME setBounds
                axes.get(real).setBounds(0, 0, 500, 500);
                //axes.get(real).setBounds(this.getX(), this.getY(), 
                        // this.getWidth(), this.getHeight());
                        
                this.add(axes.get(real));
                real++;
            }
        }
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize); 
        setBounds(0, 0, preferredSize.width, preferredSize.height);
    }
    
    @Override
    public void setBounds (int x, int y, int width, int height) {
         super.setBounds(x, y, width, height);
         axes.stream().forEach((axe) -> {
             axe.setBounds(0, 0, width, height);
        });
    }

    @Override
    public void paint(Graphics g) {
    
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Polygon lines = new Polygon();
        
        for(AxisComponent axis : axes) {
            lines.addPoint(axis.getValueCoordinates().x, axis.getValueCoordinates().y);
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.BLACK);
        g2.draw(lines);
        
        Color light_orange = new Color(230, 162, 140);
        g2.setColor(light_orange);
        g2.fill(lines);
        
    }
}
