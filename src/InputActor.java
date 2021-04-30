public class InputActor implements Actor {
	private final StreamChannel in = (StreamChannel) Simulation.getStandardIOChannel();
	private volatile Channel out;

	@Override
	public void connectIn(Channel c, int i) {
		throw new IllegalArgumentException();
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
			out.set(t);
			Simulation.getExec().execute(InputActor.this);
		});
	}
}
