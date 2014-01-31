/*
*  Generated by AN Unibo
*/
package it.unibo.contact.headquarter_controlunit;
//Import generated by the contact spec
//Other Import
import it.unibo.contact.platformuv.*;
import it.unibo.is.interfaces.*;
import it.unibo.is.interfaces.platforms.*;
//import org.eclipse.xtext.xbase.lib.*;
//import org.eclipse.xtext.xbase.lib.Functions.*;
import java.util.Vector;
import it.unibo.contact.platformuv.LindaLike;
import it.unibo.is.interfaces.protocols.IConnInteraction;
//import java.awt.Color;
//For Xbase code 
import org.eclipse.xtext.xbase.lib.Functions.*;
import org.eclipse.xtext.xbase.lib.*;

public abstract class ControlUnitSupport extends Subject{
	private static ControlUnit obj = null;
	private IMessage resCheckMsg;
	private boolean resCheck;
	/*
	* Factory method: returns a singleton
	*/
	public static ControlUnit create(String name) throws Exception{
		if( obj == null ) obj = new ControlUnit(name);
		return obj;
	}
	/* -------------------------------------
	* Local state of the subject
	* --------------------------------------
	*/
	protected int lastMsgNum = 0;
	
	
	//Constructor
	public ControlUnitSupport(String name) throws Exception{
		super(name);
	 	isMultiInput=true;
	 	inputMessageList=new String[]{"sensorsData","picturePackage","forwardCommand", "endSelectInput"};
	 	initLastMsgRdMemo();  //put in initGui since the name must be set
		//Singleton
		if( obj != null ) return;
		 obj = (ControlUnit)this;
	}
	
	/* -------------------------------------
	* Init
	* --------------------------------------
	*/
	//PREPARE INPUT THREADS
	public void initInputSupports() throws Exception{
	PlatformExpert.findInSupport( getName(), "picturePackage" ,false,view);
	PlatformExpert.findInSupport( getName(), "forwardCommand" ,true,view);
	}
	
 	protected void initLastMsgRdMemo(){
 			lastMsgRdMemo.put("sensorsData"+getName(),0);
 			lastMsgRdMemo.put("picturePackage"+getName(),0);
 			lastMsgRdMemo.put("forwardCommand"+getName(),0);
 	}
	protected void initGui(){
		if( env != null ) view = env.getOutputView();
	    initLastMsgRdMemo(); //put here since the name must be set
	}
	
	/* -------------------------------------
	* State-based Behavior
	* -------------------------------------- 
	*/ 
	protected abstract void init() throws Exception;
	protected abstract void storeMissionStarted() throws Exception;
	protected abstract void storeDataSensors(java.lang.String sensorsDatasReceived) throws Exception;
	protected abstract void storePicturePackage(java.lang.String picturePackageReceived) throws Exception;
	protected abstract boolean checkCommandStart(java.lang.String command) throws Exception;
	protected abstract boolean checkReplyCommandStart(java.lang.String reply) throws Exception;
	protected abstract java.lang.String getWrongStartCommandReply() throws Exception;
	protected abstract void storeCommandAndReply(java.lang.String c,java.lang.String r) throws Exception;
	protected abstract boolean checkEndMission() throws Exception;
	protected abstract void shutdown() throws Exception;
	/* --- USER DEFINED STATE ACTIONS --- */
	/* --- USER DEFINED TASKS --- */
	/* 
		Each state acquires some input and performs some action 
		Each state is mapped into a void method 
	*/
	//Variable behavior declarations
	protected 
	String cmd = null;
	protected 
	String rpl = null;
	protected 
	String sensorsDatasReceived = null;
	protected 
	String picturePackageReceived = null;
	protected 
	boolean tmpCheck = false;
	public  java.lang.String get_cmd(){ return cmd; }
	public  java.lang.String get_rpl(){ return rpl; }
	public  java.lang.String get_sensorsDatasReceived(){ return sensorsDatasReceived; }
	public  java.lang.String get_picturePackageReceived(){ return picturePackageReceived; }
	public  boolean get_tmpCheck(){ return tmpCheck; }
	
