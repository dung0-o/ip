package dook.loader;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dook.io.SerialisedData;

/**
 * Represents the template for loading from raw string to serialised data.
 */
public class TaskLoader0 implements Loader {
	private final String DELIMITER = "|";

	/**
	 * Returns list of serialised data given list of strings.
	 *
	 * @param  lines The list of strings.
	 * @return       The list of serialised data.
	 */
	public List<SerialisedData> load(List<String> lines) {
		List<SerialisedData> content = new ArrayList<>();
		for (String line : lines) {
			try {
				content.add(new SerialisedData(line.split(Pattern.quote(DELIMITER))));
			} catch (ClassNotFoundException e) {
            	e.printStackTrace();
				return new ArrayList<>();
			}
		}
		return content;
	}
}
