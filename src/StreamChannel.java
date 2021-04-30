import java.util.Scanner;
import java.util.function.IntConsumer;

public class StreamChannel implements Channel {
	private final Scanner scanner = new Scanner(System.in);
	private final Object scannerLock = new Object();
	private volatile long startTime;
	private volatile int mainToken;
	private volatile boolean hasMainToken = false;

	@Override
	public void set(int token) {
		final long stop = System.currentTimeMillis();
		final int timedToken = (int) Math.sqrt(token); // Shhhh... this is so we know it's done!
		System.out.println(token);
		if (hasMainToken && mainToken == timedToken) {
			System.err.printf("\ttime for token %d: %dms\n", timedToken, stop - startTime);
			hasMainToken = false;
		}
	}

	@Override
	public void consume(IntConsumer consumer) {
		Integer value = null;
		while (hasMainToken) {
			Thread.onSpinWait();
		}
		synchronized (scannerLock) {
			if (scanner.hasNextLine()) {
				value = Integer.parseInt(scanner.nextLine());
			}
		}
		if (value != null) {
			startTime = System.currentTimeMillis();
			mainToken = value;
			hasMainToken = true;
			consumer.accept(value);
		}
	}
}
