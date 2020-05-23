package GameTools;

import DataStructures.DoubleLinkedList;
import DataStructures.MultiLinkedList;
import DataStructures.SingleLinkedList;

import java.util.Random;


public class WordPuzzleManagement {

    private String[][] puzzleSchema;
    private String[][] playedPuzzleSchema;
    private String[][] puzzleSolution;
    private String[][] scoresTable;

    private int currentUser;
    private User userOne;
    private User userTwo;

    private SingleLinkedList wordsSLL;
    private MultiLinkedList wordsMLL;
    private DoubleLinkedList scoresDBL;

    public WordPuzzleManagement(){

        // Create Two Player Dont forget

        createGameProperties();
    }

    private void createGameProperties(){

        userOne=new User();
        userTwo=new User();

        Reader reader=new Reader();
        scoresDBL = new DoubleLinkedList();
        wordsSLL = new SingleLinkedList();
        wordsMLL = new MultiLinkedList();

        this.puzzleSchema = reader.readSchema();
        this.playedPuzzleSchema = reader.readSchema();
        this.puzzleSolution = reader.readAnswers();
        this.scoresTable = reader.readHighScores();

        String[][] wordList = reader.readWords();

        scoresDBL.fillDoubleList(this.scoresTable);
        scoresDBL.Display();

        wordsSLL.fillList(wordList);
        wordsSLL.display();

        wordsMLL.multiLinkedListFiller(wordsSLL);
        wordsMLL.display();

        this.currentUser=0;
    }

    private WordSchema[] controlAddingRequest(String letter,int x,int y){
        int horResult = horizontalControl(x,y);
        int verResult = verticalControl(x,y);

        WordSchema wordSchema;
        WordSchema[] wordSchemas=new WordSchema[2];
        String[] wordSolution;

        /*
        * Vertical Control
        * */

        if (verResult==1){
            wordSchema=createWordSchema(letter,x,y,0,1);
            wordSolution=this.goWordEnd(wordSchema.getStartX(),wordSchema.getStartY(),0,1 ,puzzleSolution);
            if (wordSolution[0].equalsIgnoreCase(wordSchema.getEngMean())){
                wordSchema.setWordProcess("completedWord");
            }else{
                wordSchema.setWordProcess("falseCompletedWord");
                for (int i = 0; i < wordSchema.getEngMean().length() ; i++) {
                    if (! (wordSolution[0].substring(i,i+1).equalsIgnoreCase(wordSchema.getEngMean().substring(i,i+1))) ){
                        wordSchema.setEngMean(wordSchema.getEngMean().substring(0,i)+'1'+wordSchema.getEngMean().substring(i+1));
                    }
                }
            }
            wordSchemas[1]=wordSchema;
        }else if(verResult == 0){
            wordSchema=createWordSchema(letter,x,y,0,1);
            if (wordsMLL.isAppropriateWord(wordSchema)){
                wordSchema.setWordProcess("trueLetter");
            }else{
                wordSchema.setWordProcess("falseLetter");
            }
            wordSchemas[1]=wordSchema;
        }
        /*
        * End Vertical Control
        * */

        /*
        * Horizontal Control
        * */
        if( horResult == 1){
            wordSchema=createWordSchema(letter,x,y,1,0);
            wordSolution=this.goWordEnd(wordSchema.getStartX(),wordSchema.getStartY(),1,0 ,puzzleSolution);
            if (wordSolution[0].equalsIgnoreCase(wordSchema.getEngMean())){
                wordSchema.setWordProcess("completedWord");
            }else{
                wordSchema.setWordProcess("falseCompletedWord");
                for (int i = 0; i < wordSchema.getEngMean().length() ; i++) {
                    if (! (wordSolution[0].substring(i,i+1).equalsIgnoreCase(wordSchema.getEngMean().substring(i,i+1))) ){
                        wordSchema.setEngMean(wordSchema.getEngMean().substring(0,i)+'1'+wordSchema.getEngMean().substring(i+1));
                    }
                }
            }
            wordSchemas[0]=wordSchema;
        }else if(horResult == 0){
            wordSchema=createWordSchema(letter,x,y,1,0);
            if (wordsMLL.isAppropriateWord(wordSchema)){
                wordSchema.setWordProcess("trueLetter");
            }else{
                wordSchema.setWordProcess("falseLetter");
            }
            wordSchemas[0]=wordSchema;
        }

        /*
        * End Horizontal Control
        * */
        return wordSchemas;
    }