	protected boolean endStateControl = false;
	protected String curstate ="st_controlUnit_init";
	protected void stateControl( ) throws Exception{
		boolean debugMode = System.getProperty("debugMode" ) != null;
	 		while( ! endStateControl ){
	 			//DEBUG 
	 			if(debugMode) debugNextState(); 
	 			//END DEBUG
			/* REQUIRES Java Compiler 1.7
			switch( curstate ){
				case "st_controlUnit_init" : st_controlUnit_init(); break; 
				case "st_controlUnit_ready" : st_controlUnit_ready(); break; 
				case "st_controlUnit_startMission" : st_controlUnit_startMission(); break; 
				case "st_controlUnit_wrongStartCommand" : st_controlUnit_wrongStartCommand(); break; 
				case "st_controlUnit_sendCommand" : st_controlUnit_sendCommand(); break; 
				case "st_controlUnit_onMission" : st_controlUnit_onMission(); break; 
				case "st_controlUnit_receivedSensorsData" : st_controlUnit_receivedSensorsData(); break; 
				case "st_controlUnit_receivedpicturePackage" : st_controlUnit_receivedpicturePackage(); break; 
				case "st_controlUnit_endMission" : st_controlUnit_endMission(); break; 
			}//switch	
			*/
			if( curstate.equals("st_controlUnit_init")){ st_controlUnit_init(); }
			else if( curstate.equals("st_controlUnit_ready")){ st_controlUnit_ready(); }
			else if( curstate.equals("st_controlUnit_startMission")){ st_controlUnit_startMission(); }
			else if( curstate.equals("st_controlUnit_wrongStartCommand")){ st_controlUnit_wrongStartCommand(); }
			else if( curstate.equals("st_controlUnit_sendCommand")){ st_controlUnit_sendCommand(); }
			else if( curstate.equals("st_controlUnit_onMission")){ st_controlUnit_onMission(); }
			else if( curstate.equals("st_controlUnit_receivedSensorsData")){ st_controlUnit_receivedSensorsData(); }
			else if( curstate.equals("st_controlUnit_receivedpicturePackage")){ st_controlUnit_receivedpicturePackage(); }
			else if( curstate.equals("st_controlUnit_endMission")){ st_controlUnit_endMission(); }
		}//while
		//DEBUG 
		//if( synch != null ) synch.add(getName()+" reached the end of stateControl loop"  );
	 	}
	 	protected void selectInput(boolean mostRecent, Vector<String> tempList) throws Exception{
		Vector<IMessage> queries=comSup.prepareInput(mostRecent,getName(),
				SysKb.getSyskb(),tempList.toArray(),InteractPolicy.nopolicy() );
		//showMsg("*** queries" + queries);
		curInputMsg = selectOneInput(mostRecent,queries);	
		curInputMsgContent = curInputMsg.msgContent();	
	}
	
