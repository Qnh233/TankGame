package Tankgame_2;

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
 * 5.0 稍作改动，原本每一个敌军类都有一个子弹集合，现在变成只有一个子弹集合去存储所有敌军开的枪。
 */
public class MyPanel extends JPanel implements KeyListener,Runnable{

    public MyPanel()  {
        //初始化自己tank
        hero=new Hero(100,200);
        //初始化敌人tank
        if((enemyTanks=Recorder.UnRecord()).size()==0 ||
                (key=JOptionPane.showConfirmDialog(null,"您要继续游戏吗？","检测到您有未完成的游戏",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE))!= JOptionPane.OK_OPTION)
            //确认对话框 在此处是进行了一个是否进行上一局游戏的判断，用到了懒惰求值的设置。
            // 如 若存储的tank为0个 则 true || 随意 =》true  直接进入重新初始化游戏操作
            // 其实要想逻辑清晰，可以在游戏开始的入口处设置弹窗，即TankGame.java里编写代码。
            //但要判断是否存在可再现的残局，这里直接用到Recorder的静态方法来判断就可以。
            //再得到用户的选择后，就需要把选择传入到此类中，让MyPanel晓得该如何初始化这局游戏。
            //目前写法 此判断有BUG 就是 在存储的敌tank不为零时候 如果用户选择不继续游戏，那么就是false || true =》 true 就会执行下面语句
            //即开始重新初始化游戏 加入一些初始化的Tank 但由于存储的tank也有 所以会导致 tank变多 且 存储的tank没有开启线程 动弹不得。
            //此情况下就需要把 存储的 tank 清空 来保证不影响 新游戏的生成
        {
            if(key!=0)
            {
                enemyTanks.clear();
            }
            Recorder.setScore(0);
                for (int i = 0; i < enemyTanksize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setEnemyTanks(enemyTanks);//在此处传入实参，引用型参数，即传入地址，后续此集合变动，其对象内此集合也变化。用于防重叠
                    //设置敌军坦克方向
                    enemyTank.setDirection('s');
                    enemyTanks.add(enemyTank);
                    //加入子弹类
                    Shoot shot = new Shoot(enemyTank.getX(), enemyTank.getY(), enemyTank.getDirection());
                    Eshots.add(shot);
                    //启动shot
                    new Thread(shot).start();
                    //启动enemyTank的自移动线程
                    new Thread(enemyTank).start();
                }
        }

        else
        {

            for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    System.out.println(enemyTank.getX()+" "+enemyTank.getY());
                    //启动enemyTank的自移动线程
                    new Thread(enemyTank).start();
                }
            }
        Recorder.setEnemyTanks(enemyTanks);
    }


    int key=0;
    //敌军tank子弹集合
    Vector<Shoot> Eshots=new Vector<>();
    //我军tank子弹集合
    Vector<Shoot> Hshots=new Vector<>();
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克,使用Vector 考虑多线程安全性
    Vector<EnemyTank> enemyTanks = new Vector<>();
//    //存档
//    String storePath="D:\\code\\Tank\\src\\store.bat";
//    ObjectOutputStream oot=new ObjectOutputStream(new FileOutputStream(storePath));


    //敌军坦克数量
    int enemyTanksize = 3;
    Vector<Boom> Booms=new Vector<>();

    //炸弹动态图
    Image image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    Image image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    Image image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    //Image T=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/nnn.gif"));

    //画计分板
    public void printInfo(Graphics g)
    {
        g.setColor(Color.black);
        Font font =new Font("宋体",Font.BOLD,25);
        g.setFont(font);

        g.drawString("你目前得分:"+Recorder.getScore(),1005,30);
        drawTank(1060,60,g,'w',0);
        g.setColor(Color.black);
        g.drawString(""+Recorder.getScore(),1110,100);

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //printInfo(g); 放在这里会把画笔颜色改变 在drawTank中改变成蓝色，然后下方画游戏区域时候就会画成蓝色。
        g.fillRect(0, 0, 1000, 750);
        g.setColor(Color.yellow);
        printInfo(g);
        if(hero.isLive)
        {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), hero.getType());
        }
        else
        {
            //我方tank被击中后会留下字样，但字样也会跟着移动，因为我们并没有销毁hero对象，只是更改了其中的islive变量。
            //所以You died 能用键盘控制移动- -真特么洋气
            //因为xy的赋值是Hero.getX和getY
            //现在改为了一个固定值，无论怎么重绘都不会改变文字位置。
            g.drawString("游戏结束,您的本次游戏得分为："+Recorder.getScore(),500,350);
            //写入成绩，但不能放在此处，因为进程一直在进行repaint 此处一直进行保存 会抛出异常。
        }
//        if(!hero.isLive)
        //炮弹一个 我们发射 绘制我军子弹
        // g.drawLine(hero.getX()+30,hero.getY(),hero.getX()+30,hero.getY()+10);
        for (int i = 0; i < Hshots.size(); i++) {
            Shoot shot=Hshots.get(i);
            if(shot.isLive)
            {
                System.out.println("绘制子弹");
                System.out.println("X="+shot.x+"Y="+shot.y);
                g.setColor(Color.yellow);
                g.draw3DRect(shot.x,shot.y,1,1,false);
            }
            else
            {
                Hshots.remove(shot);
            }

        }
