package dook.io.migrator;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import dook.task.ToDoTask;
import dook.task.DeadlineTask;
import dook.task.EventTask;

import dook.io.SerialisedData;
import dook.parser.DateTimeParser;

public class TaskMigrator0 {
	private static final Class<?> DEFAULT_TASK_CLASS = ToDoTask.class;

	public static List<SerialisedData> migrate(List<SerialisedData> content) {
		List<SerialisedData> newContent = new ArrayList<>();
		for (SerialisedData data : content) {
			Class<?> klass = data.getKlass();
			List<?> details = data.getDetails();
			SerialisedData newData;

			if (klass == DeadlineTask.class) {
				newData = parseOldDeadlineTask(details);

			} else if (klass == EventTask.class) {
				newData = parseOldEventTask(details);

			} else {
				newData = parseOldGenericTask(details);
			}

			newContent.add(newData);
		}
		return newContent;
	}

	private static SerialisedData parseOldGenericTask(List<?> details) {
		boolean isDone = Boolean.parseBoolean(String.valueOf(details.get(0)));
		String description = String.valueOf(details.get(1));

		return new SerialisedData(
			DEFAULT_TASK_CLASS,
			isDone,
			description
		);
	}

	private static SerialisedData parseOldDeadlineTask(List<?> details) {
		boolean isDone = Boolean.parseBoolean(String.valueOf(details.get(0)));
		String description = String.valueOf(details.get(1));
		String rawDeadline = String.valueOf(details.get(2));
		Optional<LocalDateTime> maybeDeadline =
			DateTimeParser.parseEndTime(rawDeadline);

		if (maybeDeadline.isPresent()) {
			return new SerialisedData(
				DeadlineTask.class,
				isDone,
				description,
				maybeDeadline.get()
			);
		}

		return new SerialisedData(
			DEFAULT_TASK_CLASS,
			isDone,
			description + " by " + rawDeadline
		);
	}

	private static SerialisedData parseOldEventTask(List<?> details) {
		boolean isDone = Boolean.parseBoolean(String.valueOf(details.get(0)));
		String description = String.valueOf(details.get(1));
		String rawStart = String.valueOf(details.get(2));
		String rawEnd = String.valueOf(details.get(3));

		SerialisedData defaultData =
			new SerialisedData(
				DEFAULT_TASK_CLASS,
				isDone,
				description + " from " + rawStart
							+ " to " + rawEnd
			);

		Optional<LocalDateTime> maybeStart = DateTimeParser.parseStartTime(rawStart);
		if (maybeStart.isEmpty()) {
			return defaultData;
		}

		LocalDateTime startDatetime = maybeStart.get();
		Optional<LocalDateTime> maybeEnd = DateTimeParser.parseEndTime(
			rawEnd,
			startDatetime.toLocalDate()
		);
		if (maybeEnd.isEmpty()) {
			return defaultData;
		}

		LocalDateTime endDatetime = maybeEnd.get();
		if (startDatetime.isAfter(endDatetime)) {
			return defaultData;
		}

		return new SerialisedData(
			EventTask.class,
			isDone,
			description,
			maybeStart.get(),
			maybeEnd.get()
		);
	}
}
