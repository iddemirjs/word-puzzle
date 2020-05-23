package DataStructures;

public class DBLNode {
    private int point;
    private String name;
    private DBLNode next;
    private DBLNode prev;

    public DBLNode(String name,int point) {
        this.point = point;
        this.name = name;
        this.next=null;
        this.prev=null;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBLNode getNext() {
        return next;
    }

    public void setNext(DBLNode next) {
        this.next = next;
    }

    public DBLNode getPrev() {
        return prev;
    }

    public void setPrev(DBLNode prev) {
        this.prev = prev;
    }
}
