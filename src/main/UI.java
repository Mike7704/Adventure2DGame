package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {

	private GamePanel gamePanel;
	private GraphicsContext gc;
	
	public Font font_very_small, font_small, font_large;
	
	private Image heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
	
	public boolean gameFinished = false;
	private boolean messageOn = false;
	private ArrayList<String> message = new ArrayList<>();
	private ArrayList<Integer> messageCounter = new ArrayList<>();
	public String currentDialogue = "";
	public int subState = 0;
	public int commandNum = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	private int counter = 0;
	public Entity npc;
	public int charIndex = 0;
	public String combinedText = "";
	
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
			InputStream inputStream3 = getClass().getResourceAsStream("/Font/PixelPurl.ttf");
			font_very_small = Font.loadFont(inputStream3, 25);
			inputStream3.close();
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
		
		Entity crystal = new OBJ_ManaCrystal(gamePanel);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}
	
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
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
			drawManaCrystal();
			drawMessage();
		}
		// PAUSE STATE
		else if (gamePanel.gameState == gamePanel.pauseState) {
			drawPlayerLife();
			drawManaCrystal();
			drawPauseScreen();
		}
		// DIAGLOUE STATE
		else if (gamePanel.gameState == gamePanel.dialogueState) {
			drawDialogueScreen();
		}
		// CHARACTER STATE
		else if (gamePanel.gameState == gamePanel.characterState) {
			drawCharacterScreen();
			drawInventoryScreen(gamePanel.getPlayer(), true);
		}
		// OPTIONS STATE
		else if (gamePanel.gameState == gamePanel.optionsState) {
			drawOptionsScreen();
			drawControlScreen();
		}
		// GAME OVER STATE
		else if (gamePanel.gameState == gamePanel.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		else if (gamePanel.gameState == gamePanel.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		else if (gamePanel.gameState == gamePanel.tradeState) {
			drawTradeScreen();
		}
		// SLEEP STATE
		else if (gamePanel.gameState == gamePanel.sleepState) {
			drawSleepScreen();
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
		
		if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			// Display text one character at a time
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			if (charIndex < characters.length) {
				gamePanel.playSoundEffect(17); // Speak sound
				combinedText += String.valueOf(characters[charIndex]);
				currentDialogue = combinedText;
				charIndex++;
			}
			
			if (gamePanel.getKeyHandler().enterPressed && gamePanel.gameState == gamePanel.dialogueState) {
				charIndex = 0;
				combinedText = "";
				
				npc.dialogueIndex++;
				gamePanel.getKeyHandler().enterPressed = false;
			}
		}
		else { // No more dialogue
			npc.dialogueIndex = 0;
			if (gamePanel.gameState == gamePanel.dialogueState) {
				gamePanel.gameState = gamePanel.playState;
			}
		}
		
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
		drawTextWithShadow("Mana", textX, textY);
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
		value = String.valueOf(gamePanel.getPlayer().mana + "/" + gamePanel.getPlayer().maxMana);
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
	
	private void drawInventoryScreen(Entity entity, boolean cursor) {
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if (entity == gamePanel.getPlayer()) {
			// Player inventory
			frameX = gamePanel.screenWidth - (gamePanel.tileSize * 6);
			frameY = gamePanel.tileSize;
			frameWidth = gamePanel.tileSize * 5;
			frameHeight = gamePanel.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			// NPC inventory
			frameX = gamePanel.tileSize;
			frameY = gamePanel.tileSize;
			frameWidth = gamePanel.tileSize * 5;
			frameHeight = gamePanel.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Slots
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gamePanel.tileSize + 3;
		
		// Draw items
		for (int i = 0; i < entity.inventory.size(); i++) {
			// Background
			gc.setFill(Color.DIMGRAY);
			gc.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
			
			// Equipped item highlight
			if (entity.inventory.get(i) == entity.currentWeapon ||
				entity.inventory.get(i) == entity.currentShield ||
				entity.inventory.get(i) == entity.currentLightSource)
			{
				gc.setFill(Color.GOLD);
				gc.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
			}
			
			// Item image
			gc.drawImage(entity.inventory.get(i).down1, slotX, slotY, gamePanel.tileSize, gamePanel.tileSize);
			
			// Display stack amount
			if (entity == gamePanel.getPlayer() && entity.inventory.get(i).stackAmount > 1) {
				gc.setFont(font_very_small);
				drawTextWithShadow("" + entity.inventory.get(i).stackAmount, slotX + slotSize - 5, slotY + slotSize - 5);
			}
			
			// Next slot position
			slotX += slotSize;
			if ((i + 1) % 4 == 0) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		if (cursor) {
			int cursorX = slotXstart + (slotCol * slotSize);
			int cursorY = slotYstart + (slotRow * slotSize);
			int cursorWidth = gamePanel.tileSize;
			int cursorHeight = gamePanel.tileSize;
			
			// Draw cursor
			gc.setFill(Color.WHITE);
			gc.setLineWidth(3);
			gc.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			drawDescriptionScreen(entity, slotCol, slotRow);
		}		
	}
	
	private void drawDescriptionScreen(Entity entity, int slotCol, int slotRow) {
		// Window
		int frameX = (entity == gamePanel.getPlayer()) ? gamePanel.screenWidth - (gamePanel.tileSize * 6) : gamePanel.tileSize;
		int frameY = gamePanel.tileSize * 6;
		int frameWidth = gamePanel.tileSize * 5;
		int frameHeight = gamePanel.tileSize * 2;
				
		// Description text
		int textX = frameX + (frameWidth / 2);
		int textY = frameY + 30;	
		gc.setFont(font_very_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		
		int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
		if (itemIndex < entity.inventory.size()) {
			drawSubWindow(frameX, frameY, frameWidth, frameHeight);
			
			drawTextWithShadow(entity.inventory.get(itemIndex).description, textX, textY);
		}
	}
	
	private void drawOptionsScreen() {
		// Window
		int frameX = gamePanel.tileSize;
		int frameY = gamePanel.tileSize;
		int frameWidth = gamePanel.tileSize * 8;
		int frameHeight = gamePanel.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
				
		// Title text
		gc.setFont(font_large);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		
		// Title
		int textX = frameX + (frameWidth / 2);
		int textY = frameY + gamePanel.tileSize;
		drawTextWithShadow("OPTIONS", textX, textY);
		
		// Options text
		textX = frameX + (gamePanel.tileSize);
		gc.setFont(font_small);
		gc.setTextAlign(TextAlignment.LEFT);
		
		// Full screen
		textY += gamePanel.tileSize * 2;
		drawTextWithShadow("FULL SCREEN", textX, textY);
		if (commandNum == 0) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				gamePanel.toggleFullscreen();
			}
		}
		
		// Music
		textY += gamePanel.tileSize;
		drawTextWithShadow("MUSIC", textX, textY);
		if (commandNum == 1) {
			drawTextWithShadow(">", textX - 25, textY);
		}
		
		// Sound effects
		textY += gamePanel.tileSize;
		drawTextWithShadow("SOUND", textX, textY);
		if (commandNum == 2) {
			drawTextWithShadow(">", textX - 25, textY);
		}
				
		// Exit game
		textY += (gamePanel.tileSize * 2);
		drawTextWithShadow("EXIT GAME", textX, textY);
		if (commandNum == 3) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				gamePanel.getConfig().saveConfig();
				gamePanel.gameState = gamePanel.titleState;
				commandNum = 0;
				gamePanel.resetGame(true);
			}
		}
		
		// Close
		textY += gamePanel.tileSize;
		drawTextWithShadow("CLOSE", textX, textY);
		if (commandNum == 4) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				gamePanel.getConfig().saveConfig();
				gamePanel.gameState = gamePanel.playState;
				commandNum = 0;
			}
		}
				
		// Full screen check box
		textX = frameX + frameWidth - (gamePanel.tileSize + 25);
		textY = frameY + (gamePanel.tileSize * 3);
		gc.setFill(Color.WHITE);
		gc.setLineWidth(3);
		gc.strokeRoundRect(textX, textY - 25, 25, 25, 10, 10);
		if (gamePanel.fullscreen) {
			gc.fillRoundRect(textX, textY - 25, 25, 25, 10, 10);
		}
		
		// Music volume
		textY += gamePanel.tileSize;
		gc.strokeRoundRect(textX - 75, textY - 25, 100, 25, 10, 10);
		int volumeWidth = (int) (gamePanel.getMusic().volume * 100);
		gc.fillRoundRect(textX - 75, textY - 25, volumeWidth, 25, 10, 10);
		
		// Sound volume
		textY += gamePanel.tileSize;
		gc.strokeRoundRect(textX - 75, textY - 25, 100, 25, 10, 10);
		volumeWidth = (int) (gamePanel.getSoundEffect().volume * 100);
		gc.fillRoundRect(textX - 75, textY - 25, volumeWidth, 25, 10, 10);
				
		gamePanel.getKeyHandler().enterPressed = false;
	}
	
	private void drawControlScreen() {
		// Window
		int frameX = gamePanel.tileSize * 11;
		int frameY = gamePanel.tileSize;
		int frameWidth = gamePanel.tileSize * 8;
		int frameHeight = gamePanel.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
				
		// Title text
		gc.setFont(font_large);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		
		// Title
		int textX = frameX + (frameWidth / 2);
		int textY = frameY + gamePanel.tileSize;
		drawTextWithShadow("CONTROLS", textX, textY);
		
		// Options text
		textX = frameX + (gamePanel.tileSize);
		gc.setFont(font_small);
		gc.setTextAlign(TextAlignment.LEFT);
		
		// Move
		textY += gamePanel.tileSize * 2;
		drawTextWithShadow("MOVE", textX, textY);
		
		// Use/Attack
		textY += gamePanel.tileSize;
		drawTextWithShadow("USE/ATTACK", textX, textY);
		
		// Shoot/Cast
		textY += gamePanel.tileSize;
		drawTextWithShadow("SHOOT/CAST", textX, textY);
		
		// Character screen
		textY += gamePanel.tileSize;
		drawTextWithShadow("CHARACTER SCREEN", textX, textY);
		
		// Pause
		textY += gamePanel.tileSize;
		drawTextWithShadow("PAUSE", textX, textY);
		
		// Options
		textY += gamePanel.tileSize;
		drawTextWithShadow("OPTIONS", textX, textY);
		
		// Second column
		gc.setTextAlign(TextAlignment.RIGHT);
		textX = frameX + frameWidth - gamePanel.tileSize;
		textY = frameY + (gamePanel.tileSize * 2);
		
		// Move
		textY += gamePanel.tileSize;
		drawTextWithShadow("WASD", textX, textY);
		
		// Confirm/Attack
		textY += gamePanel.tileSize;
		drawTextWithShadow("ENTER", textX, textY);
		
		// Shoot/Cast
		textY += gamePanel.tileSize;
		drawTextWithShadow("F", textX, textY);
		
		// Character screen
		textY += gamePanel.tileSize;
		drawTextWithShadow("C", textX, textY);
		
		// Pause
		textY += gamePanel.tileSize;
		drawTextWithShadow("P", textX, textY);
		
		// Options
		textY += gamePanel.tileSize;
		drawTextWithShadow("ESC", textX, textY);
	}
	
	public void drawGameOverScreen() {
		// Background
		gc.setFill(new Color(0, 0, 0, 0.7));
		gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		// Text
		gc.setFont(font_large);
		gc.setTextAlign(TextAlignment.CENTER);
		drawTextWithShadow("GAME OVER", gamePanel.screenWidth / 2, gamePanel.screenHeight / 5);
		
		// Respawn
		drawTextWithShadow("RESPAWN", gamePanel.screenWidth / 2, gamePanel.screenHeight / 1.5);
		if (commandNum == 0) {
			drawTextWithShadow(">", gamePanel.screenWidth / 2 - 130, gamePanel.screenHeight / 1.5);
		}
		
		// Title screen
		drawTextWithShadow("QUIT", gamePanel.screenWidth / 2, (gamePanel.screenHeight / 1.5) + 42);
		if (commandNum == 1) {
			drawTextWithShadow(">", gamePanel.screenWidth / 2 - 130, (gamePanel.screenHeight / 1.5) + 42);
		}
	}
	
	public void drawTransition() {
		counter++;
		gc.setFill(new Color(0, 0, 0, Math.clamp((counter * 5) / 255.0, 0, 1)));
		gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		if (counter >= 60) {
			counter = 0;
			gamePanel.gameState = gamePanel.playState;
			gamePanel.currentMap = gamePanel.getEventHandler().tempMap;
			gamePanel.getPlayer().worldX = gamePanel.getEventHandler().tempCol * gamePanel.tileSize;
			gamePanel.getPlayer().worldY = gamePanel.getEventHandler().tempRow * gamePanel.tileSize;
			gamePanel.getEventHandler().previousEventX = gamePanel.getPlayer().worldX;
			gamePanel.getEventHandler().previousEventY = gamePanel.getPlayer().worldY;
			gamePanel.changeArea();
		}
	}
	
	public void drawTradeScreen() {
		switch (subState) {
			case 0: tradeSelect(); break;
			case 1: tradeBuy(); break;
			case 2: tradeSell(); break;
		}
		gamePanel.getKeyHandler().enterPressed = false;
	}
	
	private void tradeSelect() {
		npc.dialogueSet = 0;
		drawDialogueScreen();
		
		// Window
		int frameX = gamePanel.tileSize * 15;
		int frameY = gamePanel.tileSize * 5;
		int frameWidth = gamePanel.tileSize * 4;
		int frameHeight = gamePanel.tileSize * 4;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Text
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		int textX = frameX + gamePanel.tileSize;
		int textY = frameY + (int)(gamePanel.tileSize * 1.25);
		
		// Buy
		drawTextWithShadow("Buy", textX, textY);
		if (commandNum == 0) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				subState = 1;
				commandNum = 0;
			}
		}
		
		// Sell
		textY += gamePanel.tileSize;
		drawTextWithShadow("Sell", textX, textY);
		if (commandNum == 1) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// Leave
		textY += gamePanel.tileSize;
		drawTextWithShadow("Leave", textX, textY);
		if (commandNum == 2) {
			drawTextWithShadow(">", textX - 25, textY);
			if (gamePanel.getKeyHandler().enterPressed) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}		
	}
	
	private void tradeBuy() {
		// Inventory screens
		drawInventoryScreen(gamePanel.getPlayer(), false);
		drawInventoryScreen(npc, true);
				
		// Window
		int frameX = (gamePanel.screenWidth / 2) - (int)(gamePanel.tileSize * 2.5);
		int frameY = gamePanel.tileSize * 7;
		int frameWidth = gamePanel.tileSize * 5;
		int frameHeight = gamePanel.tileSize * 4;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Text
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		int textX = frameX + (frameWidth / 2);
		int textY = frameY + (int)(gamePanel.tileSize * 1.25);
		
		// Player coin
		drawTextWithShadow("Your coin: " + gamePanel.getPlayer().coin, textX, textY);
		textY += gamePanel.tileSize;
		
		// Item price
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			drawTextWithShadow("Item price: " + npc.inventory.get(itemIndex).price, textX, textY);		
			
			// Buy an item
			if (gamePanel.getKeyHandler().enterPressed) {
				if (npc.inventory.get(itemIndex).price > gamePanel.getPlayer().coin) {
					subState = 0;
					npc.startDialogue(npc, 2);
				}
				else if (gamePanel.getPlayer().canObtainItem(npc.inventory.get(itemIndex))) {
					gamePanel.getPlayer().coin -= npc.inventory.get(itemIndex).price;
					npc.inventory.remove(itemIndex);
				}
				else {
					subState = 0;
					npc.startDialogue(npc, 3);
					drawDialogueScreen();
				}
			}
		}
		else {
			drawTextWithShadow("Item price: 0", textX, textY);
		}
		
		// Back option
		textY += gamePanel.tileSize;
		drawTextWithShadow("[ESC] Back", textX, textY);
	}
	
	private void tradeSell() {
		// Inventory screen
		drawInventoryScreen(gamePanel.getPlayer(), true);
		
		// Window
		int frameX = (gamePanel.screenWidth / 2) - (int)(gamePanel.tileSize * 2.5);
		int frameY = gamePanel.tileSize * 7;
		int frameWidth = gamePanel.tileSize * 5;
		int frameHeight = gamePanel.tileSize * 4;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Text
		gc.setFont(font_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		int textX = frameX + (frameWidth / 2);
		int textY = frameY + (int)(gamePanel.tileSize * 1.25);
		
		// Player coin
		drawTextWithShadow("Your coin: " + gamePanel.getPlayer().coin, textX, textY);
		textY += gamePanel.tileSize;
		
		// Item price
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gamePanel.getPlayer().inventory.size()) {
			drawTextWithShadow("Sell value: " + (gamePanel.getPlayer().inventory.get(itemIndex).price / 2), textX, textY);		
			
			// Sell an item
			if (gamePanel.getKeyHandler().enterPressed) {
				
				if (gamePanel.getPlayer().inventory.get(itemIndex) == gamePanel.getPlayer().currentWeapon ||
						gamePanel.getPlayer().inventory.get(itemIndex) == gamePanel.getPlayer().currentShield)
				{
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 4);
				}
				else {
					if (gamePanel.getPlayer().inventory.get(itemIndex).stackAmount > 1) {
						gamePanel.getPlayer().inventory.get(itemIndex).stackAmount--;
					}
					else {
						gamePanel.getPlayer().inventory.remove(itemIndex);
					}
					gamePanel.getPlayer().coin += (gamePanel.getPlayer().inventory.get(itemIndex).price / 2);
				}
			}
		}
		else {
			drawTextWithShadow("Sell value: 0", textX, textY);
		}
		
		// Back option
		textY += gamePanel.tileSize;
		drawTextWithShadow("[ESC] Back", textX, textY);
	}
	
	public void drawSleepScreen() {
		counter++;
		
		if (counter < 120) {
			gamePanel.getEnvironmentManager().lighting.filterAlpha += 0.01;
			if (gamePanel.getEnvironmentManager().lighting.filterAlpha > 0.96f) {
				gamePanel.getEnvironmentManager().lighting.filterAlpha = 0.96f;
			}
		}
		if (counter >= 120) {
			gamePanel.getEnvironmentManager().lighting.filterAlpha -= 0.01;
			if (gamePanel.getEnvironmentManager().lighting.filterAlpha <= 0f) {
				gamePanel.getEnvironmentManager().lighting.filterAlpha = 0f;
				counter = 0;
				gamePanel.getEnvironmentManager().lighting.dayState = gamePanel.getEnvironmentManager().lighting.day;
				gamePanel.getEnvironmentManager().lighting.dayCounter = 0;
				gamePanel.getPlayer().getPlayerImage();
				gamePanel.gameState = gamePanel.playState;
			}
		}
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * 4);
		return itemIndex;
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
	
	private void drawManaCrystal() {
		int x = (int) (gamePanel.screenWidth - (gamePanel.tileSize * 1.5));
		int y = gamePanel.tileSize / 2;
		
		// Draw max mana
		for (int i = 0; i < gamePanel.getPlayer().maxMana; i++) {
			gc.drawImage(crystal_blank, x, y, gamePanel.tileSize, gamePanel.tileSize);
			x -= (gamePanel.tileSize / 1.5);
		}
		
		// Reset
		x = (int) (gamePanel.screenWidth - (gamePanel.tileSize * 1.5));
		y = gamePanel.tileSize / 2;
		
		// Draw current mana
		for (int i = 0; i < gamePanel.getPlayer().mana; i ++) {
			if (i < gamePanel.getPlayer().mana) {
				gc.drawImage(crystal_full, x, y, gamePanel.tileSize, gamePanel.tileSize);
			}
			x -= (gamePanel.tileSize / 1.5);
		}
	}
	
	private void drawMessage() {
		int messageX = gamePanel.tileSize;
		int messageY = gamePanel.screenHeight - (gamePanel.tileSize * 3);
		gc.setFont(font_very_small);
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		
		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {
				drawTextWithShadow(message.get(i), messageX, messageY);
				
				messageCounter.set(i, messageCounter.get(i) + 1);
				messageY += 30;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}				
			}
		}
	}
	
	public void drawTextWithShadow(String text, double x, double y) {
		int shadowOffset = 2;
        // Shadow
        gc.setFill(Color.BLACK);
        gc.fillText(text, x + shadowOffset, y + shadowOffset);

        // Text
        gc.setFill(Color.WHITE);
        gc.fillText(text, x, y);
    }
}
