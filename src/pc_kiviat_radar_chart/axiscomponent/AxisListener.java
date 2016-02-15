/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.axiscomponent;

import java.util.EventListener;

/**
 * Defines the interface for an object that listens to changes in an Axis
 * @author Yseult Bouillon
 */
public interface AxisListener extends EventListener {
    
    /**
     * This notification tells listeners that a value in the Axis changed
     * @param e 
     */
    public void axisChanged(AxisEvent e);
}
