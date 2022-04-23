package Tankgame2;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-13 15:37
 */
public class EnemyTank extends Tank{
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
    }
}
