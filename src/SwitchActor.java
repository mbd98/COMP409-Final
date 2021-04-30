public class SwitchActor implements Actor {
	private volatile Channel select, in, out0, out1;

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
			case 0 -> out0 = c;
			case 1 -> out1 = c;
			default -> throw new IllegalArgumentException();
		}
	}

	@Override
	public void run() {
		in.consume(t -> select.consume(s -> {
			if (s == 0) {
				out0.set(t);
			} else {
				out1.set(t);
			}
			Simulation.getExec().execute(SwitchActor.this);
		}));
	}
}