	protected void st_controlUnit_ready()  throws Exception{
		
		 curRequest=hl_controlUnit_grant_forwardCommand();
		 curInputMsg= curRequest.getReceivedMessage();
		 curInputMsgContent = curInputMsg.msgContent();
		tmpCheck =checkCommandStart( curInputMsgContent ) ;
		
		{//XBlockcode
		boolean _tmpCheck = tmpCheck;
		boolean _operator_equals = BooleanExtensions.operator_equals(_tmpCheck, true);
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_controlUnit_startMission"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		
		{//XBlockcode
		boolean _tmpCheck = tmpCheck;
		boolean _operator_equals = BooleanExtensions.operator_equals(_tmpCheck, false);
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_controlUnit_wrongStartCommand"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_startMission()  throws Exception{
		
		cmd =curInputMsgContent;
		curAcquireOneReply=hl_controlUnit_demand_command_drone( cmd);
		curReply=curAcquireOneReply.acquireReply();
		curReplyContent = curReply.msgContent();
		rpl =curReplyContent;
		curRequest.replyToCaller( rpl ); 
		tmpCheck =checkReplyCommandStart( rpl ) ;
		
		{//XBlockcode
		boolean _tmpCheck = tmpCheck;
		boolean _operator_equals = BooleanExtensions.operator_equals(_tmpCheck, false);
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_controlUnit_ready"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		storeMissionStarted();storeCommandAndReply(cmd,rpl);curstate = "st_controlUnit_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_wrongStartCommand()  throws Exception{
		
		cmd =curInputMsgContent;
		rpl =getWrongStartCommandReply(  ) ;
		curRequest.replyToCaller( rpl ); 
		curstate = "st_controlUnit_ready"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_sendCommand()  throws Exception{
		
		 curRequest=hl_controlUnit_grant_forwardCommand();
		 curInputMsg= curRequest.getReceivedMessage();
		 curInputMsgContent = curInputMsg.msgContent();
		cmd =curInputMsgContent;
		curAcquireOneReply=hl_controlUnit_demand_command_drone( cmd);
		curReply=curAcquireOneReply.acquireReply();
		curReplyContent = curReply.msgContent();
		rpl =curReplyContent;
		storeCommandAndReply(cmd,rpl);curRequest.replyToCaller( rpl ); 
		curstate = "st_controlUnit_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_onMission()  throws Exception{
		
		//[it.unibo.indigo.contact.impl.SignalImpl@28e14a75 (name: sensorsData) (var: null), it.unibo.indigo.contact.impl.SignalImpl@5edf5c4a (name: notify) (var: null)] | forwardCommand isSignal=false
		resCheck = checkForMsg(getName(),"forwardCommand",null);
		if(resCheck){
			curstate = "st_controlUnit_sendCommand";
			return;}
		//[it.unibo.indigo.contact.impl.SignalImpl@28e14a75 (name: sensorsData) (var: null), it.unibo.indigo.contact.impl.SignalImpl@5edf5c4a (name: notify) (var: null)] | sensorsData isSignal=true
		resCheckMsg = checkSignal("ANY","sensorsData",false);
		if(resCheckMsg != null){
			curstate = "st_controlUnit_receivedSensorsData";
			return;}
		//[it.unibo.indigo.contact.impl.SignalImpl@28e14a75 (name: sensorsData) (var: null), it.unibo.indigo.contact.impl.SignalImpl@5edf5c4a (name: notify) (var: null)] | picturePackage isSignal=false
		resCheck = checkForMsg(getName(),"picturePackage",null);
		if(resCheck){
			curstate = "st_controlUnit_receivedpicturePackage";
			return;}
		tmpCheck =checkEndMission(  ) ;
		
		{//XBlockcode
		boolean _tmpCheck = tmpCheck;
		boolean _operator_equals = BooleanExtensions.operator_equals(_tmpCheck, true);
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_controlUnit_endMission"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_receivedSensorsData()  throws Exception{
		
		inputMessageList=new String[]{  "sensorsData"  };
		curInputMsg=selectWithPriority(false, inputMessageList);
		curInputMsgContent = curInputMsg.msgContent();
		sensorsDatasReceived =curInputMsgContent;
		storeDataSensors(sensorsDatasReceived);curstate = "st_controlUnit_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_receivedpicturePackage()  throws Exception{
		
		curInputMsg=hl_controlUnit_serve_picturePackage();
		curInputMsgContent = curInputMsg.msgContent();
		picturePackageReceived =curInputMsgContent;
		storePicturePackage(picturePackageReceived);curstate = "st_controlUnit_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_endMission()  throws Exception{
		
		shutdown();curstate = "st_controlUnit_init"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_controlUnit_init()  throws Exception{
		
		showMsg("Control Unit - initial");
		init(  );curstate = "st_controlUnit_ready"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	
   	
 	/* -------------------------------------
	* COMMUNICATION CORE OPERATIONS for controlUnit
	* --------------------------------------
	*/
 
	protected IMessage hl_controlUnit_serve_picturePackage(   ) throws Exception {
	IMessage m = new Message("controlUnit_picturePackage(ANYx1y2,picturePackage,M,N)");
	IMessage inMsg = comSup.inOnly( getName() ,"picturePackage", m );
	
		return inMsg;
	
	}
	
	protected IMessage hl_controlUnit_sense_sensorsData(   ) throws Exception {
	IMessage m = new Message("signal(ANYx1y2,sensorsData,M,N)");
	IMessage inMsg = comSup.rdw( getName() ,"sensorsData",  lastMsgRdMemo,m );
		return inMsg;
	
	}
	protected IMessage hl_controlUnit_sense_sensorsData( boolean mostRecent  ) throws Exception {
	if( ! mostRecent) return hl_controlUnit_sense_sensorsData ();
	else{
	IMessage m = new Message("signal(ANYx1y2,sensorsData,M,N)");
	IMessage inMsg = comSup.rdwMostRecent(getName() ,"sensorsData",  lastMsgRdMemo,m );
		return inMsg;
	}
	
	}
	
	protected IAcquireOneReply hl_controlUnit_demand_command_drone( String M  ) throws Exception {
	//EXPERT for COMPOSED controlUnit_demand_command_drone isInput=false withAnswer=true applVisible=true
	M = MsgUtil.putInEnvelope(M);
	IAcquireOneReply answer = comSup.outIn(
	"drone","command",getName(), 
	"drone_command("+getName()+",command,"+M+","+msgNum+")","controlUnit_drone_command(drone,command,M,"+msgNum+")" ); 
	msgNum++;return answer;
	
	}
	
	protected IMessageAndContext hl_controlUnit_grant_forwardCommand(   ) throws Exception {
	//EXPERT for COMPOSED controlUnit_grant_forwardCommand isInput=true withAnswer=true applVisible=true
	IMessageAndContext answer = comSup.inOut(
	getName() ,"forwardCommand", 
	"controlUnit_forwardCommand(ANYx1y2,forwardCommand,M,N)" ); 
	return answer;
	
	}
	
	
 	/* -------------------------------------
	* CONNECTION OPERATIONS for controlUnit
	* --------------------------------------
	*/
	
	/* -------------------------------------
	* Local body of the subject
	* --------------------------------------
	*/
	
		public void doJob() throws Exception{ stateControl(); }
	 	//INSERTED FOR DEBUG
		protected boolean nextStep = false;
		protected String stateBreakpoint = null;
		protected Vector<String> synch;
		protected synchronized void debugNextState() throws Exception{
			if( stateBreakpoint != null && ! curstate.equals(stateBreakpoint) ) return;
			while( stateBreakpoint != null && curstate.equals(stateBreakpoint) ){
				showMsg(" stateBreakpoint reached "  +  stateBreakpoint);
				synch.add("stateBreakpoint reached " + stateBreakpoint);
				//showMsg("wait");
	 			wait();			
			}
	//		if( stateBreakpoint != null   ) { //resumed!
	// 	 	stateBreakpoint = null;
	//			return;
			}
	//		while( ! nextStep ) wait();
	//		if( stateBreakpoint != null ) debugNextState();
	//		else{
	//			showMsg("resume nextStep");
	//			synch.add("nextStep done");
	//			nextStep = false;
	//		}
	//	}
	//	public synchronized void nextStateStep(Vector<String> synch) throws Exception{
	//		showMsg("nextStateStep " + curstate );
	//		this.synch = synch;
	//		nextStep = true;
	//		notifyAll();
	//	}
		public synchronized void nextStateStep(String state, Vector<String> synch) throws Exception{
			this.synch = synch;
			stateBreakpoint = state;
			nextStep = true;
			showMsg("nextStateStep stateBreakpoint=" + stateBreakpoint );
	 		notifyAll();
		}
		//END INSERTED FOR DEBUG
			
		protected void do_terminationState() throws Exception {
			//showMsg(  " ---- END STATE LOOP ---- " );
		}
	
	protected IMessage acquire(String msgId) throws Exception{
	  //showMsg("acquire "  +  msgId ); 
	  IMessage m;
	  //USER MESSAGES
	  if( msgId.equals("picturePackage")){
	  	m = hl_controlUnit_serve_picturePackage();
	  	return m;
	  }
	  if( msgId.equals("forwardCommand")){
	  	curRequest = hl_controlUnit_grant_forwardCommand();
	  	m = curRequest.getReceivedMessage();
	  	return m;		
	  }
	 if( msgId.equals("endSelectInput")){
	  String ms = MsgUtil.bm(MsgUtil.channelInWithPolicy(InteractPolicy.nopolicy(),getName(), "endSelectInput"), 
	    getName(), "endSelectInput", "ANYx1y2", "N");
	  //Serve the auto-dispatch
	  IMessage min = core.in(new Message(ms).toString());
	  return min;
	 }
	  throw new Exception("Wrong msgId:"+  msgId);
	}//acquire	
	
	/* -------------------------------------
	* Operations (from Java)
	* --------------------------------------
	*/
	
		
	/* -------------------------------------
	* Termination
	* --------------------------------------
	*/
	public void terminate() throws Exception{ //by EndSubjectConnections
		controlUnitDemand_command_droneEnd();controlUnitServe_picturePackageEnd();
		controlUnitGrant_forwardCommandEnd();
	 			 //Auto-forward a dispatch to finish selectInput operations
	 		    String ms =
	 		      MsgUtil.bm(MsgUtil.channelInWithPolicy(InteractPolicy.nopolicy(),getName(), "endSelectInput"), 
	 		       getName(), "endSelectInput", "endSelectInput", "0");
	 		    core.out(ms);
	if( synch != null ){
		synch.add(getName()+" reached the end of loop"  );
	}
	obj = null;
	//System.out.println(getName() + " terminated");
	}	
	// Teminate operations
	protected void controlUnitDemand_command_droneEnd() throws Exception{
	 		PlatformExpert.findOutSupportToEnd("controlUnit","command",getName(),view );
		//showMsg("terminate controlUnitDemand_command_drone");
	}	
	protected void controlUnitServe_picturePackageEnd() throws Exception{
	 		PlatformExpert.findInSupportToEnd(getName(),"picturePackage",view );
		//showMsg("terminate controlUnitServe_picturePackage");
	}
	protected void controlUnitGrant_forwardCommandEnd() throws Exception{
	 	PlatformExpert.findInSupportToEnd(getName(),"forwardCommand",view );
		//showMsg("terminate controlUnitGrant_forwardCommand");
	}
}//ControlUnitSupport
