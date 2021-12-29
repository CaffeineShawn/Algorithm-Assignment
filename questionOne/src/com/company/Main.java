package com.company;

import java.util.Scanner;

// 定义点
class myPoint {
    int x, y;

    public myPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    // 定义填充的数字，一开始的特殊方格为1，后来在区块交界处放置L型骨牌，初始填充数字为2
    static int currentNumber = 2;

    public static void main(String[] args) {

        // 校验输入长度
        int n = -1;
        while (n == -1) {
            System.out.print("输入矩阵的边长(2^n, n in 0...10), n: ");
            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
            if (n < 0 || n > 10) {
                n = -1;
            }
        }

        // 转换为棋盘的长度（2^n）
        int len = n == 0 ? 1 : 2 << (n - 1);

        // 校验输入坐标
        int[][] board = new int[len][len];
        int x = -1, y = -1;
        while (x == -1 || y == -1) {
            System.out.print("输入特殊方格的坐标(x, y): ");
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt();
            y = sc.nextInt();
            if (x < 0 || x > len - 1 || y < 0 || y > len - 1) {
                x = -1;
                y = -1;
                System.out.println("请确认输入的坐标有效！ 0<=x,y<" + len);
            }
        }

        // 打印输入坐标
        System.out.println("矩阵的边长为"+ len);

        // 设置特殊方格为输入坐标
        myPoint mutatedPoint = new myPoint(x, y);

        // 执行棋盘覆盖
        int[][] res = chessBoard(len, mutatedPoint, board);

        // 输出结果
        System.out.println("    y");
        for (int i = 0; i < len; i++) {
            System.out.printf("%4d|",i);
            for (int j = 0; j < len; j++) {
                System.out.printf("% 6d", res[i][j]);
            }
            System.out.print("\n");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("输入任意字符回车退出: ");
        scanner.next();
    }

    static int[][] chessBoard(int len, myPoint mutatedPoint, int[][] board) {

        if (len == 1) {
            board[0][0] = 1;
            return board;
        }

        //  填充棋盘中的特殊方格
        board[mutatedPoint.y][mutatedPoint.x] = 1;

        // 递归执行棋盘覆盖算法
        helper(0,len-1,0,len-1,mutatedPoint, board);
        return board;
    }

    // 填充棋盘算法，定义了填充方向如下（由特殊方格所在区块的边界点为basePoint）
    // 假设此时特殊方格所在的点为3，则其填充方向为0
    //              -----
    //              |0|1|
    //              _____
    //              |2|3|
    //              -----
    static void fill(myPoint basePoint, int fillDirection, int[][] board) {
        switch (fillDirection) {
            case 0: {
                board[basePoint.y][basePoint.x - 1] = currentNumber;
                board[basePoint.y - 1][basePoint.x - 1] = currentNumber;
                board[basePoint.y - 1][basePoint.x] = currentNumber;
                break;
            }
            case 1: {
                board[basePoint.y][basePoint.x + 1] = currentNumber;
                board[basePoint.y - 1][basePoint.x + 1] = currentNumber;
                board[basePoint.y - 1][basePoint.x] = currentNumber;
                break;
            }
            case 2: {
                board[basePoint.y][basePoint.x - 1] = currentNumber;
                board[basePoint.y + 1][basePoint.x - 1] = currentNumber;
                board[basePoint.y + 1][basePoint.x] = currentNumber;
                break;
            }
            case 3: {
                board[basePoint.y + 1][basePoint.x] = currentNumber;
                board[basePoint.y + 1][basePoint.x + 1] = currentNumber;
                board[basePoint.y][basePoint.x + 1] = currentNumber;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + fillDirection);
        }
    }


    // 寻找特殊方格所在区块并填充
    static void helper(int leftX, int rightX, int lowerY, int upperY, myPoint mutatingPoint, int[][] board) {

        int x = -1, y = -1;

        // 此时问题规模为2x2棋盘，只需放置骨牌
        if (rightX - leftX + 1 == 2) {
            for (int i = lowerY; i <= upperY; i++) {
                for (int j = leftX; j <= rightX; j++) {
                    if (board[i][j] != 0) {
                        x = j;
                        y = i;
                    }
                }
            }

            // 由特殊方格区块边界点作为basePoint，定义填充方向并填充
            if (x == rightX && y == upperY) {
                fill(new myPoint(x, y), 0, board);
            } else if (x == leftX && y == upperY) {
                fill(new myPoint(x, y), 1, board);
            } else if (x == rightX && y == lowerY) {
                fill(new myPoint(x, y), 2, board);
            } else {
                fill(new myPoint(x, y), 3, board);
            }
            return;
        } else {
            // 此时问题规模大于2x2棋盘，需放置骨牌后递归

            // 定义中心点坐标（
            int midX = (rightX - leftX) / 2 + leftX;
            int midY = (upperY - lowerY) / 2 + lowerY;
            //              -----
            //              |0|1|
            //              _____
            //              |2|3|
            //              -----
            if (mutatingPoint.x <= midX && mutatingPoint.y <= midY) {
                // 特殊方格区块为0
                // 由特殊方格区块边界点作为basePoint，定义填充方向并填充
                // 此时basePoint为0，填充方向为3
                fill(new myPoint(midX, midY), 3, board);

                // 递归填充各块
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, mutatingPoint, board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX+1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else if (mutatingPoint.x > midX && mutatingPoint.y <= midY) {
                // 特殊方格区块为1
                // 由特殊方格区块边界点作为basePoint，定义填充方向并填充
                // 此时basePoint为1，填充方向为2
                fill(new myPoint(midX + 1, midY), 2, board);

                // 递归填充各块
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, mutatingPoint, board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else if (mutatingPoint.x <= midX && mutatingPoint.y > midY) {
                // 特殊方格区块为2
                // 由特殊方格区块边界点作为basePoint，定义填充方向并填充
                // 此时basePoint为2，填充方向为1
                fill(new myPoint(midX, midY + 1), 1, board);

                // 递归填充各块
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX + 1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, mutatingPoint, board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else {
                // 特殊方格区块为3
                // 由特殊方格区块边界点作为basePoint，定义填充方向并填充
                // 此时basePoint为3，填充方向为0
                fill(new myPoint(midX + 1, midY + 1), 0, board);

                // 递归填充各块
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX + 1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, mutatingPoint, board); // 3

            }
        }
    }
}