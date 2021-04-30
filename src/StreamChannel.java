import java.util.Scanner;
import java.util.function.IntConsumer;

public class StreamChannel implements Channel {
	private final Scanner scanner = new Scanner(System.in);
	private final Object scannerLock = new Object();
	private final boolean debug;
	private volatile long startTime;
	private volatile int mainToken;
	private volatile boolean hasMainToken = false;

	public StreamChannel(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void set(int token) {
		final long stop = System.currentTimeMillis();
		final int timedToken = (int) Math.sqrt(token);
		if (debug && hasMainToken && mainToken == timedToken) {
			System.err.printf("\ttime for token %d: %dms\n", timedToken, stop - startTime);
			hasMainToken = false;
		}
		System.out.println(token);
	}

	@Override
	public void consume(IntConsumer consumer) {
		Integer value = null;
		while (hasMainToken) {}
		synchronized (scannerLock) {
			if (scanner.hasNextLine()) {
				value = Integer.parseInt(scanner.nextLine());
			}
		}
		if (value != null) {
			if (debug) {
				startTime = System.currentTimeMillis();
			}
			mainToken = value;
			hasMainToken = true;
			consumer.accept(value);
		}
	}
}
