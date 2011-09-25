package ikor.util.jdbc;

// Title:       JDBC Connection Wrapper
// Version:     1.0
// Copyright:   2001
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


import java.sql.*;
import java.util.*;

// JDBC Connection wrapper
// -----------------------

// Version history
// v0.0 -          - Java Developer Connection
// v1.0 - Mar'2001 - Bugs detected & corrected

public class JDBCConnection implements Connection 
{
  private JDBCConnectionPool pool;
  private Connection         connection;
  private boolean            inuse;
  private long               timestamp;

  /** @pre (connection!=null) && (pool!=null) */
  public JDBCConnection (Connection connection, JDBCConnectionPool pool) 
  {
    this.connection=connection;
    this.pool=pool;
    this.inuse=false;
    this.timestamp=0;
  }

  public synchronized boolean lease() 
  {
    if (inuse)  {
       return false;
    } else {
       inuse=true;
       timestamp=System.currentTimeMillis();
       return true;
    }
  }

  public boolean validate() 
  {
    try {
	connection.getMetaData();
    }catch (Exception e) {
	return false;
    }
    return true;
  }

  public final boolean inUse() 
  {
    return inuse;
  }

  public final long getLastUse() 
  {
    return timestamp;
  }

  public void close() 
         throws SQLException 
  {
    pool.returnConnection(this);
  }

  public void closeConnection ()
         throws SQLException 
  {
    connection.close();
  }

  protected void expireLease() 
  {
    inuse = false;
  }

  protected Connection getConnection() 
  {
    return this;
  }

  public PreparedStatement prepareStatement(String sql) 
         throws SQLException 
  {
    return connection.prepareStatement(sql);
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
	 throws SQLException
  {
    return connection.prepareStatement(sql,resultSetType, resultSetConcurrency);
  }

  public CallableStatement prepareCall(String sql) 
	 throws SQLException 
  {
    return connection.prepareCall(sql);
  }

  public CallableStatement prepareCall (String sql, int resultSetType, int resultSetConcurrency)
	 throws SQLException
  {
    return connection.prepareCall(sql,resultSetType, resultSetConcurrency);
  }

  public Statement createStatement() 
	 throws SQLException 
  {
    return connection.createStatement();
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency)
	 throws SQLException
  {
    return connection.createStatement(resultSetType, resultSetConcurrency);
  }

  public String nativeSQL(String sql)
         throws SQLException 
  {
    return connection.nativeSQL(sql);
  }

  public void setAutoCommit(boolean autoCommit) throws SQLException 
  {
    connection.setAutoCommit(autoCommit);
  }

  public boolean getAutoCommit() throws SQLException 
  {
    return connection.getAutoCommit();
  }

  public void commit() throws SQLException 
  {
    connection.commit();
  }

  public void rollback() throws SQLException 
  {
    connection.rollback();
  }

  public boolean isClosed() throws SQLException 
  {
    return connection.isClosed();
  }

  public DatabaseMetaData getMetaData() throws SQLException 
  {
    return connection.getMetaData();
  }

  public void setReadOnly(boolean readOnly) throws SQLException 
  {
    connection.setReadOnly(readOnly);
  }

  public boolean isReadOnly() throws SQLException 
  {
    return connection.isReadOnly();
  }

  public void setCatalog(String catalog) throws SQLException 
  {
    connection.setCatalog(catalog);
  }

  public String getCatalog() throws SQLException 
  {
    return connection.getCatalog();
  }

  public void setTransactionIsolation(int level) throws SQLException 
  {
    connection.setTransactionIsolation(level);
  }

  public int getTransactionIsolation() throws SQLException 
  {
    return connection.getTransactionIsolation();
  }

  public SQLWarning getWarnings() throws SQLException 
  {
    return connection.getWarnings();
  }

  public void clearWarnings() throws SQLException 
  {
    connection.clearWarnings();
  }

  public Map getTypeMap()
         throws SQLException
  {
    return connection.getTypeMap();
  }

  
  // JDK 1.4
  
/* (non-Javadoc)
 * @see java.sql.Connection#createStatement(int, int, int)
 */
public Statement createStatement(int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException {
	
	return createStatement();
}

/* (non-Javadoc)
 * @see java.sql.Connection#getHoldability()
 */
public int getHoldability() throws SQLException {
    return 0;
}
 
/* (non-Javadoc)
 * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
 */
public CallableStatement prepareCall(String sql, int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return prepareCall(sql);
}
  
/* (non-Javadoc)
 * @see java.sql.Connection#prepareStatement(java.lang.String, int)
 */
public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
        throws SQLException {
    return prepareStatement(sql);
}

/* (non-Javadoc)
 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
 */
public PreparedStatement prepareStatement(String sql, int resultSetType,
        int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    
    return prepareStatement(sql);
}

/* (non-Javadoc)
 * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
 */
public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
        throws SQLException {
    return prepareStatement(sql);
}

/* (non-Javadoc)
 * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
 */
public PreparedStatement prepareStatement(String sql, String[] columnNames)
        throws SQLException {
    return prepareStatement(sql);
}

/* (non-Javadoc)
 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
 */
public void releaseSavepoint(Savepoint savepoint) throws SQLException {
}

/* (non-Javadoc)
 * @see java.sql.Connection#rollback(java.sql.Savepoint)
 */
public void rollback(Savepoint savepoint) throws SQLException {
}

/* (non-Javadoc)
 * @see java.sql.Connection#setHoldability(int)
 */
public void setHoldability(int holdability) throws SQLException {
}

/* (non-Javadoc)
 * @see java.sql.Connection#setSavepoint()
 */
public Savepoint setSavepoint() throws SQLException {
    return null;
}

/* (non-Javadoc)
 * @see java.sql.Connection#setSavepoint(java.lang.String)
 */
public Savepoint setSavepoint(String name) throws SQLException {
    return null;
}


// JDK 6

public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Blob createBlob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Clob createClob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public NClob createNClob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public SQLXML createSQLXML() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public Properties getClientInfo() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public String getClientInfo(String name) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public boolean isValid(int timeout) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public void setClientInfo(Properties properties) throws SQLClientInfoException {
	// TODO Auto-generated method stub
	
}

public void setClientInfo(String name, String value) throws SQLClientInfoException {
	// TODO Auto-generated method stub
	
}

// public void setTypeMap(Map map) throws SQLException
public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
	connection.setTypeMap(map);
	
}

public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}



}
