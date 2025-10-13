package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Chest extends SuperObject {

	private GamePanel gamePanel;
	
	public OBJ_Chest(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		name = "Chest";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/chest.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
