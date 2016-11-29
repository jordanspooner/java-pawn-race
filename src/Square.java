class Square {

    private int x;
    private int y;
    private Color c;

    Square(int x, int y) {
        assert 0 <= x && x <= 7 && 0 <= y && y <= 7;
        this.x = x;
        this.y = y;
        c = Color.NONE;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Color occupiedBy() {
        return c;
    }

    void setOccupier(Color color) {
        c = color;
    }

}
