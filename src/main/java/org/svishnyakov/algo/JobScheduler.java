package org.svishnyakov.algo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Week 1: Greedy algorithms
 *
 * In this programming problem and the next you'll code up the greedy algorithms from lecture for minimizing the weighted
 * sum of completion times..
 */
public class JobScheduler {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Job> jobs = loadJobs("/algo/jobs.txt");

        System.out.println("Sum by difference: " + findMinSumByDifference(jobs));
        System.out.println("Sum by ratio: " + findMinSumByRatio(jobs));
    }

    static long findMinSumByDifference(List<Job> jobs) {
        Stream<Job> sortedJobs = jobs.stream().sorted(comparing((Job job) -> job.weight - job.length)
                                                              .thenComparing(job -> job.weight)
                                                              .reversed());
        AtomicLong sum = new AtomicLong(0);
        AtomicLong completionTime = new AtomicLong(0);

        sortedJobs.forEach(job -> {
            completionTime.addAndGet(job.length);
            sum.addAndGet(job.weight * completionTime.get());
        });

        return sum.get();
    }

    static long findMinSumByRatio(List<Job> jobs) {
        Stream<Job> sortedJobs = jobs.stream().sorted(comparing((Job job) -> job.weight / (double) job.length)
                                                              .thenComparing(job -> job.weight)
                                                              .reversed());
        AtomicLong sum = new AtomicLong(0);
        AtomicLong completionTime = new AtomicLong(0);

        sortedJobs.forEach(job -> {
            completionTime.addAndGet(job.length);
            sum.addAndGet(job.weight * completionTime.get());
        });

        return sum.get();
    }

    static class Job {
        final long weight;
        final long length;

        Job(long weight, long length) {
            this.weight = weight;
            this.length = length;
        }
    }

    private static List<Job> loadJobs(String path) throws URISyntaxException, IOException {
        List<Job> jobs = new ArrayList<>();

        try(Scanner scanner = new Scanner(Paths.get(CountInversion.class.getResource(path).toURI()))) {
            int numberOfJobs = scanner.nextInt();
            for (int i = 0; i < numberOfJobs; i++) {
                jobs.add(new Job(scanner.nextLong(), scanner.nextLong()));
            }
        }

        return jobs;
    }
}
