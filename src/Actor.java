public interface Actor extends Runnable {
	void connectIn(Channel c, int i);
	void connectOut(Channel c, int i);
}
