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
	private Font arial_30;
	private Font arial_40;
	
	private Image keyImage;
	
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
		
		OBJ_Key key = new OBJ_Key(gamePanel);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(GraphicsContext gc) {
		
		if (gameFinished) {
			gc.setFont(arial_40);
			gc.setFill(Color.YELLOW);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText("You found the treasure!", gamePanel.screenWidth/2, gamePanel.screenHeight/4);
			
			gc.fillText("Your time is: " + df.format(playTime), gamePanel.screenWidth/2, gamePanel.screenHeight/3);
			
			gamePanel.stopGameLoop();
			return;
		}
		
		gc.setFont(arial_40);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize);
		gc.fillText("x " + gamePanel.getPlayer().hasKey, 74, 65);
		
		// Time
		playTime += (double) 1 / gamePanel.TARGET_FPS;
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.fillText("Time: " + df.format(playTime), gamePanel.screenWidth - 26, 65);
		
		// Message
		if (messageOn) {
			gc.setFont(arial_30);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(message, gamePanel.screenWidth/2, gamePanel.screenHeight/4);
			
			messageCounter++;
			if (messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
}
