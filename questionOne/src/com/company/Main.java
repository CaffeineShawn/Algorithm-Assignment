package com.company;

import java.util.Scanner;

public class Main {
    static int currentNumber = 2;


    public static void main(String[] args) {
        // write your code here


        int n = -1;

        while (n == -1) {
            System.out.print("输入矩阵的边长(2^n, n in 0...10), n: ");
            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
            if (n < 0 || n > 10) {
                n = -1;
            }
        }
        int len = 2 << (n - 1);
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
        System.out.println("矩阵的边长为"+ len);
        myPoint mutatedPoint = new myPoint(x, y);
        int[][] res = chessBoard(len, mutatedPoint, board);
        System.out.println("   y");
        for (int i = 0; i < len; i++) {
            System.out.printf("%4d",i);
            for (int j = 0; j < len; j++) {
                System.out.printf("% 6d", res[i][j]);
            }
            System.out.println("");
        }

    }


    static int[][] chessBoard(int len, myPoint mutatedPoint, int[][] board) {


        if (len == 1) {
            board[0][0] = 1;
            return board;
        }

        board[mutatedPoint.y][mutatedPoint.x] = 1;
        helper(0,len-1,0,len-1,mutatedPoint, board);
        return board;
    }


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

    static void helper(int leftX, int rightX, int lowerY, int upperY, myPoint mutatingPoint, int[][] board) {
        int x = -1, y = -1;
        if (rightX - leftX + 1 == 2) {
            for (int i = lowerY; i <= upperY; i++) {
                for (int j = leftX; j <= rightX; j++) {
                    if (board[i][j] != 0) {
                        x = j;
                        y = i;
                    }
                }
            }


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
            int midX = (rightX - leftX) / 2 + leftX;
            int midY = (upperY - lowerY) / 2 + lowerY;
//              -----
//              |0|1|
//              _____
//              |2|3|

            if (mutatingPoint.x <= midX && mutatingPoint.y <= midY) {
                // case 0
                fill(new myPoint(midX, midY), 3, board);
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, mutatingPoint, board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX+1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else if (mutatingPoint.x > midX && mutatingPoint.y <= midY) {
                // case 1
                fill(new myPoint(midX + 1, midY), 2, board);
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, mutatingPoint, board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else if (mutatingPoint.x <= midX && mutatingPoint.y > midY) {
                // case 2
                fill(new myPoint(midX, midY + 1), 1, board);
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX + 1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, mutatingPoint, board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, new myPoint(midX + 1, midY + 1), board); // 3

            } else {
                // case 3
                fill(new myPoint(midX + 1, midY + 1), 0, board);
                currentNumber += 1;
                helper(leftX, midX, lowerY, midY, new myPoint(midX, midY), board); // 0
                currentNumber += 1;
                helper(midX + 1, rightX, lowerY, midY, new myPoint(midX + 1, midY), board); // 1
                currentNumber += 1;
                helper(leftX, midX, midY + 1, upperY, new myPoint(midX, midY + 1), board); // 2
                currentNumber += 1;
                helper(midX + 1, rightX, midY + 1, upperY, mutatingPoint, board); // 3

            }

//              _____

        }
    }




}
class myPoint {
    int x, y;

    public myPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}