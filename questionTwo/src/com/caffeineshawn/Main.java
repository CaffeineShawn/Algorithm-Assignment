package com.caffeineshawn;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 处理用户输入
        int k = -1;
        Scanner sc = new Scanner(System.in);
        while (k == -1) {
            System.out.print("输入k的值（0<k<=10）: ");
            k = sc.nextInt();
            if (k > 10 || k <= 0) {
                k = -1;
            }
        }
        
        // 转化为参赛选手数量
        int n = 2 << (k - 1);

        System.out.println("参赛选手共" + n + "位");
        int[][] schedule = new int[n][n];
            // 初始化最小左上区
            schedule[0][0] = 1;
            schedule[0][1] = 2;
            schedule[1][0] = 2;
            schedule[1][1] = 1;
            
            
            int totalTimesOfExecution = -1, length = schedule.length;
            
            // 计算出算法执行的次数
            while (length > 0) {
                totalTimesOfExecution++;
                length = length >> 1;
            }

            for (int currentTimesOfExecution = 2; currentTimesOfExecution <= totalTimesOfExecution; currentTimesOfExecution++) {
                int currentLength = 1 << currentTimesOfExecution;
                // 计算出半区的长度
                int halfCurrentLength = currentLength >> 1;

                // 由左上区计算出右上区
                for (int i = 0; i < halfCurrentLength; i++) {
                    for (int j = 0; j < halfCurrentLength; j++) {
                        schedule[i + halfCurrentLength][j] = schedule[i][j] + halfCurrentLength;
                    }
                }

                // 拷贝左下区到右上区
                for (int i = 0; i < halfCurrentLength; i++) {
                    System.arraycopy(schedule[i + halfCurrentLength], 0, schedule[i], halfCurrentLength, halfCurrentLength);
                }

                // 拷贝右下区到左上区
                for (int i = 0; i < halfCurrentLength; i++) {
                    System.arraycopy(schedule[i], 0, schedule[i + halfCurrentLength], halfCurrentLength, halfCurrentLength);
                }
            }

            // 输出循环赛结果
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j == 0) {
                        System.out.printf("选手%-6d", i + 1);
                    } else {
                        System.out.printf("% 5d",schedule[i][j]);
                    }

                }
                System.out.print("\n");
            }

        Scanner scanner = new Scanner(System.in);
        System.out.print("输入任意字符回车退出: ");
        scanner.next();
    }
}
