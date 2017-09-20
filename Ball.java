package tennis;


import tennis.Movable;
import javafx.scene.paint.Color;

/*
 The ball
 */

/**
 *
 * @author ermes
 */
public class Ball extends Movable {
    
    Double ballMoverX,ballMoverY;
    public Ball(){
        
    }
    
    public Ball(double posx, double posy, double size){
        this.ballMoverX=size*0.01;
        this.ballMoverY=size*0.01;
        this.posx=posx;
        this.posy=posy;
        this.setFill(Color.valueOf("#2070FF"));
        this.setWidth(size*0.04);
        this.setHeight(size*0.04);        
    }
    
    
}
