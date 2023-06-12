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