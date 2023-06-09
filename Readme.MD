# 目前問題
        使用 while 來產生目前 圓圈 的隨機座標後
        呼叫 swing timer 用來延遲 paint() 劃出圓與縮圈
        且希望在 timer 結束後才使 while 繼續執行
        timer 執行的過程中也需要能繼續接收視窗事件
        KeyListener, MouseMotionListener
        但目前只能顯示第一個圓後就拋出例外了

game area 360~1560 border

// Thread.sleep((long)map.AR*50);

// answer area is  y = 800

>   public void stateChanged(ChangeEvent e)
>
>   public void setMap(TextField textField)
>
>   public void mapInfo()
>
>   public void actionPerformed(ActionEvent e)