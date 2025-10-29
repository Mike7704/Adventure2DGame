package main;

import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import object.OBJ_Heart;

public class UI {

	private GamePanel gamePanel;
	private GraphicsContext gc;
	
	private Font font_small, font_large;
	
	private Image heart_full, heart_half, heart_blank;
	
	public boolean gameFinished = false;
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setFont();
		setHud();
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
	
	private void setHud() {
		Entity heart = new OBJ_Heart(gamePanel);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(GraphicsContext gc) {
		this.gc = gc;
		
		gc.setFont(font_large);
		gc.setFill(Color.WHITE);
		
		// TITLE STATE
		if (gamePanel.gameState == gamePanel.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		else if (gamePanel.gameState == gamePanel.playState) {
			drawPlayerLife();
		}
		// PAUSE STATE
		else if (gamePanel.gameState == gamePanel.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIAGLOUE STATE
		else if (gamePanel.gameState == gamePanel.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		// CHARACTER STATE
		else if (gamePanel.gameState == gamePanel.characterState) {
			drawCharacterScreen();
		}
	}
	
	private void drawTitleScreen() {
	    // Constants for positions
	    final double screenWidth = gamePanel.screenWidth;
	    final double screenHeight = gamePanel.screenHeight;
	    final double tileSize = gamePanel.tileSize;

	    // Background fill
	    gc.setFill(new Color(0.35, 0.6, 0.4, 1));
	    gc.fillRect(0, 0, screenWidth, screenHeight);

	    // Title Text
	    gc.setFont(font_large);
	    gc.setTextAlign(TextAlignment.CENTER);
	    drawTextWithShadow("2D ADVENTURE", screenWidth / 2, screenHeight / 5);

	    // Image
	    int x = (int) ((screenWidth / 2) - (tileSize * 2) / 2);
	    int y = (int) ((screenHeight / 2.5) - tileSize);
	    gc.drawImage(gamePanel.getPlayer().down1, x, y, tileSize * 2, tileSize * 2);

	    // Menu Text
	    gc.setFont(font_large);
	    gc.setTextAlign(TextAlignment.CENTER);
	    drawTextWithShadow("NEW GAME", screenWidth / 2, screenHeight / 1.5);
	    if (commandNum == 0) {
	    	drawTextWithShadow(">", screenWidth / 2 - 130, screenHeight / 1.5);
	    }

	    drawTextWithShadow("LOAD GAME", screenWidth / 2, (screenHeight / 1.5) + 42);
	    if (commandNum == 1) {
	    	drawTextWithShadow(">", screenWidth / 2 - 130, (screenHeight / 1.5) + 42);
	    }

	    drawTextWithShadow("QUIT", screenWidth / 2, (screenHeight / 1.5) + 84);
	    if (commandNum == 2) {
	    	drawTextWithShadow(">", screenWidth / 2 - 130, (screenHeight / 1.5) + 84);
	    }
	}
		
	private void drawPauseScreen() {
		gc.setFont(font_large);
		gc.setTextAlign(TextAlignment.CENTER);
		drawTextWithShadow("PAUSED", gamePanel.screenWidth / 2, gamePanel.screenHeight / 4);
	}	
	
	private void drawDialogueScreen() {
		// Window
		int x = gamePanel.tileSize;
		int y = gamePanel.tileSize / 2;
		int width = gamePanel.screenWidth - (gamePanel.tileSize * 2);
		int height = gamePanel.tileSize * 4;
		drawSubWindow(x, y, width, height);
		
		// Text
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		x += gamePanel.tileSize;
		y += gamePanel.tileSize;
		
		drawTextWithShadow(currentDialogue, x, y);
	}
	
	private void drawCharacterScreen() {
		// Window
		int frameX = gamePanel.tileSize;
		int frameY = gamePanel.tileSize;
		int frameWidth = gamePanel.tileSize * 5;
		int frameHeight = gamePanel.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Text
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		int textX = frameX + 20;
		int textY = frameY + gamePanel.tileSize;
		final int lineHeight = 35;
		
		// Labels
		drawTextWithShadow("Level", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Life", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Strength", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Dexterity", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Attack", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Defense", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Exp", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Next Level", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Coin", textX, textY);
		textY += lineHeight + 20;
		drawTextWithShadow("Weapon", textX, textY);
		textY += lineHeight;
		drawTextWithShadow("Shield", textX, textY);
		
		// Values
		gc.setTextAlign(TextAlignment.RIGHT);
		textX = (frameX + frameWidth) - 20;
		textY = frameY + gamePanel.tileSize;
		String value;
		
		value = String.valueOf(gamePanel.getPlayer().level);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().life + "/" + gamePanel.getPlayer().maxLife);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().strength);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().dexterity);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().attack);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().defense);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().exp);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().nextLevelExp);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight;
		value = String.valueOf(gamePanel.getPlayer().coin);
		drawTextWithShadow(value, textX, textY);
		textY += lineHeight - 5;
		gc.drawImage(gamePanel.getPlayer().currentWeapon.down1, textX - 32, textY, 32, 32);
		textY += lineHeight;
		gc.drawImage(gamePanel.getPlayer().currentShield.down1, textX - 32, textY, 32, 32);
	}
	
	private void drawSubWindow(int x, int y, int width, int height) {
		Color colour = Color.rgb(0, 0, 0, 0.7);
		gc.setFill(colour);
		gc.fillRoundRect(x, y, width, height, 35, 35);
		
		colour = Color.WHITE;
		gc.setStroke(colour);
		gc.setLineWidth(5);
		gc.strokeRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
	private void drawPlayerLife() {
		int x = gamePanel.tileSize / 2;
		int y = gamePanel.tileSize / 2;
		
		// Draw max life
		for (int i = 0; i < gamePanel.getPlayer().maxLife / 2; i++) {
			gc.drawImage(heart_blank, x, y, gamePanel.tileSize, gamePanel.tileSize);
			x += gamePanel.tileSize;
		}
		
		// Reset
		x = gamePanel.tileSize / 2;
		y = gamePanel.tileSize / 2;
		
		// Draw current life
		for (int i = 0; i < gamePanel.getPlayer().life; i += 2) {
			if (i + 1 < gamePanel.getPlayer().life) {
				gc.drawImage(heart_full, x, y, gamePanel.tileSize, gamePanel.tileSize);
			} else {
				gc.drawImage(heart_half, x, y, gamePanel.tileSize, gamePanel.tileSize);
			}
			x += gamePanel.tileSize;
		}
	}
	
	private void drawTextWithShadow(String text, double x, double y) {
		int shadowOffset = 2;
        // Shadow
        gc.setFill(Color.BLACK);
        gc.fillText(text, x + shadowOffset, y + shadowOffset);

        // Text
        gc.setFill(Color.WHITE);
        gc.fillText(text, x, y);
    }
}
