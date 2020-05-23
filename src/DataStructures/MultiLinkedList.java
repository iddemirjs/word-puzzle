package DataStructures;


import GameTools.WordSchema;

public class MultiLinkedList {
	private RowNode head;

	public MultiLinkedList() {
		this.head = null;
	}

	public void addRow(String dataToAdd) {
		//System.out.println(dataToAdd);
		RowNode newNode = new RowNode(dataToAdd.toUpperCase().toUpperCase());
		if (head == null) {
			head = newNode;
		} else {
			RowNode temp = head;
			RowNode keepAfter;
			if (head.getRowNo().compareTo(dataToAdd.toUpperCase())>0){
				newNode.setDown(head);
				head=newNode;
			}
			while (temp != null) {

				if (temp.getDown()==null){
					temp.setDown(newNode);
					return;
				}else{
					if (temp.getDown().getRowNo().compareTo(dataToAdd.toUpperCase())>0){
						keepAfter=temp.getDown();
						temp.setDown(newNode);
						temp.getDown().setDown(keepAfter);
						return;
					}
				}
				temp=temp.getDown();
			}
		}
	}

	public void addElement(String row, String engWord,String trWord) {
		if (head == null) {
			System.out.println("Add a row before Node");
		} else {
			RowNode temp = head;
			while (temp != null) {
				if (row.equals(temp.getRowNo())) {
					Node temp2 = temp.getRigth();
					if (temp2 == null) {
						Node newNode = new Node(engWord,trWord);
						temp.setRigth(newNode);
					} else {
						while (temp2.getNextNode() != null) {
							temp2 = temp2.getNextNode();
						}
						Node newNode = new Node(engWord,trWord);
						temp2.setNextNode(newNode);
					}
				}
				temp = temp.getDown();
			}
		}
	}

	public void multiLinkedListFiller(SingleLinkedList sll) {
        Node temp1 = sll.getHead();

        while(temp1!=null)
        {
			if (!hasRow(temp1.getEngWord().substring(0,1))){
				this.addRow(temp1.getEngWord().substring(0,1));
			}
			this.addElement(temp1.getEngWord().substring(0,1).toUpperCase(),temp1.getEngWord(),temp1.getTrWord());
			temp1 = temp1.getNextNode();

        }
    }

	public void display() {
		if (head == null) {
			System.out.println("Linked list is empty");
		} else {
			RowNode temp = head;
			while (temp != null) {
				System.out.print(temp.getRowNo() + " --> ");
				Node temp2 = temp.getRigth();
				while (temp2 != null) {
					System.out.print(temp2.getEngWord() + "-" + temp2.getTrWord()+", " );
					temp2 = temp2.getNextNode();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}

	public int sizeRow() {
		int count = 0;
		if (head == null) {
			System.out.println("Linked list is empty");
		} else {
			RowNode temp = head;
			while (temp != null) {
				count++;
				temp = temp.getDown();
			}
		}
		return count;
	}

	public RowNode getHead()
	{
		return this.head;
	}

	public boolean isAppropriateWord(WordSchema ws){
		if (head == null) {
			System.out.println("Linked list is empty");
		} else {
			RowNode tempRowNode = head;
			while (tempRowNode != null) {
				if (tempRowNode.getRowNo().equalsIgnoreCase(ws.getEngMean().substring(0,1)) || ws.getEngMean().substring(0,1).equalsIgnoreCase("1")){
					Node tempNode = tempRowNode.getRigth();
					while (tempNode != null) {
						if (compareWords(ws,tempNode.getEngWord())) return true;
						tempNode = tempNode.getNextNode();
					}
				}
				tempRowNode = tempRowNode.getDown();

			}
		}
		return false;
	}

	public boolean compareWords(WordSchema puzzleWord,String testWord){
		if (puzzleWord.getEngMean().length()!=testWord.length()) return false;

		int letterIndex=0;
		if (puzzleWord.getDirectionX()==1){
			letterIndex=puzzleWord.getPressedX()-puzzleWord.getStartX();
		}else{
			letterIndex=puzzleWord.getPressedY()-puzzleWord.getStartY();
		}
		if (puzzleWord.getLetter().equalsIgnoreCase(String.valueOf(testWord.charAt(letterIndex)))){
			return true;
		}else{
			return false;
		}
		/*
		for (int i = 0; i < puzzleWord.getEngMean().length() ; i++) {
			if (!(puzzleWord.getEngMean().substring(i,i+1).equalsIgnoreCase("1"))){
				if (!( puzzleWord.getEngMean().substring(i,i+1).equalsIgnoreCase(testWord.substring(i,i+1)) )){
					return false;
				}
			}
		}
		return true;
		*/
	}

	public boolean hasRow(String firstLetter) {
		if (head == null) {
			return false;
		} else {
			RowNode temp = head;
			while (temp != null) {
				if (temp.getRowNo().equalsIgnoreCase(firstLetter)){
					return true;
				}
				temp = temp.getDown();
			}
		}
		return false;
	}

	public String getTurkishMean(String word){
		RowNode tempRow=this.head;
		while(tempRow!=null){
			if(tempRow.getRowNo().equalsIgnoreCase(String.valueOf(word.charAt(0)))){
				Node tempNode=tempRow.getRigth();
				while(tempNode!=null){
					if (tempNode.getEngWord().equalsIgnoreCase(word)){
						return tempNode.getTrWord();
					}
					tempNode=tempNode.getNextNode();
				}
			}
			tempRow=tempRow.getDown();
		}

		return "Not Found";
	}

	public void deleteWord(String word){
		RowNode tempRowNode= this.getHead();
		while (tempRowNode != null){
			if (tempRowNode.getRowNo().equalsIgnoreCase(String.valueOf(word.charAt(0)))){
				Node tempNode = tempRowNode.getRigth();
				if (tempNode.getEngWord().equalsIgnoreCase(word)){
					if (tempNode.getNextNode()==null){
						tempRowNode.setRigth(null);
						this.removeRow(String.valueOf(word.charAt(0)));
						return;
					}else{
						tempRowNode.setRigth(tempNode.getNextNode());
						return;
					}
				}
				while (tempNode != null){
					if (tempNode.getNextNode().getEngWord().equalsIgnoreCase(word)){
						if (tempNode.getNextNode().getNextNode() == null){
							tempNode.setNextNode(null);
							return;
						}else{
							tempNode.setNextNode(tempNode.getNextNode().getNextNode());
							return;
						}
					}
					tempNode=tempNode.getNextNode();
				}
				tempRowNode=tempRowNode.getDown();
			}
			tempRowNode = tempRowNode.getDown();
		}
	}

	private void removeRow(String valueOf) {
		RowNode tempRowNode=this.head;
		if (tempRowNode.getRowNo().equalsIgnoreCase(valueOf)){
			if (tempRowNode.getDown() == null) {
				head=null;
				return;
			}else{
				tempRowNode.setDown(tempRowNode.getDown());
				return;
			}
		}

		while(tempRowNode != null){
			if (tempRowNode.getDown().getRowNo().equalsIgnoreCase(valueOf)){
				if (tempRowNode.getDown().getDown()==null){
					tempRowNode.setDown(null);
					return;
				}else{
					tempRowNode.setDown(tempRowNode.getDown().getDown());
					return;
				}
			}
			tempRowNode = tempRowNode.getDown();
		}
	}
}
