# 目前問題
        使用 while 來產生目前 圓圈 的隨機座標後
        呼叫 swing timer 用來延遲 paint() 劃出圓與縮圈
        且希望在 timer 結束後才使 while 繼續執行
        timer 執行的過程中也需要能繼續接收視窗事件
        KeyListener, MouseMotionListener
        但目前只能顯示第一個圓後就拋出例外了



## 執行操作

        按 Play 按鈕
        下方輸入欄輸入 AR 後按 Enter
        按右側 Play 按鈕
        按下鍵盤 X, Y 鍵與滑鼠左右鍵的次數被記錄並顯示在右側 4 個按鈕的標籤上
        按下的位置會被紅圓點標示

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
        game area 360~1560 border
        圓圈像素大小    CS*8
        縮圈速度        map.AR*50
        answer area is x:, y:800