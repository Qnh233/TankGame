package Tankgame6;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:29
 * 用户坦克磊类
 */
public class Hero extends Tank {

    public Hero(int x, int y) {
        super(x, y);
    }
    //子弹类

    Shoot shot=null;
    public Shoot shotEnemyTank()
    {
        switch (this.getDirection())
        {
            case 'w':
                shot = new Shoot(getX()+20,getY(),'w');
                break;
            case 'd':
                shot = new Shoot(getX()+60,getY()+20,'d');
                break;
            case 'a':
                shot = new Shoot(getX(),getY()+20,'a');
                break;
            case 's':
                shot = new Shoot(getX()+20,getY()+60,'s');
                break;
            default:
        }
        new Thread(shot).start();
        return shot;
    }
}
