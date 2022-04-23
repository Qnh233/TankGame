package Tankgame6;

import javax.swing.*;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-22 15:47
 *  0.4 加入爆炸画面 利用生命周期。
 *  0.5 自己人死亡 √
 *  0.6 Hero子弹多发 √
 */
public class TankGame06 extends JFrame {
    MyPanel mp=null;

    public static void main(String[] args) {
        TankGame06 tankGame=new TankGame06();
    }
    public TankGame06()
    {
        mp = new MyPanel();
        Thread thread=new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
