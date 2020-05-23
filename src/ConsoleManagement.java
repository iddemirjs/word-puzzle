import DataStructures.DBLNode;
import DataStructures.Node;
import GameTools.AddingRequestAnswer;
import GameTools.User;
import GameTools.WordPuzzleManagement;
import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ConsoleManagement {

    private enigma.console.Console console;

    // Console Constants
    private int puzzleStartX=2;
    private int puzzleStartY=3;
    private int listStartY=2;
    private int listStartX=27;
    private int listStartSecondColumnX=47;
    //* Console Constants

    // Console Events Requirements
    private int keyPress;   // key pressed?
    private int rKey;       // key ( for press/release )
    private int currentX=puzzleStartX;
    private int currentY=puzzleStartY;
    private int currentPage=0;
    //*

    private WordPuzzleManagement game;
    private boolean isFinish=false;

    public ConsoleManagement(String title){
        System.out.println("melike".compareTo("melake"));
        game = new WordPuzzleManagement();
        this.console= Enigma.getConsole(title,70,35,20);
        this.console.getTextWindow().addKeyListener(this.getMainKeyListener());
        this.buildScreen();
        this.buildPuzzle(game.getPuzzleSchema());
        this.writeWordLists();
        try {
            this.startKeyListener(game.getPuzzleSchema());
        }catch (InterruptedException interruptedException){
            System.out.println(interruptedException);
        }

    }

    public void buildScreen(){
        this.placedPrint("WORD = "+game.getWordsSLL().size()+"  - CURRENT USER: P1 - PLAYER1 = 000 - PLAYER2 = 000",0,0);
        this.placedPrint("  012345678901234        +-WORD-LIST------------------------------+",0,1);
        this.placedPrint("  ###############        |                   |                 |",0,2);
        for (int i = 0; i < 15 ; i++) {
            this.placedPrint(i%10+"#               #"+ i%10 + "      |                   |                 |",0,i+3);
        }
        this.placedPrint("  ###############        |                   |                 |",0,18);
        this.placedPrint("  012345678901234        +-1----------------------------WORD-LIST-+",0,19);
        this.resetQuestionConsole();
    }

    public void startKeyListener(String[][] puzzle) throws InterruptedException {
        this.console.getTextWindow().addKeyListener(this.getMainKeyListener());
        while (true){

            if(this.keyPress==1) {    // if keyboard button pressed
                char rckey = (char) this.rKey;

                if ((this.rKey>=65 && this.rKey<=90) || (this.rKey>=97 && this.rKey<=122)){
                    AddingRequestAnswer[] result = game.addingLetterRequest(String.valueOf(rckey),currentX-puzzleStartX,currentY-puzzleStartY);
                    this.letterPressed(result,String.valueOf(rckey));
                }

                if (!this.isFinish){
                    if(this.rKey==KeyEvent.VK_LEFT && currentX > puzzleStartX){
                        this.puzzleWriter(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                        currentX--;
                        this.puzzleWriterSelected(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                    }
                    if(this.rKey==KeyEvent.VK_RIGHT && currentX < puzzleStartX + 14 ) {
                        this.puzzleWriter(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                        currentX++;
                        this.puzzleWriterSelected(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                    }
                    if (this.rKey == KeyEvent.VK_UP && currentY > puzzleStartY) {
                        this.puzzleWriter(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                        currentY--;
                        this.puzzleWriterSelected(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                    }
                    if (this.rKey == KeyEvent.VK_DOWN && currentY < puzzleStartY + 14 ){
                        this.puzzleWriter(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                        currentY++;
                        this.puzzleWriterSelected(this.game.getLetterAtLocation(currentX-puzzleStartX,currentY-puzzleStartY),currentX,currentY);
                    }

                }

                if (this.rKey == KeyEvent.VK_F1){
                    this.currentPage++;
                    this.currentPage=this.currentPage%3;
                    writeWordLists();
                    placedPrint(String.valueOf(currentPage+1),27,19);
                }
                if (this.rKey == KeyEvent.VK_F2){
                    this.currentPage--;
                    this.currentPage=Math.abs(this.currentPage%3);
                    writeWordLists();
                    placedPrint(String.valueOf(currentPage+1),27,19);
                }
                if (this.rKey == KeyEvent.VK_F3){
                    this.writeScoreDBL();
                }
                this.keyPress=0;
                this.placedPrint("X : " + (((currentX-this.puzzleStartX)<10)?"0"+(currentX-this.puzzleStartX):(currentX-this.puzzleStartX)) + " " +
                        "Y : " + (((currentY-this.puzzleStartY)<10)?"0"+(currentY-this.puzzleStartY):(currentY-this.puzzleStartY)),0,21);
            }
            sleep(20);
        }
    }

    public void letterPressed(AddingRequestAnswer[] result,String pressedLetter){
        if ( (result[0] != null && result[0].getRequestResult().equals("trueLetter")) || (result[1] != null && result[1].getRequestResult().equals("trueLetter"))){
            this.buildPuzzle(game.getPlayedPuzzleSchema());
            this.puzzleWriterSelected(game.getPlayedPuzzleSchema()[currentY-puzzleStartY][currentX-puzzleStartX],currentX,currentY);
        }else if (result[0] != null && result[0].getRequestResult().equals("falseCompletedWord")){
            this.buildPuzzle(game.getPlayedPuzzleSchema());
        }else if(result[1] != null && result[1].getRequestResult().equals("falseCompletedWord")){
            this.buildPuzzle(game.getPlayedPuzzleSchema());
        }else if ((result[0] != null && result[0].getRequestResult().equals("falseLetter")) || result[1] != null && result[1].getRequestResult().equals("falseLetter")){
            this.coloringPrint("False Letter...",0,25,Color.red,Color.WHITE);
        }
        if (result[0] != null && result[0].getRequestResult().equals("askQuestion")){
            this.resetQuestionConsole();
            this.puzzleWriterSelected(pressedLetter,currentX,currentY);
            this.coloringPrint("The word "+result[0].getTruthWord().toUpperCase()+" is placed correctly. " +
                            "PLAYER"+(game.getCurrentUser()+1)+ " gets 10 points.What is the meaning of enhance in Turkish?\nPlease enter your option.\n" +
                            "a) "+result[0].getOptionA().toUpperCase()+"\n" +
                            "b) "+result[0].getOptionB().toUpperCase()+"\n" +
                            "c) "+ result[0].getOptionC().toUpperCase()+"\n"+
                            "Your answer: ",0,22,Color.BLACK,Color.WHITE);

            Scanner scanner=new Scanner(System.in);
            String userAnswer = scanner.nextLine();
            if (userAnswer.equalsIgnoreCase(result[0].getQuestionAnswer())){
                game.addUserPoint(10);
            }
            this.resetQuestionConsole();
        }
        if(result[1] != null && result[1].getRequestResult().equals("askQuestion")){
            this.resetQuestionConsole();
            this.puzzleWriterSelected(pressedLetter,currentX,currentY);
            this.coloringPrint("The word "+result[1].getTruthWord().toUpperCase()+" is placed correctly. " +
                            "PLAYER"+(game.getCurrentUser()+1)+" gets 10 points.What is the meaning of enhance in Turkish?\nPlease enter your option.\n" +
                            "a) "+result[1].getOptionA().toUpperCase()+"\n" +
                            "b) "+result[1].getOptionB().toUpperCase()+"\n" +
                            "c) "+ result[1].getOptionC().toUpperCase()+"\n"+
                            "Your answer: ",0,22,Color.BLACK,Color.WHITE);

            Scanner scanner=new Scanner(System.in);
            String userAnswer = scanner.nextLine();
            if (userAnswer.equalsIgnoreCase(result[1].getQuestionAnswer())){
                game.addUserPoint(10);
            }
            this.resetQuestionConsole();
        }
        User[] users=game.getUsers();
        placedPrint(String.valueOf(game.getCurrentUser()+1),28,0);
        placedPrint("   ",42,0);
        placedPrint("   ",58,0);
        placedPrint(String.valueOf(users[0].getPoint()),42,0);
        placedPrint(String.valueOf(users[1].getPoint()),58,0);
        boolean foundedOne=false;
        for (int i = 0; i < game.getPlayedPuzzleSchema().length ; i++) {
            for (int j = 0; j < game.getPlayedPuzzleSchema()[i].length ; j++) {
                if (game.getPlayedPuzzleSchema()[i][j].equalsIgnoreCase("1")){
                    foundedOne=true;
                    break;
                }
                if (foundedOne) break;
            }
        }
        if (!foundedOne) this.finishGame();
    }

    private void finishGame() {
        this.isFinish=true;
        this.resetQuestionConsole();
        Scanner scanner=new Scanner(System.in);
        this.coloringPrint("Does p1 wants to save his/her score?(Y/N)  ",0,22,Color.BLACK,Color.WHITE);
        String answerP1=scanner.nextLine();
        if (answerP1.equalsIgnoreCase("y")) {
            this.resetQuestionConsole();
            this.coloringPrint("P1 Please enter a name:  ",0,22,Color.BLACK,Color.WHITE);
            String p1Name=scanner.nextLine();
            game.savePoint(p1Name,0);
        }
        this.resetQuestionConsole();

        this.coloringPrint("Does p2 wants to save his/her score?(Y/N)  ",0,22,Color.BLACK,Color.WHITE);
        String answerP2=scanner.nextLine();
        if (answerP2.equalsIgnoreCase("y")) {
            this.resetQuestionConsole();
            this.coloringPrint("P2 Please enter a name:  ",0,22,Color.BLACK,Color.WHITE);
            String p2Name=scanner.nextLine();
            game.savePoint(p2Name,1);
        }
        this.resetQuestionConsole();

        this.writeScoreDBL();
    }

    public void buildPuzzle(String[][] puzzle){
        for (int y = 0; y < puzzle.length ; y++) {
            for (int x = 0; x <puzzle[y].length ; x++) {
                if (puzzle[y][x].equals("1")){
                    this.coloringPrint(" ",this.puzzleStartX+x,this.puzzleStartY+y,Color.WHITE,Color.WHITE);
                }else if (puzzle[y][x].equals("0")){
                    this.coloringPrint(" ",this.puzzleStartX+x,this.puzzleStartY+y,Color.BLACK,Color.BLACK);
                }else{
                    this.coloringPrint(puzzle[y][x].toUpperCase(),this.puzzleStartX+x,this.puzzleStartY+y,Color.BLACK,Color.WHITE);
                }
            }
        }
        this.placedPrint(" ",0,30);
    }

    public void resetConsole(int sizeX,int sizeY,Color foreColor,Color backColor) {
        for (int x = 0; x < sizeX - 1 ; x++) {
            for (int y = 0; y < sizeY ; y++) {
                this.coloringPrint(" ",x,y,foreColor,backColor);
            }
        }
    }

    public void coloringPrint(String m, int x, int y, Color foreColor, Color backColor){
        this.console.getTextWindow().setCursorPosition(x,y);
        this.console.getTextWindow().output(m,new TextAttributes(foreColor,backColor));
    }

    public void placedPrint(String m, int x, int y){
        this.console.getTextWindow().setCursorPosition(x,y);
        this.console.getTextWindow().output(m,new TextAttributes(Color.WHITE,Color.BLACK));
    }

    public void print(String m){
        this.console.getTextWindow().output(m,new TextAttributes(Color.WHITE,Color.BLACK));
    }

    public void puzzleWriter(String s,int x,int y){
        if (s.equals("1")){
            coloringPrint(" ",x,y,Color.WHITE,Color.WHITE);
        }else if(s.equals("0")){
            coloringPrint(" ",x,y,Color.BLACK,Color.BLACK);
        }else{
            coloringPrint(s,x,y,Color.BLACK,Color.WHITE);
        }
    }

    public void puzzleWriterSelected(String s,int x,int y){
        if (s.equals("1")){
            coloringPrint(" ",x,y,Color.LIGHT_GRAY,Color.LIGHT_GRAY);
        }else if(s.equals("0")){
            coloringPrint(" ",x,y,Color.LIGHT_GRAY,Color.LIGHT_GRAY);
        }else{
            coloringPrint(s.toUpperCase(),x,y,Color.BLACK,Color.LIGHT_GRAY);
        }
    }

    public void resetQuestionConsole(){
        for (int i = 0; i < 7 ; i++) {
            for (int j = 0; j < 70 ; j++) {
                this.coloringPrint(" ",0+j,22+i,Color.red,Color.WHITE);
            }
        }
    }

    private KeyListener getMainKeyListener(){
        KeyListener kl=new KeyListener() {
         public void keyTyped(KeyEvent e) {}
         public void keyPressed(KeyEvent e) {
            if(keyPress==0) {
               keyPress=1;
               rKey=e.getKeyCode();
            }
         }
         public void keyReleased(KeyEvent e) {}
      };
        return kl;
    }

    private void writeWordLists(){
        this.resetWordLists();
        Node[] lists=game.getWordsSLL().getPage(currentPage,34);
        for (int i = 0; i < lists.length ; i++) {
            if (lists[i] == null) break;
            String found=(lists[i].isFound())?"X":" ";
            if (i<17) placedPrint("["+found+"]"+lists[i].getEngWord(),listStartX,listStartY+i);
            else placedPrint("["+found+"]"+lists[i].getEngWord(),listStartSecondColumnX,(i%17)+listStartY);
        }
    }

    private void resetWordLists(){
        for (int i = 0; i < 17 ; i++) {
            placedPrint("|                   |                    |",listStartX-2,listStartY+i);
        }
    }

    private void writeScoreDBL(){
        this.resetWordLists();
        DBLNode temp=game.getScoresDBL().getHead();
        int counter=0;
        while (temp!=null){
            placedPrint(temp.getName() +" - "+ temp.getPoint(),listStartX,listStartY+counter);
            temp=temp.getNext();
            counter++;
        }
    }
}
