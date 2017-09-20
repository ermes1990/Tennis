package tennis;


import javafx.scene.paint.Color;

/*
    racket
 */

/**
 *
 * @author ermes
 */
public class Racket extends Movable{
    
    double size;
    
    public Racket(){
        
    }
    
    public Racket(double posx, double posy, double size){
        this.size=size;
        this.posx=posx;
        this.posy=posy;
        this.setWidth(size*0.04);
        this.setHeight(size*0.3);
        this.setFill(Color.valueOf("#FF6690"));
        
    };
    
    // here I can modify the speed of rackets, 
    public void moveUp(){
        this.move(0,-size*0.02);
    }
    
    public void moveDown(){
        this.move(0,size*0.02);
    }
}
