package dook.loader;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import dook.io.SerialisedData;

/**
 * Represents the template for loading from raw string to serialised data.
 */
public class TaskLoader0 {
	private static final String DELIMITER = "|";
    private static final Logger LOGGER =
        Logger.getLogger(TaskLoader0.class.getName());

	/**
	 * Returns list of serialised data given list of strings.
	 *
	 * @param  lines The list of strings.
	 * @return       The list of serialised data.
	 */
	public static List<SerialisedData> load(List<String> lines) {
		List<SerialisedData> content = new ArrayList<>();
		for (String line : lines) {
			try {
				content.add(new SerialisedData(line.split(Pattern.quote(DELIMITER))));
			} catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Local task file corrupted.", e);
				return new ArrayList<>();
			}
		}
		return content;
	}
}
