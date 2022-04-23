package Work1;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-04-17 9:59
 * 在main中启动两个线程
 *  第一个线程循环随机打印100以内整数
 *  直到第二个从键盘读取Q命令
 */
public class Homework01{

    public static void main(String[] args) {
        PrintNum printNum = new PrintNum();
        printNum.start();
        A2 a2 = new A2(printNum);
        a2.start();
    }


}
class PrintNum extends Thread
{
    boolean flag=true;
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    @Override
    public void run() {
        while(flag)
        {
            Random random = new Random(25);
//            System.out.println(Thread.currentThread().getName()+" 数字"+random.nextInt(100));
            System.out.println((int)(Math.random()*100+1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("结束、、、");
    }
}

class A2 extends Thread
{
    PrintNum pn;
    A2(PrintNum pN)
    {
        this.pn=pN;
    }
    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            String li=sc.next();
            if(li.toUpperCase().charAt(0)=='Q')
            {
                pn.setFlag(false);
                System.out.println("程序退出");
                break;
            }

        }

    }


}