class Move {

    private Square from;
    private Square to;
    private boolean isCapture;
    private boolean isEnPassantCapture;

    Move(Square from, Square to, boolean isCapture, boolean isEnPassantCapture) {
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
        this.isEnPassantCapture = isEnPassantCapture;
    }

    Square getFrom() {
        return from;
    }

    Square getTo() {
        return to;
    }

    boolean isCapture() {
        return isCapture;
    }

    boolean isEnPassantCapture() {
        return isEnPassantCapture;
    }

    String getSAN() {
        String[] files = {"a","b","c","d","e","f","g","h"};
        String[] ranks = {"1","2","3","4","5","6","7","8"};
        return files[from.getX()] + ranks[from.getY()] + "-" + files[to.getX()] + ranks[to.getY()];
    }

}
