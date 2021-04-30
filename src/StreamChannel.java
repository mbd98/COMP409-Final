import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntConsumer;

public class StreamChannel implements Channel {
	private final Scanner scanner = new Scanner(System.in);
	private final Object scannerLock = new Object();
	private final Map<Integer, Long> tokenTimes = new ConcurrentHashMap<>();

	@Override
	public void set(int token) {
		final long stop = System.currentTimeMillis();
		if (tokenTimes.containsKey(token))
			System.err.printf("\ttime for token %d: %dms\n", token, stop - tokenTimes.remove(token));
		System.out.println(token);
	}

	@Override
	public void consume(IntConsumer consumer) {
		Integer value = null;
		synchronized (scannerLock) {
			if (scanner.hasNextLine()) {
				value = Integer.parseInt(scanner.nextLine());
			}
		}
		if (value != null) {
			tokenTimes.put(value, System.currentTimeMillis());
			consumer.accept(value);
		}
	}
}
