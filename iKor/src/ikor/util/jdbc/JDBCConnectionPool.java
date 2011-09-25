package ikor.util.jdbc;

// Title:       JDBC Connection Wrapper
// Version:     1.0
// Copyright:   2001
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.sql.*;
import java.util.*;

// JDBC Connection Pool
// --------------------

// Version history
// v0.0 -          - Java Developer Connection
// v1.0 - Mar'2001 - Bugs detected & corrected + Automatic reaper start-up & shutdown


public class JDBCConnectionPool 
{
  private Vector connections;
  private String url, user, password;
  private int activeConnections;

  private JDBCConnectionReaper reaper;

  final private int reaperStartThreshold = 0; // 3;
  final private int reaperStopThreshold  = 0; // 2;

  final private long timeout=3600000;  // 1h
  final private int  poolsize=10;

  // Constructor

  public JDBCConnectionPool (String url, String user, String password) 
  {
    this.url = url;
    this.user = user;
    this.password = password;
 
    connections = new Vector(poolsize);
    activeConnections = 0;
    reaper = null;
  }

  // REAPER -> Avoids deadlocks and hangs closing unused connections 

  public synchronized void startReaper ()
  {
    if (reaper==null) {
       reaper = new JDBCConnectionReaper(this);
       reaper.setPriority(Thread.MIN_PRIORITY);
       reaper.setDaemon(true);  // Daemon thread (does not prevent JVM from exiting)
       reaper.start();
    }
  }

  public synchronized void stopReaper ()
  {
    if (reaper!=null) {
       reaper.stopRequest();
       reaper = null;
    }
  }

  public synchronized void reapConnections() 
  {
    long           stale = System.currentTimeMillis() - timeout;
    Enumeration    connlist = connections.elements();
    JDBCConnection conn;
   
    while ((connlist != null) && (connlist.hasMoreElements())) {

	  conn = (JDBCConnection)connlist.nextElement();

	  if (  (conn.inUse()) 
	     && (stale > conn.getLastUse()) 
	     && (!conn.validate())) {

	     removeConnection(conn);

	     activeConnections--;
	     // System.err.println("REAP "+activeConnections);
   	  }
    }

    if (activeConnections<reaperStopThreshold)
       stopReaper(); 
  }

  public synchronized void closeConnections() 
  {
    Enumeration    connlist = connections.elements();
    JDBCConnection conn;

    while ((connlist != null) && (connlist.hasMoreElements())) {
	  conn = (JDBCConnection)connlist.nextElement();
	  removeConnection(conn);
    }

    activeConnections = 0;
    stopReaper();
  }

  private synchronized void removeConnection (JDBCConnection conn) 
  {
    try {
        conn.closeConnection();
    } catch (Exception error) {
    }

    connections.removeElement(conn);
  }

  public synchronized Connection getConnection() throws SQLException 
  {
    int i;
    Connection     real;
    JDBCConnection wrapper;
    JDBCConnection aux;

    wrapper = null;

    // Pool

    for (i=0; i<connections.size() && wrapper==null; i++) {
	aux = (JDBCConnection)connections.elementAt(i);
	
	if (aux.lease())
	   wrapper = aux;
    }

    // New connection

    if (wrapper==null) {

       real    = DriverManager.getConnection(url, user, password);
       wrapper = new JDBCConnection(real, this);
        
       wrapper.lease();
       connections.addElement(wrapper);
    }

    activeConnections++;

    if (activeConnections>reaperStartThreshold)
       startReaper(); 
    
    // System.err.println("GET "+activeConnections);

    return wrapper;
  } 

  public synchronized void returnConnection (JDBCConnection conn) 
  {
    activeConnections--;

    if (activeConnections<reaperStopThreshold)
       stopReaper();

    // System.err.println("RETURN "+activeConnections);

    if (connections.size()-activeConnections < poolsize)
       conn.expireLease();
    else
       removeConnection (conn);
  }
}



class JDBCConnectionReaper extends Thread 
{
  private JDBCConnectionPool pool;
  private final long delay=600000;  // 10min
  private boolean stop;

  /** @pre (pool!=null) */
  JDBCConnectionReaper (JDBCConnectionPool pool) 
  {
    this.pool = pool;
  }

  public void stopRequest()
  {
    this.stop = true;
  }

  public void run() 
  {
    stop = false;

    // System.err.println("REAPER STARTED");

    while (!stop) {

      // System.err.println("REAPER...");
                 
      pool.reapConnections();

      if (!stop) {

         try {

   	    sleep(delay);

         } catch( InterruptedException e) { 
	 }
      }
    }
  }
}
