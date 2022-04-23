package ticket;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-16 20:03
 *   使用synchronizd 加入同步锁 保证不会超卖 但效率变低。
 */
public class SellTicket_2
{
    public static void main(String[] args) {

        SellTicket02 ticket01 = new SellTicket02();
        SellTicket02 ticket02 = new SellTicket02();
        SellTicket02 ticket03 = new SellTicket02();

        //
        ticket01.start();
        ticket02.start();
        ticket03.start();

    }
}
class SellTicket02 extends Thread{

    private static int ticketNum = 100;
    private boolean bool=true;

    public synchronized void doc()
    {
        if(ticketNum<=0)
        {
            System.out.println("售票结束。");
            bool=false;
            return;
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("窗口 "+Thread.currentThread().getName()+"售出一张票"
                + "剩余票数="+(--ticketNum));
    }

    @Override
    public  void run() {
        while(bool)
        {
            doc();
        }

    }
}
class SellTicket03 implements Runnable
{

    private static int ticketNum = 100;
    private boolean bool=true;
    public synchronized void doc()
    {
        if(ticketNum<=0)
        {
            System.out.println("售票结束。");
            bool=false;
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("窗口 "+Thread.currentThread().getName()+"售出一张票"
                + "剩余票数="+(--ticketNum));
    }
    @Override
    public void run() {
        while(bool)
        {
            doc();
        }

    }
}