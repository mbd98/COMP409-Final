import java.util.Map;
import java.util.TreeMap;

public class DoubleMinusOneActor implements Actor {
	private volatile Channel in, out;

	@Override
	public void connectIn(Channel c, int i) {
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		in = c;
	}

	@Override
	public void connectOut(Channel c, int i) {
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		out = c;
	}

	@Override
	public void run() {
		final Map<String, Actor> actorMap = new TreeMap<>();
		actorMap.put("inFork", Factory.createActor("fork"));
		actorMap.put("doubler", Factory.createActor("add"));
		actorMap.put("dec", Factory.createActor("dec"));

		actorMap.get("inFork").connectIn(in, 0);
		actorMap.get("dec").connectOut(out, 0);

		Factory.channelBuilder(actorMap).source("inFork", 0).sink("doubler", 0)
				.next().source("inFork", 1).sink("doubler", 1)
				.next().source("doubler", 0).sink("dec", 0);

		for (Actor a : actorMap.values()) {
			Simulation.getExec().execute(a);
		}
	}
}
