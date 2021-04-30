public class SwitchActor implements Actor {
	private volatile Channel select, in, falseOut, trueOut;

	@Override
	public void connectIn(Channel c, int i) {
		switch (i) {
			case 0 -> select = c;
			case 1 -> in = c;
			default -> throw new IllegalArgumentException();
		}
	}

	@Override
	public void connectOut(Channel c, int i) {
		switch (i) {
			case 0 -> trueOut = c;
			case 1 -> falseOut = c;
			default -> throw new IllegalArgumentException();
		}
	}

	@Override
	public void run() {
		in.consume(t -> select.consume(s -> {
			if (s == 0) {
				falseOut.set(t);
			} else {
				trueOut.set(t);
			}
			Simulation.getExec().execute(SwitchActor.this);
		}));
	}
}
