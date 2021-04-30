public class MergeActor implements Actor {
	private volatile Channel select, falseIn, trueIn, out;

	@Override
	public void connectIn(Channel c, int i) {
		switch (i) {
			case 0 -> select = c;
			case 1 -> trueIn = c;
			case 2 -> falseIn = c;
			default -> throw new IllegalArgumentException();
		}
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
		select.consume(s -> {
			if (s == 0) {
				falseIn.consume(t -> {
					out.set(t);
					Simulation.getExec().execute(MergeActor.this);
				});
			} else {
				trueIn.consume(t -> {
					out.set(t);
					Simulation.getExec().execute(MergeActor.this);
				});
			}
		});
	}
}
