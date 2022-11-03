//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Dice{
    private int faces;
    private int faceValue;
    
    //Creates die w/ specified number of faces
    public Dice(int _faces){
        faces = _faces;
        faceValue = (int)(Math.random()*faces) + 1;
    }
    
    //Rolls dice, random btwn 1 and faces
    public void roll(){
        faceValue = (int)(Math.random()*faces) + 1;
    }
    
    //Getters
    public int getFaceValue(){
        return faceValue;
    }
    public String getFaceValueToString(){
        return "The die rolled a " + faceValue;
    }
}

