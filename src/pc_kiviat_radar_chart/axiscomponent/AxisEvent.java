/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc_kiviat_radar_chart.axiscomponent;

import java.util.EventObject;

/**
 * An event that indicates that an Axis action occurred.
 * @author Yseult Bouillon
 */
public class AxisEvent extends EventObject {
    
    
    /* **********************************************************
     *
     *  Constants
     *
     * ***********************************************************/
    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * Indicates the name of the axis changed
     */
    public static final int NAME_CHANGED = 0;
    
    /**
     * Indicates the value of the axis changed
     */
    public static final int VALUE_CHANGED = 1;
    
    /**
     * Indicates the min value of the axis changed
     */
    public static final int MIN_CHANGED = 2;
    
    /**
     * Indicates the max value of the axis changed
     */
    public static final int MAX_CHANGED = 3;
    // </editor-fold>
    
    
    /* **********************************************************
     *
     *  Attributes
     *
     * ***********************************************************/
    // <editor-fold defaultstate="collapsed" desc="Attributes"> 
    /**
     * The source axis
     */
    private final AxisComponent axis;
    
    /**
     * Indicates the type of event
     */
    private final int type;
    // </editor-fold>
    
    
    /**
     * Constructs an AxisEvent object.
     * @param source
     * @param axis
     * @param type
     */
    public AxisEvent(Object source, AxisComponent axis, int type) {
        super(source);
        
        this.axis = axis;
        this.type = type;
    }
    
    /**
     * Returns the axis that triggered the event
     * @return AxisComponent
     */
    public AxisComponent getAxis() {
        return axis;
    }
    
    /**
     * Returns the type of event
     * @return int
     */
    public int getType() {
        return type;
    }
}
