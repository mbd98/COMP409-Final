import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Simulation {
	private Simulation() {}

	private static volatile ExecutorService exec;
	private static volatile Channel standardIOChannel;
	private static volatile Actor squareTop;

	public static ExecutorService getExec() {
		return exec;
	}

	public static Channel getStandardIOChannel() {
		return standardIOChannel;
	}

	public static void start() {
		exec.execute(squareTop);
	}

	public static void main(String[] args) {
		final int t;
		if (args.length != 1) {
			throw new IllegalArgumentException("Need thread count");
		}
		t = Integer.parseInt(args[0]);
		if (t <= 0) {
			throw new IllegalArgumentException("Need t > 0");
		}
		exec = Executors.newFixedThreadPool(t);
		squareTop = Factory.createActor("square");
		standardIOChannel = Factory.createChannel("stream");
		squareTop.connectIn(standardIOChannel, 0);
		start();
	}
}
