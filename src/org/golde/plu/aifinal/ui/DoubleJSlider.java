package org.golde.plu.aifinal.ui;

import javax.swing.JSlider;

public class DoubleJSlider extends JSlider {

    /**
     * Constructor - initializes with 0.0,100.0,50.0
     */
    public DoubleJSlider() {
        super();
        setDoubleMinimum(0.0);
        setDoubleMaximum(100.0);
        setDoubleValue(50.0);
    }

    /**
     * Constructor
     */
    public DoubleJSlider(double min, double max, double val) {
        super();
        setDoubleMinimum(min);
        setDoubleMaximum(max);
        setDoubleValue(val);
    }

    /**
     * returns Maximum in double precision
     */
    public double getDoubleMaximum() {
        return (getMaximum() / 100.0);
    }

    /**
     * returns Minimum in double precision
     */
    public double getDoubleMinimum() {
        return (getMinimum() / 100.0);
    }

    /**
     * returns Value in double precision
     */
    public double getDoubleValue() {
        return (getValue() / 100.0);
    }

    /**
     * sets Maximum in double precision
     */
    public void setDoubleMaximum(double max) {
        setMaximum((int) (max * 100));
    }

    /**
     * sets Minimum in double precision
     */
    public void setDoubleMinimum(double min) {
        setMinimum((int) (min * 100));
    }

    /**
     * sets Value in double precision
     */
    public void setDoubleValue(double val) {
        setValue((int) (val * 100));
        setToolTipText(Double.toString(val));
    }
}
