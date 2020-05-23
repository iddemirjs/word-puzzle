package DataStructures;

public class Node {

    private String engWord;
    private String trWord;
    private Node nextNode;
    private boolean isFound;

    public Node(String engWord, String trWord) {
        this.engWord = engWord;
        this.trWord = trWord;
        this.isFound=false;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getTrWord() {
        return trWord;
    }

    public void setTrWord(String trWord) {
        this.trWord = trWord;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
