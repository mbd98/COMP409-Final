import java.util.Scanner;
import java.util.function.IntConsumer;

public class StreamChannel implements Channel {
	private final Scanner stdIn = new Scanner(System.in);

	@Override
	public void set(int token) {
		System.out.println(token);
	}

	@Override
	public void consume(IntConsumer consumer) {
		if (stdIn.hasNextLine()) {
			consumer.accept(Integer.parseInt(stdIn.nextLine()));
		}
	}
}
