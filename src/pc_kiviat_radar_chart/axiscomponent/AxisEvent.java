/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.axiscomponent;

import java.util.EventObject;

/**
 *
 * @author Yseult Bouillon
 */
public class AxisEvent extends EventObject {
    
    public static final int NAME_CHANGED = 0;
    public static final int VALUE_CHANGED = 1;
    public static final int MIN_CHANGED = 2;
    public static final int MAX_CHANGED = 3;
    
    private final AxisComponent axis;
    private final int type;

    /**
     *
     * @param source
     * @param axis
     * @param type
     */
    public AxisEvent(Object source, AxisComponent axis, int type) {
        super(source);
        
        this.axis = axis;
        this.type = type;
    }
    
    public AxisComponent getAxis() {
        return axis;
    }
    
    public int getType() {
        return type;
    }
}
