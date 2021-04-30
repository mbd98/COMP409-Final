import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.IntConsumer;

public class QueueChannel implements Channel {
	private final Deque<Integer> inbox = new ArrayDeque<>(10);
	private final Deque<IntConsumer> consumers = new ArrayDeque<>(10);

	private void runNextConsumer() {
		final int nextToken;
		final IntConsumer nextConsumer;
		if (!inbox.isEmpty() && !consumers.isEmpty()) {
			nextToken = inbox.removeFirst();
			nextConsumer = consumers.removeFirst();
			Simulation.getExec().execute(() -> nextConsumer.accept(nextToken));
		}
	}

	@Override
	public synchronized void set(int token) {
		inbox.addLast(token);
		runNextConsumer();
	}

	@Override
	public synchronized void consume(IntConsumer consumer) {
		consumers.addLast(consumer);
		runNextConsumer();
	}
}
