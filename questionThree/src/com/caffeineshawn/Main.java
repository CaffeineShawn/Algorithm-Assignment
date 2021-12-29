package com.caffeineshawn;

import java.util.Scanner;

import static java.lang.Integer.max;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入物品的数量: ");
        int numOfLoots = sc.nextInt();

        System.out.print("请输入背包的容量: ");
        int capacity = sc.nextInt();

        int[] values = new int[numOfLoots];
        int[] weights = new int[numOfLoots];
        System.out.print("请输入各个物品的价值: ");
        for (int i = 0; i < numOfLoots; i++) {
            values[i] = sc.nextInt();
        }
        System.out.print("请输入各个物品的重量: ");
        for (int i = 0; i < numOfLoots; i++) {
            weights[i] = sc.nextInt();
        }

        int[][] dp = new int[numOfLoots + 1][capacity + 1];

        for (int i = 1; i <= numOfLoots; i++) {
            // 设置变量为当前物品的重量、价值
            int curValue = values[i - 1];
            int curWeight = weights[i - 1];

            for (int j = 1; j <= capacity; j++) {
                if (j >= curWeight) {
                    // 如果容量为j的背包放得下当前物品
                    dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - curWeight] + curValue);
                } else {
                    // 放不下，继承之前的结果
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 回溯路径
        boolean[] path = new boolean[numOfLoots + 1];
        int availableCapacity = capacity;
        for(int i = numOfLoots; i >= 1; i--) {
            if(dp[i][availableCapacity] > dp[i-1][availableCapacity]) {
                // 放入后价值增加
                path[i] = true;
                availableCapacity -= weights[i-1];
            }
        }

        // 输出
        System.out.print("背包中的货物为: ");
        for (int i = 1; i <= numOfLoots; i++) {
            if (path[i]) {
                System.out.printf("第%d件 ", i);
            }
        }
        System.out.print("\n");
        System.out.println("总价值为: " + dp[numOfLoots][capacity]);

        Scanner scanner = new Scanner(System.in);
        System.out.print("输入任意字符回车退出: ");
        scanner.next();
    }
}
