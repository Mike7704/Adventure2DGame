package main;

import java.text.DecimalFormat;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import object.OBJ_Key;

public class UI {

	private GamePanel gamePanel;
	private GraphicsContext gc;
	
	private Font arial_30;
	private Font arial_40;
	
	public boolean gameFinished = false;
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	
	private double playTime;
	private DecimalFormat df = new DecimalFormat("#00.00");
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", 40);
		arial_30 = new Font("Arial", 30);
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(GraphicsContext gc) {
		this.gc = gc;
		
		gc.setFont(arial_40);
		gc.setFill(Color.WHITE);
		
		if (gamePanel.gameState == gamePanel.playState) {
			// play
		}
		else if (gamePanel.gameState == gamePanel.pauseState) {
			drawPauseScreen();
		}
	}
	
	public void drawPauseScreen() {
		gc.setFont(arial_40);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("PAUSED", gamePanel.screenWidth / 2, gamePanel.screenHeight / 2);
	}	
}
