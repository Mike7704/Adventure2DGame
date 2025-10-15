package main;

import java.io.IOException;
import java.io.InputStream;
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
	
	private Font font_small, font_large;
	
	public boolean gameFinished = false;
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	public String currentDialogue = "";
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setFont();
	}
	
	private void setFont() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/Font/PixelPurl.ttf");
			font_small = Font.loadFont(inputStream, 35);
			inputStream.close();
			InputStream inputStream2 = getClass().getResourceAsStream("/Font/PixelPurl.ttf");
			font_large = Font.loadFont(inputStream2, 50);
			inputStream2.close();
		} catch (IOException e) {
			font_small = Font.loadFont("Arial", 25);
			font_large = Font.loadFont("Arial", 35);
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(GraphicsContext gc) {
		this.gc = gc;
		
		gc.setFont(font_large);
		gc.setFill(Color.WHITE);
		
		// PLAY STATE
		if (gamePanel.gameState == gamePanel.playState) {
			// play
		}
		// PAUSE STATE
		else if (gamePanel.gameState == gamePanel.pauseState) {
			drawPauseScreen();
		}
		// DIAGLOUE STATE
		else if (gamePanel.gameState == gamePanel.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawPauseScreen() {
		gc.setFont(font_large);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("PAUSED", gamePanel.screenWidth / 2, gamePanel.screenHeight / 2);
	}	
	
	public void drawDialogueScreen() {
		// WINDOW
		int x = gamePanel.tileSize;
		int y = gamePanel.tileSize / 2;
		int width = gamePanel.screenWidth - (gamePanel.tileSize * 2);
		int height = gamePanel.tileSize * 4;
		
		drawSubWindow(x, y, width, height);
		
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		x += gamePanel.tileSize;
		y += gamePanel.tileSize;
		
		// Split dialogue into multiple lines if necessary
		for (String line : currentDialogue.split("\n")) {
			gc.fillText(line, x, y);
			y += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color colour = Color.rgb(0, 0, 0, 0.7);
		gc.setFill(colour);
		gc.fillRoundRect(x, y, width, height, 35, 35);
		
		colour = Color.WHITE;
		gc.setStroke(colour);
		gc.setLineWidth(5);
		gc.strokeRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
}
