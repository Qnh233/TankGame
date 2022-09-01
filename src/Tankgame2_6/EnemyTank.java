package Tankgame2_6;

import java.util.Vector;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-22 15:27
 *  6.0 想实现一个 herotank 同时开多枪。
 *  7.0 tank自由移动 加入 线程
 */
public class EnemyTank extends Tank implements Runnable{

    //为了确保子类能够将变量x，y序列化保存到变量中去，在此处不再使用父类的x和y

    Vector<EnemyTank> enemyTanks;

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public EnemyTank(int x, int y) {super(x,y);}

    //因为不再使用父类的X和Y，就重写getX 让其他方法调用的时候不要去调用父类的X Y 导致 实际上没有修改此类真正的X Y tank不动。 不用了
    // 后面的move方法也用的父类的方法，也得重写 还要吧 SPEED，方向等变量重写 包括他们的get和set.
    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
    }

    private int type=0;

    public int getType() {
        return type;
    }

    public boolean ifTouchEnemyTank()
    {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank=enemyTanks.get(i);
            //8种情况，4个方向
            switch (this.getDirection())
            {
                case 'w':
                    if (this!=enemyTank)
                    {
                        if(enemyTank.getDirection()=='w'||enemyTank.getDirection()=='s')
                        {
                            if((this.getX()>=enemyTank.getX() && this.getX()<=enemyTank.getX()+40 && this.getY()<=enemyTank.getY()+60 &&
                             this.getY()>=enemyTank.getY())||(this.getX()+40>=enemyTank.getX() && this.getX()<=enemyTank.getX() && this.getY()<=enemyTank.getY()+60 &&
                                this.getY()>=enemyTank.getY()))
                            {
                                return true;
                            }
                        }
                        if(enemyTank.getDirection()=='a'||enemyTank.getDirection()=='d')
                        {
                            if((this.getX()>=enemyTank.getX() && this.getX()<=enemyTank.getX()+60 && this.getY()<=enemyTank.getY()+40 &&
                             this.getY()>=enemyTank.getY())||(this.getX()+40>=enemyTank.getX() && this.getX()+40<=enemyTank.getX()+60 && this.getY()<=enemyTank.getY()+40 &&
                                this.getY()>=enemyTank.getY()))
                            {
                                return true;
                            }
                        }
                    }
                case 's':
                    if (this!=enemyTank)
                    {
                        if(enemyTank.getDirection()=='w'||enemyTank.getDirection()=='s')
                        {
                            //                        (this.getX()>=enemyTank.getX() && this.getX()<=enemyTank.getX()+40 && this.getY()-60>=enemyTank.getY()-60 &&
//                                this.getY()-60<=enemyTank.getY())||(this.getX()+40>=enemyTank.getX() && this.getX()<=enemyTank.getX() &&
//                                this.getY()-60>=enemyTank.getY()-60 &&
//                                this.getY()-60<=enemyTank.getY())
                            if (this.getY()+60>=enemyTank.getY()&&this.getY()+60<=enemyTank.getY()+60
                                        &&(this.getX()>=enemyTank.getX() && this.getX()<=enemyTank.getX()+40
                                        ||this.getX()+40>=enemyTank.getX() && this.getX()<=enemyTank.getX()))
                            {
                                return true;
                            }
                        }
                        if(enemyTank.getDirection()=='a'||enemyTank.getDirection()=='d')
                        {
                            if(this.getY()+60>=enemyTank.getY()&&this.getY()+60<=enemyTank.getY()+40
                                    &&(this.getX()>=enemyTank.getX() && this.getX()<=enemyTank.getX()+60
                                    ||this.getX()+40>=enemyTank.getX() && this.getX()+40<=enemyTank.getX()+60))
                            {
                                return true;
                            }
                        }
                    }
                case 'a':
                    if (this!=enemyTank)
                    {
                        if(enemyTank.getDirection()=='w'||enemyTank.getDirection()=='s')
                        {
                            if (this.getX()>=enemyTank.getX()&&this.getX()<=enemyTank.getX()+40
                                    &&(this.getY()>=enemyTank.getY() && this.getY()<=enemyTank.getY()+60
                                    ||this.getY()+40>=enemyTank.getY() && this.getY()+40<=enemyTank.getY()+60))
                            {
                                return true;
                            }
                        }
                        if(enemyTank.getDirection()=='a'||enemyTank.getDirection()=='d')
                        {
                            if (this.getX()>=enemyTank.getX()&&this.getX()<=enemyTank.getX()+60
                                    &&(this.getY()>=enemyTank.getY() && this.getY()<=enemyTank.getY()+40
                                    ||this.getY()+40>=enemyTank.getY() && this.getY()+40<=enemyTank.getY()+40))
                            {
                                return true;
                            }
                        }
                    }
                case 'd':
                    if (this!=enemyTank)
                    {
                        if(enemyTank.getDirection()=='w'||enemyTank.getDirection()=='s')
                        {
                            if (this.getX()+60>=enemyTank.getX()&&this.getX()+60<=enemyTank.getX()+40
                                    &&(this.getY()>=enemyTank.getY() && this.getY()<=enemyTank.getY()+60
                                    || this.getY()+40>=enemyTank.getY() && this.getY()+40<=enemyTank.getY()+60))
                            {
                                return true;
                            }
                        }
                        if(enemyTank.getDirection()=='a'||enemyTank.getDirection()=='d')
                        {
                            if (this.getX()+60>=enemyTank.getX()&&this.getX()+60<=enemyTank.getX()+60
                                    &&(this.getY()>=enemyTank.getY() && this.getY()<=enemyTank.getY()+40
                                    ||this.getY()+40>=enemyTank.getY() && this.getY()+40<=enemyTank.getY()+40))
                            {
                                return true;
                            }
                        }
                    }



            }
        }
        return false;

    }


    @Override
    public void run() {
        char[]cs=new char[]{'w','s','a','d'};
        while(this.isLive)
        {
            int steps=(int)(Math.random()*10);
            for (int i = 0; i < steps; i++)
            {
                //撞墙改变方向(回头) 或者重叠
                if((!(getX()>0&&getX()<1000-60&&getY()>0&&getY()<750-60)))
                {
                    switch (this.getDirection())
                    {
                        case 'w':
                            setDirection('s');
                            break;
                        case 's':
                            setDirection('w');
                            break;
                        case 'a':
                            setDirection('d');
                            break;
                        case 'd':
                            setDirection('a');
                            break;
                    }
                }
                if(!this.ifTouchEnemyTank())
                switch (this.getDirection())
                {
                case 'w':
                    this.moveUP();
                    break;
                case 's':
                    this.moveDown();
                    break;
                case 'a':
                    this.moveLeft();
                    break;
                case 'd':
                    this.moveRight();
                    break;
                }

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //随机改变方向
            this.setDirection(cs[(int)(Math.random()*4)]);

//            //自由射击
//            Shoot shoot = new Shoot(getX(), getY(), getDirection());
//            new Thread(shoot).start();
        }

    }

}
