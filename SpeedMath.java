/*
* Filename : SpeedMath.java
* Function : Main class and main function
* Author   : 許書和 41043152
* DateTime : First commits on Jun 5, 2023
* Description : Java final term project
* Additional file: 41043152_Log.log
                   41043152_Ref.MD
*/

package SpeedMath;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.util.concurrent.atomic.AtomicBoolean;



public class SpeedMath {
    public static void main(String[] args) {
        String mapFile1 = "Map1.txt";
        SpeedMathMaps map1 = new SpeedMathMaps();
        SpeedMathGraphics window = new SpeedMathGraphics(map1);
    }
}

class SpeedMathGraphics extends Frame 
implements ActionListener, ChangeListener, MouseListener, MouseMotionListener, KeyListener {
    private int qPosX[] = new int[3];    // = {200, 900, 1500};
    private int qPosY;
    private int innerCircleSize;
    private int outerCircleSize;
    private int innerCirclePosX, innerCirclePosY;
    private int outerCirclePosX, outerCirclePosY;
    private int innerQCirclePosX[], innerQCirclePosY[];
    private int outerQCirclePosX[], outerQCirclePosY[];
    private int circlePosX;
    private int circlePosY;
    private int key1Count;
    private int key2Count;
    private int mouse1Count;
    private int mouse2Count;
    private int fullScore;
    private int earnScore;
    private int currentNote;
    private int currentQ;
    private int ansNoteIndex;
    private int missTimer;
    private int hitFadeTimer;
    private float wrongAns[];
    private float approachingSpeed;
    private boolean noteDone;
    private boolean canAddScore;
    private boolean isQNote;
    private boolean isHit;
    private boolean isMiss;
    private boolean canPaintGame;
    private boolean exit;
    SpeedMathMaps map;
    GameDriver driver;

    CardLayout mainLayout;
    GridLayout rightside;
    Panel mainPage;
    Panel mainPage_Panel;
    Panel editPage;
    Panel editPage_Panel;
    Panel gamePage;
    Panel gamePaintArea;
    Panel gameConsolePanel;
    Panel rightPanel;
    Panel leftPanel;
    Panel buttomPanel;
    JButton clickCounter[];
    JButton mainPageButton[];
    JButton edit_Return;
    JButton edit_LoadMap;
    JButton edit_SaveMap;
    JButton play_Return;
    JButton startGame;
    TextField mapEditField[];
    TextField gameConsoleField;
    Label mapEditLabel[];
    Label gameProgress;
    Label gameConsLabel;
    Label quizDisplay;
    JSlider qNoteSlider;
    Timer ARtimer;
    AtomicBoolean isTimerDone;
    FontMetrics metrics;
    Font editFont = new Font("Arial", Font.PLAIN, 25);
    Font quizFont = new Font("Arial", Font.PLAIN, 75);
    Point hitPosition;

    public SpeedMathGraphics(SpeedMathMaps obj) {
        super("SpeedMath - by 41043152");
        setSize(1920, 1080);
        setMinimumSize(new Dimension(1920, 1080));
        setResizable(false);
        setLayout(new CardLayout());
        setBackground(Color.LIGHT_GRAY);
        this.map = obj;
        
        String mainPageButtonName[] = {"Play", "Editors", "Exit"};
        String mapAttributeString[] = {"Map name", "Total notes", "Quiz notes", "Circle size", "Approaching Rate", "Question", "Anser", "qNote slider"};
        key1Count=0; key2Count=0; mouse1Count=0; mouse2Count=0;

        // main page initialization
        mainPage = new Panel(new BorderLayout(200, 100));
        mainPage.setName("Main Page");
        mainPage_Panel = new Panel();
        mainPage_Panel.setPreferredSize(new Dimension(300, this.getHeight()));
        mainPage_Panel.setLayout(new GridLayout(3, 1, 150, 50));
        mainPageButton = new JButton[3];
        for (int i = 0; i < mainPageButton.length; i++) {
            mainPageButton[i] = new JButton(mainPageButtonName[i]);
            mainPageButton[i].setName(new String(mainPageButtonName + " Button"));
            mainPageButton[i].addActionListener(this);
            mainPageButton[i].setPreferredSize(new Dimension(250, 80));
            mainPageButton[i].setFont(new Font("Arial", Font.PLAIN, 40));
            mainPage_Panel.add(mainPageButton[i]);
        }
        mainPage.add(new Panel(), BorderLayout.WEST);
        mainPage.add(new Panel(), BorderLayout.EAST);
        mainPage.add(new Panel(), BorderLayout.NORTH);
        mainPage.add(new Panel(), BorderLayout.SOUTH);
        mainPage.add(mainPage_Panel, BorderLayout.CENTER);
        
        // edit page initalization
        editPage = new Panel(new BorderLayout(20, 50));
        editPage.setName("Edit Page");
        editPage_Panel = new Panel(new GridLayout(8, 1, 0, 25));
        mapEditLabel = new Label[8];
        mapEditField = new TextField[7];
        Panel labelPanel = new Panel(new GridLayout(8, 1, 0, 50));
        Panel editPageEastPanel = new Panel(new GridLayout(3,1, 0, 100));
        for (int i = 0; i < 8; i++) {
            mapEditLabel[i] = new Label(mapAttributeString[i]);
            mapEditLabel[i].setForeground(Color.RED);
            mapEditLabel[i].setFont(editFont);
            mapEditLabel[i].setAlignment(Label.LEFT);
            labelPanel.add(mapEditLabel[i]);
            if (i >= 7) break;
            mapEditField[i] = new TextField();
            mapEditField[i].setFont(editFont);
            mapEditField[i].addActionListener(this);
            editPage_Panel.add(mapEditField[i]);
        }
        mapEditField[2].setEditable(false);
        qNoteSlider = new JSlider(1, 30, 1);
        qNoteSlider.setPaintTrack(true);
        qNoteSlider.setPaintTicks(true);
        qNoteSlider.setPaintLabels(true);
        qNoteSlider.setMajorTickSpacing(2);
        qNoteSlider.setMinorTickSpacing(1);
        qNoteSlider.addChangeListener(this);
        editPage_Panel.add(qNoteSlider);

        edit_Return = new JButton("<html>Return<br>Menu</html>");
        edit_Return.setName("From Editor Return");
        edit_Return.setFont(editFont);
        edit_Return.addActionListener(this);

        edit_LoadMap = new JButton("<html>Load<br>Map</html>");
        edit_LoadMap.setName("Load Map");
        edit_LoadMap.setFont(editFont);
        edit_LoadMap.addActionListener(this);
        
        edit_SaveMap = new JButton("<html>Save<br>Map</html>");
        edit_SaveMap.setName("Save Map");
        edit_SaveMap.setFont(editFont);
        edit_SaveMap.addActionListener(this);

        editPageEastPanel.add(edit_LoadMap);
        editPageEastPanel.add(edit_SaveMap);
        editPageEastPanel.add(edit_Return);
        editPageEastPanel.setPreferredSize(new Dimension(100, getHeight()));
        labelPanel.setPreferredSize(new Dimension(220, getHeight()));
        editPage_Panel.setPreferredSize(new Dimension(getWidth()-labelPanel.getWidth()-editPageEastPanel.getWidth()-20, getHeight()));

        editPage.add(editPage_Panel, BorderLayout.CENTER);
        editPage.add(labelPanel, BorderLayout.WEST);
        editPage.add(editPageEastPanel, BorderLayout.EAST);
        editPage.add(new Panel(), BorderLayout.NORTH);
        editPage.add(new Panel(), BorderLayout.SOUTH);

        // game page initialization
        gamePage = new Panel(new BorderLayout());
        gamePage.setName("Game Page");
        gamePage.addMouseMotionListener(this);
        gamePage.addMouseListener(this);
        
        rightside = new GridLayout(6, 1, 0, 40);
        rightPanel = new Panel();
        rightPanel.setPreferredSize(new Dimension(150, this.getHeight()));
        rightPanel.setLayout(rightside);

        Panel leftPanel = new Panel();
        Panel northPanel = new Panel();
        leftPanel.setPreferredSize(new Dimension(100, getHeight()));
        northPanel.setPreferredSize(new Dimension(getWidth(), 100));
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        clickCounter = new JButton[4];
        gameProgress = new Label("0/0", Label.CENTER);
        gameProgress.setPreferredSize(new Dimension(200, 40));
        gameProgress.setFont(new Font("Arial", Font.PLAIN, 40));
        quizDisplay = new Label("Quiz will display here.", Label.CENTER);
        quizDisplay.setPreferredSize(new Dimension(getWidth(), 80));
        quizDisplay.setFont(quizFont);
        northPanel.add(quizDisplay);

        
        gameConsolePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        gameConsolePanel.addMouseMotionListener(this);
        gameConsLabel = new Label();
        gameConsLabel.setFont(editFont);
        gameConsLabel.setText("Type map name to play.");
        gameConsLabel.setPreferredSize(new Dimension(200, 50));
        gameConsoleField = new TextField();
        gameConsoleField.setName("gameConsole");
        gameConsoleField.setFont(editFont);
        gameConsoleField.setPreferredSize(new Dimension(this.getWidth()-440, 50));
        gameConsoleField.addActionListener(this);
        play_Return = new JButton("Menu");
        play_Return.setFont(editFont);
        play_Return.setName("From Game Return");
        play_Return.setPreferredSize(new Dimension(100, 50));
        play_Return.addActionListener(this);
        startGame = new JButton("Play");
        startGame.setFont(editFont);
        startGame.setName("Start Game");
        startGame.setPreferredSize(new Dimension(100, 50));
        startGame.addActionListener(this);
        rightPanel.add(gameProgress);

        gameConsolePanel.add(gameConsLabel);
        gameConsolePanel.add(gameConsoleField);
        gameConsolePanel.add(play_Return);
        gameConsolePanel.add(startGame);
        
        for(int i = 0; i < clickCounter.length; i++) {
            clickCounter[i] = new JButton("0");
            clickCounter[i].addActionListener(this);
            clickCounter[i].setPreferredSize(new Dimension(140, 100));
            clickCounter[i].setFont(new Font("Arial", Font.PLAIN, 40));
            clickCounter[i].setEnabled(false);
            rightPanel.add(clickCounter[i]);
        }
        rightPanel.add(new Panel());
        gamePaintArea = new Panel();
        gamePaintArea.addMouseListener(this);
        gamePaintArea.addMouseMotionListener(this);
        gamePaintArea.addKeyListener(this);

        gamePage.add(gamePaintArea, BorderLayout.CENTER);
        gamePage.add(gameConsolePanel, BorderLayout.SOUTH);
        gamePage.add(northPanel, BorderLayout.NORTH);
        gamePage.add(rightPanel, BorderLayout.EAST);
        gamePage.add(leftPanel, BorderLayout.WEST);
        
        // set cardlayout
        mainLayout = new CardLayout();
        setLayout(mainLayout);
        add(mainPage, "Main Page");
        add(gamePage, "Game Page");
        add(editPage, "Edit Page");
        mainLayout.show(this, mainPage.getName());

        setFocusable(false);
        addWindowListener(new windowListener());
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(new resizeListener());
        addKeyListener(this);
        setVisible(true);
    }

    public void setMap(TextField textField) {
        if (textField == mapEditField[0]) {
            this.map.mapName = textField.getText();
            mapEditLabel[0].setForeground(Color.GREEN);
            mapEditLabel[0].setText("Map name (V");
        } else if (textField == mapEditField[1]) {
            int getInt = Integer.parseInt(textField.getText());
            int q;
            if (getInt < 10) {
                JOptionPane.showMessageDialog(this, "Must greater than 10 notes");
                textField.setText("");
                return;
            }
            this.map.totalNotes = getInt;
            q = getInt/5;
            this.map.setQNotes(q);
            mapEditField[2].setText(String.valueOf(q));
            qNoteSlider.setMaximum(q);
            mapEditLabel[1].setForeground(Color.GREEN);
            mapEditLabel[1].setText("Total notes (V");
            mapEditLabel[2].setForeground(Color.GREEN);
            mapEditLabel[2].setText("Quiz notes (V");
        } else if (textField == mapEditField[3]) {
            this.map.CS = Integer.parseInt(textField.getText());
            mapEditLabel[3].setForeground(Color.GREEN);
            mapEditLabel[3].setText("Circle size (V");
        } else if (textField == mapEditField[4]) {
            this.map.AR = Float.parseFloat(textField.getText());
            mapEditLabel[4].setForeground(Color.GREEN);
            mapEditLabel[4].setText("Approching Rate (V");
        } else if (textField == mapEditField[5]) {
            this.map.setQnA(textField.getText(), qNoteSlider.getValue(), 1);
            mapEditLabel[5].setForeground(Color.GREEN);
            mapEditLabel[5].setText("Question (V");
        } else if (textField == mapEditField[6]) {
            this.map.setQnA(textField.getText(), qNoteSlider.getValue(), 2);
            mapEditLabel[6].setForeground(Color.GREEN);
            mapEditLabel[6].setText("Answer (V");
        }
    }

    public void mapInfo() {
        System.out.println("Map info");
        this.map.info();
        qNoteSlider.setMaximum(map.qNotes);

        for (int i = 0; i < 5; i++) {
            mapEditField[i].setText(map.getMapAttribute(i));
        }

        for (int j = 0; j < map.qNotes; j++) {
            mapEditField[5].setText(map.quiz[j]);
            mapEditField[6].setText(String.valueOf(map.ans[j]));
        }
    }

    private Panel getTopCard() {
        Panel panel = null;
        for (Component component : this.getComponents()) {
            if (component.isVisible()) {
                panel = (Panel) component;
                // System.out.println("Top card is >" + panel.getName());
            }
        }
        return panel;
    }

    class GameDriver extends Thread {
        private int oCs;
        private int qIndex[];
        private int xLeftOffset;
        private int xRightOffset;
        private int yUpOffset;
        private int yDownOffset;

        public GameDriver() {
            super();
            setDaemon(false);
            innerCircleSize = map.CS * 8;
            outerCircleSize = innerCircleSize * 4;

            xLeftOffset = gamePaintArea.getX() + outerCircleSize / 2;
            xRightOffset = gamePaintArea.getWidth() - (outerCircleSize / 2);
            yUpOffset = gamePaintArea.getX() + outerCircleSize / 2;
            yDownOffset = gamePaintArea.getHeight() - (outerCircleSize / 2);

            qPosX[0] = xLeftOffset;
            qPosX[1] = gamePaintArea.getWidth()/2;
            qPosX[2] = xRightOffset;
            qPosY = yDownOffset;
    
            innerQCirclePosX = new int [3];
            innerQCirclePosY = new int [3];
            outerQCirclePosX = new int [3];
            outerQCirclePosY = new int [3];
            wrongAns = new float[3];
            approachingSpeed = map.AR * 3;

            currentQ = 1;
            this.oCs = outerCircleSize;
            this.qIndex = getQ();
            noteDone = false;
            canPaintGame = true;
            currentNote = 1;
        }
        public void run() {
            System.out.println("driver.start();"); //

            while (currentNote <= map.totalNotes) {
                if (ARtimer.isRunning() && !noteDone) {
                    continue;
                }

                if (exit) {
                    break;
                }
                
                if (!ARtimer.isRunning()) {
                    circlePosX = (int) (Math.random() * (xRightOffset - xLeftOffset + 1)) + xLeftOffset;
                    circlePosY = (int) (Math.random() * (yDownOffset - yUpOffset + 1)) + yUpOffset;
                    if (noteDone){
                        currentNote++;
                        System.out.println("Note " + currentNote + " at " + circlePosX + ", " + circlePosY);
                    }
                    if (isQNote && noteDone && currentNote < this.qIndex[qIndex.length-1]) {
                        currentQ++;
                    }

                    
                    outerCircleSize = this.oCs;
                    canAddScore = true;
                    noteDone = false;
                    isQNote = false;
                    isHit = false;
                    
                    
                    quizDisplay.setText(map.quiz[currentQ]);
                    if (currentNote == this.qIndex[currentQ]) {
                        isQNote = true;
                        ansNoteIndex = (int) (Math.random() * 3);
                        genWrongAnswer();
                        // currentQ++;
                    }
                    
                    ARtimer.start();
                    // currentNote++;
                }
            }
            ARtimer.stop();
            canPaintGame = false;
            if (exit){
                resetGame();
            } else {
                JOptionPane.showMessageDialog(gamePage, "Game finished", "Map done", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            }
        }

        private void resetGame() {
            for(int i = 0; i < clickCounter.length; i++) {
                clickCounter[i].setText("0");
            }
            mouse1Count = 0;
            mouse2Count = 0;
            key1Count = 0;
            key2Count = 0;
            earnScore = 0;
            gameProgress.setText("0/0");
            quizDisplay.setText("Quiz will display here");
            gameConsoleField.setText("");
        }

        private void genWrongAnswer() {
            for (int i = 0; i < 3; i++) {
                wrongAns[i] = (int) map.ans[currentQ] + (int) (Math.random() * (30-2+1)) + 1;
            }
        }

    }
    
    private void gameLoop() {
        ARtimer = new Timer(100, this::paintCircleAction);
        ARtimer.setInitialDelay(0);
        driver.start();
    }
    
    public void paint(Graphics game) {
        if (!canPaintGame){
            System.out.println("Can't paint");
            return;
        }

        if (noteDone || isHit) {
            System.out.println("Note done, clearing");
            game.clearRect(0,0,getWidth(), getHeight());
            return;
        }
        
        if (isMiss) {
            System.out.println("**************Painting miss affect area");
            game.setColor(Color.RED);
            game.fillRect(0, 0, getWidth(), getHeight());
        }

        if (!isQNote) {
            innerCirclePosX = circlePosX-innerCircleSize/2;
            innerCirclePosY = circlePosY-innerCircleSize/2;
            outerCirclePosX = circlePosX-outerCircleSize/2;
            outerCirclePosY = circlePosY-outerCircleSize/2;
            
            // outer circle
            game.setColor(new Color(197, 57, 67, 100));
            game.fillOval(outerCirclePosX, outerCirclePosY, outerCircleSize, outerCircleSize);
            // inner circle
            game.setColor(Color.CYAN);
            game.fillOval(innerCirclePosX, innerCirclePosY, innerCircleSize, innerCircleSize);
            // draw the string in the center of x axis
        } else {            
            // set 3 answer circles
            for (int i = 0; i < 3; i++) {
                innerQCirclePosX[i] = qPosX[i]-innerCircleSize/2;
                innerQCirclePosY[i] = qPosY-innerCircleSize/2;
                outerQCirclePosX[i] = qPosX[i]-outerCircleSize/2;
                outerQCirclePosY[i] = qPosY-outerCircleSize/2;
            }
            // outer Qcircle
            game.setColor(Color.GRAY);
            game.fillOval(outerQCirclePosX[0], outerQCirclePosY[0], outerCircleSize, outerCircleSize);
            game.fillOval(outerQCirclePosX[1], outerQCirclePosY[1], outerCircleSize, outerCircleSize);
            game.fillOval(outerQCirclePosX[2], outerQCirclePosY[2], outerCircleSize, outerCircleSize);
            // inner Qcircle
            game.setColor(Color.YELLOW);
            game.fillOval(innerQCirclePosX[0], innerQCirclePosY[0], innerCircleSize, innerCircleSize);
            game.fillOval(innerQCirclePosX[1], innerQCirclePosY[1], innerCircleSize, innerCircleSize);
            game.fillOval(innerQCirclePosX[2], innerQCirclePosY[2], innerCircleSize, innerCircleSize);

            paintAns(game);
        }
    }

    private void paintHit(Graphics g, Timer t, int x, int y) {
        // currently not working
        while (t.isRunning()) {
            // System.out.println("Painting hit point " + x + " " + y);
            g.setColor(Color.RED);
            g.fillOval(x-5, y-5, 10, 10);
        }
        g.setColor(getBackground());
        g.fillOval(x-5, y-5, 10, 10);
    }
    
    private void paintAns(Graphics game) {
        // System.out.println("Painting Answer>>>>>>>>>>>>>>>[" + currentQ + "]" + ">>>>" + map.ans[currentQ]);
        metrics = game.getFontMetrics(quizFont);
        game.setColor(Color.MAGENTA);
        game.setFont(quizFont);

        for (int i = 0; i < 3; i++) {
            int x = qPosX[i] - (metrics.stringWidth(map.quiz[currentQ]) / 2);
            if (i == ansNoteIndex) {
                game.drawString(String.valueOf(map.ans[currentQ]), x, qPosY - innerCircleSize*4);
            }else{
                game.drawString(String.valueOf(wrongAns[i]), x, qPosY - innerCircleSize*4);
            }
        }
    }

    private String pathChooser(int mode) {
        String filename = null;
        JFileChooser fChooser = new JFileChooser();
        fChooser.setCurrentDirectory(new java.io.File("./SpeedMath/Maps/"));   // ./SpeedMath/Maps/
        if (mode == 1) {
            fChooser.setDialogTitle("Map Load Path");
            fChooser.setFileFilter(new FileNameExtensionFilter("Speed Math beatmap file", "SPMbeatmap"));
            System.out.println("getCurrentDirectory(): " + fChooser.getCurrentDirectory());
            if (fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                System.out.println("getSelectedFile() : " + fChooser.getSelectedFile());
            } else {
                return null;
            }
            filename = new String(fChooser.getSelectedFile().toString());
        }
        if (mode == 2) {
            fChooser.setDialogTitle("Map Save Path");
            fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fChooser.setAcceptAllFileFilterUsed(false);
            System.out.println("getCurrentDirectory(): " + fChooser.getCurrentDirectory());
            if (fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                System.out.println("getSelectedFile() : " + fChooser.getSelectedFile());
            } else {
                return null;
            }
            filename = new String(fChooser.getSelectedFile().toString() + "\\" + map.mapName + ".SPMbeatmap");
        }
        return filename;
    }
    
    public void stateChanged(ChangeEvent e) {
        int qIndex = qNoteSlider.getValue();
        mapEditLabel[5].setForeground(Color.RED);;
        mapEditLabel[5].setText("Question " + String.valueOf(qIndex));
        mapEditField[5].setText(map.quiz[qIndex]);
        
        mapEditLabel[6].setForeground(Color.RED);;
        mapEditLabel[6].setText("Answer " + String.valueOf(qIndex));
        mapEditField[6].setText(String.valueOf(map.ans[qIndex]));
        System.out.print(qIndex + ">>>>>" + map.quiz[qIndex]);
        System.out.println(" = " + map.ans[qIndex]);
    }
    
    public void actionPerformed(ActionEvent e) {
        String sourceName = e.getSource().getClass().getName();
        System.out.println("Source Name: " + sourceName);

        TextField getText;
        JButton clickedJButton;

        if (sourceName.equals("java.awt.TextField")){
            getText = (TextField)e.getSource();
            if (getTopCard() == editPage) {
                setMap(getText);
                return;
            }
            System.out.println("Action to game console event");
            String mapName = "./SpeedMath/Maps/" + getText.getText() + ".SPMbeatmap";   // "./SpeedMath/Maps/"
            try {
                this.map = map.loadMap(mapName);
            } catch (ClassNotFoundException | IOException e1) {e1.printStackTrace();}
            fullScore = map.totalNotes;
            getText.setText("Enjoy " + mapName);
            return;
        }
        
        clickedJButton = (JButton)e.getSource();
        if (clickedJButton == mainPageButton[0]) {
            System.out.println("Game page");
            this.setFocusable(true);
            mainLayout.show(this, gamePage.getName());
        }            
        
        if (clickedJButton == mainPageButton[1]) {
            System.out.println("Editor page");
            mainLayout.show(this, editPage.getName());
        }
        if (clickedJButton == mainPageButton[2]) {
            System.out.println("Exit");
            System.exit(0);
        }
        if (clickedJButton == edit_Return) {
            System.out.println("Return to main page");
            this.setFocusable(false);
            mainLayout.show(this, mainPage.getName());
        }
        if (clickedJButton == play_Return) {
            System.out.println("Return to main page");
            this.setFocusable(false);
            exit = true;
            mainLayout.show(this, mainPage.getName());
        }
        if (clickedJButton == startGame) {
            System.out.println("Click the cIrClEs!");
            driver = new GameDriver();
            driver.resetGame();
            gameProgress.setText(String.valueOf(earnScore)+"/"+String.valueOf(fullScore));
            gameLoop();
            this.gameConsoleField.transferFocusUpCycle();
        }
        if (clickedJButton == edit_LoadMap) {
            System.out.println("Loading Map!");
            try {
                String fileName = pathChooser(1);
                System.out.println(fileName);
                if (fileName == null) {
                    return;
                }
                this.map = map.loadMap(fileName);
                fullScore = map.totalNotes;
                this.mapInfo();
            } catch (ClassNotFoundException | IOException e1) {e1.printStackTrace();}
        }
        if (clickedJButton == edit_SaveMap) {
            System.out.println("Saving Map!");
            try {
                String fileName = pathChooser(2);
                System.out.println(fileName);
                if (fileName == null) {
                    return;
                }
                if(JOptionPane.showConfirmDialog(this, "File Save at" + fileName) != 0) {
                    return;
                }
                map.saveMap(this.map, fileName);
                this.mapInfo();
            } catch (IOException e1) {e1.printStackTrace();}
        }
    }
    
    public void paintCircleAction(ActionEvent e) {
        // System.out.println("Note " + currentNote + "@" + circlePosX + ", " + circlePosY + " is painting...");
        if (isMiss) {
            missTimer++;
        }
        if (missTimer == 2) {
            missTimer = 0;
            isMiss = false;
        }
        if (hitFadeTimer == 4) {
            
        }
        if (outerCircleSize <= innerCircleSize) {
            noteDone = true;
            canAddScore = false;
            update(gamePaintArea.getGraphics());
            System.out.println("Done drawing");
            ARtimer.stop();
        } else {
            update(gamePaintArea.getGraphics());
            this.outerCircleSize -= approachingSpeed;   // AR * 3
            noteDone = false;
        }
    }
    
    public void fadeActionPerformed(ActionEvent e) {}
    
    private boolean checkhit (int x, int y) {
        System.out.println("Clicked at X: " + x + " Y: " + y);
        // hitFadeTimer.start();
        
        double center_X = (double) circlePosX;
        double center_Y = (double) circlePosY;
        double radius_sq = Math.pow(innerCircleSize / 2, 2);
        double hitComp = Math.pow((x - center_X), 2) + Math.pow((y - center_Y), 2);
        
        if (hitComp <= radius_sq) {
            System.out.println("SCORE!");
            return true;
        }
        
        if (isQNote) {
            hitComp = Math.pow((x - qPosX[ansNoteIndex]), 2) + Math.pow((y - qPosY), 2);
            if (hitComp <= radius_sq)
                return true;
        }
        isMiss = true;
        return false;
    }

    private void changescore() {
        if (canAddScore && !noteDone) {
            earnScore++;
            canAddScore = false;
            gameProgress.setText(String.valueOf(earnScore)+"/"+String.valueOf(fullScore));
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouse1Count++;
            clickCounter[2].setText(String.valueOf(mouse1Count));
            if (checkhit(x, y)) {
                isHit = true;
                changescore();
            }
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            mouse2Count++;
            clickCounter[3].setText(String.valueOf(mouse2Count));
            if (checkhit(x, y)) {
                isHit = true;
                changescore();
            }
        }
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void mouseMoved(MouseEvent e) {}
        
    public void keyPressed(KeyEvent e) {
        hitPosition = gamePaintArea.getMousePosition();
        if (e.getKeyChar() == 'Z' || e.getKeyChar() == 'z') {
            System.out.println("keyPressed> " + e.getKeyChar() + "at " + hitPosition.x +", "+ hitPosition.y);
            this.key1Count++;
            this.clickCounter[0].setText(String.valueOf(this.key1Count));
            if(checkhit(hitPosition.x, hitPosition.y)) {
                isHit = true;
                changescore();
            }
        }
        if (e.getKeyChar() == 'X' || e.getKeyChar() == 'x') {
            System.out.println("keyPressed> " + e.getKeyChar());
            this.key2Count++;
            this.clickCounter[1].setText(String.valueOf(this.key2Count));
            if(checkhit(hitPosition.x, hitPosition.y)) {
                isHit = true;
                changescore();
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {}

    private int[] getQ() {
        int q[] = new int[map.qNotes+1];
        int m = 0;
        for (int i = 0; i < map.qNotes+1; i++) {
            q[i] = m;
            m+=5;
        }
        return q;
    }

    class windowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            Component component = (Component)e.getSource();
            // System.out.println("Resizing " + component.getSize());
        }
    }
}