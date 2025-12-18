package environment;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import main.GamePanel;

public class Lighting {
	
	private GamePanel gamePanel;
	private RadialGradient darknessGradient;
	
	public Lighting(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setLightSource();
	}
	
	public void setLightSource() {
		if (gamePanel.getPlayer().currentLightSource == null) {
			// No light source equipped, set maximum darkness
			darknessGradient = new RadialGradient(
		        0, 0,
		        0, 0,
		        0,
		        false,
		        CycleMethod.NO_CYCLE,
		        new Stop(0.0, Color.rgb(0, 0, 0, 0.98)),
		        new Stop(1.0, Color.rgb(0, 0, 0, 0.98))
		    );
		}
		else {
			// Get the centre position for the light circle
			int centreX = gamePanel.getPlayer().screenX + (gamePanel.tileSize / 2);
			int centreY = gamePanel.getPlayer().screenY + (gamePanel.tileSize / 2);
			
			// Create a radial gradient for the darkness effect
		    darknessGradient = new RadialGradient(
		        0, 0,
		        centreX, centreY,
		        gamePanel.getPlayer().currentLightSource.lightRadius * 0.5,
		        false,
		        CycleMethod.NO_CYCLE,
		        new Stop(0.0, Color.TRANSPARENT),			// Transparent centre
		        new Stop(0.3, Color.TRANSPARENT),
		        new Stop(0.5, Color.rgb(0, 0, 0, 0.25)),
		        new Stop(0.65, Color.rgb(0, 0, 0, 0.4)),
		        new Stop(0.8, Color.rgb(0, 0, 0, 0.65)),
		        new Stop(0.9, Color.rgb(0, 0, 0, 0.85)),
		        new Stop(1.0, Color.rgb(0, 0, 0, 0.98))		// Dark edges
		    );
		}
	}
	
	public void draw(GraphicsContext gc) {
	    // Fill the entire screen with darkness
	    gc.setFill(darknessGradient);
	    gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
	}
	
	public void update() {
		if (gamePanel.getPlayer().lightUpdated) {
			setLightSource();
			gamePanel.getPlayer().lightUpdated = false;
		}
	}
	
}
