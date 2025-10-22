package main;

import javafx.scene.shape.Rectangle;

public class EventRect extends Rectangle {
	
	protected int eventRectDefaultX, eventRectDefaultY;
	protected boolean eventDone = false;

	public EventRect(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.eventRectDefaultX = x;
		this.eventRectDefaultY = y;
	}

}
