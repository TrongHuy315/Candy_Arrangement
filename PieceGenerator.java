// Sinh ra các mảnh ghép ngẫu nhiên
import java.util.Random;

public class PieceGenerator {
    public int generateRandomPiece() {
        Piece sub = new Piece();
        
        Random rnd = new Random();

        return rnd.nextInt(sub.piece.length);
    }
}
