package tennis;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import tennis.Racket;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/*
 Control the game (the black rectangle in the middle)
 */

/**
 *
 * @author ermes
 */
public class Gioco extends Pane{
    
    Racket lRacket,rRacket;
    Ball ball;
    Double size;
    int lscore,rscore;
    Timeline t;
    Text score;
    
    //key pressed status
    boolean sPress, wPress, upPress, downPress;
    
    double grow;

    public Gioco(){
        
    }
    
    public Gioco(double size){
        
        //set various parameters
        this.lscore=0;
        this.rscore=0;
        this.size=size;
        Racket lRacket = new Racket(size*0.02, size*0.40, size);
        Racket rRacket = new Racket(size*0.96*1.618, size*0.40, size);
        Ball ball = new Ball(size*0.48*1.618,size*0.48,size);
        this.lRacket=lRacket;
        this.rRacket=rRacket;
        this.ball=ball;
        
        //initial conditions keys are unpresses
        this.sPress=false;
        this.wPress=false;
        this.upPress=false;
        this.downPress=false;
        
        this.setMaxHeight(size);
        this.setMaxWidth(size*1.618);
        this.setMinHeight(size);
        this.setMinWidth(size*1.618);
        
        Text score=new Text("Score: "+ Integer.toString(lscore) + " - "+Integer.toString(rscore));
        this.score=score;
        
        this.getChildren().add(ball);
        this.getChildren().add(lRacket);
        this.getChildren().add(rRacket);
        this.getChildren().add(score);
        
        
        /*set initial position of rackets and ball*/
        this.lRacket.setLayoutX(lRacket.posx);
        this.lRacket.setLayoutY(lRacket.posy);
        this.rRacket.setLayoutX(rRacket.posx);
        this.rRacket.setLayoutY(rRacket.posy);
        this.ball.setLayoutX(ball.posx);
        this.ball.setLayoutY(ball.posy);
        score.setLayoutX(size*0.36*1.618);
        score.setLayoutY(size*0.1);
        score.setFill(Color.valueOf("#DFDF30"));
        score.setFont(Font.font("Arial", FontWeight.BOLD, Math.floor(size/14)));
        

        this.setStyle("-fx-background: black;");
        //normalize the angle along the field
        double normAngle= Math.PI/(size*1.618);
        //hill grower
        this.grow = 0;
        
        //dinamic part of the game
        Timeline t = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
        //ball movements
            ball.move(ball.ballMoverX, ball.ballMoverY);
            //air friction
            ball.ballMoverX=ball.ballMoverX*0.999;
            ball.ballMoverY=ball.ballMoverY*0.999;
            
            
            //for fun, suppose it is a ball on the ground and we are playing on a field with a hill in the middle that slowly grows...
                // grow =grow+1;
                // ball.ballMoverX=ball.ballMoverX + (-Math.cos(ball.posx*normAngle))*(Math.sin(grow*0.01))*0.25;
                //ball.ballMoverY=ball.ballMoverY + (-Math.cos(ball.posy*(Math.PI/size)))*(Math.sin(grow*0.01))*0.25;
           
            
            //bounce on upper/down border
            if(ball.posy>size*0.94 || ball.posy< 0.03){
                ball.ballMoverY=-ball.ballMoverY*0.95;
                ball.ballMoverX=ball.ballMoverX*0.95;
                ball.move(ball.ballMoverX, ball.ballMoverY);
            }

            //bounce on right racket
            if(ball.posx>size * 1.52 && (ball.posy > rRacket.posy -size*0.04 && ball.posy < rRacket.posy + size*0.32)){
                
                Double delta = ball.posy - rRacket.posy -size*0.17; 
                // norm the delta such as it varies in [-pi/2 , pi/2]
                Double bounceAngle= delta*(Math.PI/(size*0.34));                
                ball.ballMoverY=ball.ballMoverY*0.97 + size*0.01*Math.sin(bounceAngle);
                ball.ballMoverX=-ball.ballMoverX*0.97 - size*0.01*Math.cos(bounceAngle);
                ball.move(ball.ballMoverX, ball.ballMoverY);
            }
            
            //bounce on left racket
            if(ball.posx< size*0.07 &&(ball.posy > lRacket.posy - size*0.04 && ball.posy < lRacket.posy + size*0.32)){
                
                Double delta = ball.posy - lRacket.posy -size*0.17; 
                 // norm the delta such as it varies in [-pi/2 , pi/2]
                Double bounceAngle= delta*(Math.PI/(size*0.34));                
                ball.ballMoverY=ball.ballMoverY*0.95 + size*0.01*Math.sin(bounceAngle);
                ball.ballMoverX=-ball.ballMoverX*0.95 + size*0.01*Math.cos(bounceAngle);
                ball.move(ball.ballMoverX, ball.ballMoverY);
             }
            
            //check if somebody scores
            // ball out of screen, score for the adversary
            //out on right, score for left
            if (ball.posx > size*1.58){
                lscore=lscore +1;
                score.setText("Score: "+ Integer.toString(lscore) + " - "+ Integer.toString(rscore));
                //reset ball initial position and speed=0
                ball.posx=size*0.48*1.618;
                ball.posy=size*0.48;
                ball.setLayoutX(ball.posx);
                ball.setLayoutY(ball.posy);
                ball.ballMoverX=0.0;
                ball.ballMoverY=0.0;
                //grow=0;
                           
            }
            //out on left, score for right
             if (ball.posx < 0){
                rscore=rscore +1;
                score.setText("Score: "+ Integer.toString(lscore) + " - "+ Integer.toString(rscore));
                ball.posx=size*0.48*1.618;
                ball.posy=size*0.48;
                ball.setLayoutX(ball.posx);
                ball.setLayoutY(ball.posy);
                ball.ballMoverX=0.0;
                ball.ballMoverY=0.0;
                //grow=0;
            }
            
    //rackets movements
    // if button pressed move, do not go out of the screen
       if(wPress){
          if(lRacket.posy>(size*0.03)){ 
            lRacket.moveUp();
          }
        }
      else if(sPress){
          
          if(lRacket.posy<(size*0.65)){ 
            lRacket.moveDown();
          }
        }
        if(upPress){
          if(rRacket.posy>(size*0.03)){ 
            rRacket.moveUp();
          }
        }
      else if(downPress){
          
          if(rRacket.posy<(size*0.65)){ 
            rRacket.moveDown();
          }
        }    
            refresh();
            }
        }));
        
         t.setCycleCount(Timeline.INDEFINITE);
        this.t =t;   
    }
    
    //positioning racket and ball according to their new coordinates
    //refresh score
    public void refresh(){
        this.lRacket.setLayoutX(lRacket.posx);
        this.lRacket.setLayoutY(lRacket.posy);
        this.rRacket.setLayoutX(rRacket.posx);
        this.rRacket.setLayoutY(rRacket.posy);
        this.ball.setLayoutX(ball.posx);
        this.ball.setLayoutY(ball.posy);
        this.score.setText("Score: "+ Integer.toString(lscore) + " - "+ Integer.toString(rscore));
    }
    
    //unnecessary but intuitive.
    public void start(){
        t.play();
    }
}
