package Tankgame7;

import javax.swing.*;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-23
 *  0.4 加入爆炸画面 利用生命周期。
 *  0.5 自己人死亡 √
 *  0.6 Hero子弹多发 √
 *  0.7 敌军tank自移动 不撞墙 控制子弹频率 √
 */
public class TankGame07 extends JFrame {
    MyPanel mp=null;

    public static void main(String[] args) {
        TankGame07 tankGame=new TankGame07();
    }
    public TankGame07()
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
