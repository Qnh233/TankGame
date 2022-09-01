package Tankgame_2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-08-31 15:31
 * IO工具 计分板工具类
 */
public class Recorder {

    //得分
    private static int Score = 0;
    //IO流
    private static String Txtpath = "D:\\code\\Tank\\src\\score.txt";
    private static String storePath = "D:\\code\\Tank\\src\\store.bat";
    private static BufferedWriter bw = null;

    private static Vector<EnemyTank> enemyTanks=null;
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks)
    {
        Recorder.enemyTanks=enemyTanks;
    }
    public static void recordScore() {

        try {
            bw = new BufferedWriter(new FileWriter(Txtpath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //游戏结束 记录成绩并退出循环。
        LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            bw.write(dateTime.format(formatter) + " 得分:" + Score
                    + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ObjectOutputStream OOS = null;

    /**
     * 此处尝试的是保存若干个EnemyTank对象，也可以直接保存Vector<EnemyTank> enemyTanks
     * @param enemy
     */
    public static void record(Object enemy,boolean b) {
        try {
            OOS = new ObjectOutputStream(new FileOutputStream(storePath));
            if(b)
            {
                OOS.writeInt(enemyTanks.size());
                for (int i = 0; i < enemyTanks.size(); i++) {
                    System.out.println(enemyTanks.get(i).getX()+""+enemyTanks.get(i).getY());
                    OOS.writeObject(enemyTanks.get(i));
                }
                OOS.writeInt(Score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            OOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObjectInputStream OIS= null;
    public static Vector<EnemyTank> UnRecord() {
        Vector<EnemyTank> enemys=new Vector<EnemyTank>();
        try {
            OIS = new ObjectInputStream(new FileInputStream(storePath));
            Object enemy=null;
            if(OIS.available()!=0)
           {
                int ESize=OIS.readInt();
                for (int i = 0; i < ESize; i++) {
                    enemy=OIS.readObject();
                    enemys.add((EnemyTank) enemy);
                }
                //ESIZE==0被保存的情况有 1.击毁全部目标
                if(ESize!=0)
                Score=OIS.readInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return enemys;
    }


    public static int getScore() {
        return Score;
    }

    public static void setScore(int score) {
        Score = score;
    }

    public static void addScore() {
        Recorder.Score++;
    }

}
