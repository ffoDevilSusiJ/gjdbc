package org.gjdbc.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The QueryBuilder class provides static methods for building SQL queries.
 *
 * It provides methods for building SELECT, INSERT, UPDATE, and DELETE queries.
 */
public class QueryBuilder {

	/**
	 *
	 * Returns a new SelectBuilder object with the given columns.
	 *
	 * @param columns the columns to select
	 * @return a new SelectBuilder object
	 */
	public static SelectBuilder select(String... columns) {
		return new SelectBuilder(columns);
	}

	/**
	 *
	 * Returns a new InsertBuilder object for the given table.
	 *
	 * @param table the table to insert into
	 * @return a new InsertBuilder object
	 */
	public static InsertBuilder insert(String table) {
		return new InsertBuilder(table);
	}

	/**
	 *
	 * Returns a new UpdateBuilder object for the given table.
	 *
	 * @param table the table to update
	 * @return a new UpdateBuilder object
	 */
	public static UpdateBuilder update(String table) {
		return new UpdateBuilder(table);
	}

	/**
	 *
	 * Returns a new DeleteBuilder object for the given table.
	 *
	 * @param table the table to delete from
	 * @return a new DeleteBuilder object
	 */
	public static DeleteBuilder delete(String table) {
		return new DeleteBuilder(table);
	}

	/**
	 *
	 * The SelectBuilder class provides methods for building SELECT queries.
	 */
	public static class SelectBuilder {
		private List<String> columns;
		private String fromTable;
		private String whereCondition;

		/**
		 *
		 * Constructs a new SelectBuilder object with the given columns.
		 *
		 * @param columns the columns to select
		 */
		private SelectBuilder(String... columns) {
			this.columns = Arrays.asList(columns);
		}

		/**
		 *
		 * Sets the table to select from.
		 *
		 * @param table the table to select from
		 * @return this SelectBuilder object
		 */
		public SelectBuilder from(String table) {
			this.fromTable = table;
			return this;
		}

		/**
		 *
		 * Sets the WHERE condition for the query.
		 *
		 * @param condition the WHERE condition
		 * @return this SelectBuilder object
		 */
		public SelectBuilder where(String condition) {
			this.whereCondition = condition;
			return this;
		}

		/**
		 *
		 * Builds and returns the SQL query as a string.
		 *
		 * @return the SQL SELECT statement
		 */
		public String build() {
			StringBuilder query = new StringBuilder("SELECT ");
			query.append(String.join(", ", columns));
			query.append(" FROM ").append(fromTable);
			if (whereCondition != null) {
				query.append(" WHERE ").append(whereCondition);
			}
			return query.toString();
		}
	}

	/**
	 *
	 * The InsertBuilder class provides methods for building INSERT queries.
	 */
	public static class InsertBuilder {
		private String table;
		private List<String> columns;
		private List<Object> values;

		/**
		 *
		 * Constructs a new InsertBuilder object for the given table.
		 *
		 * @param table the table to insert into
		 */
		public InsertBuilder(String table) {
			this.table = table;
			this.columns = new ArrayList<>();
			this.values = new ArrayList<>();
		}

		/**
		 *
		 * Sets the columns for the query.
		 *
		 * @param columns the columns to insert into
		 * @return this InsertBuilder object
		 */
		public InsertBuilder columns(String... columns) {
			this.columns.addAll(Arrays.asList(columns));
			return this;
		}

		/**
		 *
		 * Sets the values for the query.
		 *
		 * @param values the values to insert into the columns
		 * @return this InsertBuilder object
		 */
		public InsertBuilder values(Object... values) {
			this.values.addAll(Arrays.asList(values));
			return this;
		}

		/**
		 *
		 * Builds and returns the SQL INSERT statement as a string.
		 *
		 * @return the SQL INSERT statement
		 */
		public String build() {
			StringBuilder query = new StringBuilder("INSERT INTO ");
			query.append(table).append(" (");
			query.append(String.join(", ", columns));
			query.append(") VALUES (");
			query.append(values.stream().map(Object::toString).collect(Collectors.joining(", ")));
			query.append(")");
			return query.toString();
		}
	}

	/**
	 *
	 * The UpdateBuilder class provides methods for building SQL UPDATE statements.
	 */
	public static class UpdateBuilder {
		private String table;
		private Map<String, Object> setValues;
		private String whereCondition;

		/**
		 *
		 * Constructs a new UpdateBuilder object for the given table.
		 *
		 * @param table the table to update
		 */
		public UpdateBuilder(String table) {
			this.table = table;
			this.setValues = new LinkedHashMap<>();
		}

		/**
		 *
		 * Sets the value for the given column.
		 *
		 * @param column the column to update
		 * @param value  the value to set for the column
		 * @return this UpdateBuilder object
		 */
		public UpdateBuilder set(String column, Object value) {
			setValues.put(column, value);
			return this;
		}

		/**
		 *
		 * Sets the WHERE condition for the query.
		 *
		 * @param condition the WHERE condition to set
		 * @return this UpdateBuilder object
		 */
		public UpdateBuilder where(String condition) {
			this.whereCondition = condition;
			return this;
		}

		/**
		 *
		 * Builds and returns the SQL UPDATE statement as a string.
		 *
		 * @return the SQL UPDATE statement
		 */
		public String build() {
			StringBuilder query = new StringBuilder("UPDATE ");
			query.append(table).append(" SET ");
			query.append(setValues.entrySet().stream()
					.map(entry -> entry.getKey() + " = " + entry.getValue().toString())
					.collect(Collectors.joining(", ")));
			if (whereCondition != null) {
				query.append(" WHERE ").append(whereCondition);
			}
			return query.toString();
		}
	}

	/**
	 *
	 * The DeleteBuilder class provides functionality for building SQL DELETE
	 * queries.
	 */
	public static class DeleteBuilder {
		private String table;
		private String whereCondition;

		/**
		 *
		 * Constructs a DeleteBuilder object with the specified table name.
		 *
		 * @param table the name of the table to delete from
		 */
		public DeleteBuilder(String table) {
			this.table = table;
		}

		/**
		 *
		 * Sets the WHERE condition for the DELETE query.
		 *
		 * @param condition the WHERE condition for the query
		 * @return the current DeleteBuilder object
		 */
		public DeleteBuilder where(String condition) {
			this.whereCondition = condition;
			return this;
		}

		/**
		 *
		 * Builds the SQL DELETE query.
		 *
		 * @return the SQL DELETE statement
		 */
		public String build() {
			StringBuilder query = new StringBuilder("DELETE FROM ");
			query.append(table);
			if (whereCondition != null) {
				query.append(" WHERE ").append(whereCondition);
			}
			return query.toString();
		}
	}
}
