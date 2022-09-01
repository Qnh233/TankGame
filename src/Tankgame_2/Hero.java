package Tankgame_2;

import java.io.Serializable;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:29
 * 用户坦克类
 */
public class Hero extends Tank implements Serializable {

    public Hero(int x, int y) {
        super(x, y);
    }
    //子弹类
    private int type=1;

    public int getType() {
        return type;
    }


    Shoot shot=null;
    public Shoot shotEnemyTank()  {
        switch (this.getDirection())
        {
            case 'w':
                shot = new Shoot(getX(),getY(),'w');
                break;
            case 'd':
                shot = new Shoot(getX(),getY(),'d');
                break;
            case 'a':
                shot = new Shoot(getX(),getY(),'a');
                break;
            case 's':
                shot = new Shoot(getX(),getY(),'s');
                break;
            default:
        }
        new Thread(shot).start();
        return shot;
    }
}
