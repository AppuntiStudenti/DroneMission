/*
*  Generated by AN Unibo
*/
package it.unibo.contact.DroneMissionSystem;
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

public abstract class HeadQuarterSupport extends Subject{
	private static HeadQuarter obj = null;
	private IMessage resCheckMsg;
	private boolean resCheck;
	/*
	* Factory method: returns a singleton
	*/
	public static HeadQuarter create(String name) throws Exception{
		if( obj == null ) obj = new HeadQuarter(name);
		return obj;
	}
	/* -------------------------------------
	* Local state of the subject
	* --------------------------------------
	*/
	protected int lastMsgNum = 0;
	
	
	//Constructor
	public HeadQuarterSupport(String name) throws Exception{
		super(name);
	 	isMultiInput=true;
	 	inputMessageList=new String[]{"sensorsData","photo", "endSelectInput"};
	 	initLastMsgRdMemo();  //put in initGui since the name must be set
		//Singleton
		if( obj != null ) return;
		 obj = (HeadQuarter)this;
	}
	
	/* -------------------------------------
	* Init
	* --------------------------------------
	*/
	//PREPARE INPUT THREADS
	public void initInputSupports() throws Exception{
	PlatformExpert.findInSupport( getName(), "photo" ,false,view);
	}
	
 	protected void initLastMsgRdMemo(){
 			lastMsgRdMemo.put("sensorsData"+getName(),0);
 			lastMsgRdMemo.put("photo"+getName(),0);
 	}
	protected void initGui(){
		if( env != null ) view = env.getOutputView();
	    initLastMsgRdMemo(); //put here since the name must be set
	}
	
	/* -------------------------------------
	* State-based Behavior
	* -------------------------------------- 
	*/ 
	protected abstract java.lang.String getCommandToSend() throws Exception;
	protected abstract void updateDashboard(java.lang.String sensorsDatasReceived) throws Exception;
	protected abstract void storePhotoData(java.lang.String photoReceived) throws Exception;
	protected abstract void shutdown() throws Exception;
	/* --- USER DEFINED STATE ACTIONS --- */
	/* --- USER DEFINED TASKS --- */
	/* 
		Each state acquires some input and performs some action 
		Each state is mapped into a void method 
	*/
	//Variable behavior declarations
	protected 
	String command = null;
	protected 
	String sensorsDatasReceived = null;
	protected 
	String photoReceived = null;
	protected 
	String commandAnswer = null;
	public  java.lang.String get_command(){ return command; }
	public  java.lang.String get_sensorsDatasReceived(){ return sensorsDatasReceived; }
	public  java.lang.String get_photoReceived(){ return photoReceived; }
	public  java.lang.String get_commandAnswer(){ return commandAnswer; }
	
