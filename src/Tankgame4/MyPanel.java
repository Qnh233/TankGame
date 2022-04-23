package Tankgame4;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-22 15:31
 * 画板类
 *
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
            //加入子弹类
            Shoot shot = new Shoot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
            enemyTank.Eshots.add(shot);
            //q启动shot
            new Thread(shot).start();

        }
    }
    //定义我的坦克

    Hero hero = null;
    //定义敌人坦克,使用Vector 考虑多线程安全性

    Vector<EnemyTank> enemyTanks = new Vector<>();
    //敌军坦克数量

    int enemyTanksize = 3;
    Vector<Boom> Booms=new Vector<>();
    //炸弹动态图

    Image image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    Image image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    Image image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

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
            System.out.println("X="+getX()+"Y="+getY());
            g.draw3DRect(hero.getShot().x,hero.getShot().getY(),1,1,false);
        }
        for (int i = 0; i < Booms.size(); i++) {
            Boom boom=Booms.get(i);
            if(boom.life>9)
            {
                g.drawImage(image1,boom.x,boom.y,80,80,this);
            }
            else if(boom.life>5)
            {
                g.drawImage(image2,boom.x,boom.y,60,60,this);
            }
            else{
                g.drawImage(image3,boom.x,boom.y,60,60,this);
            }
            boom.lifedown();
            if (!boom.isLive)
            {
                Booms.remove(boom);
            }
        }

        //敌军
        for (int i = 0; i < enemyTanksize; i++) {
            EnemyTank enemyTank=enemyTanks.get(i);
//            enemyTank.setDirection(2);
            if(enemyTank.isLive)
            {
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),0);
                //画敌军tank子弹
                for (int j = 0; j < enemyTank.Eshots.size(); j++) {
                    Shoot shot =enemyTank.Eshots.get(j);
                    if(shot.loop)
                    {
                        g.draw3DRect(shot.x,shot.getY(),1,1,false);
                    }
                    else
                    {
                        //子弹死亡 线程结束 移除
                        enemyTank.Eshots.remove(shot);
                    }
                }
            }

        }


    }

    //判断tank是否死亡
    public  void hitTank(Shoot s, EnemyTank enemyTank)
    {
        //根据敌军tank朝向判断子弹是否与其重合
        if(enemyTank.isLive)
        {
            switch (enemyTank.getDirection())
            {
                case 'w':
                case 's':
                    if(s.x> enemyTank.getX()&& s.x<enemyTank.getX()+40
                            && s.y> enemyTank.getY()&&s.y< enemyTank.getY()+60)
                    {
                        s.isLive=false;
                        enemyTank.isLive=false;
                        s.loop=false;
                        Booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));

                    }
                    break;
                case 'a':
                case 'd':
                    if (s.x>enemyTank.getX()&&s.x<enemyTank.getX()+60
                            &&s.y>enemyTank.getY()&&s.y<enemyTank.getY()+40)
                    {
                        s.isLive=false;
                        enemyTank.isLive=false;
                        s.loop=false;
                        Booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                    }
                    break;
                    default:
        }



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
            //击杀敌人
            if(hero.getShot()!=null&&hero.getShot().isLive)
            {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.getShot(),enemyTank);
                }
            }
            this.repaint();
        }
    }
}
