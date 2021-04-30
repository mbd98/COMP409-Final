public class OutputActor implements Actor {
	private final StreamChannel out = (StreamChannel) Simulation.getStandardIOChannel();
	private volatile Channel in;

	@Override
	public void connectIn(Channel c, int i) {
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		in = c;
	}

	@Override
	public void connectOut(Channel c, int i) {
		throw new IllegalArgumentException();
	}

	@Override
	public void run() {
		in.consume(t -> {
			out.set(t);
			Simulation.getExec().execute(OutputActor.this);
		});
	}
}
