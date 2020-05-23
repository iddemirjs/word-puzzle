package GameTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    public String readFile(File file){

        try {
            Scanner scanner=new Scanner(file);
            scanner.useDelimiter("\\Z");
            return scanner.next();
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }
    }

    public String[][] readAnswers(){
        File answersFile=new File("Texts/solution.txt");
        String[] lines = this.readFile(answersFile).split("\n");
        String[][] allAnswers = new String[15][15];
        for (int i = 0; i < lines.length ; i++) {
            allAnswers[i] = lines[i].split("	");
        }
        return allAnswers;
    }

    public String[][] readSchema(){
        File puzzlesFile = new File("Texts/puzzle.txt");
        String puzzle = this.readFile(puzzlesFile);
        String[] lines = puzzle.split("\n");
        String[][] puzzleArr = new String[15][15];
        for (int i = 0; i < lines.length ; i++) {
            puzzleArr[i] = lines[i].split("	");
        }
        return puzzleArr;
    }

    public String[][] readHighScores(){
        File file=new File("Texts/high_score_table.txt");
        String text=this.readFile(file);
        String[] lines = text.split("\n");
        String[][] highScores = new String[lines.length][2];
        for (int i = 0; i < lines.length ; i++) {
            highScores[i] = lines[i].split(";");
        }
        return highScores;
    }

    public String[][] readWords(){
        File file=new File("Texts/word_list.txt");
        String text=this.readFile(file);
        String[] lines = text.split("\n");
        String[][] words = new String[lines.length][2];
        for (int i = 0; i < lines.length ; i++) {
            words[i] = lines[i].split(",");
        }
        return words;
    }



}
