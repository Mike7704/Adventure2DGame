package object;

public class OBJ_Boots extends SuperObject {

	public OBJ_Boots() {
		name = "Boots";
		try {
			image = new javafx.scene.image.Image(getClass().getResourceAsStream("/Object/boots.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
