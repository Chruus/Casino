//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Dice{
    private int faces;
    private int faceValue;
    
    public Dice(int _faces){
        faces = _faces;
        faceValue = (int)(Math.random()*faces) + 1;
    }
    
    public void roll(){
        faceValue = (int)(Math.random()*faces) + 1;
    }
    
    public int getFaceValue(){
        return faceValue;
    }
    
    public String getFaceValueToString(){
        return "The die rolled a " + faceValue;
    }
}

