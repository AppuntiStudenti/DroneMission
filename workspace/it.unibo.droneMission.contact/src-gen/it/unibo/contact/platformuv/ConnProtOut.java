/*
*  Generated by AN Unibo
*/
package it.unibo.contact.platformuv;
import it.unibo.is.interfaces.*;
import it.unibo.is.interfaces.platforms.ILindaLike;
import it.unibo.is.interfaces.protocols.*;
import it.unibo.is.interfaces.services.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class ConnProtOut implements ILindaLike {

	protected String receiver;
	protected String msgId;
	protected String sender;
	protected IConnInteraction connection;
	protected IServiceTcp tcpServ;
	protected boolean debug = false;
	protected IOutputView view;
	protected String hostname;
	protected int portNum;
	protected boolean timeToEnd = false;
	private static HashMap<String, IConnInteraction> outConn = new HashMap<String, IConnInteraction>();

	public ConnProtOut(String receiver, String msgId, String sender,
			IOutputView view) throws Exception {
		this.receiver = receiver;
		this.msgId = msgId;
		this.sender = sender;
		this.view = view;
		hostname = RunTimeKb.getHostName(receiver, msgId);
		portNum = RunTimeKb.getPortNum(receiver, msgId);
		println("ConnProtOut on " + portNum);
		if (System.getProperty("ConnProtOut") != null)
			debug = System.getProperty("ConnProtOut").equals("set");
	}

	/*
	 * This constructor is used by ConnReceiver.handleConnectionMsg
	 */
	public ConnProtOut(String receiver, String msgId, String sender,
			IConnInteraction connection, IOutputView view) throws Exception {
		this(receiver, msgId, sender, view);
		this.connection = connection;
		outConn.put("" + portNum, connection);
	}

	public synchronized void out(final String msg) throws Exception {
		println("sending " + msg + " conn= " + connection);
		if (connection == null) {
			// Start a thread to establish a connection
			ConnectThread cth = new ConnectThread(this, msg, view);
			cth.start();
			while (connection == null && !timeToEnd) {
				println("waiting for a connection to " + receiver);
				wait();
				println("resuming " + receiver + "  timeToEnd=" + timeToEnd);
			}
			if (timeToEnd) {
				timeToEnd = false;
				terminate();
				throw new Exception("connection exception");
			}
			if (connection != null)
				connection.sendALine(msg);
		} else
			connection.sendALine(msg);
	}

	public synchronized IMessage in(String query) throws Exception {
		System.out.println(" ****  NEVER HERE ConnProtOut ****** "
				+ query);
		throw new Exception("	%%% ConnProtOut does not support rd");
	}

	public IMessage rd(int LastMsgNum, String queryMsg) throws Exception {
		throw new Exception("	%%% ConnProtOut does not support rd");
	}
	@Override
	public IMessage rdMostRecent(int LastMsgNum, String queryMsg)
			throws Exception {
		throw new Exception("	%%% ConnProtOut does not support rdMostRecent");
	}

	@Override
	public IMessage rdwMostRecent(int LastMsgNum, String queryMsg)
			throws Exception {
		throw new Exception("	%%% ConnProtOut does not support rdwMostRecent");
	}
	 

	public boolean check(String queryS) throws Exception {
		throw new Exception("	%%% ConnProtOut does not support check");
	}

	public Vector<IMessage> inMany(Vector<String> tokens) throws Exception {
		throw new Exception("	%%% ConnProtOut does not support inMany");
	}

	public IMessage select(String subjName,Hashtable<String, Integer> lastMsgRdMemo,
			java.util.List<IMessage> queries) throws Exception {
		throw new Exception(
				"	%%% ConnProtOut does not support inOneFromMany");
	}
	public IMessage selectMostRecent( 
		String subjName, Hashtable<String,Integer> lastMsgRdMemo, java.util.List<IMessage> queries ) throws Exception {
		throw new Exception("	%%% ConnProtOut does not support selectMostRecent");
	}

	public IMessage rdw(int LastMsgNum, String queryMsg) throws Exception {
		throw new Exception("	%%% ConnProtOut does not support rdw");
	}

	// _-------------------------------------------------------

	public IConnInteraction connect() throws Exception {
		IConnInteraction conn = connection;
		conn = outConn.get("" + portNum);
		println(" --- FOUND connection for " + portNum + " = " + conn);
		if (conn == null) {
			conn = doConnect();
			println(" connection after doConnect for " + portNum + " = " + conn);
			if (conn != null && ! RunTimeKb.worksWith( "HTTP", receiver, msgId) ){
				outConn.put("" + portNum, conn);
				new ConnInputReceiver(""+portNum,conn,view).start();
			}
		}
		connection = conn;
		return connection;
	}

	// To be redefined
	public IConnInteraction doConnect() throws Exception {
		return null;
	}

	public synchronized void setConnection(IConnInteraction conn) {
		connection = conn;
		notifyAll();
	}

	public synchronized IConnInteraction getConnection() {
		return connection;
	}

	public synchronized void setTimeToEnd() {
		this.timeToEnd = true;
		notifyAll();
	}

	// _-------------------------------------------------------
	public void terminate() throws Exception {
		if (connection != null) {
			println("close out connection");
			connection.closeConnection();
		} else
			println("terminates without any connection");
	}

	protected void println(String msg) {
		String m = "    %%% ConnProtOut " + msg;
		if (debug)
			if (view != null)
				view.addOutput(m);
			else
				System.out.println(m);
	}
}

/*
 * ------------------------------------------- 
 * ConnectThread
 * -------------------------------------------
 */

class ConnectThread extends Thread {
	private IConnInteraction conn = null;
	private ConnProtOut outSupport;
	private String msg;
	private boolean running = false;
	private IOutputView view;
	private boolean debug = false;

	public ConnectThread(ConnProtOut outSupport, String msg, IOutputView view) {
		this.outSupport = outSupport;
		this.msg = msg;
		this.view = view;
		setName("ConnectThread_" + msg);
	}

	public void run() {
		println("started for ... " + msg);
		running = true;
		connect();
	}

	protected void connect() {
		int numOfTest = 15;
		if (System.getProperty("numOfConnectionAttempts") != null)
			numOfTest = Integer.parseInt(System
					.getProperty("numOfConnectionAttempts"));
		for (int i = 0; i < numOfTest; i++) {
			try {
				// println("connection attempt n=" + i );
				conn = outSupport.connect(); // bloccante
				outSupport.setConnection(conn);
				break;
			} catch (Exception e) {
				println(e.getMessage() + " attempt n=" + i);
			}
		}// for
		try {
			println("connect ends its attempts " + (conn != null));
			running = false;
			if (conn == null)
				outSupport.setTimeToEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void println(String msg) {
		String m = "    %%% ConnectThread  " + msg;
		if (debug)
			if (view != null)
				view.addOutput(m);
			else
				System.out.println(m);
	}
}
