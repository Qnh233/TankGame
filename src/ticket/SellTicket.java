package ticket;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-15 22:42
 */
public class SellTicket
        {
            public static void main(String[] args) {

                SellTicket01 ticket01 = new SellTicket01();
                SellTicket01 ticket02 = new SellTicket01();
                SellTicket01 ticket03 = new SellTicket01();
                //
                ticket01.start();
                ticket02.start();
                ticket03.start();
            }
        }
class SellTicket01 extends Thread{

    private static int ticketNum = 100;

    @Override
    public void run() {
        while(true)
        {
            if(ticketNum<=0)
            {
                System.out.println("售票结束。");
                break;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口 "+Thread.currentThread().getName()+"售出一张票"
            + "剩余票数="+(--ticketNum));
        }
    }
}
