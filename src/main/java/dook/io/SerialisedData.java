package dook.io;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class SerialisedData {
	private static final String DELIMITER = "|";
	private Class<?> klass;
	private List<String> details;

	public SerialisedData(Class<?> klass, String... args) {
		this.klass = klass;
		details = new ArrayList<>(Arrays.asList(args));
	}

	public SerialisedData(String... args) throws ClassNotFoundException {
		this(
			Class.forName(args[0]),
			Arrays.copyOfRange(args, 1, args.length)
		);
	}

	public void add(String... args) {
		details.addAll(Arrays.asList(args));
	}

	public Class<?> getKlass() {
		return klass;
	}

	public List<String> getDetails() {
		return List.copyOf(details);
	}

	@Override
	public String toString() {
		return klass.getName() + DELIMITER + String.join(DELIMITER, details);
	}
}
