1.  > void SpeedMath.SpeedMathGraphics.paint(Graphics game)
    >
    >       Draw concentric circles
    >       Notice that the circlePosX, circlePosY is the center of the circle
    >       innerCirclePosX = circlePosX-innerCircleSize/2;
    >       innerCirclePosY = circlePosY-innerCircleSize/2;
    >       outerCirclePosX = circlePosX-outerCircleSize/2;
    >       outerCirclePosY = circlePosY-outerCircleSize/2;
    >[How to draw 2 concentric circles? [closed]](https://stackoverflow.com/questions/19773986/how-to-draw-2-concentric-circles)
    

2.  >void SpeedMath.SpeedMathGraphics.paint(Graphics game)
    >
    >       Make a count down timer for approching circles
    >
    >[How can I count the time it takes a function to complete in Java?](https://stackoverflow.com/questions/692569/how-can-i-count-the-time-it-takes-a-function-to-complete-in-java)
    >
    >[Creating a count down timer- Java](https://stackoverflow.com/questions/32110208/creating-a-count-down-timer-java)
    >
	>[How to make a countdown timer in Java [closed]](https://stackoverflow.com/questions/14393423/how-to-make-a-countdown-timer-in-java)


3.	>	void SpeedMath.SpeedMathGraphics.keyPressed(KeyEvent e)
    >
    >	void SpeedMath.SpeedMathGraphics.mouseClicked(MouseEvent e)
    >
    >	    input counter display on button
	>	    use isLeftMouseButton(MouseEvent anEvent) to determine whether left click or right click
    >
    >[Java Mouse Event Right Click](https://stackoverflow.com/questions/4525733/java-mouse-event-right-click)

4.  >Panel SpeedMath.SpeedMathGraphics.getTopCard()
    >
    >       Get the top layer from the cardlayout
    >
    >[How to get the top card in Java's CardLayout](https://stackoverflow.com/questions/4413251/how-to-get-the-top-card-in-javas-cardlayout)

5.  >void SpeedMath.SpeedMathGraphics.actionPerformed(ActionEvent e)
    >
    >       Add file chooser to get file path for save and load
    >[Selecting Folder Destination in Java?](https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java)

6.  >Swing timers
    >
    >       All issuse in swing timer and thread usage
    >       About Swing.Timer start a dispatch thread, and non daemon threads
    >[Make a Swing Timer execute N times?](https://stackoverflow.com/questions/16280425/make-a-swing-timer-execute-n-times)
    >
    >[Java Swing Timer Not Starting?](https://stackoverflow.com/questions/24102757/java-swing-timer-not-starting)
    >
    >[What is a daemon thread in Java?](https://stackoverflow.com/questions/2213340/what-is-a-daemon-thread-in-java)
    >
    >[Class Timer](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/Timer.html)

7.  >void SpeedMath.SpeedMathGraphics.paint(Graphics game)
    >
    >       Draw the string centered on the game area
    >[How can I center Graphics.drawString() in Java?](https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java)

8.  >
    >
    >
---
*       void SpeedMath.SpeedMathGraphics.paint(Graphics game)
        don't work properly on mac os
        need to drag window border to make paint work

*       Set GridLayout to a panel, and add this pannel into the Borderlayout        
        To make button in the panel looks vertically

*       Use ComponentAdapter to get any resizing event

*       After coding up components of all layer of cardlayout and execute,
        I noticed that the button for switching between cardlayout is not functional.
        So I have to add actionListener to those buttons

        Update
        After implementing the actionListener, the button still out of function

        Figuring out how to  implement the actionListener to swap between cards

            06/07 13:35
        Understand that the "name" is not set to component (panel.setName)
        it is second argument of add component to Frame that set cardlayout add(Component comp, Object constraints)

            06/07 14:46
        The hit counter suddenly out of function...

            06/07 15:46
        Solve by setfocusable to true on frame

            06/07 17:39
        this.gameConsoleField.transferFocusUpCycle();