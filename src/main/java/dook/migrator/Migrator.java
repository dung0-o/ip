package dook.migrator;

import java.util.List;

public interface Migrator {
	List<List<String>> migrate(List<List<String>> content);
}
