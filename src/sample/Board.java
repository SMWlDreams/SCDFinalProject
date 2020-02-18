package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.ball.BallItem;
import sample.ball.PaddleItem;

public class Board {
    private BallItem ball;
    private PaddleItem leftPaddle;
    private PaddleItem rightPaddle;
    private boolean start = false;

    public void init(Pane pane, Controller controller) {
        leftPaddle = new PaddleItem(pane.getHeight(), 25);
        rightPaddle = new PaddleItem(pane.getHeight(), pane.getWidth() - 50);
        pane.getChildren().add(leftPaddle);
        pane.getChildren().add(rightPaddle);
        ball = new BallItem(pane.getWidth(), pane.getHeight(), controller);
        ball.setPaddles(leftPaddle, rightPaddle);
        pane.getChildren().add(ball);
    }

    public void start(Pane pane) {
        if (!start) {
            leftPaddle.resetPos(pane.getHeight());
            rightPaddle.resetPos(pane.getHeight());
            ball.setPaddles(leftPaddle, rightPaddle);
            ball.start(pane.getWidth(), pane.getHeight());
            start = true;
        }
    }

    public void nextFrame(ImageView view) {
        ball.nextFrame(view);
        leftPaddle.nextFrame(view);
        rightPaddle.nextFrame(view);
    }

    public void leftUp() {
        leftPaddle.setUp();
    }

    public void leftDown() {
        leftPaddle.setDown();
    }

    public void rightUp() {
        rightPaddle.setUp();
    }

    public void rightDown() {
        rightPaddle.setDown();
    }

    public void resetLeft() {
        leftPaddle.release();
    }

    public void resetRight() {
        rightPaddle.release();
    }

    public void stop() {
        start = false;
    }

    public void setTroll() {
        ball.initTroll();
    }

    public void cleanup() {
        ball.killThread();
    }
}
