package Work1;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-17 9:32
 */
public class DeadLock
{
    public static void main(String[] args) {
        DeadLockDemo A = new DeadLockDemo(true);
        A.setName("线程A");
        DeadLockDemo B = new DeadLockDemo(false);
        B.setName("线程B");
        A.start();
        B.start();
    }
}
 class DeadLockDemo extends Thread {
    boolean flag=false;
    static Object o1=new Object();
    static Object o2=new Object();
    public DeadLockDemo(boolean flag)
    {
        this.flag=flag;
    }

    @Override
    public void run() {
        if(flag)
        {
            //两锁分开 并不会造成死锁 执行完第一个就会释放这个锁
//            synchronized (o1)
//            {
//                System.out.println(Thread.currentThread().getName()+"进入o1...");
//            }
//            synchronized (o2)
//            {
//                System.out.println(Thread.currentThread().getName()+"进入o2...");
//            }
            //两锁嵌套才会发生死锁，因为进入o1后 就进入了一个线程 必须要完成后才能释放o1锁
            synchronized (o1)
            {
                System.out.println(Thread.currentThread().getName()+"进入o1...");
                synchronized (o2)
                {
                    System.out.println(Thread.currentThread().getName()+"进入o2...");
                }
            }

        }
        else
        {
            synchronized (o2)
            {
                System.out.println(Thread.currentThread().getName()+"进入o2...");
                synchronized (o1)
                {
                    System.out.println(Thread.currentThread().getName()+"进入o1...");
                }
            }

        }
    }
}
