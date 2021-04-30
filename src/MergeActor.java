public class MergeActor implements Actor {
	private volatile Channel select, in0, in1, out;

	@Override
	public void connectIn(Channel c, int i) {
		switch (i) {
			case 0 -> select = c;
			case 1 -> in0 = c;
			case 2 -> in1 = c;
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
				in0.consume(t -> {
					out.set(t);
					Simulation.getExec().execute(MergeActor.this);
				});
			} else {
				in1.consume(t -> {
					out.set(t);
					Simulation.getExec().execute(MergeActor.this);
				});
			}
		});
	}
}
