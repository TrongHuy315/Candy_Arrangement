import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView{
    int rand = -1;

    public void createView(GraphicsContext sub) {        
        sub.setFill(Color.rgb(98, 121, 199));
        sub.fillRect(5, 13, 900, 774);

        sub.setFill(Color.rgb(105, 165, 120));
        sub.fillRect(910, 13, 430, 774);

        // Cac duong thang doc
        sub.strokeLine(105, 13, 105, 787);
        sub.strokeLine(205, 13, 205, 787);
        sub.strokeLine(405, 13, 405, 787);
        sub.strokeLine(505, 13, 505, 787);
        sub.strokeLine(705, 13, 705, 787);
        sub.strokeLine(805, 13, 805, 787);

        // Cac duong thang ngang
        sub.strokeLine(5, 99, 905, 99);
        sub.strokeLine(5, 185, 905, 185);
        sub.strokeLine(5, 357, 905, 357);
        sub.strokeLine(5, 443, 905, 443);
        sub.strokeLine(5, 615, 905, 615);
        sub.strokeLine(5, 701, 905, 701);

        sub.setLineWidth(3);
        sub.strokeLine(305, 13, 305, 787);
        sub.strokeLine(605, 13, 605, 787);
        sub.strokeLine(5, 271, 905, 271);
        sub.strokeLine(5, 529, 905, 529);
    }

    public Group createPiece() {
        Piece value = new Piece();

        Group group = new Group();
        PieceGenerator elements = new PieceGenerator();

        rand = elements.generateRandomPiece();
        
        int[][] array = value.piece[rand];
        int size = array.length;
        for (int i = 0; i < size; i++) {
            Rectangle sub = new Rectangle(array[i][0], array[i][1], value.getX(), value.getY());
            sub.setFill(Color.rgb(158, 117, 30));
            group.getChildren().add(sub);
        }

        return group;
    }

    public void showGameOverAlert(GamePoint point, Runnable resetGameAction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Trò chơi kết thúc!\n" +
                             "Điểm số mới nhất: " + point.intermediatePoint + "\n" +
                             "Điểm số cao nhất: " + point.get_HighestPoint());

        ButtonType playAgain = new ButtonType("Chơi tiếp");
        ButtonType exitGame = new ButtonType("Thoát");
        alert.getButtonTypes().setAll(playAgain, exitGame);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == playAgain) {
            // Người dùng chọn chơi tiếp: gọi hành động reset game
            resetGameAction.run();
        } else {
            // Người dùng chọn thoát: đóng ứng dụng
            Platform.exit();
        }
    }
}
