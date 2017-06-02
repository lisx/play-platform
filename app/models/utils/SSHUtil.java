package models.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import framework.logs.LogUtil;

public class SSHUtil {
	private final static Logger logger = LogUtil.getInstance(SSHUtil.class);
	
	public static int execCmd(String host, String username, String password,
		      String cmd, String finishFlag) throws IOException {
			if(logger.isDebugEnabled()){
				logger.debug(String.format("[%s]: cmd<%s>, finishFlag<%s>","execCmd",cmd,finishFlag));
			}
		    Connection conn = getOpenedConnection(host, username, password);
		    Session sess = conn.openSession();
		    sess.execCommand(cmd);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));		 
		    try {
				while (true) {
				  String line = reader.readLine();
				  if(logger.isDebugEnabled()){
					  logger.debug(String.format("[%s]: stdout<%s>", "execCmd::stdout",line));
				  }
				  if (line == null || finishFlag.equals(line))
				    break;
				  
				}
			} catch (Exception e) {
				logger.error(String.format("[%s]: message<%s>", "execCmd::exception",e.getMessage()),e);
				throw new IOException("EXEC [" + cmd + "] ERROR!");
			} finally {
				try {if (reader != null) reader.close();} catch(Exception e) {};
			    try {if (sess != null) sess.close();} catch(Exception e) {};
			    try {if (conn != null) conn.close();} catch(Exception e) {};			
			}
		    return sess.getExitStatus().intValue();			
			//return 0;
	}	
	 
	 public static int execCmd(Connection conn, String cmd, String finishFlag) throws IOException {
		 	if(logger.isDebugEnabled()){
		 		logger.debug(String.format("[%s]: cmd<%s>, finishFlag<%s>", "execCmd",cmd,finishFlag));
		 	}
		 	Session sess = conn.openSession();
		    sess.execCommand(cmd);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));		 
		    try {
				while (true) {
				  String line = reader.readLine();
				  if(logger.isDebugEnabled()){
					  logger.debug(String.format("[%s]: stdout<%s>", "execCmd::stdout",line));
				  }
				  if (line == null || finishFlag.equals(line))
				    break;			  
				}
			} catch (Exception e) {
				logger.error(String.format("[%s]: message<%s>","execCmd::exception" ), e);
				throw new IOException("EXEC [" + cmd + "] ERROR!");
			} finally {
				try {if (reader != null) reader.close();} catch(Exception e) {};
			    try {if (sess != null) sess.close();} catch(Exception e) {};
			    try {if (conn != null) conn.close();} catch(Exception e) {};
			    if(logger.isDebugEnabled()){
			    	logger.debug(String.format("[%s]: message<%s>", "execCmd::closeConnection", "connection is closed!"));
			    }
			}	
		    return sess.getExitStatus().intValue();
	}		 
	
	 public static Connection getOpenedConnection(String host, String username,
			String password) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s]: host<%s>, username<%s>, password<%s>","getOpenedConnection",host,username,password));
		}

		Connection conn = new Connection(host);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(username,
				password);
		if (isAuthenticated == false){
			logger.error(String.format("[%s]: message<%s>","getOpenedConnection::exception","ssh login authenicated failed!"));
			throw new IOException("ssh login authenicated failed!");
		}
		return conn;
	}	
	
	 public static Connection getOpenedConnection(String host,int port, String username,
				String password) throws IOException {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("[%s]: host<%s>, username<%s>, password<%s>","getOpenedConnection", host,username,password));
			}

			Connection conn = new Connection(host,port);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username,
					password);
			if (isAuthenticated == false){
				logger.error(String.format("[%s]: userName<%s>, password<%s> ,message<%s>","getOpenedConnection::auth",username,password,"ssh login authenicated failed!"));
				throw new IOException("ssh login authenicated failed!");
			}
			return conn;
		}
	 
	 
	 
	 public static void closeConnections(Connection connction, Session session) throws IOException {
		    try {if (session != null) session.close();} catch(Exception e) {};
		    try {if (connction != null) connction.close();} catch(Exception e) {};			 
	}	
}
