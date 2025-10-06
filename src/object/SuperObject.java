package object;

import application.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SuperObject {

	public Image image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
	public void draw(GraphicsContext gc, GamePanel gamePanel) {
		int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
		int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;
		
		// Draw object only visible on screen around the player
		if (worldX + gamePanel.tileSize > gamePanel.getPlayer().worldX - gamePanel.getPlayer().screenX &&
			worldX - gamePanel.tileSize < gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX &&
			worldY + gamePanel.tileSize > gamePanel.getPlayer().worldY - gamePanel.getPlayer().screenY &&
			worldY - gamePanel.tileSize < gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY)
		{
			gc.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
		}
	}
	
}
