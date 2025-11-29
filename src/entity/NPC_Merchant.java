package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;

public class NPC_Merchant extends Entity{

	public NPC_Merchant(GamePanel gamePanel) {
	super(gamePanel);
		
		name = "Merchant";
		type = type_npc;
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		getNPCImage();
		setDialogue();
		setItems();
	}
	
	// NPC sprite images
	public void getNPCImage() {
		up1 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_1.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/NPC/merchant_down_2.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}
	
	public void setDialogue() {
		dialogues[0] = "Welcome to my store.\nWould you like to trade?";
	}

	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gamePanel));
		inventory.add(new OBJ_Key(gamePanel));
		inventory.add(new OBJ_Shield_Blue(gamePanel));
	}
	
	public void speak() {
		super.speak();
		gamePanel.gameState = gamePanel.tradeState;
		gamePanel.getUI().npc = this;
	}
}
