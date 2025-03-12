// Điều khiển toàn bộ logic của game (thêm / xóa mảnh, kiểm trra điều kiện thua)
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;

public class GameController extends Application {
    private static double mouseX, mouseY;
    GameView view = new GameView();
    GameBoard board = new GameBoard();
    Piece value = new Piece();
    GamePoint point = new GamePoint();

    Pane root = null;
    private final Map<Group, Boolean> lockedGroups = new HashMap<>();

    private boolean isInsideRestrictedArea(Group group) {
        Bounds bounds = group.localToScene(group.getBoundsInLocal());
        
        return bounds.getMinX() >= board.startX && bounds.getMinY() >= board.startY && bounds.getMaxX() <= board.endX && bounds.getMaxY() <= board.endY;
    }

    void objectMovement(Group group) {
        lockedGroups.put(group, false);
    
        group.setOnMousePressed(event -> {
            if (!lockedGroups.get(group) && event.getClickCount() == 1) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            }
        });
    
        group.setOnMouseDragged(event -> {
            if (lockedGroups.get(group)) return;
    
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
    
            if (Math.abs(deltaX) > 2 || Math.abs(deltaY) > 2) {
                group.setTranslateX(group.getTranslateX() + deltaX);
                group.setTranslateY(group.getTranslateY() + deltaY);
    
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            }
        });
    
        group.setOnMouseReleased(event -> {
            if (event.getClickCount() == 1) {
                boolean isValid = validPieceInBoard(group);
    
                if (isValid) {
                    if (isInsideRestrictedArea(group)) {
                        if (!lockedGroups.get(group)) {
                            lockedGroups.put(group, true);
                            board.boardControl(group);
                            List<Group> groupList = root.getChildren().stream()
                                .filter(n -> n instanceof Group)
                                .map(n -> (Group) n)
                                .collect(Collectors.toList());

                            point.intermediatePoint += board.checkGame(groupList);
                            if (point.intermediatePoint > point.get_HighestPoint()) point.set_HighestPoint(point.intermediatePoint);

                            Group newGroup = view.createPiece();

                            if (!board.validGame(newGroup.getChildren().size()) || !board.canPlacePiece(value.shapeCoordinates[view.rand])) {
                                view.showGameOverAlert(point, () -> resetGame());
                                return;
                            }

                            objectMovement(newGroup);
                            root.getChildren().add(newGroup);
                        }
                    }
                }
            }
        });
    }    

    boolean validPieceInBoard(Group group) {
        int[] root = board.checkPieceInBoard(group);

        if (root == null && !lockedGroups.get(group)) {
            if (!lockedGroups.get(group)) {
                group.setTranslateX(0);
                group.setTranslateY(0);
            }

            return false;
        }

        Bounds bounds = group.localToScene(group.getBoundsInLocal());
        double dx = root[0] - bounds.getMinX();
        double dy = root[1] - bounds.getMinY();

        for (Node node : group.getChildren()) {
            node.setTranslateX(node.getTranslateX() + dx);
            node.setTranslateY(node.getTranslateY() + dy);
        }

        return true;
    }

    private void resetGame() {
        // Reset lại bảng trò chơi: khởi tạo lại mảng, xóa các mảnh trên pane, v.v...
        board = new GameBoard();  // Hoặc bạn có thể reset lại mảng chessBoard của board hiện tại
        point.intermediatePoint = 0; // Reset điểm tạm thời, nhưng giữ highestPoint không đổi
        root.getChildren().clear();
        
        // Giả sử canvas là thành phần nền, cần được thêm lại
        Canvas canvas = new Canvas(1345, 800);
        GraphicsContext sub = canvas.getGraphicsContext2D();
        view.createView(sub);
        root.getChildren().add(canvas);
        
        // Tạo mảnh mới
        Group group = view.createPiece();
        objectMovement(group);
        root.getChildren().add(group);
    }    

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(1345, 800);
        GraphicsContext sub = canvas.getGraphicsContext2D();

        view.createView(sub);

        root = new Pane();
        root.getChildren().add(canvas);

        Group group = view.createPiece();
        objectMovement(group);

        root.getChildren().add(group);
        Scene board = new Scene(root, 1345, 800);

        primaryStage.setTitle("Game Board");
        primaryStage.setScene(board);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
