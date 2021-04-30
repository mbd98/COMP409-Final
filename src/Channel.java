import java.util.function.IntConsumer;

public interface Channel {
	void set(int token);
	void consume(IntConsumer consumer);
}
