enum Color {
    BLACK, WHITE, NONE;

    static Color opposite(Color c) {
        if (c == BLACK) {
            return WHITE;
        }
        if (c == WHITE) {
            return BLACK;
        }
        return NONE;
    }
}