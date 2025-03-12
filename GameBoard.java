import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class GameBoard {
    private int[][] chessBoard = new int[9][9];
    protected final int startX = 5;
    protected final int startY = 13;
    protected final int endX = 905;
    protected final int endY = 787;

    int[] arrX = {5, 105, 205, 305, 405, 505, 605, 705, 805};
    int[] arrY = {13, 99, 185, 271, 357, 443, 529, 615, 701};

    Piece value = new Piece();

    private boolean checkBoard(Group group, double dx, double dy) {
        for (Node node : group.getChildren()) {
            Bounds sceneBounds = node.localToScene(node.getBoundsInLocal());
            double xScene = sceneBounds.getMinX();
            double yScene = sceneBounds.getMinY();

            int i = (int)((yScene - dy - startY) / value.getY());
            int j = (int)((xScene - dx - startX) / value.getX());

            if (i < 0 || i >= 9 || j < 0 || j >= 9) {
                return false; // hoặc xử lý phù hợp
            }

            if (chessBoard[i][j] == 1) return false;
        }

        return true;
    }

    public int[] checkPieceInBoard(Group group) {
        Bounds bounds = group.localToScene(group.getBoundsInLocal());
        double x_min = bounds.getMinX();
        double y_min = bounds.getMinY();
        double x_max = bounds.getMaxX();
        double y_max = bounds.getMaxY();

        if (x_min < startX - value.getX() / 2 || y_min < startY - value.getY() / 2 || x_max > endX + value.getX() / 2 || y_max > endY + value.getY() / 2) return null;

        int rootX = 0;
        int rootY = 0;
        double subX = 0;
        double subY = 0;

        for (int i = 0; i < 9; i++) {
            subX = x_min - arrX[i];

            if (subX <= value.getX() / 2) {
                rootX = arrX[i];
                break;
            }
        }

        for (int i = 0; i < 9; i++) {
            subY = y_min - arrY[i];

            if (subY <= value.getY() / 2) {
                rootY = arrY[i];
                break;
            }
        }

        if (checkBoard(group, subX, subY)) return new int[] {rootX, rootY};       

        return null;
    }

    void boardControl(Group group) {
        for (Node node : group.getChildren()) {
            Bounds sceneBounds = node.localToScene(node.getBoundsInLocal());

            double xScene = sceneBounds.getMinX();
            double yScene = sceneBounds.getMinY();

            int j = (int)((xScene - startX) / value.getX());
            int i = (int)((yScene - startY) / value.getY());

            chessBoard[i][j] = 1;
        }
    }

    int checkGame(List<Group> groups) {
        List<Integer> fullRows = new ArrayList<>();
        List<Integer> fullCols = new ArrayList<>();
        int point = 0;

        for (int i = 0; i < 9; i++) {
            int countRow = 0;
            int countCol = 0;
            for (int j = 0; j < 9; j++) {
                if (chessBoard[i][j] == 1) countRow++;
                if (chessBoard[j][i] == 1) countCol++;
            }

            if (countRow == 9) fullRows.add(i);
            if (countCol == 9) fullCols.add(i);
        }

        for (Group group : groups) {
            List<Node> nodesToRemove = new ArrayList<>();

            for (Node node : group.getChildren()) {
                Bounds bounds = node.localToScene(node.getBoundsInLocal());
                double xScene = bounds.getMinX();
                double yScene = bounds.getMinY();

                int row = (int) ((yScene - startY) / value.getY());
                int col = (int) ((xScene - startX) / value.getX());

                if (fullRows.contains(row) || fullCols.contains(col)) {
                    nodesToRemove.add(node);
                }
            }

            group.getChildren().removeAll(nodesToRemove);
        }

        for (int row : fullRows) {
            for (int j = 0; j < 9; j++) {
                chessBoard[row][j] = 0;
            }
            point += 10;
        }

        for (int col : fullCols) {
            for (int i = 0; i < 9; i++) {
                chessBoard[i][col] = 0;
            }

            point += 10;
        }

        return point;
    }

    boolean validGame(int pieceSize) {
        int emptyCount = 0;
    
        // Đếm số ô trống trên bàn cờ
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (chessBoard[i][j] == 0) {
                    emptyCount++;
                }
            }
        }
        
        if (emptyCount < pieceSize) {
            return false;
        }
    
        return true;
    }

    boolean canPlacePiece(int[][] shapeCoordinates) {
        int rows = 9, cols = 9;
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean canPlace = true;
    
                for (int[] offset : shapeCoordinates) {
                    int dy = offset[1];
                    int dx = offset[0];
                    int newRow = row + dy;
                    int newCol = col + dx;
    
                    if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                        canPlace = false;
                        break;
                    }
    
                    if (chessBoard[newRow][newCol] != 0) {
                        canPlace = false;
                        break;
                    }
                }
    
                if (canPlace) {
                    return true;
                }
            }
        }
    
        return false;
    }    
}
