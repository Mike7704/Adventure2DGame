package main;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		// Objects
	}
	
	public void setNPC() {
		
		gamePanel.getNPC()[0] = new entity.NPC_OldMan(gamePanel);
		gamePanel.getNPC()[0].worldX = gamePanel.tileSize * 21;
		gamePanel.getNPC()[0].worldY = gamePanel.tileSize * 21;
	}
	
}
