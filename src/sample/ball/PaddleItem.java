package sample.ball;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * @author langjr
 * @version 1.0
 * @created 22-Jan-2020 3:21:43 PM
 */
public class PaddleItem extends Rectangle implements Entity {
	private static final double DEFAULT_HEIGHT = 80;
	private static final double DEFAULT_WIDTH = 25;
	private double nextY;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean invert = false;

	public PaddleItem(double height, double xOffset) {
		setHeight(DEFAULT_HEIGHT);
		setWidth(DEFAULT_WIDTH);
		setFill(Color.BLACK);
		setY((height / 2.0) - (DEFAULT_HEIGHT / 2));
		nextY = getY();
		setX(xOffset);
		setFill(new ImagePattern(new Image("/fun/PaddleFill.gif", 25, 80, false, false)));
	}

	@Override
	public void nextFrame(ImageView view) {
		setY(nextY);
		if (movingUp) {
			setUp();
		} else if (movingDown) {
			setDown();
		}
	}

	public void setUp() {
		movingUp = true;
		if (!invert) {
			nextY = getY() - 15 >= 0 ? getY() - 15 : 0;
		} else {
			nextY = getY() + DEFAULT_HEIGHT + 15 <= 400 ? getY() + 15 : 400 - DEFAULT_HEIGHT;
		}
	}

	public void setDown() {
		movingDown = true;
		if (invert) {
			nextY = getY() - 15 >= 0 ? getY() - 15 : 0;
		} else {
			nextY = getY() + DEFAULT_HEIGHT + 15 <= 400 ? getY() + 15 : 400 - DEFAULT_HEIGHT;
		}
	}

	public void release() {
		movingUp = false;
		movingDown = false;
	}

	public void resetPos(double height) {
		setY((height / 2.0) - (DEFAULT_HEIGHT / 2));
		nextY = getY();
	}

	public void invert() {
		invert = !invert;
	}
}