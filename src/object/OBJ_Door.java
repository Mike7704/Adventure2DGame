package object;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	public OBJ_Door(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Door";
		down1 = new Image(getClass().getResourceAsStream("/Object/door.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		collision = true;
				
		solidArea = new Rectangle(0, 16, 48, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
	}
}
