package main;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GamePanel gamePanel = new GamePanel(primaryStage);
			Scene scene = new Scene(gamePanel);
			
			KeyHandler keyHandler = gamePanel.getKeyHandler();
	        scene.setOnKeyPressed(keyHandler::handleKeyPressed);
	        scene.setOnKeyReleased(keyHandler::handleKeyReleased);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("2D Adventure");
			primaryStage.setResizable(false);
			primaryStage.setFullScreen(false);
			primaryStage.setFullScreenExitHint("");
			primaryStage.show();
			
			gamePanel.setupGame();
			gamePanel.startGameLoop();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
