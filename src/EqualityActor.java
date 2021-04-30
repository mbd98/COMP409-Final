public class EqualityActor implements Actor {
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
		in0.consume(t0 -> {
			in1.consume(t1 -> {
				out.set(t0 == t1 ? 1 : 0);
				Simulation.getExec().execute(EqualityActor.this);
			});
		});
	}
}
