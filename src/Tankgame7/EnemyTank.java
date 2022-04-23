package Tankgame7;


/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-22 15:27
 *  6.0 想实现一个 herotank 同时开多枪。
 *  7.0 tank自由移动 加入 线程
 */
public class EnemyTank extends Tank implements Runnable{

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
    }

    private int type=0;

    public int getType() {
        return type;
    }



    @Override
    public void run() {
        char[]cs=new char[]{'w','s','a','d'};
        while(this.isLive)
        {
            int steps=(int)(Math.random()*10);
            for (int i = 0; i < steps; i++)
            {
                //撞墙改变方向
                if(!(getX()>0&&getX()<1000-60&&getY()>0&&getY()<750-60))
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
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //随机改变方向
            this.setDirection(cs[(int)(Math.random()*4)]);
        }

    }
}
