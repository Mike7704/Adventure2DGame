package environment;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import main.GamePanel;

public class Lighting {
	
	private GamePanel gamePanel;
	private RadialGradient darknessGradient;
	
	public int dayCounter;
	public float filterAlpha = 0f;
	
	// Day states
	public final int day = 0;
	private final int dusk = 1;
	private final int night = 2;
	private final int dawn = 3;
	public int dayState = day;
	
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
		        new Stop(0.0, Color.rgb(0, 0, 0, 0.96)),
		        new Stop(1.0, Color.rgb(0, 0, 0, 0.96))
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
		        new Stop(1.0, Color.rgb(0, 0, 0, 0.96))		// Dark edges
		    );
		}
	}
	
	public void draw(GraphicsContext gc) {
		
	    if (dayState == night) {
	    	// Fill the entire screen with darkness and a light source
		    gc.setFill(darknessGradient);
		    gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
	    }
	    else {
	    	// Apply day-night filter
	    	gc.setFill(Color.rgb(0, 0, 0, filterAlpha));
	    	gc.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
	    }
	    	    
	    // Debug
	    String dayStateString = "";
	    switch (dayState) {
		    case day: dayStateString = "Day"; break;
		    case dusk: dayStateString = "Dusk"; break;
		    case night: dayStateString = "Night"; break;
		    case dawn: dayStateString = "Dawn"; break;
	    }
	    gc.setFont(gamePanel.getUI().font_very_small);
	    gc.setTextAlign(TextAlignment.RIGHT);
	    gamePanel.getUI().drawTextWithShadow("Day State: " + dayStateString, gamePanel.screenWidth - 20, gamePanel.screenHeight - 20);
	}
	
	public void update() {
		if (gamePanel.getPlayer().lightUpdated) {
			setLightSource();
			gamePanel.getPlayer().lightUpdated = false;
		}
		
		// Check the state of the day-night cycle
		if (dayState == day) {
			dayCounter++;
			
			if (dayCounter > 1200) {
				dayState = dusk;
				dayCounter = 0;
			}
		}
		if (dayState == dusk) {
			filterAlpha += 0.001f;
			
			if (filterAlpha > 0.960f) {
				filterAlpha = 0.960f;
				dayState = night;
			}
		}
		if (dayState == night) {
			dayCounter++;
			
			if (dayCounter > 600) {
				dayState = dawn;
				dayCounter = 0;
			}
		}
		if (dayState == dawn) {
			filterAlpha -= 0.001f;
			
			if (filterAlpha < 0f) {
				filterAlpha = 0f;
				dayState = day;
			}
		}
	}
	
}
