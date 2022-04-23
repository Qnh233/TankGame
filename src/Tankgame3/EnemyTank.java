package Tankgame3;

import java.util.Vector;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:37
 */
public class EnemyTank extends Tank {

    public EnemyTank(int x, int y) {
        super(x, y);
    }
    boolean isLive=true;

    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
    }

    Vector<Shoot> Eshots=new Vector<>();
}