	protected boolean endStateControl = false;
	protected String curstate ="st_HeadQuarter_init";
	protected void stateControl( ) throws Exception{
		boolean debugMode = System.getProperty("debugMode" ) != null;
	 		while( ! endStateControl ){
	 			//DEBUG 
	 			if(debugMode) debugNextState(); 
	 			//END DEBUG
			/* REQUIRES Java Compiler 1.7
			switch( curstate ){
				case "st_HeadQuarter_init" : st_HeadQuarter_init(); break; 
				case "st_HeadQuarter_ready" : st_HeadQuarter_ready(); break; 
				case "st_HeadQuarter_onMission" : st_HeadQuarter_onMission(); break; 
				case "st_HeadQuarter_receivedSensorsData" : st_HeadQuarter_receivedSensorsData(); break; 
				case "st_HeadQuarter_receivedPhoto" : st_HeadQuarter_receivedPhoto(); break; 
				case "st_HeadQuarter_endMission" : st_HeadQuarter_endMission(); break; 
			}//switch	
			*/
			if( curstate.equals("st_HeadQuarter_init")){ st_HeadQuarter_init(); }
			else if( curstate.equals("st_HeadQuarter_ready")){ st_HeadQuarter_ready(); }
			else if( curstate.equals("st_HeadQuarter_onMission")){ st_HeadQuarter_onMission(); }
			else if( curstate.equals("st_HeadQuarter_receivedSensorsData")){ st_HeadQuarter_receivedSensorsData(); }
			else if( curstate.equals("st_HeadQuarter_receivedPhoto")){ st_HeadQuarter_receivedPhoto(); }
			else if( curstate.equals("st_HeadQuarter_endMission")){ st_HeadQuarter_endMission(); }
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
	
	protected void st_HeadQuarter_ready()  throws Exception{
		
		showMsg("----- Ready to send command -----");
		curAcquireOneReply=hl_headQuarter_demand_command_drone( "setspeed 60");
		curReply=curAcquireOneReply.acquireReply();
		curReplyContent = curReply.msgContent();
		commandAnswer =curReplyContent;
		showMsg("DRONE REPLY: "+commandAnswer);
		
		{//XBlockcode
		String _commandAnswer = commandAnswer;
		boolean _operator_equals = ObjectExtensions.operator_equals(_commandAnswer, "OK");
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_HeadQuarter_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_HeadQuarter_onMission()  throws Exception{
		
		command =getCommandToSend(  ) ;
		curAcquireOneReply=hl_headQuarter_demand_command_drone( command);
		curReply=curAcquireOneReply.acquireReply();
		curReplyContent = curReply.msgContent();
		commandAnswer =curReplyContent;
		
		{//XBlockcode
		String _commandAnswer = commandAnswer;
		boolean _operator_equals = ObjectExtensions.operator_equals(_commandAnswer, "FAIL");
		expXabseResult=_operator_equals;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		showMsg("DRONE CMD FAILED: "+commandAnswer);
		}//if cond
		
		{//XBlockcode
		boolean _operator_and = false;
		String _commandAnswer = commandAnswer;
		boolean _operator_equals = ObjectExtensions.operator_equals(_commandAnswer, "OK");
		if (!_operator_equals) {
		  _operator_and = false;
		} else {
		  String _command = command;
		  boolean _operator_equals_1 = ObjectExtensions.operator_equals(_command, "stop");
		  _operator_and = BooleanExtensions.operator_and(_operator_equals, _operator_equals_1);
		}
		expXabseResult=_operator_and;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_HeadQuarter_endMission"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		//[it.unibo.indigo.contact.impl.SignalImpl@2b61066a (name: sensorsData) (var: null), it.unibo.indigo.contact.impl.SignalImpl@541bc8c7 (name: notify) (var: null)] | sensorsData isSignal=true
		resCheckMsg = checkSignal("ANY","sensorsData",false);
		if(resCheckMsg != null){
			curstate = "st_HeadQuarter_receivedSensorsData";
			return;}
		//[it.unibo.indigo.contact.impl.SignalImpl@2b61066a (name: sensorsData) (var: null), it.unibo.indigo.contact.impl.SignalImpl@541bc8c7 (name: notify) (var: null)] | photo isSignal=false
		resCheck = checkForMsg(getName(),"photo",null);
		if(resCheck){
			curstate = "st_HeadQuarter_receivedPhoto";
			return;}
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_HeadQuarter_receivedSensorsData()  throws Exception{
		
		inputMessageList=new String[]{  "sensorsData"  };
		curInputMsg=selectWithPriority(false, inputMessageList);
		curInputMsgContent = curInputMsg.msgContent();
		sensorsDatasReceived =curInputMsgContent;
		updateDashboard(sensorsDatasReceived);curstate = "st_HeadQuarter_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_HeadQuarter_receivedPhoto()  throws Exception{
		
		curInputMsg=hl_headQuarter_serve_photo();
		curInputMsgContent = curInputMsg.msgContent();
		photoReceived =curInputMsgContent;
		storePhotoData(photoReceived);curstate = "st_HeadQuarter_onMission"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_HeadQuarter_endMission()  throws Exception{
		
		shutdown();/* --- TRANSITION TO NEXT STATE --- */
		resetCurVars();
		do_terminationState();
		endStateControl=true;
	}
	protected void st_HeadQuarter_init()  throws Exception{
		
		showMsg("----- HeadQuarter Initialized -----");
		curstate = "st_HeadQuarter_ready"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	
   	
 	/* -------------------------------------
	* COMMUNICATION CORE OPERATIONS for headQuarter
	* --------------------------------------
	*/
 
	protected IMessage hl_headQuarter_serve_photo(   ) throws Exception {
	IMessage m = new Message("headQuarter_photo(ANYx1y2,photo,M,N)");
	IMessage inMsg = comSup.inOnly( getName() ,"photo", m );
	
		return inMsg;
	
	}
	
	protected IMessage hl_headQuarter_sense_sensorsData(   ) throws Exception {
	IMessage m = new Message("signal(ANYx1y2,sensorsData,M,N)");
	IMessage inMsg = comSup.rdw( getName() ,"sensorsData",  lastMsgRdMemo,m );
		return inMsg;
	
	}
	protected IMessage hl_headQuarter_sense_sensorsData( boolean mostRecent  ) throws Exception {
	if( ! mostRecent) return hl_headQuarter_sense_sensorsData ();
	else{
	IMessage m = new Message("signal(ANYx1y2,sensorsData,M,N)");
	IMessage inMsg = comSup.rdwMostRecent(getName() ,"sensorsData",  lastMsgRdMemo,m );
		return inMsg;
	}
	
	}
	
	protected IAcquireOneReply hl_headQuarter_demand_command_drone( String M  ) throws Exception {
	//EXPERT for COMPOSED headQuarter_demand_command_drone isInput=false withAnswer=true applVisible=true
	M = MsgUtil.putInEnvelope(M);
	IAcquireOneReply answer = comSup.outIn(
	"drone","command",getName(), 
	"drone_command("+getName()+",command,"+M+","+msgNum+")","headQuarter_drone_command(drone,command,M,"+msgNum+")" ); 
	msgNum++;return answer;
	
	}
	
	
 	/* -------------------------------------
	* CONNECTION OPERATIONS for headQuarter
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
	  if( msgId.equals("photo")){
	  	m = hl_headQuarter_serve_photo();
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
		headQuarterDemand_command_droneEnd();headQuarterServe_photoEnd();
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
	protected void headQuarterDemand_command_droneEnd() throws Exception{
	 		PlatformExpert.findOutSupportToEnd("headQuarter","command",getName(),view );
		//showMsg("terminate headQuarterDemand_command_drone");
	}	
	protected void headQuarterServe_photoEnd() throws Exception{
	 		PlatformExpert.findInSupportToEnd(getName(),"photo",view );
		//showMsg("terminate headQuarterServe_photo");
	}
}//HeadQuarterSupport
