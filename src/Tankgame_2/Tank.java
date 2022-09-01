package Tankgame_2;

import java.io.Serializable;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-23
 */
public class Tank implements Serializable{
    private int x;
    private int y;
    private char direction = 'w';
    private int speed = 5;
    boolean isLive = true;


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

    //此处四个方向的移动 直接限制所有tank的活动范围。
    public void moveUP() {
        if (y > 0) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y < 750 - 60) {
            y += speed;
        }
    }

    public void moveRight() {
        if (x < 1000 - 60) {
            x += speed;
        }
    }

    public void moveLeft() {
        if (x > 0) {
            x -= speed;
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
