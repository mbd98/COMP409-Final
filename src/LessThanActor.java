public class LessThanActor implements Actor {
	private volatile Channel leftIn, rightIn, out;

	@Override
	public void connectIn(Channel c, int i) {
		switch (i) {
			case 0 -> leftIn = c;
			case 1 -> rightIn = c;
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
		leftIn.consume(lt -> rightIn.consume(rt -> {
			out.set(lt < rt ? 1 : 0);
			Simulation.getExec().execute(LessThanActor.this);
		}));
	}
}
