package entity;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity {

	public static final String npcName = "Big Rock";
	
	public NPC_BigRock(GamePanel gamePanel) {
		super(gamePanel);
		
		name = npcName;
		type = type_npc;
		direction = "down";
		speed = 4;
		
		solidArea = new Rectangle(2, 6, 44, 40);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		getNPCImage();
		
		dialogueSet = -1;
		setDialogue();
	}
	
	// NPC sprite images
	public void getNPCImage() {
		up1 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		up2 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down1 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		down2 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left1 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		left2 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right1 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		right2 = new Image(getClass().getResourceAsStream("/NPC/bigrock.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
	}

	public void setDialogue() {
		dialogues[0][0] = "It's a big rock";
	}
	
	public void update() {
		// Disabled
	}
	
	public void speak() {
		
		facePlayer();
		startDialogue(this, dialogueSet);
		dialogueSet++;
		
		if (dialogues[dialogueSet][0] == null) {
			// Reset dialogue
			dialogueSet = 0;
		}
	}
	
	public void move(String direction) {
		this.direction = direction;
		
		checkCollision();
		
		if (!collisionOn) {
			switch(direction) {
				case "up": 		worldY -= speed; 	break;
				case "down": 	worldY += speed; 	break;
				case "left": 	worldX -= speed;  	break;
				case "right": 	worldX += speed; 	break;
				default: 		break;
			}
		}
		
		detectPlate();
	}
	
	public void detectPlate() {
		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();
		int count = 0;
		
		// Create plate list
		for (int i = 0; i < gamePanel.getInteractiveTile()[1].length; i++) {
			InteractiveTile plate = gamePanel.getInteractiveTile()[gamePanel.currentMap][i];
			if (plate != null && plate.name != null && plate.name.equals(IT_MetalPlate.itName)) {
				plateList.add(plate);
			}
		}
		
		// Create rock list
		for (int i = 0; i < gamePanel.getNPC()[1].length; i++) {
			Entity rock = gamePanel.getNPC()[gamePanel.currentMap][i];
			if (rock != null && rock.name.equals(NPC_BigRock.npcName)) {
				rockList.add(rock);
			}
		}
		
		// Scan plate list
		for (int i = 0; i < plateList.size(); i++) {
			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			
			if (distance < 8) {
				// On metal plate
				if (linkedEntity == null) {
					linkedEntity = plateList.get(i);
					gamePanel.playSoundEffect(3);
				}
			}
			else if (linkedEntity == plateList.get(i)) {
				linkedEntity = null;
			}
		}
		
		// Scan rock list
		for (int i = 0; i < rockList.size(); i++) {
			if (rockList.get(i).linkedEntity != null) {
				count++;
			}
		}
		
		// Open the door if all rock are on the plates
		if (count == rockList.size()) {
			for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
				Entity ironDoor = gamePanel.getObject()[gamePanel.currentMap][i];
				if (ironDoor != null && ironDoor.name.equals(OBJ_Door_Iron.objName)) {
					gamePanel.getObject()[gamePanel.currentMap][i] = null;
					gamePanel.playSoundEffect(21); // Open door sound
				}
			}
		}
	}
}
