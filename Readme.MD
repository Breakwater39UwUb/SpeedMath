# 目前問題 1
        (部分解決)
        使用 while 來產生目前 圓圈 的隨機座標後
        呼叫 swing timer 用來延遲 paint() 劃出圓與縮圈
        且希望在 timer 結束後才使 while 繼續執行
        timer 執行的過程中也需要能繼續接收視窗事件
        KeyListener, MouseMotionListener
        但目前只能顯示第一個圓後就拋出例外了


## 首頁操作
        Play    進入遊戲
        Editor  進入編輯器
        Exit    退出

## 遊戲
<pre>
    Play 按鈕       開始遊戲
    Menu 按鈕       返回主頁
    文字輸入欄       輸入地圖名稱

    按下鍵盤 X, Y 鍵與滑鼠左右鍵的次數被記錄並顯示在右側 4 個按鈕的標籤上
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
        按 Editor 按鈕
        每個輸入欄皆需要按下 Enter
        Map name        : 決定存檔名稱
        Total notes     : 總圓圈數, 包含 quiz note
        Quiz notes      : 特殊圓圈數
        Circle size     : 圓圈大小 1 ~ 10
        Approaching rate: 縮圈速度 1 ~ 10
        Question        : 數學題目, 字串輸入
        Answer  : 上述題目答案, 以浮點數輸入

        

### 公式
        game area X:160~1480, Y:
        圓圈像素大小    CS*8
        外圈像素大小    CS*4
        縮圈速度        map.AR*50
        answer area is x:, y:800

        qNote   totalNotes/5, no round
        qNote Position  X:200, 900, 1500
                        Y:850