package CpuNum;

/**
 * @author 必燃
 * @version： 1.0
 * @create 2022-04-14 17:52
 */
public class CpuNum {
    public static void main(String[] args) {
        Runtime runtime=Runtime.getRuntime();
        int cpuNum=runtime.availableProcessors();
        System.out.println(cpuNum);
    }
}
