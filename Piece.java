public class Piece {
    private final int x = 100;
    private final int y = 86;
    int[][][] piece = {
        {
            {925, 185},
            {1025, 185},
            {1125, 185},
            {1225, 185}
        },
        {
            {925, 185},
            {1025, 185},
            {1125, 185}
        },
        {
            {925, 185},
            {1025, 185}
        },
        {
            {925, 185}
        },
        {
            {1125, 56},
            {1125, 142},
            {1125, 228},
            {1125, 314}
        },
        {
            {1125, 56},
            {1125, 142},
            {1125, 228}
        },
        {
            {1125, 56},
            {1125, 142}
        },
        {
            {1125, 56}
        },
        {
            {1025, 99},
            {1025, 185},
            {1125, 99},
            {1125, 185}
        },
        {
            {1025, 99},
            {1025, 185},
            {1125, 185},
            {1225, 185}
        },
        {
            {1025, 99},
            {1025, 185},
            {1125, 99},
            {1225, 99}
        },
        {
            {1025, 99},
            {1025, 185},
            {1025, 271},
            {1125, 271}
        },
        {
            {1025, 99},
            {1025, 185},
            {1025, 271},
            {1125, 99}
        }
    };

    int[][][] shapeCoordinates = {
        {
            {0, 0},
            {1, 0},
            {2, 0},
            {3, 0}
        },
        {
            {0, 0},
            {1, 0},
            {2, 0}
        },
        {
            {0, 0},
            {1, 0}
        },
        {
            {0, 0}
        },
        {
            {0, 0},
            {0, 1},
            {0, 2},
            {0, 3}
        },
        {
            {0, 0},
            {0, 1},
            {0, 2}
        },
        {
            {0, 0},
            {0, 1}
        },
        {
            {0, 0}
        },
        {
            {0, 0},
            {1, 0},
            {0, 1},
            {1, 1}
        },
        {
            {0, 0},
            {1, 0},
            {1, 1},
            {1, 2}
        },
        {
            {0, 0},
            {1, 0},
            {0, 1},
            {0, 2}
        },
        {
            {0, 0},
            {1, 0},
            {2, 0},
            {2, 1}
        },
        {
            {0, 0},
            {1, 0},
            {2, 0},
            {0, 1}
        }
    };

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
