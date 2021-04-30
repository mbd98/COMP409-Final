import java.util.Map;
import java.util.Objects;

public final class Factory {
	private Factory() {}

	/**
	 * Helper class for connecting channels.
	 */
	public static final class ChannelBuilder {
		private volatile Actor source = null, sink = null;
		private volatile Channel c = createChannel("queue");
		private final Map<String, Actor> actorMap;

		private ChannelBuilder(Map<String, Actor> actorMap) {
			this.actorMap = Objects.requireNonNull(actorMap);
		}

		public ChannelBuilder source(String as, int i) {
			source = actorMap.get(as);
			source.connectOut(c, i);
			return this;
		}

		public ChannelBuilder sink(String as, int i) {
			sink = actorMap.get(as);
			sink.connectIn(c, i);
			return this;
		}

		public ChannelBuilder next() {
			if (source == null || sink == null) {
				throw new IllegalStateException();
			}
			source = null;
			sink = null;
			c = createChannel("queue");
			return this;
		}
	}

	/**
	 * Creates a new channel of the given type.
	 * @param type The channel type (one of "queue" or "stream")
	 * @return The new channel
	 */
	public static Channel createChannel(String type) {
		return switch (type) {
			case "queue" -> new QueueChannel();
			case "stream" -> new StreamChannel();
			default -> throw new IllegalArgumentException();
		};
	}

	/**
	 * Creates a new (default type) channel.
	 * @return The new channel
	 */
	public static Channel createChannel() {
		return new QueueChannel();
	}

	/**
	 * Creates a new actor of the given type.
	 * @param type The actor type
	 * @return The new actor
	 */
	public static Actor createActor(String type) {
		return switch (type) {
			case "add" -> new AddActor();
			case "==" -> new EqualityActor();
			case "fork" -> new ForkActor();
			case "switch" -> new SwitchActor();
			case "merge" -> new MergeActor();
			case "const 1" -> new ConstantActor(1);
			case "const -1" -> new ConstantActor(-1);
			case "inc" -> new IncrementActor(1);
			case "dec" -> new IncrementActor(-1);
			case "dm1" -> new DoubleMinusOneActor();
			case "square" -> new SquareActor();
			case "input" -> new InputActor();
			case "output" -> new OutputActor();
			case "cdr" -> new CdrActor();
			case "<" -> new LessThanActor();
			default -> throw new IllegalArgumentException();
		};
	}

	public static ChannelBuilder channelBuilder(Map<String, Actor> actorMap) {
		return new ChannelBuilder(actorMap);
	}
}
