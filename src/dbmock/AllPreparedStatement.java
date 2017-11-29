package dbmock;


import java.sql.ResultSet;
import java.sql.SQLException;




/**
 * Wraps the {@link AllStatement} as a prepared statement.
 *
 * @author Kai Winter
 */
public class AllPreparedStatement extends PreparedStatementAdapter {

	private final AllStatement statement;
	private final String sql;

	/**
	 * Constructs a new {@link AllPreparedStatement}.
	 *
	 * @param sql
	 *            the SQL statement.
	 */
	public AllPreparedStatement(String sql) {
		this.statement = new AllStatement();
		this.sql = sql;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return statement.executeQuery(sql);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

}
