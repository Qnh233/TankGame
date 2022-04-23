package Tankgame6;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:30
 */
public class Tank {
    private int x;
    private int y;
    private char direction='w';
    private int speed=3;
    boolean isLive=true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void moveUP()
    {
        if(y>0) {
            y-=speed;
        }
    }
    public void moveDown()
    {
        if(y<750-60) {
            y+=speed;
        }
    }
    public void moveRight()
    {
        if(x<1000-60) {
            x+=speed;
        }
    }
    public void moveLeft()
    {
        if(x>0) {
            x-=speed;
        }
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
