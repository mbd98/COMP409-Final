public class AddActor implements Actor {
	private volatile Channel in0, in1, out;

	@Override
	public void connectIn(Channel c, int i) {
		switch (i) {
			case 0 -> in0 = c;
			case 1 -> in1 = c;
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
		in0.consume(token0 -> in1.consume(token1 -> {
			out.set(token0 + token1);
			Simulation.getExec().execute(AddActor.this);
		}));
	}
}
