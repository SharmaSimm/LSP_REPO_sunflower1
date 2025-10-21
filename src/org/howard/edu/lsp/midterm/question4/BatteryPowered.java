package org.howard.edu.lsp.midterm.question4;

/**
 * Battery capability for devices with battery percentage
 */
public interface BatteryPowered {
    int getBatteryPercent();
    void setBatteryPercent(int percent);

}
