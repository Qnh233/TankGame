package Tankgame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:31
 * 画板类
 * 直接让mypanel加入线程功能，一直进行重绘。
 */
public class MyPanel extends JPanel implements KeyListener,Runnable{

    public MyPanel()
    {
        //初始化自己tank
        hero=new Hero(100,100);
        //初始化敌人tank
        for (int i = 0; i < enemyTanksize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            //设置敌军坦克方向
            enemyTank.setDirection('s');
            enemyTanks.add(enemyTank);
        }
    }
    //定义我的坦克

    Hero hero = null;
    //定义敌人坦克,使用Vector 考虑多线程安全性

    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌军坦克数量

    int enemyTanksize = 3;


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1000, 750);

        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);

        //炮弹一个
        //g.drawLine(hero.getX()+30,hero.getY(),hero.getX()+30,hero.getY()+10);
        if(hero.getShot()!=null&&hero.getShot().loop)
        {
            System.out.println("绘制子弹");
            g.draw3DRect(hero.getShot().x,hero.getShot().getY(),1,1,false);
        }

        //敌军
        for (int i = 0; i < enemyTanksize; i++) {
            EnemyTank enemyTank=enemyTanks.get(i);
//            enemyTank.setDirection(2);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),0);
        }


    }



    /**
     * 画坦克
     *
     * @param x    横坐标
     * @param y    纵坐标
     * @param g    画笔
     * @param d    方向
     * @param type 类型
     */
    private void drawTank(int x, int y, Graphics g, char d, int type) {
        switch (type)
        {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
            default:
        }
        switch (d)
        {
            case 'w':
                g.fill3DRect(x,y,10,60,false);
                //左伦子
                g.fill3DRect(x,y,10,60,false);
                //you lun zi
                g.fill3DRect(x+30,y,10,60,false);
                //tank top
                g.fill3DRect(x+10,y+10,20,40,false);
                //tank top o
                g.fillOval(x+10,y+20,20,20);
                //tank pao
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 'd':
                //向右
                //左伦子
                g.fill3DRect(x,y,60,10,false);
                //you lun zi
                g.fill3DRect(x,y+30,60,10,false);
                //tank top
                g.fill3DRect(x+10,y+10,40,20,false);
                //tank top o
                g.fillOval(x+20,y+10,20,20);
                //tank pao
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 's':
                //xiangxia下
                //左伦子
                g.fill3DRect(x,y,10,60,false);
                //you lun zi
                g.fill3DRect(x+30,y,10,60,false);
                //tank top
                g.fill3DRect(x+10,y+10,20,40,false);
                //tank top o
                g.fillOval(x+10,y+20,20,20);
                //tank pao
                g.drawLine(x+20,y+20,x+20,y+60);
                break;
            case 'a':
                //向左
                //左伦子
                g.fill3DRect(x,y,60,10,false);
                //you lun zi
                g.fill3DRect(x,y+30,60,10,false);
                //tank top
                g.fill3DRect(x+10,y+10,40,20,false);
                //tank top o
                g.fillOval(x+20,y+10,20,20);
                //tank pao
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:

        }
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W:
                hero.setDirection('w');
                hero.moveUP();break;
            case KeyEvent.VK_S:
                hero.moveDown();
                hero.setDirection('s');
                break;
            case KeyEvent.VK_D:
                hero.setDirection('d');
                hero.moveRight();
                break;
            case KeyEvent.VK_A:
                hero.setDirection('a');
                hero.moveLeft();
                break;
                default:
        }
        if (e.getKeyCode()==KeyEvent.VK_J)
        {
            hero.shotEnemyTank();
            new Thread(this).start();
        }
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
