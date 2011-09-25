package ikor.util.jdbc;

// Title:       JDBC Driver Wrapper
// Version:     1.1
// Copyright:   2001
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.sql.*;
import java.util.*;

// JDBC Driver wrapper
// -------------------

// Version history
// v0.0 -          - Java Developer Connection
// v1.0 - Mar'2001 - Improved version -> URL == pool:url:user:password
// v1.1 - Apr'2001 - Uniqueness checking before Driver registering

/*
 * USAGE
 *
 * Initialization:
 *
 *   Class.forName(driver);
 *   new JDBCConnectionDriver(driver,url,user,password);
 *
 * Getting a connection
 *
 *   connection = DriverManager.getConnection("pool:"+url+":"+user+":"+password);
 *
 */


public class JDBCConnectionDriver implements Driver 
{
  private String URL_PREFIX = "pool:";

  private Driver driver;

  private JDBCConnectionPool pool;

  /** @pre  (driver!=null) && (url!=null) && (user!=null) && (password!=null) */
  public JDBCConnectionDriver (String driver, String url, String user, String password) 
         throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
  {
    Driver available = null;

    URL_PREFIX = "pool:"+url+":"+user+":"+password;

    try { // checks if another pool has been already created
        available = DriverManager.getDriver(URL_PREFIX);
    } catch (Exception error) {
    }

    if ( available == null) {  
       DriverManager.registerDriver(this);
       this.driver = (Driver) Class.forName(driver).newInstance();
       this.pool = new JDBCConnectionPool(url, user, password);
    } 
  }

  public Connection connect (String url, Properties props) 
         throws SQLException 
  {
    if (!url.startsWith(URL_PREFIX)) {
       return null;
    }

    return pool.getConnection();
  }

  public boolean acceptsURL(String url) 
  {
    return url.startsWith(URL_PREFIX);
  }

  public int getMajorVersion() 
  {
    return driver.getMajorVersion();
  }

  public int getMinorVersion() 
  {
    return driver.getMinorVersion();
  }

  public DriverPropertyInfo[] getPropertyInfo(String str, Properties props) 
         throws SQLException
  {
    return driver.getPropertyInfo(str,props);
  }

  public boolean jdbcCompliant() 
  {
    return driver.jdbcCompliant();
  }
}
