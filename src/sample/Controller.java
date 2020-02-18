package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Controller {

    @FXML
    private Pane pane;
    @FXML
    private ImageView view;

    private Board board;

    private MediaPlayer player;
    private boolean start = false;
    private KeyCode leftCode = KeyCode.CODE_INPUT;
    private KeyCode rightCode = KeyCode.CODE_INPUT;
    private Timeline timeline;
    private int leftWinCount = 0;
    private int rightWinCount = 0;
    private boolean bo7 = false;
    private boolean bo7Start = false;

    public void init() {
        board = new Board();
        board.init(pane, this);
    }

    public void parseInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.W) && !leftCode.equals(KeyCode.S)) {
            board.leftUp();
            leftCode = KeyCode.W;
            return;
        } else if (event.getCode().equals(KeyCode.S) && !leftCode.equals(KeyCode.W)) {
            board.leftDown();
            leftCode = KeyCode.S;
            return;
        }
        if (event.getCode().equals(KeyCode.UP) && !rightCode.equals(KeyCode.DOWN)) {
            board.rightUp();
            rightCode = KeyCode.UP;
            return;
        } else if (event.getCode().equals(KeyCode.DOWN) && !rightCode.equals(KeyCode.UP)) {
            board.rightDown();
            rightCode = KeyCode.DOWN;
            return;
        }
        if (event.getCode().equals(KeyCode.ENTER)) {
            board.start(pane);
            if (!start) {
                if (!bo7Start) {
                    try {
                        if (player != null) {
                            player.stop();
                        }
                        if (bo7) {
                            player = new MediaPlayer(new Media(
                                    new File(System.getProperty("user.dir") +
                                            "\\audio\\All Star.mp3").toURI().toURL().toString()));
                        } else {
                            player = new MediaPlayer(new Media(
                                    new File(System.getProperty("user.dir") +
                                            "\\audio\\game.mp3").toURI().toURL().toString()));
                        }
                        player.setCycleCount(MediaPlayer.INDEFINITE);
                        player.setOnReady(() -> player.play());
                        bo7Start = true;
                    } catch (Exception ignored) {
                    }
                }
                KeyFrame frame = new KeyFrame(Duration.seconds(1.0/60), e -> board.nextFrame(view));
                timeline = new Timeline(frame);
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                start = true;
            }
            return;
        }
        if (event.getText().equals(Character.toString(TROLLER.charAt(troll.length())))) {
            troll += event.getText();
            if (troll.equals(TROLLER)) {
                board.setTroll();
                leftWinCount = 0;
                rightWinCount = 0;
                troll = "";
                bo7 = true;
                bo7Start = false;
                System.out.println("Best of 7 initialized");
            }
        } else {
            troll = "";
        }
    }

    public void endInput(KeyEvent event) {
        if (event.getCode().equals(leftCode)) {
            board.resetLeft();
            leftCode = KeyCode.CODE_INPUT;
        }
        if (event.getCode().equals(rightCode)) {
            board.resetRight();
            rightCode = KeyCode.CODE_INPUT;
        }
    }

    public void kill(boolean left) {
        try {
            if (left) leftWinCount++;
            else rightWinCount++;
            if (leftWinCount == 4 || rightWinCount == 4 || !bo7) {
                if (left) {
                    System.out.println("LEFT IS WINNER! CONGRALTURATIONS!!!!!!!!!!! :)");
                } else {
                    System.out.println("RIGHT IS WINNER! CONGRALTURATIONS!!!!!!!!!!! :)");
                }
                timeline.stop();
                player.stop();
                player = new MediaPlayer(new Media(new File(System.getProperty("user.dir") +
                        "\\audio\\lose.mp3").toURI().toURL().toString()));
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.setOnReady(() -> player.play());
                start = false;
                board.stop();
                timeline = null;
            } else {
                System.out.println("LEFT SCORE: " + leftWinCount);
                System.out.println("RIGHT SCORE: " + rightWinCount);
                board.stop();
                start = false;
                timeline.stop();
                timeline = null;
            }
        } catch (Exception ignored) {}
    }

    private String troll = "";
    private static final String TROLLER = "troll";
}
