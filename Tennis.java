package tennis;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ermes
 */
public class Tennis extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //interface
        Button NewGame = new Button("New Game");
        Button startButton = new Button("Start"); 
        HBox Under = new HBox(NewGame,startButton);
        Under.setSpacing(150);
        Under.setAlignment(Pos.BOTTOM_CENTER);
        Under.setPadding(new Insets(30));
        
       //initialize game
        Gioco game = new Gioco(400);
        
        //positioning the game
        HBox gameBox= new HBox(game);
        gameBox.setAlignment(Pos.CENTER);
        game.setStyle("-fx-background-color: #104030");
        gameBox.setPadding(new Insets(30));
        
        BorderPane root = new BorderPane();
        root.setBottom(Under);
        root.setCenter(gameBox);
        
        Scene scene = new Scene(root, 900, 600);
        
        primaryStage.setTitle("Tennis");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        //scene.setOnKeyPressed(handler);
        
        //remember which key are pressed
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.W){
                    game.wPress=true;
                }
                 if(event.getCode()==KeyCode.S){
                    game.sPress=true;
                }
                 if(event.getCode()==KeyCode.UP){
                    game.upPress=true;
                }
                 if(event.getCode()==KeyCode.DOWN){
                    game.downPress=true;
                }
            }
        });
        
        //unpress te keys no more pressed
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                   if(event.getCode()==KeyCode.W){
                    game.wPress=false;
                }
                 if(event.getCode()==KeyCode.S){
                    game.sPress=false;
                }
                 if(event.getCode()==KeyCode.UP){
                    game.upPress=false;
                }
                 if(event.getCode()==KeyCode.DOWN){
                    game.downPress=false;
                }
            }
        });
        
     
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                //Random kick the ball.
                Random r = new Random();
                double xSpeed=0;
                // we want at least a certain speed in X component
                do{       
                xSpeed= r.nextDouble()*20 -10; 
                }
                while(Math.abs(xSpeed)<5);
                game.ball.ballMoverX=game.size*0.002*(xSpeed);
                game.ball.ballMoverY=game.size*0.002*(r.nextDouble()*20 - 10.0);
                //center the ball
                game.ball.posx= game.size*0.48*1.618;
                game.ball.posy= game.size*0.48;
                game.grow=0;
                //start the game
                game.refresh();
                game.t.play();
            }
        });
                
        NewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //reset everithing
                game.lscore=0;
                game.rscore=0;
                game.ball.posx= game.size*0.48*1.618;
                game.ball.posy= game.size*0.48;
                game.t.stop();
                game.grow=0;
                game.refresh();
            }
        });
        
        // Same as start button but more enjoyable during game
        root.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER){
                    startButton.fire();
                }
                
            }
        
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
