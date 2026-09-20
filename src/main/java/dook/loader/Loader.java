package dook.loader;

import java.util.List;

import dook.io.SerialisedData;

/**
 * Represents the template for loading from raw string to serialised data.
 * Used in {@link dook.io.FileIO#readFile()}.
 */
@FunctionalInterface
public interface Loader {

	/**
	 * Returns list of serialised data given list of strings.
	 *
	 * @param  lines The list of strings.
	 * @return       The list of serialised data.
	 */
	List<SerialisedData> load(List<String> lines);
}
