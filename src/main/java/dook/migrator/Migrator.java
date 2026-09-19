package dook.migrator;

import java.util.List;

import dook.io.SerialisedData;

/**
 * Represents the template for migration of outdated serialised data.
 * Used in {@link dook.io.FileIOr#readFile()}.
 */
public interface Migrator {

	/**
	 * Modifies the serialised data.
	 *
	 * @param  content The serialised data in outdated format.
	 * @return         The updated serialised data.
	 */
	List<SerialisedData> migrate(List<SerialisedData> content);
}