    private WordSchema createWordSchema(String letter,int x,int y,int directionX,int directionY){
        WordSchema wordSchema = new WordSchema();
        int[] wordBeginCoordinate=this.getWordBegin(x,y,directionX,directionY);
        wordSchema.setStartX(wordBeginCoordinate[0]);
        wordSchema.setStartY(wordBeginCoordinate[1]);
        String[] wordEndCoordinate=this.goWordEnd(wordBeginCoordinate[0],wordBeginCoordinate[1],directionX,directionY);
        wordSchema.setEngMean(wordEndCoordinate[0]);
        wordSchema.setEndX(Integer.valueOf(wordEndCoordinate[1]));
        wordSchema.setEndY(Integer.valueOf(wordEndCoordinate[2]));
        wordSchema.setLength(wordEndCoordinate[0].length());
        wordSchema.setLetter(letter);
        wordSchema.setPressedX(x);
        wordSchema.setPressedY(y);
        wordSchema.setDirectionX(directionX);
        wordSchema.setDirectionY(directionY);
        return wordSchema;
    }

    private int horizontalControl(int x,int y){
        int controlX=x;
        int checkedWordSize=1;
        int controlResultRight=1;
        int controlResultLeft=1;

        if (this.playedPuzzleSchema[y][x].equals("0")) return -1;

        if (controlX>0){
            do {
                controlX--;
                if (this.playedPuzzleSchema[y][controlX].equals("0")) {
                    break;
                }else if(this.playedPuzzleSchema[y][controlX].equals("1")){
                    controlResultRight=0;
                }
                checkedWordSize++;
            } while (controlX>0);
        }

        controlX=x;
        if (controlX < this.playedPuzzleSchema.length-1){
            do{
                controlX++;
                if (this.playedPuzzleSchema[y][controlX].equals("0")) {
                    break;
                }else if(this.playedPuzzleSchema[y][controlX].equals("1")){
                    controlResultLeft=0;
                }
                checkedWordSize++;
            } while (controlX<this.playedPuzzleSchema.length-1);
        }

        if (checkedWordSize<3){
            return -1;
        }else if (controlResultLeft==1 && controlResultRight==1){
            return 1;
        }else{
            return 0;
        }
    }

    private int verticalControl(int x,int y){
        int controlY=y;
        int checkedWordSize=1;
        int controlResultRight=1;
        int controlResultLeft=1;

        if (this.playedPuzzleSchema[y][x].equals("0")) return -1;

        if (controlY>0){
            do {
                controlY--;
                if (this.playedPuzzleSchema[controlY][x].equals("0")) {
                    break;
                }else if(this.playedPuzzleSchema[controlY][x].equals("1")){
                    controlResultRight=0;
                }
                checkedWordSize++;
            } while (controlY>0);
        }

        controlY=y;
        if (controlY < this.playedPuzzleSchema.length-1){
            do{
                controlY++;
                if (this.playedPuzzleSchema[controlY][x].equals("0")) {
                    break;
                }else if(this.playedPuzzleSchema[controlY][x].equals("1")){
                    controlResultLeft=0;
                }
                checkedWordSize++;
            } while (controlY < this.playedPuzzleSchema.length-1);
        }

        if (checkedWordSize<3){
            return -1;
        }else if (controlResultLeft==1 && controlResultRight==1){
            return 1;
        }else{
            return 0;
        }
    }

    private int[] getWordBegin(int x,int y,int directionX,int directionY){
        if (x-directionX >= 0 && y-directionY>=0){
            x-=directionX;
            y-=directionY;
            while (x>=0 && y>=0 && !(this.playedPuzzleSchema[y][x].equals("0"))){
                x-=directionX;
                y-=directionY;
            }
            x+=directionX;
            y+=directionY;
        }
        return new int[]{x,y};
    }

    private String[] goWordEnd(int x,int y,int directionX,int directionY){
        String found=this.playedPuzzleSchema[y][x];
        if (x-directionX <= this.playedPuzzleSchema.length && y-directionY<=playedPuzzleSchema.length){
            x+=directionX;
            y+=directionY;
            while (x<= this.playedPuzzleSchema.length - 1 && y<=playedPuzzleSchema.length -1 && !(this.playedPuzzleSchema[y][x].equals("0"))){
                found+=this.playedPuzzleSchema[y][x];
                x+=directionX;
                y+=directionY;
            }
            x-=directionX;
            y-=directionY;
        }
        return new String[]{found, String.valueOf(x),String.valueOf(y)};
    }

