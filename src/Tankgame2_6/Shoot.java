package Tankgame2_6;

import java.io.Serializable;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-17 16:05
 */
public class Shoot  implements Runnable , Serializable {
    int x;
    int y;
    int speed=3;
    char d;
    boolean isLive=true;

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
        this.d = d;
//        this.x=x;
//        this.y=y;
//      根据tank（子弹）方向，设置子弹初始位置。
        switch (d)
        {
            case 'w':
                this.x = x+20;
                this.y = y;
                break;
            case 's':
                this.x = x+20;
                this.y = y+60;
                break;
            case 'a':
                this.x = x;
                this.y = y+20;
                break;
            case 'd':
                this.x = x+60;
                this.y = y+20;
                break;
        }
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 射击我的宝贝
     */
    @Override
    public void run() {

        while(isLive){
            try {
                Thread.sleep(100);
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
