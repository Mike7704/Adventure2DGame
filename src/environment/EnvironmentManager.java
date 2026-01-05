package environment;

import javafx.scene.canvas.GraphicsContext;
import main.GamePanel;

public class EnvironmentManager {

	private GamePanel gamePanel;
	public Lighting lighting;
	
	public EnvironmentManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setup() {
		lighting = new Lighting(gamePanel);
	}
	
	public void update() {
		lighting.update();
	}
	
	public void draw(GraphicsContext gc) {
		lighting.draw(gc);
	}
	
	
}
