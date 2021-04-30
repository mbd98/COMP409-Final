/**
 * Main Actor interface.
 */
public interface Actor extends Runnable {
	/**
	 * Connects to an input channel.
	 * @param c The channel
	 * @param i Input slot
	 */
	void connectIn(Channel c, int i);

	/**
	 * Connects to an output channel.
	 * @param c The channel
	 * @param i Output slot
	 */
	void connectOut(Channel c, int i);
}
