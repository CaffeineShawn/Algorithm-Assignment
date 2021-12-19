package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import static java.lang.Math.min;

public class Main {

    public static void main(String[] args) {
        int jobCount = -1;
        Scanner scanner = new Scanner(System.in);
        while (jobCount < 0 ) {
            System.out.printf("请输入作业的个数: ");
            jobCount = scanner.nextInt();
        }

        int partA = -1, partB = -1;
        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 1; i <= jobCount; i++) {
            System.out.printf("输入作业" + i + "的a部分工作量: ");
            partA = scanner.nextInt();
            while (partA < 0) {
                System.out.printf("输入作业" + i + "的a部分工作量(大于等于0): ");
                partA = scanner.nextInt();
            }
            System.out.printf("输入作业" + i + "的b部分工作量: ");
            partB = scanner.nextInt();
            while (partB < 0) {
                System.out.printf("输入作业" + i + "的b部分工作量(大于等于0): ");
                partB = scanner.nextInt();

            }

            jobs.add(new Job(partA, partB, i));
        }

//        Job job1 = new Job(2,6, 1);
//        Job job2 = new Job(3,3,2);
//        Job job3 = new Job(5,4,3);
        // write your code here

//        jobs.add(job1);
//        jobs.add(job2);
//        jobs.add(job3);

        

        ArrayList<Job> bJob = new ArrayList<>();
        ArrayList<Job> aJob = new ArrayList<>();
        for (Job job : jobs) {
            System.out.println(job.id);
            if (job.b > job.a) {
                bJob.add(job);
            } else {
                aJob.add(job);
            }
        }

        Collections.sort(bJob, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2) {
                return o2.a - o1.a;
            }
        });

        Collections.sort(aJob, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2) {
                return o1.a == o1.b ? -1 : o2.a == o2.b ? -1 : o2.b - o1.b > 0 ? 1 : -1;
            }
        });
        ArrayList<Job> sortedJobs = new ArrayList<>();
        sortedJobs.addAll(bJob);
        sortedJobs.addAll(aJob);


        for (Job job : sortedJobs) {
            System.out.println(job.id);
        }
        boolean exit = false;
        int consumedTime = 0;
        int m1index = 0, m2index = -1;
        int timeSlice = 0;
        int m1 = sortedJobs.get(0).a, m2 = 0;
        while (!exit) {
            if (m1index > sortedJobs.size() - 1 && m2index > sortedJobs.size() - 1) {
                exit = true;
            }

            timeSlice = m1 <= 0 ? m2 : m2 <= 0 ? m1 : min(m1, m2);


            System.out.println("Consumed time so far: " + consumedTime);
            if (timeSlice != 0) {
                log(timeSlice, m1, m2);

            }


            consumedTime += timeSlice;
            if (m1 != 0) {
                m1 -= timeSlice;
            }
            if (m2 != 0) {
                m2 -= timeSlice;
            }


            if (m1 == 0) {
                if (m2 == 0) {
                    m1index += 1;
                    if (m1index < sortedJobs.size()) {
                        m1 = sortedJobs.get(m1index).a;
                    }
                }
            }

            if (m2 == 0) {
                m2index += 1;
                if (m2index < sortedJobs.size()) {

                    m2 = sortedJobs.get(m2index).b;
                }
            }


            // waiting for m2...
            if (m1index - m2index > 1) {
                m1index -= 1;
                m1 = 0;
            }
        }
    }


    static void log(int timeSlice, int m1, int m2) {
        System.out.println("timeSlice: " + timeSlice + ", m1: " + m1 + ", m2: " + m2);
    }




}


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

