package sample.ball;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * @author langjr
 * @version 1.0
 * @created 22-Jan-2020 3:21:29 PM
 */
public abstract class Ball extends Circle implements Entity {
	private Random color = new Random();

	protected Ball() {
		setFill(selectRandomColor());
	}

	protected Paint selectRandomColor() {
		return new Color(color.nextDouble(), color.nextDouble(), color.nextDouble(), 1);
	}

	public abstract void kill();
}