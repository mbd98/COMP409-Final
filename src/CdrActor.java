public class CdrActor implements Actor {
	private volatile Channel in, out;
	private volatile boolean visited = false;

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
			if (visited) {
				out.set(t);
			} else {
				visited = true;
			}
		});
	}
}
