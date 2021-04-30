import java.util.Map;
import java.util.TreeMap;

public class SquareActor implements Actor {
	private volatile Channel in = null, out = null;

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
		actorMap.put("in'", Factory.createActor("fork"));
		actorMap.put("1", Factory.createActor("const 1"));
		actorMap.put("==1", Factory.createActor("=="));
		actorMap.put("==1'", Factory.createActor("fork"));
		actorMap.put("inSwitch", Factory.createActor("switch"));
		actorMap.put("switch0'", Factory.createActor("fork"));
		actorMap.put("dm1", Factory.createActor("dm1"));
		actorMap.put("dec", Factory.createActor("dec"));
		actorMap.put("square(n-1)", Factory.createActor("square"));
		actorMap.put("preresult", Factory.createActor("add"));
		actorMap.put("merge", Factory.createActor("merge"));

		actorMap.get("in'").connectIn(in, 0);
		actorMap.get("merge").connectOut(out, 0);

		Factory.channelBuilder(actorMap).source("in'", 0).sink("==1", 0)
				.next().source("1", 0).sink("==1", 1)
				.next().source("in'", 1).sink("inSwitch", 1)
				.next().source("==1", 0).sink("==1'", 0)
				.next().source("==1'", 0).sink("inSwitch", 0)
				.next().source("inSwitch", 1).sink("merge", 2)
				.next().source("==1'", 1).sink("merge", 0)
				.next().source("inSwitch", 0).sink("switch0'", 0)
				.next().source("switch0'", 0).sink("dm1", 0)
				.next().source("switch0'", 1).sink("dec", 0)
				.next().source("dec", 0).sink("square(n-1)", 0)
				.next().source("dm1", 0).sink("preresult", 0)
				.next().source("square(n-1)", 0).sink("preresult", 1)
				.next().source("preresult", 0).sink("merge", 1);

		for (Actor a : actorMap.values()) {
			Simulation.getExec().execute(a);
		}
	}
}
