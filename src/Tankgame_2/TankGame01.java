package Tankgame_2;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author 必燃
 * @version： 2.0
 * @create 2022-04-23 15:37
 *  0.4 加入爆炸画面 利用生命周期。
 *  0.5 自己人死亡 √
 *  0.6 Hero子弹多发 √
 *  0.7 敌军tank自移动 不撞墙 控制子弹频率 √
 *  0.8 敌军可自由射击。
 *  2.0 防敌军重叠  保存成绩与存档

 */
public class TankGame01 extends JFrame {
    MyPanel mp=null;

    public static void main(String[] args)  {
        TankGame01 tankGame=new TankGame01();
    }
    public TankGame01()  {
        mp = new MyPanel();
        Thread thread=new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1200,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //关闭窗口时的操作
        //在JFrame中增加监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口要关了！");
                if(mp.hero.isLive)
                {
                    Recorder.record(mp.enemyTanks,true);
                }
                else
                {
                    Recorder.record(mp.enemyTanks,false);
                    Recorder.recordScore();
                }
                //减少开IO流写入的写法是 加判断 如上
                //减少代码量的写法是 在MtPanel中run的Hero死亡时操作中加入enemyTanks的清零，然后此处无论死活都进行序列化存储。
                super.windowClosing(e);
            }
        });

    }
}
