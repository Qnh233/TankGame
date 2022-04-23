package Work1;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-16 19:00
 */
public class ThreadMethodExercise {
    public static void main(String[] args) throws InterruptedException {
        Thread t3=new T3();t3.start();
        for (int i = 1; i < 11; i++) {
            System.out.println("hi"+i);
            Thread.sleep(1000);
            if(i==5)
            {

                t3.join();
            }

        }
        System.out.println("主线程结束...");

    }
}
class T3 extends Thread
{
    @Override
    public void run() {
        int i=1;
        while (true)
        {
            System.out.println("hello"+(i++));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i==11)
            {
                System.out.println("子线程结束...");
                break;
            }
        }
    }
}