package sample.ball;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import sample.Controller;

import java.io.File;
import java.util.Random;

/**
 * @author langjr
 * @version 1.0
 * @created 22-Jan-2020 3:21:41 PM
 */
public class BallItem extends Ball {
	private static final int RADIUS = 15;


	private Image[] bgImages =
			{
					new Image("/fun/1.jpg",600, 400, true, true),
					new Image("/fun/2.png",600, 400, true, true),
					new Image("/fun/3.png",600, 400, true, true),
					new Image("/fun/4.png",600, 400, true, true),
					new Image("/fun/5.png",600, 400, true, true),
					new Image("/fun/6.png",600, 400, true, true),
			};

	private BallThread m_BallThread;
	private AudioClip clip;
	private Controller controller;

	volatile int index = 0;
	double angle;
	volatile double nextX;
	volatile double nextY;
	volatile boolean update = true;
	volatile double speed = 1;
	static final int DEFAULT_DIST = 4;
	int direction;
	volatile Random random = new Random();
	boolean reflect = false;
	boolean altBehavior = false;

	public BallItem(double width, double height, Controller controller) {
		init(width, height);
		m_BallThread = new BallThread();
		m_BallThread.m_BallItem = this;
		this.controller = controller;
		setFill(new ImagePattern(new Image("/fun/spin.gif")));
		try {
			clip = new AudioClip(
					new File(
							System.getProperty("user.dir") + "\\audio\\bounce.wav")
							.toURI().toURL().toString());
		} catch (Exception ignored) {}
	}

	private void init(double width, double height) {
		angle = (Math.PI / 4.0) * random.nextDouble();
		direction = random.nextInt(Integer.MAX_VALUE) % 2 == 0 ? 1 : -1;
		setCenterX(width / 2);
		setCenterY(height / 2);
		nextX = getCenterX();
		nextY = getCenterY();
		setRadius(RADIUS);
		speed = 1;
	}

	public void start(double width, double height) {
		init(width, height);
		m_BallThread.cachedSpeed = speed;
		m_BallThread.start();
	}

	public void setPaddles(PaddleItem left, PaddleItem right) {
		m_BallThread.left = left;
		m_BallThread.right = right;
	}

	@Override
	public void nextFrame(ImageView view) {
		setCenterX(nextX);
		setCenterY(nextY);
		if (reflect) {
			reflect = false;
			clip.play();
			view.setImage(bgImages[index]);
		}
		if (nextX <= 0 || nextX >= 600) {
			System.out.println("Please help me I need a life");
			m_BallThread.loaded = false;
			controller.kill(nextX >= 600);
			m_BallThread = new BallThread();
			m_BallThread.m_BallItem = this;
		}
		update = true;
	}

	public void killThread() {
		m_BallThread.loaded = false;
	}

	@Override
	public void kill() {
		m_BallThread.loaded = false;
	}

	public void initTroll() {
		altBehavior = true;
	}
}