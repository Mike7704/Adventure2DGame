package object;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {
	
	public static final String objName = "Iron Door";
	
	public OBJ_Door_Iron(GamePanel gamePanel) {
		super(gamePanel);
		
		name = objName;
		type = type_obstacle;
		down1 = new Image(getClass().getResourceAsStream("/Object/door_iron.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		collision = true;
				
		solidArea = new Rectangle(0, 16, 48, 32);
		solidAreaDefaultX = (int) solidArea.getX();
		solidAreaDefaultY = (int) solidArea.getY();
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "It won't open.";
	}
	
	public void interact() {
		startDialogue(this, 0);
	}
}
