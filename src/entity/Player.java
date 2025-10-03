package entity;

import application.GamePanel;
import application.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {

	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	// Initial player start
	public void setDefaultValues() {
		x = 100;
		y = 100;
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
				y -= speed;
			}
			else if (keyHandler.isDownPressed()) {
				direction = "down";
				y += speed;
			}
			else if (keyHandler.isLeftPressed()) {
				direction = "left";
				x -= speed;
			}
			else if (keyHandler.isRightPressed()) {
				direction = "right";
				x += speed;
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
		gc.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize);
		
	}
}
