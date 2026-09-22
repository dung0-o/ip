package dook.loader;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.LocalDateTime;

import dook.io.SerialisedData;

/**
 * Represents the template for loading from raw string to serialised data.
 */
public class TaskLoader1 {
	private static final String DELIMITER = "|";
    private static final Logger LOGGER =
        Logger.getLogger(TaskLoader1.class.getName());

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
				String[] tokens = line.split(Pattern.quote(DELIMITER));
				SerialisedData data = new SerialisedData(
					Class.forName(tokens[0]),
					Boolean.parseBoolean(tokens[1]),
					tokens[2]
				);

				data.add(Arrays.stream(tokens)
							   .skip(3)
							   .map(LocalDateTime::parse)
							   .toArray());

				content.add(data);
			} catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Local task file corrupted.", e);
				return new ArrayList<>();
			}
		}
		return content;
	}
}
