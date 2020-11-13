package models.powerstation;

import java.util.*;

public class Sensor {
    private float maxReading;
    private Random rand;
    
    public Sensor(float MaxReading){
        this.maxReading = MaxReading;
        rand = new Random();
    }

    public float getSensorReading(){
        return rand.nextFloat()*maxReading;
    }

    public float getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(float MaxReading){
        this.maxReading =Math.max(MaxReading, 2);
    }
}
