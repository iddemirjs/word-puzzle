package DataStructures;

import java.util.Random;

public class SingleLinkedList {

	private Node head;

	public SingleLinkedList() {
		this.head = null;
	}

	public void insert(String engWord,String trWord) {
		if (head == null) {
			Node newnode = new Node(engWord, trWord);
			head = newnode;
		}
		else {
			Node temp = head;
			while (temp.getNextNode() != null)
				temp = temp.getNextNode();
			Node newnode = new Node(engWord, trWord);
			temp.setNextNode(newnode);
		}
	}

	public void add(String engWord,String trWord) {
		Node newNode;
		Node temp = head;
		Node previous=null;
		if (head == null) {
			newNode = new Node(engWord,trWord);
			head = newNode;
		} else if(head!=null && ((String)engWord).compareTo ((String)head.getEngWord())<0) {
			newNode=new Node(engWord.toUpperCase(),trWord.toUpperCase());
			newNode.setNextNode(head);
			head=newNode;
		}
		else {
			while(temp!=null && ((String)engWord).compareTo((String)temp.getEngWord())>0 ) {
				previous=temp;
				temp=temp.getNextNode();
			}
			if(temp==null) {
				newNode=new Node(engWord.toUpperCase(), trWord.toUpperCase());
				previous.setNextNode(newNode);
			}
			else {
				newNode=new Node(engWord.toUpperCase(), trWord.toUpperCase());
				newNode.setNextNode(temp);
				previous.setNextNode(newNode);
			}
		}
	}

	public void addAlphanumeric(String data,String mean) {
        //Create a new node
    	Node temp ;
    	Node newNode=new Node(data,mean);
    	if (head==null) {
			head = newNode;
		}
    	else {
    		temp=head;
    		if (data.compareTo(head.getEngWord())<0) {
    			newNode=new Node(data,mean);
    			newNode.setNextNode(head);
    			head=newNode;
			}
    		else if (data.compareTo(head.getEngWord())>0) {
				temp=head;
				Node prev=null;
				while(temp!=null && data.compareTo(temp.getEngWord())>0) {
					prev=temp;
					temp=temp.getNextNode();
				}
				if (temp==null) {
					newNode = new Node(data,mean);
					prev.setNextNode(newNode);
				}
				else {
					newNode=new Node(data,mean);
					newNode.setNextNode(temp);
					prev.setNextNode(newNode);
				}
			}
    		else {
    			temp=head;
    			while(temp.getNextNode()!=null)
    					temp=temp.getNextNode();

    			newNode = new Node(data,mean);
    			temp.setNextNode(newNode);
    		}
    	}
    }

    public void delete(Object dataToDelete) {
		if(head==null)
			System.out.println("linked list is empty");
		else {
			while((String) head.getEngWord() == (String)dataToDelete) {
				head=head.getNextNode();
			}
			Node temp=head;
			Node previous=null;
			while(temp!=null) {
				if((String) temp.getEngWord() == (String)dataToDelete) {
					previous.setNextNode(temp.getNextNode());
					temp=previous;
				}
				previous=temp;
				temp=temp.getNextNode();
			}
		}
	}

	public void display() {
		if (head == null)
			System.out.println("Linked list is empty");
		else {
			Node temp = head;
			while (temp != null) {
				System.out.print(temp.getEngWord()+ " " + temp.getTrWord());
				temp = temp.getNextNode();
				System.out.println();
			}

		}

	}

	public int size(){
		int count=1;
		if(head==null)
			System.out.println("Linked list is empty");
		else{
			Node temp=head.getNextNode();
			while(temp!=null){
				count++;
				temp=temp.getNextNode();
			}
		}
		return count;
	}

	public void fillList(String[][] wordList){
		for (int i = 0; i < wordList.length ; i++) {
			this.addAlphanumeric(wordList[i][0],wordList[i][1]);
		}
	}

	public Node getHead()
	{
		return this.head;
	}

	public boolean wordIsFound(String word){
		if (head == null)
			System.out.println("Linked list is empty");
		else {
			Node temp = head;
			while (temp != null) {
				if (temp.getEngWord().equalsIgnoreCase(word)){
					return temp.isFound();
				}
				temp = temp.getNextNode();
			}
		}
		return false;
	}

	public void changeIsFound(String word){
		if (head == null)
			System.out.println("Linked list is empty");
		else {
			Node temp = head;
			while (temp != null) {
				if (temp.getEngWord().equalsIgnoreCase(word)){
					temp.setFound(true);
					return;
				}
				temp = temp.getNextNode();
			}
		}
	}

	public String[] getRandomTwoWord(String trueWord){
		Random random = new Random();
		int randomOne=0,randomTwo=0;
		while(randomOne==randomTwo){
			int listSize=this.size();
			randomOne=random.nextInt(listSize);
			randomTwo=random.nextInt(listSize);
			Node wordOne=this.getWordWithIndex(randomOne);
			Node wordTwo=this.getWordWithIndex(randomTwo);
			if (!(wordOne.getEngWord().equalsIgnoreCase(trueWord)) && !(wordTwo.getEngWord().equalsIgnoreCase(trueWord)) ){
				return new String[]{wordOne.getTrWord(),wordTwo.getTrWord() };
			}
		}
		return new String[]{ };
	}

	public Node getWordWithIndex(int i){
		Node tempNode=this.head;
		int count=0;
		while(tempNode!=null){
			if (count!=i){
				tempNode=tempNode.getNextNode();
				count++;
				continue;
			}
			return tempNode;
		}
		return tempNode;
	}

	public Node[] getPage(int pageNumber,int pageSize){
		Node[] nodeList=new Node[pageSize];
		if (head == null)
			System.out.println("Linked list is empty");
		else {
			int index=0;
			Node temp = head;
			while (temp != null) {
				int page=index/pageSize;
				if (page==pageNumber){
					nodeList[index-(pageSize*pageNumber)]=temp;
				}else if (page>pageNumber){
					break;
				}
				index++;
				temp = temp.getNextNode();
			}

		}
		return nodeList;
	}
}
