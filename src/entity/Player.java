package entity;

import main.GamePanel;
import main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

	private GamePanel gamePanel;
	private KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	private int hasKey = 0;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		// Player position is fixed to the centre of the screen
		screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
		screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
		
		// Player collision
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		setDefaultValues();
		getPlayerImage();
	}
	
	// Initial player start position
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed= 4;
		direction = "down";
	}
	
	// Player sprite images
	public void getPlayerImage() {
		up1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_up_1.png"));
		up2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_up_2.png"));
		down1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_down_1.png"));
		down2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_down_2.png"));
		left1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_left_1.png"));
		left2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_left_2.png"));
		right1 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_right_1.png"));
		right2 = new Image(getClass().getResourceAsStream("/Player/Walking sprites/boy_right_2.png"));
	}
	
	public void update() {
		// Move player
		if (keyHandler.isKeyPressed()) {
			if (keyHandler.isUpPressed()) {
				direction = "up";
			}
			else if (keyHandler.isDownPressed()) {
				direction = "down";
			}
			else if (keyHandler.isLeftPressed()) {
				direction = "left";
			}
			else if (keyHandler.isRightPressed()) {
				direction = "right";
			}
			
			// Check tile collision
			collisionOn = false;
			gamePanel.getCollisionChecker().checkTile(this);
			
			// Check object collision
			int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
			pickUpObject(objectIndex);
			
			// Player can move if no collision
			if (!collisionOn) {
				switch(direction) {
					case "up": 		worldY -= speed; break;
					case "down": 	worldY += speed; break;
					case "left": 	worldX -= speed; break;
					case "right": 	worldX += speed; break;
				}
			}
			
			// Walk animation
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int index) {
		if (index != 999) {
			// Pick up the object			
			String objectName = gamePanel.getObject()[index].name;
			
			switch(objectName) {
			case "Key":
				hasKey++;
				gamePanel.getObject()[index] = null;
				System.out.println("You got a key! Keys: " + hasKey);
				break;
			case "Door":
				if (hasKey > 0) {
					System.out.println("You opened the door!");
					hasKey--;
					gamePanel.getObject()[index] = null;
				}
				else {
					System.out.println("You need a key to open this door.");
				}
				break;
			}
		}
	}
	
	// Draw player at updated position and image
	public void draw(GraphicsContext gc) {
		Image image = null;
		
		switch(direction) {
			case "up": 		image = (spriteNum == 1 ? up1 : up2); 		break;
			case "down": 	image = (spriteNum == 1 ? down1 : down2); 	break;
			case "left": 	image = (spriteNum == 1 ? left1 : left2); 	break;
			case "right": 	image = (spriteNum == 1 ? right1 : right2); break;
			default: 		break;
		}
		gc.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
		
	}
}
