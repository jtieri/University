import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String runAgain;
        long startTime;
        double durationTime;

        do {
            System.out.println("Welcome to the prime number finder. ");
            System.out.println("We will find all the prime numbers up to 10000 for you. ");
            System.out.println("You can choose to run the program in 1 thread, 100 threads, or 1000 threads. ");
            System.out.println("  ");
            System.out.println("Press 1 to run the program in a single thread... ");
            System.out.println("Press 2 to run the program in 100 threads... ");
            System.out.println("Press 3 to run the program in 1000 threads... ");

            int userChoice = keyboard.nextInt();
            keyboard.nextLine();

            System.out.println("-------------------------------------------------------------------------------- ");

            switch(userChoice) {
                case 1:
                    // Single Thread
                    startTime = System.nanoTime();

                    PrimeFinder pf = new PrimeFinder(2, 10000);

                    pf.start();

                    try { pf.join(); } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (Integer i : pf.getPrimeNums()) {
                        System.out.println("Prime number: " + i);
                    }

                    durationTime = System.nanoTime()-startTime;
                    System.out.printf("Single threaded solution ran for %f seconds \n", durationTime /1000000000);

                    System.out.println("-------------------------------------------------------------------------------- ");

                    break;
                case 2:
                    // 100 Threads
                    // Each thread tests 100 numbers
                    startTime = System.nanoTime();

                    PrimeFinder[] primeFinders = new PrimeFinder[100];
                    int tmpStart = 0;
                    int tmpEnd = 99;

                    for (int i = 0; i < 100; i++) {

                        // My prime test breaks on 0 & 1 because of how I handle ranges.
                        // This check handles the issue by not checking 0 or 1 as prime numbers
                        if (i == 0) {
                            primeFinders[i] = new PrimeFinder(tmpStart + 2, tmpEnd);
                        } else {
                            primeFinders[i] = new PrimeFinder(tmpStart, tmpEnd);
                        }

                        tmpStart += 100;
                        tmpEnd += 100;
                        primeFinders[i].start();
                    }

                    for (PrimeFinder pfTmp : primeFinders) {
                        for (Integer i : pfTmp.getPrimeNums()) {
                            System.out.println("Prime Number: " + i);
                        }
                    }

                    durationTime = System.nanoTime()-startTime;
                    System.out.printf("100 thread solution ran for %f seconds \n", durationTime /1000000000);

                    System.out.println("-------------------------------------------------------------------------------- ");

                    break;
                case 3:
                    // 1000 Threads
                    // Each thread tests 10 numbers
                    startTime = System.nanoTime();

                    PrimeFinder[] primeFinders1 = new PrimeFinder[1000];
                    tmpStart = 0;
                    tmpEnd = 9;

                    for (int i = 0; i < 1000; i++) {

                        // My prime test breaks on 0 & 1 because of how I handle ranges.
                        // This check handles the issue by not checking 0 or 1 as prime numbers
                        if (i == 0) {
                            primeFinders1[i] = new PrimeFinder(tmpStart + 2, tmpEnd);
                        } else {
                            primeFinders1[i] = new PrimeFinder(tmpStart, tmpEnd);
                        }

                        tmpStart += 10;
                        tmpEnd += 10;
                        primeFinders1[i].start();
                    }

                    for (PrimeFinder pfTmp : primeFinders1) {
                        for (Integer i : pfTmp.getPrimeNums()) {
                            System.out.println("Prime Number: " + i);
                        }
                    }

                    durationTime = System.nanoTime()-startTime;
                    System.out.printf("1000 thread solution ran for %f seconds \n", durationTime /1000000000);

                    System.out.println("-------------------------------------------------------------------------------- ");

                    break;
                default:
                    System.out.println("You have entered an invalid operation. ");
            }

            System.out.println("Would you like to run the program again? ");
            System.out.println("Please enter Y or y to run again, any other input will terminate the program ");
            runAgain = keyboard.nextLine().trim().toLowerCase();

            System.out.println("-------------------------------------------------------------------------------- ");

        } while (runAgain.equals("y"));
    }
}

class PrimeFinder extends Thread {
    private final int start;
    private final int end;
    private final List<Integer> primeNums;

    PrimeFinder(final int start, final int end) {
        this.start = start;
        this.end = end;
        primeNums = new LinkedList<>();
    }

    public void run() {
        for (int i = this.start; i <= this.end; i++) {
            if (isPrimeNumber(i)) {
                primeNums.add(i);
            }
        }
    }

    public boolean isPrimeNumber(final int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getPrimeNums() {
        return this.primeNums;
    }
}



