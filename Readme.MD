#   ~~Need Help~~

~~slider 的數值會對應 SpeedMathMaps map 的 quizp[] ansp[] 的 index~~

~~但是在 mapEditField[5] mapEditField[6] 輸入後，移動滑條會變成 null~~

~~可以觀察cmd輸出~~

        問題產生操作
        1.  輸入 Quiz notes, 並按 Enter, Enter 後 slider 會改變最大值
        2.  輸入 Qusetion, 是字串輸入, 並按 Enter
        3.  輸入 Answer, 是浮點數, 並按 Enter
        4.  滑動滑條到2, 再回到1後會變成 null

#   ~~2.  No paint?~~

##  360~1560 border

// Thread.sleep((long)map.AR*50);

// answer area is  y = 800

>   public void stateChanged(ChangeEvent e)
>
>   public void setMap(TextField textField)
>
>   public void mapInfo()
>
>   public void actionPerformed(ActionEvent e)