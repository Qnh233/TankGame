package Tankgame3;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-17 16:05
 */
public class Shoot  implements Runnable {
    int x;
    int y;
    int speed=5;
    char d;
    boolean isLive=true;
    public Shoot()
    {

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public char getD() {
        return d;
    }

    public Shoot(int x, int y, char d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }

    void wshoot()
    {
        y-=speed;
    }
    void sshoot()
    {
        y+=speed;
    }
    void ashoot()
    {
        x-=speed;
    }
    void dshoot()
    {
        x+=speed;
    }

    /**
     * 子弹是否存活
     */
    boolean loop=true;

    /**
     * 射击我的宝贝
     */

    @Override
    public void run() {

        while(isLive){

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (d)
            {
                case 'w':
                    wshoot();
                    break;
                case 's':
                    sshoot();
                    break;
                case 'a':
                    ashoot();
                    break;
                case 'd':
                    dshoot();
                    break;
                default:

            }
            if (!(x>=0&&x<=1000&&y>=0&&y<=750))
            {
                isLive=false;
                break;
            }

        }
    }
}
