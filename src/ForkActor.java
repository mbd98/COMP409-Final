public class ForkActor implements Actor {
	private volatile Channel in, out0, out1;

	@Override
	public void connectIn(Channel c, int i) {
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		in = c;
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
		in.consume(t -> {
			out0.set(t);
			out1.set(t);
			Simulation.getExec().execute(ForkActor.this);
		});
	}
}
