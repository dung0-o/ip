package dook.migrator;

import java.util.List;

/**
 * Represents the template for migration of outdated serialised data.
 * Used in {@link dook.FileManager#readTasks()}.
 */
public interface Migrator {

	/**
	 * Modifies the serialised data.
	 *
	 * @param  content The serialised data in outdated format.
	 * @return         The updated serialised data.
	 */
	List<List<String>> migrate(List<List<String>> content);
}
