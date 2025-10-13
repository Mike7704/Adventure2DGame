package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Door extends SuperObject {

	private GamePanel gamePanel;
	
	public OBJ_Door(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		name = "Door";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/door.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
