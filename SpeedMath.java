package SpeedMath;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

public class SpeedMath {
    public static void main(String[] args) {
        String mapFile1 = "Map1.txt";
        SpeedMathMaps map1 = new SpeedMathMaps();
        SpeedMathGraphics window = new SpeedMathGraphics(map1);
        /* try {
            do {
                System.out.print("Select mode\n1. Edit mode, 2. Load map 3. Exit mapEditor(1/2/3): ");
                int sel = Integer.parseInt(scanner.nextLine());
                if (sel == 1) {
                    map1.mapEditor(1,scanner);
                    window.setMap(map1);
                } else if (sel == 2) {
                    map1 = map1.mapEditor(2,scanner);
                    map1.info();
                    window.setMap(map1);
                } else if (sel == 3) {
                    break;
                } else {
                    System.out.println("Wrong argument.");
                }
            } while(scanner.hasNext());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.mapInfo(); */

    }
}

class SpeedMathGraphics extends Frame 
implements ActionListener, ChangeListener, MouseListener, MouseMotionListener, KeyListener {
    private int innerCircleSize;
    private int outerCircleSize;
    private int circlePosX;
    private int circlePosY;
    private int key1Count;
    private int key2Count;
    private int mouse1Count;
    private int mouse2Count;
    private int fullScore;
    private int earnScore;
    private int endNote;
    private boolean noteDone;
    private boolean noteIn;
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
    JSlider qNoteSlider;
    Timer ARtimer;
    Timer noTimer;

    public SpeedMathGraphics(SpeedMathMaps obj) {
        super("SpeedMath - by 41043152");
        setSize(1920, 1080);
        setMinimumSize(new Dimension(1920, 1080));
        // setMaximumSize(new Dimension(1920, 1080));
        setResizable(false);
        setLayout(new CardLayout());
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
        Font editFont = new Font("Arial", Font.PLAIN, 25);
        for (int i = 0; i < 8; i++) {
            mapEditLabel[i] = new Label(mapAttributeString[i]);
            mapEditLabel[i].setFont(editFont);
            mapEditLabel[i].setAlignment(Label.LEFT);
            labelPanel.add(mapEditLabel[i]);
            if (i >= 7) break;
            mapEditField[i] = new TextField();
            mapEditField[i].setFont(editFont);
            mapEditField[i].addActionListener(this);
            editPage_Panel.add(mapEditField[i]);
        }
        qNoteSlider = new JSlider(0, 30, 0);
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
        
        rightside = new GridLayout(4, 1, 0, 40);
        rightPanel = new Panel();
        rightPanel.setPreferredSize(new Dimension(150, this.getHeight()));
        rightPanel.setLayout(rightside);

        clickCounter = new JButton[4];
        gameProgress = new Label("0/0", Label.CENTER);
        gameProgress.setPreferredSize(new Dimension(200, 50));
        gameProgress.setFont(new Font("Arial", Font.PLAIN, 50));
        
        gameConsolePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        gameConsolePanel.addMouseMotionListener(this);
        gameConsLabel = new Label();
        gameConsLabel.setText("Type map name to play.");
        gameConsLabel.setPreferredSize(new Dimension(200, 50));
        gameConsoleField = new TextField();
        gameConsoleField.setName("gameConsole");
        gameConsoleField.setFont(editFont);
        gameConsoleField.setPreferredSize(new Dimension(this.getWidth()-440, 50));
        gameConsoleField.addActionListener(this);
        play_Return = new JButton("Return Menu");
        play_Return.setName("From Game Return");
        play_Return.setPreferredSize(new Dimension(100, 50));
        play_Return.addActionListener(this);
        startGame = new JButton("Play");
        startGame.setName("Start Game");
        startGame.setPreferredSize(new Dimension(100, 50));
        startGame.addActionListener(this);

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
        gamePaintArea = new Panel();
        gamePaintArea.addMouseListener(this);
        gamePaintArea.addMouseMotionListener(this);
        gamePaintArea.addKeyListener(this);

        gamePage.add(gamePaintArea, BorderLayout.CENTER);
        gamePage.add(gameConsolePanel, BorderLayout.SOUTH);
        gamePage.add(gameProgress, BorderLayout.NORTH);
        gamePage.add(rightPanel, BorderLayout.EAST);
        
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
        // gamePaintArea.update(gamePaintArea.getGraphics());
        update(gamePaintArea.getGraphics());
    }

    public void setMap(TextField textField) {
        if (textField == mapEditField[0]) {
            this.map.mapName = textField.getText();
            System.out.println("Get map name from user");
        } else if (textField == mapEditField[1]) {
            this.map.totalNotes = Integer.parseInt(textField.getText());
            System.out.println("Get total notes from user");
        } else if (textField == mapEditField[2]) {
            int n = Integer.parseInt(textField.getText());
            this.map.setQNotes(n);
            qNoteSlider.setMaximum(n-1);
            System.out.println("Get quiz notes from user");
        } else if (textField == mapEditField[3]) {
            this.map.CS = Integer.parseInt(textField.getText());
            System.out.println("Get circle size from user");
        } else if (textField == mapEditField[4]) {
            this.map.AR = Float.parseFloat(textField.getText());
            System.out.println("Get approaching rate from user");
        } else if (textField == mapEditField[5]) {
            this.map.setQnA(textField.getText(), qNoteSlider.getValue(), 1);
        } else if (textField == mapEditField[6]) {
            this.map.setQnA(textField.getText(), qNoteSlider.getValue(), 2);
        }
    }

    public void mapInfo() {
        System.out.println("Map info");
        this.map.info();
        qNoteSlider.setMaximum(map.qNotes-1);

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
        boolean isDone;
        public GameDriver() {
            super();
        }
        public void run() {
            innerCircleSize = map.CS;
            outerCircleSize = (int) Math.round(map.CS*2.5);
            int oCS = outerCircleSize;
            endNote = 0;
            
            while (endNote < map.qNotes) {
                circlePosX = (int) (Math.random() * (1620 - 200 + 1)) + 200;
                circlePosY = (int) (Math.random() * (600 - 100 + 1)) + 100;
                System.out.println("Note " + endNote + " at " + circlePosX + ", " + circlePosY);
                
                noteDone = false;
                /* if (outerCircleSize <= innerCircleSize) {
                    noteDone = true;
                    break;
                } else {
                    noteDone = false;
                } */
                
                // SwingUtilities.invokeAndWait(()->approaching(ARtimer));
                
                // this.wait();

                outerCircleSize = oCS;
                endNote++;
            }

            isDone = true;
        }
    }
    
    private synchronized void gameLoop() {
        ARtimer = new Timer(150, this::gactionPerformed);
        driver = new GameDriver();
        driver.start();

        while(!driver.isDone && endNote < map.qNotes) {
            ARtimer.start();
            try {
                driver.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // SwingUtilities.invokeLater(()->approaching(ARtimer));
    }
    
    public void paint(Graphics game) {
        
        int innerCirclePosX = circlePosX-innerCircleSize/2;
        int innerCirclePosY = circlePosY-innerCircleSize/2;
        int outerCirclePosX = circlePosX-outerCircleSize/2;
        int outerCirclePosY = circlePosY-outerCircleSize/2;
        // System.out.println(">"+map.CS+outerCircleSize+"<  "+innerCirclePosX + "," + innerCirclePosY + "," + outerCirclePosX + "," + outerCirclePosY);

        if (noteDone) {
            game.clearRect(0,0,getWidth(), getHeight());
            return;
        }
        // outer circle
        game.setColor(new Color(197, 57, 67, 100));
        game.fillOval(outerCirclePosX, outerCirclePosY, outerCircleSize, outerCircleSize);
        // inner circle
        game.setColor(Color.CYAN);
        game.fillOval(innerCirclePosX, innerCirclePosY, innerCircleSize, innerCircleSize);
    }
    public void paintHit(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillOval(x-10, y-10, 10, 10);
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
        mapEditLabel[5].setText("Question " + String.valueOf(qIndex));
        mapEditLabel[6].setText("Answer " + String.valueOf(qIndex));
        mapEditField[5].setText(map.quiz[qIndex]);
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
            mainLayout.show(this, mainPage.getName());
        }
        if (clickedJButton == startGame) {
            System.out.println("Click the cIrClEs!");
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

    public synchronized void gactionPerformed(ActionEvent e) {
        System.out.println(endNote+" is painting...");
        Timer t = (Timer)e.getSource();
        if (outerCircleSize <= innerCircleSize) {
            noteDone = true;
            // paint(gamePaintArea.getGraphics());
            update(gamePaintArea.getGraphics());
            System.out.print("Done drawing");
            t.stop();
            t.notifyAll();
            // return;
        } else {
            noteDone = false;
            this.outerCircleSize-=5;
            // paint(gamePaintArea.getGraphics());
            update(gamePaintArea.getGraphics());
        }
    }

    private boolean checkhit (int x, int y) {
        System.out.println("Clicked at X: " + x + " Y: " + y);
        paintHit(gamePaintArea.getGraphics(), x, y);
        if (x > circlePosX+5 && x < circlePosX + innerCircleSize-5) {
            if (y > circlePosY+5 && y < circlePosY + innerCircleSize-5) {
                System.out.println("SCORE!");
                return true;
            }
        }
        return false;
    }

    private void changescore() {
        earnScore++;
        gameProgress.setText(String.valueOf(earnScore)+"/"+String.valueOf(fullScore));
    }
    
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Clicked at X: " + x + " Y: " + y);
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouse1Count++;
            clickCounter[2].setText(String.valueOf(mouse1Count));
            if (checkhit(x, y)) {
                changescore();
            }
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            mouse2Count++;
            clickCounter[3].setText(String.valueOf(mouse2Count));
            if (checkhit(x, y)) {
                changescore();
            }
        }
    }
    
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    public void mouseMoved(MouseEvent e) {
        // int posX = e.getX();
        // int posY = e.getY();
        // System.out.println("X: " + posX + " Y: " + posY);


        // if (posY >= gameConsolePanel.getY()-5) {
        //     System.out.println("Leaving game area: ");
        // }
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) {
        Point pos = gamePaintArea.getMousePosition();
        // not working
        if (e.getKeyChar() == 'Z' || e.getKeyChar() == 'z') {
            System.out.println("keyPressed> " + e.getKeyChar() + "at " + pos.getX()+", "+pos.getY());
            this.key1Count++;
            this.clickCounter[0].setText(String.valueOf(this.key1Count));
            if(checkhit((int)pos.getX(), (int)pos.getY())) {
                changescore();
            }
        }
        if (e.getKeyChar() == 'X' || e.getKeyChar() == 'x') {
            System.out.println("keyPressed> " + e.getKeyChar());
            this.key2Count++;
            this.clickCounter[1].setText(String.valueOf(this.key2Count));
            if(checkhit((int)pos.getX(), (int)pos.getY())) {
                changescore();
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {}

    class windowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            Component component = (Component)e.getSource();
            // paint(getGraphics());
            System.out.println("Resizing " + component.getSize());
        }
    }
}