//        if(hero.getShot()!=null&&hero.getShot().isLive)
//        {
//            System.out.println("绘制子弹");
//            System.out.println("X="+hero.getShot().x+"Y="+hero.getShot().y);
//            g.draw3DRect(hero.getShot().x,hero.getShot().getY(),1,1,false);
//        }
        //循环绘制爆炸效果
        for (int i = 0; i < Booms.size(); i++) {
            Boom boom=Booms.get(i);
            if(boom.life>9)
            {
                g.drawImage(image1,boom.x,boom.y,60,60,this);
            }
            else if(boom.life>5)
            {
                g.drawImage(image2,boom.x,boom.y,60,60,this);
            }
            else{
                g.drawImage(image3,boom.x,boom.y,60,60,this);
            }
            //g.drawImage(T,boom.x,boom.y,80,80,this);
            boom.lifedown();
            if (!boom.isLive)
            {
                Booms.remove(boom);
            }
        }

        //敌军
        for (int i = 0; i < enemyTanks.size(); i++)
        {
            EnemyTank enemyTank=enemyTanks.get(i);
            //enemyTank.setEnemyTanks(enemyTanks);
//            enemyTank.setDirection(2);
            //boolean ifchong=false;
            //一层保险，若存活才绘制敌军
            if(enemyTank.isLive) {
                //下段为一个防重叠的一个循环判断，不可取
//                for (int j = i+1; j < enemyTanks.size(); j++) {
//                    if(enemyTank.equals(enemyTanks.get(j)))
//                    {
//                        ischong=true;
//                    }
//                }
//                for (int j = 0; j < enemyTanks.size(); j++) {
//                    if((enemyTank.getX()> enemyTanks.get(j).getX()&& enemyTank.getX()< enemyTanks.get(j).getX()+40
//                            && enemyTank.getY()> enemyTanks.get(j).getY()&&enemyTank.getY()< enemyTanks.get(j).getY()+60)&&i!=j)
//                    {
//                        ifchong=true;
//                    }
//                }
//                if(ifchong)
//                {
//                    break;
//                }
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), enemyTank.getType());

                int emission=(int) (Math.random()*50);
                //此处可尝试将子弹集合封装至hero或者enemytank中，这样就可以用一个对象来寻找他本身的子弹数量并加以限制。
                if((emission==1||emission==5)&&Eshots.size()<20)
                    //所有敌军tank的子弹数量同一时刻不能超过20颗，
                    // 而且每次绘制这辆tank时候，会有1/25的概率发射子弹。无时无刻不在重绘。
                {
                    Shoot shoot=new Shoot(enemyTank.getX(),enemyTank.getY(),enemyTank.getDirection());
                    Eshots.add(shoot);
                    new Thread(shoot).start();
                }
            }

        }
        //画敌军tank子弹
        for (int j = 0; j < Eshots.size(); j++) {
            Shoot shot =Eshots.get(j);
            if(shot.isLive)
            {
                g.setColor(Color.white);
                g.draw3DRect(shot.getX(),shot.getY(),1,1,false);
                hitTank(shot,hero);
            }
            else
            {
                //子弹死亡 线程结束 移除
                Eshots.remove(shot);
            }

        }

    }

    /**
     *  判断tank是否被击中
     * @param s 子弹
     * @param Tank 坦克
     */
    public void hitTank(Shoot s, Tank Tank)
    {
        //根据tank朝向判断子弹是否与其重合
        if(Tank.isLive)
        {
            switch (Tank.getDirection())
            {
                case 'w':
                case 's':
                    if(s.x> Tank.getX()&& s.x< Tank.getX()+40
                            && s.y> Tank.getY()&&s.y< Tank.getY()+60)
                    {
                        s.isLive=false;
                        Tank.isLive=false;
                        Booms.add(new Boom(Tank.getX(), Tank.getY()));
                        //如果Tank是hero remove会返回false 不影响使用。
                        if(enemyTanks.remove(Tank))
                        {
                            Recorder.addScore();
                        }
                    }
                    break;
                case 'a':
                case 'd':
                    if (s.x> Tank.getX()&&s.x< Tank.getX()+60
                            &&s.y> Tank.getY()&&s.y< Tank.getY()+40)
                    {
                        s.isLive=false;
                        Tank.isLive=false;
                        Booms.add(new Boom(Tank.getX(), Tank.getY()));
                        if(enemyTanks.remove(Tank))
                        {
                            Recorder.addScore();
                        }
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
            if(Hshots.size()<5)
            {
                Shoot shot=hero.shotEnemyTank();
                Hshots.add(shot);
            }

            //new Thread(this).start();
        }
        //监听到键盘也能进行重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    /**
     * 判断敌人是否死亡，持续重绘整个面板，（休眠100ms后判断并重绘）
     */
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
            for (int i = 0; i < Hshots.size(); i++) {
                Shoot shot=Hshots.get(i);
                if(shot.isLive)
                {
                    if(shot!=null&&shot.isLive)
                    {
                        for (int j = 0; j < enemyTanks.size(); j++) {
                            EnemyTank enemyTank = enemyTanks.get(j);
                            hitTank(shot,enemyTank);
                        }
                    }
                }
            }
            //或者被杀
//            else
//            {
//                hitTank();
//            }
            if(hero.isLive)
            this.repaint();
            else
            {
                break;
            }
        }
    }
}
