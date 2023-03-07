package org.example.utils;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncTaskUtil {

	private static final int corePoolSize = Runtime.getRuntime().availableProcessors();//CPU核心数
	private static final int maximumPoolSize = corePoolSize * 2;
	private static long keepAliveTime = 1;
	private static TimeUnit keepAliveTimeUnit = TimeUnit.MINUTES;
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1024);
	private static RejectedExecutionHandler rejectedHandler = new ThreadPoolExecutor.CallerRunsPolicy();

	/**
	 * corePoolSize : 线程池核心线程数，最好默认CPU核心数
	 * maximumPoolSize : 线程池最大线程数，最好是核心线程数的两倍，太多会引起线程切换
	 * keepAliveTime : 大于核心线程数的空闲线程存活时间
	 * keepAliveTimeUnit : 空闲线程存活时间的单位（秒、分钟、小时等等）
	 * workQueue : 线程池有界队列，新任务没有可用线程处理时会把任务放到该队列中，等待被处理
	 * rejectedHandler : 拒绝处理策略，默认直接丢弃并抛出异常-AbortPolicy，调用者线程直接处理-CallerRunsPolicy
	 */
	private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
			corePoolSize,
			maximumPoolSize,
			keepAliveTime,
			keepAliveTimeUnit,
			workQueue,
			rejectedHandler
	);

	/**
	 * 直接提交任务，无返回值
	 * @param task
	 */
	public static void submit(Runnable task){
		poolExecutor.submit(task);
	}

	/**
	 * 提交任务，返回泛型结果
	 * @param task
	 * @param <T>
	 * @return
	 */
	public static <T> Future<T> submit(Callable<T> task){
		return poolExecutor.submit(task);
	}

	/**
	 * 直接提交任务，无返回值
	 * @param task
	 */
	public static void execute(Runnable task){
		poolExecutor.execute(task);
	}

	public static void main(String[] args) throws InterruptedException {
		List<String> list = Lists.newArrayList();
		int size = 9998;
		for (int i = 0; i < size; i++) {
			list.add("hello-" + i);
		}
		// 大集合里面包含多个小集合
		List<List<String>> temps = SplitListUtils.split(list, 500);
		// 对大集合里面的每一个小集合进行操作
		for (List<String> obj : temps) {
			AsyncTaskUtil.execute(() -> {
				try {
					Thread.sleep(2000);
					System.out.println(obj.get(0) + " ");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
			System.out.println("\r\n");
			System.out.println("\r\n");
		}

		Thread.sleep(50000);

		System.out.println("stop~~~~~~~");
	}
}
