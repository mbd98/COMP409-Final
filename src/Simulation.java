import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Simulation {
	private Simulation() {}

	private static volatile int t;
	private static volatile ExecutorService exec;
	private static volatile Channel standardIOChannel;

	public static ExecutorService getExec() {
		return exec;
	}

	public static Channel getStandardIOChannel() {
		return standardIOChannel;
	}

	public static void start() {
		final Actor sq = Factory.createActor("square");
		standardIOChannel = Factory.createChannel("stream");
		sq.connectIn(standardIOChannel, 0);
		//sq.connectOut(standardIOChannel, 0);

		exec.execute(sq);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Need thread count");
		}
		t = Integer.parseInt(args[0]);
		if (t <= 0) {
			throw new IllegalArgumentException("Need t > 0");
		}
		exec = Executors.newFixedThreadPool(t);
		start();
	}
}
