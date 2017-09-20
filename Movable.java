package tennis;


import javafx.scene.shape.Rectangle;

/*
 This class is the protptipe of movable objects on the screen.
 */

/**
 *
 * @author ermes
 */
public class Movable extends Rectangle{
    
    
    double posx,posy;
    
    
    
    public Movable(){
        
    }
    
    public Movable(double posx,double posy){
        this.posx=posx;
        this.posy=posy;
    }
    
    public void move(double xMove, double yMove){
       
             this.posx = posx + xMove;
             this.posy = posy + yMove;
        
        //System.out.println("(" + Double.toString(posx)+"," + Double.toString(posy)+")");
    }
    
}
