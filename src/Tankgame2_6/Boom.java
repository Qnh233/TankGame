package Tankgame2_6;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-22 15:16
 * 爆炸类
 */
public class Boom {
    //生命周期
    int life=12;
    //炸弹坐标
    int x,y;
    boolean isLive=true;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifedown()
    {
        if (life > 0) {
            life--;
        }
        else
        {
            isLive=false;
        }
    }

}
