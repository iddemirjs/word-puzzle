package GameTools;

public class WordSchema {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private String engMean;
    private String turkishMean;
    private int length;
    private int directionX;
    private int directionY;
    private int pressedX;
    private int pressedY;
    private String letter;
    private String wordProcess;

    public int getPressedX() {
        return pressedX;
    }

    public void setPressedX(int pressedX) {
        this.pressedX = pressedX;
    }

    public int getPressedY() {
        return pressedY;
    }

    public void setPressedY(int pressedY) {
        this.pressedY = pressedY;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getWordProcess() {
        return wordProcess;
    }

    public void setWordProcess(String wordProcess) {
        this.wordProcess = wordProcess;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getEngMean() {
        return engMean;
    }

    public void setEngMean(String engMean) {
        this.engMean = engMean;
    }

    public String getTurkishMean() {
        return turkishMean;
    }

    public void setTurkishMean(String turkishMean) {
        this.turkishMean = turkishMean;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
