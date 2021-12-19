package com.caffeineshawn;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int k = -1;
        Scanner sc = new Scanner(System.in);
        while (k == -1) {
            System.out.printf("输入k的值（0<k<=10）: ");
            k = sc.nextInt();
            if (k > 10 || k <= 0) {
                k = -1;
            }
        }
        int n = 2 << (k - 1);

        System.out.println("n的值为" + n);
        int[][] schedule = new int[n][n];
        Map<Integer, Integer> map = new HashMap<>();


            schedule[0][0] = 1;
            schedule[0][1] = 2;
            schedule[1][0] = 2;
            schedule[1][1] = 1;

//            for (int i = 0; i < n; i++) {
//                schedule[i][0] = i + 1;
//            }
            int power = -1, size = schedule.length;
            while (size > 0) {
                power++;
                size = size >> 1;
            }
            for (int cur = 2; cur <= power; cur++) {
                int len = 1 << cur;
                int half = len >> 1;

                for (int i = 0; i < half; i++) {
                    for (int j = 0; j < half; j++) {
                        schedule[i + half][j] = schedule[i][j] + half;
                    }
                }
                for (int i = 0; i < half; i++) {
                    for (int j = 0; j < half; j++) {
                        schedule[i][j + half] = schedule[i + half][j];
                    }
                }
                for (int i = 0; i < half; i++) {
                    for (int j = 0; j < half; j++) {
                        schedule[i + half][j + half] = schedule[i][j];
                    }
                }
            }




            // out
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j == 0) {
                        System.out.printf("选手%d", i + 1);
                    } else {
                        System.out.printf("% 4d",schedule[i][j]);
                    }

                }
                System.out.println("");
            }

    }
}
