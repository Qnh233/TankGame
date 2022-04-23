package Tankgame6;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-22 15:27
 *  6.0 想实现一个 herotank 同时开多枪。
 */
public class EnemyTank extends Tank {

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
    }

}
