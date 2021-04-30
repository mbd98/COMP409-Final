public class ConstantActor implements Actor {
	private final int token;
	private volatile Channel out;

	public ConstantActor(int token) {
		this.token = token;
	}

	@Override
	public void connectIn(Channel c, int i) {

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
		out.set(token);
		Simulation.getExec().execute(this);
	}
}
