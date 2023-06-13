# 目前問題 1
    (部分解決)
        使用 while 來產生目前 圓圈 的隨機座標後
        呼叫 Swing Timer 用來延遲 paint() 劃出圓與縮圈
        timer 執行的過程中也需要能繼續接收視窗事件
        KeyListener, MouseMotionListener
        且希望在 timer 結束後才使 while 繼續產生下個圓圈的座標
        但目前只能顯示第一個圓後就拋出例外了

# 執行緒的認知不足
        將主要遊戲迴圈寫在在繼承 Runnable 子類別中,
        主執行緒與 Java AWT 繪圖所使用的 "Dispatching Thread"
        之間有許多問題, 導致程式運作不正常

## 潛在冗余碼多

---
#   Notes
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

---
## 首頁操作
        Play    進入遊戲
        Editor  進入編輯器
        Exit    退出

## 遊戲
<pre>
    底部
        Play 按鈕       開始遊戲
        Menu 按鈕       返回主頁
        文字輸入欄       輸入地圖名稱
    右側
        分數標籤        顯示總拍數與得分比
        直列4按鈕       以標籤顯示點擊次數
    頂部
        題目標籤        顯示題目

    按下鍵盤 X, Y 鍵與滑鼠左右鍵的次數被記錄
    並顯示在右側 4 個按鈕的標籤上
    <strike>按下的位置會被紅圓點標示 Not working</strike>
        
    點擊位置於圈內會記錄分數, 否則判斷為 Miss
    若 Miss 但未縮圈完之前皆有機會點擊
    目前題目會顯示於上方 Label
    沒隔四圈會出現一次答案圈
    答案圈會出現三點, 每圈上方會顯示答案, 以浮點數表示
    三答案圈中只有一個正確答案
    點擊在正確答案圈外不計分

    完成後會跳出對話提示視窗
</pre>

## 編輯器
<pre>
    每個輸入欄皆需要按下 Enter
    未被紀錄的欄位會以紅字呈現
    已被紀錄的欄位會以綠字呈現
    
    <b>Texfield Label  : Feature</b>
    Map name        : 決定存檔名稱
    Total notes     : 總圓圈數
    Quiz notes      : 特殊圓圈數
    Circle size     : 圓圈大小 1 ~ 10
    Approaching rate: 縮圈速度 1.0 ~ 10.0
    Question        : 數學題目, 字串輸入
    Answer          : 題目答案, 以浮點數格式輸入字串
    qNote slider    : 在各題目間選擇, 顯示

    右側按鈕
        Load Map    : 讀取地圖檔案, 只顯示 .SPMbeatmap 檔
                      讀取即設定為遊戲地圖
        Save Map    : 儲存地圖檔案, 檔名由輸入欄決定
        Return Menu : 返回主頁

    補充解釋
        檔名會加上副檔名.SPMbeatmap儲存
        總圓圈數, 也稱為節拍, 包含 quiz note
        答案圈, quiz note, 為總拍數的 1/5 倍
        圓圈大小只決定內圈大小
        一圈分為內圈與縮圈, 內圈為設定的固定大小
        縮圈為內圈的 4 倍, 且會逐漸所小至內圈大小
        縮圈 <= 內圈時, 此拍即結束
            圓圈像素大小    CS*8   pixels
            外圈像素大小    CS*8*4 pixels
        縮圈速度為  AR*3, 每次繪圖更新縮圈所減少的像素寬度
        滑動 scrollbar 會在題目間切換, 藉此顯示與編輯

        答案圈顯示為固定區域
        三個答案圈的 X 座標分別為 gamePaintArea 的三分之一
                   Y 座標為 gamePaintArea 高度對齊圓圈大小

        普通圈的顯示區域為 gamePaintArea 高度對齊圓圈大小
            X: 1670 Y: 892  
</pre>

---
#   References

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