    private String[] goWordEnd(int x,int y,int directionX,int directionY,String[][] controlArea){
        String found=controlArea[y][x];
        if (x-directionX <= controlArea.length && y-directionY<=playedPuzzleSchema.length){
            x+=directionX;
            y+=directionY;
            while (x<= controlArea.length - 1 && y<=controlArea.length -1 && !(controlArea[y][x].equals("0"))){
                found+=controlArea[y][x];
                x+=directionX;
                y+=directionY;
            }
            x-=directionX;
            y-=directionY;
        }
        return new String[]{found, String.valueOf(x),String.valueOf(y)};
    }

    public void addUserPoint(int point){
        if (this.currentUser==0){
            this.userOne.setPoint(this.userOne.getPoint()+point);
        }else{
            this.userTwo.setPoint(this.userTwo.getPoint()+point);
        }
    }

    private void changeCurrentUser(){
        if (this.currentUser==1){
            this.currentUser=0;
        }else{
            this.currentUser=1;
        }
    }

    public User[] getUsers(){
        return new User[]{this.userOne,userTwo};
    }

    public AddingRequestAnswer[] addingLetterRequest(String letter,int x,int y){
        Random random=new Random();
        AddingRequestAnswer[] returnResult=new AddingRequestAnswer[2];
        if(this.playedPuzzleSchema[y][x].equals("1")){
            this.playedPuzzleSchema[y][x]=letter;
            WordSchema[] result = this.controlAddingRequest(letter.toLowerCase(),x,y);
            if(result[0] != null){
                AddingRequestAnswer reqResultH=new AddingRequestAnswer();
                if (result[0].getWordProcess().equalsIgnoreCase("trueLetter")){
                    reqResultH.setRequestResult("trueLetter");
                    if (result[1]!=null && !result[1].getWordProcess().equalsIgnoreCase("falseLetter"))
                        this.addUserPoint(1);
                }else if(result[0].getWordProcess().equalsIgnoreCase("falseLetter")){
                    this.playedPuzzleSchema[y][x]="1";
                    reqResultH.setRequestResult("falseLetter");
                    this.changeCurrentUser();
                }else if(result[0].getWordProcess().equalsIgnoreCase("completedWord")){
                    if (wordsSLL.wordIsFound(result[0].getEngMean())){
                        reqResultH.setRequestResult("foundedWord");
                    }else{
                        reqResultH.setRequestResult("askQuestion");
                        wordsSLL.changeIsFound(result[0].getEngMean());
                        reqResultH.setTruthWord(result[0].getEngMean());
                        String trMean=wordsMLL.getTurkishMean(reqResultH.getTruthWord());
                        wordsMLL.deleteWord(result[0].getEngMean());
                        int trueWordPlace=random.nextInt(3);
                        if(trueWordPlace==0){
                            reqResultH.setOptionA(trMean);
                            reqResultH.setQuestionAnswer("a");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultH.getTruthWord());
                            reqResultH.setOptionB(otherOptions[0]);
                            reqResultH.setOptionC(otherOptions[1]);
                        }else if(trueWordPlace==1){
                            reqResultH.setOptionB(trMean);
                            reqResultH.setQuestionAnswer("b");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultH.getTruthWord());
                            reqResultH.setOptionA(otherOptions[0]);
                            reqResultH.setOptionC(otherOptions[1]);
                        }else{
                            reqResultH.setOptionC(trMean);
                            reqResultH.setQuestionAnswer("c");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultH.getTruthWord());
                            reqResultH.setOptionA(otherOptions[1]);
                            reqResultH.setOptionB(otherOptions[0]);
                        }
                    }
                }else if(result[0].getWordProcess().equalsIgnoreCase("falseCompletedWord")){
                    reqResultH.setRequestResult("falseCompletedWord");
                    this.playedPuzzleSchema[y][x]="1";
                    reqResultH.setTruthWord(result[0].getEngMean());
                    int falseLetterSize=0;
                    for (int i = 0; i < result[0].getEngMean().length() ; i++) {
                        if (String.valueOf(result[1].getEngMean().charAt(i)).equalsIgnoreCase("1")) falseLetterSize++;
                        playedPuzzleSchema[result[0].getStartY()][result[0].getStartX()+i]=String.valueOf(result[0].getEngMean().charAt(i));
                    }
                    this.addUserPoint(falseLetterSize*(-1));
                    if (!result[1].getWordProcess().equalsIgnoreCase("falseCompletedWord"))
                        this.changeCurrentUser();
                }
                returnResult[0]=reqResultH;
            }
            if (result[1] != null){
                AddingRequestAnswer reqResultV=new AddingRequestAnswer();
                if (result[1].getWordProcess().equalsIgnoreCase("trueLetter")){
                    reqResultV.setRequestResult("trueLetter");
                    if (result[0]!=null && !result[0].getWordProcess().equalsIgnoreCase("falseLetter"))
                        this.addUserPoint(1);
                }else if(result[1].getWordProcess().equalsIgnoreCase("falseLetter")){
                    this.playedPuzzleSchema[y][x]="1";
                    reqResultV.setRequestResult("falseLetter");
                    this.changeCurrentUser();
                }else if(result[1].getWordProcess().equalsIgnoreCase("completedWord")){
                    if (wordsSLL.wordIsFound(result[1].getEngMean())){
                        reqResultV.setRequestResult("foundedWord");
                    }else{
                        reqResultV.setRequestResult("askQuestion");
                        this.playedPuzzleSchema[y][x]=letter;
                        wordsSLL.changeIsFound(result[1].getEngMean());
                        reqResultV.setTruthWord(result[1].getEngMean());
                        String trMean=wordsMLL.getTurkishMean(reqResultV.getTruthWord());
                        wordsMLL.deleteWord(result[1].getEngMean());
                        int trueWordPlace=random.nextInt(3);
                        if(trueWordPlace==0){
                            reqResultV.setOptionA(trMean);
                            reqResultV.setQuestionAnswer("a");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultV.getTruthWord());
                            reqResultV.setOptionB(otherOptions[0]);
                            reqResultV.setOptionC(otherOptions[1]);
                        }else if(trueWordPlace==1){
                            reqResultV.setOptionB(trMean);
                            reqResultV.setQuestionAnswer("b");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultV.getTruthWord());
                            reqResultV.setOptionA(otherOptions[0]);
                            reqResultV.setOptionC(otherOptions[1]);
                        }else{
                            reqResultV.setOptionC(trMean);
                            reqResultV.setQuestionAnswer("c");
                            String[] otherOptions=wordsSLL.getRandomTwoWord(reqResultV.getTruthWord());
                            reqResultV.setOptionA(otherOptions[0]);
                            reqResultV.setOptionB(otherOptions[1]);
                        }
                    }
                }else if(result[1].getWordProcess().equalsIgnoreCase("falseCompletedWord")){
                    reqResultV.setRequestResult("falseCompletedWord");
                    this.playedPuzzleSchema[y][x]="1";
                    reqResultV.setTruthWord(result[1].getEngMean());
                    int falseLetterSize=0;
                    for (int i = 0; i < result[1].getEngMean().length() ; i++) {
                        if (String.valueOf(result[1].getEngMean().charAt(i)).equalsIgnoreCase("1")) falseLetterSize++;
                        playedPuzzleSchema[result[1].getStartY()+i][result[1].getStartX()]=String.valueOf(result[1].getEngMean().charAt(i));
                    }
                    this.addUserPoint(falseLetterSize*(-1));
                    this.changeCurrentUser();
                }
                returnResult[1]=reqResultV;
            }
            return returnResult;
        }else{
            return returnResult;
        }
    }

    /*
    * Getter and Setters
    * */
    public String[][] getPuzzleSchema() {
        return puzzleSchema;
    }

    public String[][] getPlayedPuzzleSchema() {
        return playedPuzzleSchema;
    }

    public void setPlayedPuzzleSchema(String[][] playedPuzzleSchema) {
        this.playedPuzzleSchema = playedPuzzleSchema;
    }

    public String getLetterAtLocation(int x, int y){
        return this.playedPuzzleSchema[y][x].toUpperCase();
    }

    public int getCurrentUser(){
        return this.currentUser;
    }

    public SingleLinkedList getWordsSLL(){
        return this.wordsSLL;
    }

    public void savePoint(String name,int i) {
        if (i==0){
            scoresDBL.AddByPoint(name,userOne.getPoint());
        }else{
            scoresDBL.AddByPoint(name,userTwo.getPoint());
        }
    }

    public DoubleLinkedList getScoresDBL() {
        return scoresDBL;
    }

    public void setScoresDBL(DoubleLinkedList scoresDBL) {
        this.scoresDBL = scoresDBL;
    }

    /*
    * /Getter and Setters
    * */

}
