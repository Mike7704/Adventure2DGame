package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Key extends SuperObject {

	private GamePanel gamePanel;
	
	public OBJ_Key(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		name = "Key";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/key.png"), gamePanel.tileSize, gamePanel.tileSize, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
