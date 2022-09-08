package main;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Backup {

	static LinkedHashSet<Integer> nums = new LinkedHashSet<Integer>();	
	static int maxPrime = 100_000_000;
	static int maxRoot = (int)Math.sqrt(maxPrime);
	static int prevKey = 2;
	static int sampleSize = 25;
	static int warmupBuffer = 10;
	static void populateNums() {
		nums.add(2);
		for (int i = 3; i < maxPrime; i+=2) {
			if (i % 3 != 0) { 
				nums.add(i);
			}
		}
	}
	
	static void findNextKey() {
		for (int val : nums) {
			if (val > prevKey) {
				prevKey = val;
				break;
			}
		}
	}
	
	static void checkMults() {
		for (int i = 2; i <= maxPrime / prevKey; i++) {
			if (nums.contains(i * prevKey)) nums.remove(i * prevKey);
		}
	}
	
	static long findPrimes() {
		prevKey = 2;
		long startTime = System.currentTimeMillis();
		nums.clear();
		populateNums();
		
		while (prevKey < maxRoot) {
			findNextKey();
			checkMults();
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	static void printAverage(ArrayList<Long> times) {
		long total = 0;
		for (long time : times) {
			total += time;
		}
		long average = total / times.size();
		long minutes = (average/1000) / 60;
		long seconds = (average/1000) % 60;
		System.out.println("Average time: " + minutes + " Minutes and " + seconds + " Seconds");
	}
	
	
	public static void main(String[] args) {
		ArrayList<Long> times = new ArrayList<Long>();
		for (int i = 0; i < sampleSize + warmupBuffer; i++) {
			if (i >= warmupBuffer)  {
				times.add(findPrimes());
				System.out.println(i + 1 - warmupBuffer);
			}
		}
		printAverage(times);
	}

}
