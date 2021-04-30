public class IncrementActor implements Actor {
	private final int value;
	private volatile Channel in, out;

	public IncrementActor(int value) {
		this.value = value;
	}

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
		in.consume(t -> {
			out.set(t + value);
			Simulation.getExec().execute(IncrementActor.this);
		});
	}
}
