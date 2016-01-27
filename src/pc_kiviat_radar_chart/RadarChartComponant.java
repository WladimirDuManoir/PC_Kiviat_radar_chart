/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JComponent;
import pc_kiviat_radar_chart.MyTableModel;
import pc_kiviat_radar_chart.axiscomponent.AxisComponent;

/**
 * Radar Chart made of Axis Components for graphical 
 * radar chart representation of a table.
 * @author rooty
 */
public class RadarChartComponant extends JComponent {
    
    private ArrayList<AxisComponent> axes = new ArrayList<AxisComponent>();
    
    public RadarChartComponant (MyTableModel data) {
        setPreferredSize(new Dimension(20000, 20000));
        Double angle = Math.toRadians(360)/data.getRowCount();
        int real = 0;
        for (int i = 0; i < data.getRowCount() ; i++) {
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
         for (int i = 0; i < axes.size(); i++) {
             axes.get(i).setBounds(this.getX(), this.getY(), 
                        this.getWidth(), this.getHeight());
        }
    }
    
}
