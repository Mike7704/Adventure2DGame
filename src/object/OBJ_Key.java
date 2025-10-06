package object;

import javafx.scene.image.Image;

public class OBJ_Key extends SuperObject {

	public OBJ_Key() {
		name = "Key";
		try {
			image = new Image(getClass().getResourceAsStream("/Object/key.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
