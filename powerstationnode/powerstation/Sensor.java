package powerstationnode.powerstation;
import java.util.*;
public class Sensor {
    private float sensorReading;
    private float maxReading;
    private Random rand;

    private void generateRandomReading(){
        sensorReading = rand.nextFloat()*maxReading;
    }

    public Sensor(float MaxReading){
        this.maxReading = MaxReading;
        rand = new Random();
    }

    public float getSensorReading(){
        generateRandomReading();
        return sensorReading;
    }

    public float getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(float MaxReading){
        this.maxReading =MaxReading;
    }
}
