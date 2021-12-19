package com.caffeineshawn;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner sc = new Scanner(System.in);
        System.out.printf("n: ");
        int numsOfLoots = sc.nextInt();

        int[] values = new int[numsOfLoots];
        int[] weights = new int[numsOfLoots];
        System.out.printf("values: ");
        for (int i = 0; i < numsOfLoots; i++) {
            values[i] = sc.nextInt();
        }
        System.out.printf("weights: ");
        for (int i = 0; i < numsOfLoots; i++) {
            weights[i] = sc.nextInt();
        }

        LinkedList<Integer> res = new LinkedList<>();
        int[][] dp = new int[numsOfLoots + 1][numsOfLoots + 1];
        boolean[][] path = new boolean[numsOfLoots + 1][numsOfLoots + 1];
        for (int i = 1; i <= numsOfLoots; i++) {
            int curValue = values[i - 1];
            int curWeight = weights[i - 1];
            for (int j = 1; j <= numsOfLoots; j++) {
                if (curWeight > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    if (dp[i - 1][j] < dp[i - 1][j - curWeight] + curValue) {
                        path[i][j] = true;
                        dp[i][j] = dp[i - 1][j - curWeight] + curValue;
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }

        for (int i = numsOfLoots; i > 0; i--) {

            for (int j = numsOfLoots; j > 0; j--) {
                if (path[i][j]) {
                    res.add(0, values[i-1]);
                    break;
                }
            }
        }
        System.out.printf("背包中的货物价值为: ");
        for (int num : res) {
            System.out.printf("%d ", num);
        }



    }
}
