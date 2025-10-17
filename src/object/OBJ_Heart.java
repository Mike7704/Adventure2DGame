package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Heart extends SuperObject {

	GamePanel gamePanel;
	
	public OBJ_Heart(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		name = "Heart";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/heart_full.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
			image2 = new Image(getClass().getResourceAsStream("/Object/heart_half.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
			image3 = new Image(getClass().getResourceAsStream("/Object/heart_blank.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
