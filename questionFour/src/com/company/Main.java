package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Job {
    int a;
    int b;
    int id;

    public Job(int a, int b, int id) {
        this.a = a;
        this.b = b;
        this.id = id;

    }
}

public class Main {

    static String log(int m1, int m2) {
        return  "m1: " + m1 + ", m2: " + m2;
    }

    public static void main(String[] args) {
        int jobCount = -1;
        Scanner scanner = new Scanner(System.in);
        while (jobCount < 0 ) {
            System.out.print("请输入作业的个数: ");
            jobCount = scanner.nextInt();
        }

        int partA, partB;
        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 1; i <= jobCount; i++) {
            System.out.print("输入作业" + i + "的a部分工作量: ");
            partA = scanner.nextInt();
            while (partA < 0) {
                System.out.print("输入作业" + i + "的a部分工作量(大于等于0): ");
                partA = scanner.nextInt();
            }
            System.out.print("输入作业" + i + "的b部分工作量: ");
            partB = scanner.nextInt();
            while (partB < 0) {
                System.out.print("输入作业" + i + "的b部分工作量(大于等于0): ");
                partB = scanner.nextInt();
            }
            // 将作业A、B部分组装成一个作业整体
            jobs.add(new Job(partA, partB, i));
        }

        // 第一部分的作业就是a部分工作量少于b部分工作量，且按a部分工作量升序排序
        ArrayList<Job> firstPartOfJobs = new ArrayList<>();

        // 第二部分的作业就是b部分工作量少于等于a部分工作量，且按b部分工作量升序排序
        ArrayList<Job> secondPartOfJobs = new ArrayList<>();

        for (Job job : jobs) {
            if (job.b > job.a) {
                firstPartOfJobs.add(job);
            } else {
                secondPartOfJobs.add(job);
            }
        }

        // 根据Johnson法则，对b工作量较大的作业按a升序排列
        firstPartOfJobs.sort(Comparator.comparingInt(o -> o.a));
        // 对a工作量大于或等于b工作量的作业按b工作量升序排列，a、b相同的情况放在末尾
        secondPartOfJobs.sort((o1, o2) -> o1.a == o1.b ? -1 : o2.a == o2.b ? -1 : o2.b - o1.b > 0 ? 1 : o2.b - o1.b == 0 ? o2.a - o1.a : -1 ) ;
        ArrayList<Job> sortedJobs = new ArrayList<>();

        // 将两部分排序后合并到新数组，就是最优流水调度
        sortedJobs.addAll(firstPartOfJobs);
        sortedJobs.addAll(secondPartOfJobs);

        // 打印排序后的作业
        for (Job job : sortedJobs) {
            System.out.printf("作业%d ", job.id);
        }

        // 换行
        System.out.print("\n");

        boolean exit = false;
        int consumedTime = 0;
        int m1index = 0, m2index = -1;
        int timeSlice = -1;
        // 让m1先拿到第一个作业的a部分
        int m1 = sortedJobs.get(0).a, m2 = 0;

        // 模拟作业调度过程
        while (!exit) {
            if (m1index > sortedJobs.size() - 1 && m2index > sortedJobs.size() - 1) {
                exit = true;
            }
            timeSlice = m1 > 0 ?
                    (m1index - m2index > 1 && m1 > m2 && m2 > 0? m2 : m1 ) : m2;
            if (timeSlice == 0) {
                break;
            }
            // 打印当前时间片大小
            System.out.println("\n\nCurrent slice: " + timeSlice);

            // 打印当前执行的作业下标，越界下标表示工作已做完
            System.out.println("m1index: "+ m1index + ", m2index: " + m2index);

            // 记录到现在为止消耗的时间
            if (timeSlice != 0) {
                System.out.println(log(m1, m2));
            }
            consumedTime += timeSlice;

            // 模拟在时间片执行作业
            m1 -= timeSlice;
            m2 -= timeSlice;

            // 打印执行后m1、m2的剩余工作时间，负数表示为工作提前完成
            System.out.println("After execution: \n" + log(m1, m2));

            // 如果m1、m2都执行完毕，则m1从排序好的作业中拿到下一个作业的a部分执行
            if (m1 <= 0) {

                    m1index += 1;
                if (m1index < sortedJobs.size()) {
                    m1 = sortedJobs.get(m1index).a;
                }
            }

            // 当m2执行完毕时，此时m1执行结果不影响m2拿到下一个作业执行
            if (m2 <= 0) {
                m2index += 1;
                if (m2index < sortedJobs.size()) {

                    m2 = sortedJobs.get(m2index).b;
                }
            }

            // 打印迄今为止所花费的时间
            System.out.printf("Consumed time so far: %d\n", consumedTime);
        }

        System.out.print("输入任意字符回车退出: ");
        scanner.next();
    }
}


