package com.bodgod;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTask {

    public static void main(String[] args) {

        // Використовуємо runAsync() для виведення повідомлення про старт задач
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            System.out.println("Asynchronous tasks started...");
        });

        // supplyAsync() - Завантажуємо список чисел для обчислення факторіалів
        List<Integer> numbers = Arrays.asList(5, 7, 3, 6, 8, 4);
        CompletableFuture<List<Integer>> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Loading numbers for factorial calculation...");
            return numbers;
        });

        // thenApplyAsync() - Обчислюємо факторіали для кожного числа
        CompletableFuture<List<Long>> task3 = task2.thenApplyAsync(numbersList -> {
            System.out.println("Calculating factorials...");
            return computeFactorials(numbersList);
        });

        // thenAcceptAsync() - Виводимо обчислені факторіали на екран
        CompletableFuture<Void> task4 = task3.thenAcceptAsync(factorials -> {
            System.out.println("Calculated factorials:");
            factorials.forEach(factorial -> System.out.println(factorial));
        });

        // thenRunAsync() - Виводимо повідомлення про завершення всіх асинхронних задач
        CompletableFuture<Void> task5 = task4.thenRunAsync(() -> {
            System.out.println("All asynchronous tasks completed.");
        });

        // Чекаємо, поки всі асинхронні задачі завершаться
        try {
            // Чекаємо на завершення всіх задач
            CompletableFuture.allOf(task1, task5).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Метод для обчислення факторіала числа
    private static List<Long> computeFactorials(List<Integer> numbers) {
        return numbers.stream()
                .map(CompletableFutureTask::factorial)
                .toList();
    }

    // Обчислення факторіала числа
    private static long factorial(int number) {
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}
