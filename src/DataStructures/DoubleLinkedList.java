package DataStructures;

public class DoubleLinkedList {
    private DBLNode head;
    private DBLNode tail;

    public DoubleLinkedList(){
        this.head=null;
        this.tail=null;
    }

    public void Add(String name,int point){
        DBLNode newDBLNode;
        newDBLNode = new DBLNode(name,point);
        if (this.head==null){
            this.head= newDBLNode;
        }else{
            newDBLNode.setPrev(this.tail);
            tail.setNext(newDBLNode);
        }
        this.tail= newDBLNode;
    }

    public void AddByPoint(String name,int point){
        DBLNode newDBLNode = new DBLNode(name,point);
        if (this.head==null){
            this.head= newDBLNode;
        }else{
            DBLNode tempHead=this.head;

            if (tempHead.getPoint()<point){
                newDBLNode.setNext(tempHead);
                tempHead.setPrev(newDBLNode);
                this.head=newDBLNode;
                return;
            }
            while ( tempHead != null ){
                if (tempHead.getPoint()<point)
                {
                    if (tempHead.getNext()==null){
                        tempHead.getPrev().setNext(newDBLNode);
                        tempHead.setPrev(tempHead);
                        tail=tempHead;
                        return;
                    }else{
                        tempHead.getPrev().setNext(newDBLNode);
                        newDBLNode.setNext(tempHead);
                        return;
                    }
                }
                if (tempHead.getNext() == null){
                    tempHead.setNext(newDBLNode);
                    newDBLNode.setPrev(tempHead);
                    this.tail=newDBLNode;
                    return;
                }
                tempHead=tempHead.getNext();
            }
        }
        this.tail= newDBLNode;
    }

    public void fillDoubleList(String[][] records){
        for (int i = 0; i < records.length ; i++) {
            this.AddByPoint(records[i][0],Integer.valueOf(records[i][1]));
        }
    }

    public void Remove(Object dataToRemove){
        if (head==null){
            System.out.println("Err500: LinkedList is empty.");
        }else{
            while(this.head.getName().equals(dataToRemove)){
                this.head=this.head.getNext();
                head.setPrev(null);
            }
            DBLNode temp=this.head;
            while(temp != null){
                if (temp.getName().equals(dataToRemove)){
                    if (temp.getNext() == null ){
                        tail = tail.getPrev();
                        tail.setNext(null);
                    }else{
                        temp.getPrev().setNext(temp.getNext());
                        temp.getNext().setPrev(temp.getPrev());
                    }
                }
                temp=temp.getNext();
            }
        }
    }

    public int Size(){
        int count=0;
        if (head==null){
            System.out.println("Err501: LinkedList is empty");
        }
        else{
            DBLNode temp=head;
            while (temp != null){
                count++;
                temp=temp.getNext();
            }
        }
        return count;
    }

    public void Display(){
        if (head==null){
            System.out.println("Err503: LinkedList is empty.");
        }else{
            DBLNode temp=this.head;
            while(temp !=null){
                System.out.println(temp.getName()+" - "+temp.getPoint());
                temp = temp.getNext();
            }
            System.out.println();
        }
    }

    public DBLNode getHead() {
        return head;
    }
}
