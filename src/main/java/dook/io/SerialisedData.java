package dook.io;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class SerialisedData {
	private static final String DELIMITER = "|";
	private Class<?> klass;
	private List<?> details;

	public SerialisedData(Class<?> klass, Object... args) {
		this.klass = klass;
		details = new ArrayList<>(Arrays.asList(args));
	}

	public SerialisedData(String... args) throws ClassNotFoundException {
		this(
			Class.forName(args[0]),
			(Object[]) Arrays.copyOfRange(args, 1, args.length)
		);
	}

	public void add(Object... args) {
		((List) details).addAll(Arrays.asList(args));
	}

	public Class<?> getKlass() {
		return klass;
	}

	public List<?> getDetails() {
		return List.copyOf(details);
	}

	@Override
	public String toString() {
		String detailsString = details.stream()
									  .map(String::valueOf)
									  .collect(Collectors.joining(DELIMITER));
		return klass.getName() + DELIMITER + detailsString;
	}
}
