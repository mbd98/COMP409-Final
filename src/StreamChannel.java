import java.util.Scanner;
import java.util.function.IntConsumer;

public class StreamChannel implements Channel {
	private final Scanner scanner = new Scanner(System.in);
	private final Object scannerLock = new Object();

	@Override
	public void set(int token) {
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
			consumer.accept(value);
		}
	}
}
