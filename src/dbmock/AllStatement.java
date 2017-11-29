package dbmock;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.regex.Pattern;







/**
 * This class does the actual work of the Generic... classes. It tries to open a CSV file for the table name in the
 * query and parses the contained data.
 * 
 * @author Kai Winter
 */
public final class AllStatement extends StatementAdapter {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(CsvStatement.class);

	/** Pattern to get table name from an SQL statement. */
	private static final Pattern TABLENAME_PATTERN = Pattern.compile(".*from (\\S*)\\s?.*", Pattern.CASE_INSENSITIVE);

	/** Pattern to get the name of a stored procedure from an SQL statement. */
	private static final Pattern STORED_PROCEDURE_PATTERN = Pattern.compile(".*(EXEC|EXECUTE) (\\S*)\\s?.*",
			Pattern.CASE_INSENSITIVE);



	public int queryTimeMilliSec = 100;
	/**
	 * Constructs a new {@link AllStatement}.
	 *
	 */
	public AllStatement() {

	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		try {
			Thread.sleep(queryTimeMilliSec);
		}
		catch(Exception ex)
		{

		}

		return new DummyResultSet();
	}


}
