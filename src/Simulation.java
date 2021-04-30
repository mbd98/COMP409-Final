import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Simulation {
	private Simulation() {}

	private static volatile ExecutorService exec;

	public static ExecutorService getExec() {
		return exec;
	}

	public static void start() {
		final Actor sq = Factory.createActor("square");
		final Channel io = Factory.createChannel("stream");
		sq.connectIn(io, 0);
		sq.connectOut(io, 0);

		exec.execute(sq);
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
		start();
	}
}
