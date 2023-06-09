package SpeedMath;

import java.io.*;
import java.util.Scanner;

class SpeedMathMaps implements Serializable {
    private static final long serialVersionUID = -500485894589460908L;
    protected String mapName;
    protected int totalNotes;
    protected int qNotes;
    protected int CS;   // [0]=inner circle, [1]=outer circle
    protected float AR;
    protected String quiz[];
    protected float ans[];
    private String zipData[];
    protected int notePositions[];  // [0]=x, [1]=y

    public SpeedMathMaps() {
        ans = new float[5];
        quiz = new String[5];
        zipData = new String[6];
    }

    protected SpeedMathMaps mapEditor(int mode, Scanner scn) throws IOException, ClassNotFoundException {
        //Scanner scn = new Scanner(System.in);
        switch (mode) {
        case 1:
            // readMapInfo();
            int numOfQuiz;
            System.out.print("Export file name: ");
            this.mapName = scn.nextLine();
    
            System.out.print("Total notes: ");
            totalNotes = Integer.parseInt(scn.nextLine());

            System.out.print("Number of quiz: ");
            numOfQuiz = Integer.parseInt(scn.nextLine());
            
            System.out.print("Circle Size: ");
            CS = Integer.parseInt(scn.nextLine());
            
            System.out.print("Approching Rate: ");
            AR = Float.parseFloat(scn.nextLine());

            this.ans = new float[numOfQuiz];
            this.quiz = new String[numOfQuiz];

            for(int i = 0; i < numOfQuiz; i++) {
                System.out.println("Quiz" + i);
                this.quiz[i] = new String(scn.nextLine());
                this.ans[i] = Float.parseFloat(scn.nextLine());
            }
            saveMap(this, this.mapName);
            // return this;
            break;
        case 2:
            System.out.print("Open file name: ");
            String str = scn.nextLine(); 
            // return loadMap(this, str);   // method1
            //scn.close();
            return loadMap(str);
        default:
            break;
        }
       // scn.close();
        return null;
    }

    protected void setQNotes(int number) {
        qNotes = number;
        quiz = new String[number];
        ans = new float[number];
    }

    protected void setQnA(String str, int index, int type) {
        if (type == 1) {
            quiz[index] = str;
            System.out.println("Get question from user");
        }
        if (type == 2) {
            ans[index] = Float.parseFloat(str);
            System.out.println("Get answer from user");
        }
    }

    protected void saveMap(SpeedMathMaps currentEdit, String exportFile) throws IOException {
        ObjectOutputStream saveFile;
        // exportFile = new String("./SpeedMath/Maps/" + exportFile);
        saveFile = new ObjectOutputStream(new FileOutputStream(exportFile));
        saveFile.writeObject(this);
        saveFile.flush(); saveFile.close();
        System.out.println("File saved at: " + exportFile);
    }

    /* protected static SpeedMathMaps loadMap(SpeedMathMaps imported, String importFile) throws IOException, ClassNotFoundException {
        ObjectInputStream readFile;
        importFile = new String("./SpeedMath/Maps/" + importFile);
        readFile = new ObjectInputStream(new FileInputStream(importFile));
        imported = (SpeedMathMaps) readFile.readObject();
        readFile.close();
        return(imported);
    } */
    protected SpeedMathMaps loadMap(String importFile) throws IOException, ClassNotFoundException {
        ObjectInputStream readFile;
        // importFile = new String("./SpeedMath/Maps/" + importFile);
        readFile = new ObjectInputStream(new FileInputStream(importFile));
        SpeedMathMaps imported = (SpeedMathMaps) readFile.readObject();
        readFile.close();
        return imported;
    }

    protected void info() {
        System.out.println("Name: " + this.mapName);
        System.out.println("Notes: " + this.quiz.length);
        System.out.println("Circle Size: " + this.CS);
        System.out.println("Approching Rate: " + this.AR);
        System.out.println("Number of quiz: " + this.quiz.length);
        for(int i = 0; i < this.quiz.length; i++) {
            System.out.println("No." + i + ": " + this.quiz[i] + " = " + this.ans[i]);
        }
    }

    protected String getMapAttribute(int index) {
        zipData[0] = this.mapName;
        zipData[1] = String.valueOf(totalNotes);
        zipData[2] = String.valueOf(qNotes);
        zipData[3] = String.valueOf(CS);
        zipData[4] = String.valueOf(AR);
        return zipData[index];
    }
}
