package environment;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.FillRule;
import main.GamePanel;

public class Lighting {
	
	private GamePanel gamePanel;
	private int circleSize;
	private int centreX;
	private int centreY;
	private double x;
	private double y;
	
	public Lighting(GamePanel gamePanel, int circleSize) {
		this.gamePanel = gamePanel;
		this.circleSize = circleSize;
		
		// Get the centre position for the light circle
		centreX = gamePanel.getPlayer().screenX + (gamePanel.tileSize / 2);
		centreY = gamePanel.getPlayer().screenY + (gamePanel.tileSize / 2);
		x = centreX - (circleSize / 2);
		y = centreY - (circleSize / 2);
	}
	
	public void draw(GraphicsContext gc) {
	    // Create a radial gradient for the darkness effect
	    RadialGradient darkness = new RadialGradient(
	        0, 0,
	        centreX, centreY,
	        circleSize * 0.5,
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

	    // Fill the entire screen with darkness
	    gc.setFill(darkness);
	    gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
	}
}
