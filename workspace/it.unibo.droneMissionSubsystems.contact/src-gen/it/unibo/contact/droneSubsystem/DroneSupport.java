/*
*  Generated by AN Unibo
*/
package it.unibo.contact.droneSubsystem;
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
import it.unibo.baseEnv.basicFrame.EnvFrame;

public abstract class DroneSupport extends Subject{
	private static Drone obj = null;
	private IMessage resCheckMsg;
	private boolean resCheck;
	/*
	* Factory method: returns a singleton
	*/
	public static Drone create(String name) throws Exception{
		if( obj == null ) obj = new Drone(name);
		return obj;
	}
	/* -------------------------------------
	* Local state of the subject
	* --------------------------------------
	*/
	protected int lastMsgNum = 0;
	
	
	//Constructor
	public DroneSupport(String name) throws Exception{
		super(name);
	 	isMultiInput=true;
	 	inputMessageList=new String[]{"tic","photo", "endSelectInput"};
	 	initLastMsgRdMemo();  //put in initGui since the name must be set
		//Singleton
		if( obj != null ) return;
		 obj = (Drone)this;
	}
	
	/* -------------------------------------
	* Init
	* --------------------------------------
	*/
	//PREPARE INPUT THREADS
	public void initInputSupports() throws Exception{
	}
	
 	protected void initLastMsgRdMemo(){
 			lastMsgRdMemo.put("tic"+getName(),0);
 			lastMsgRdMemo.put("photo"+getName(),0);
 	}
	protected void initGui(){
	    env = new EnvFrame( getName(), this, new java.awt.Color(151, 228, 255), java.awt.Color.black );
	    env.init();
	    env.writeOnStatusBar(getName() + " | DroneSupport working ... ",14);
	    view = env.getOutputView();
	    initLastMsgRdMemo(); //put here since the name must be set
	 }
	
	/* -------------------------------------
	* State-based Behavior
	* -------------------------------------- 
	*/ 
	protected abstract void initDrone() throws Exception;
	protected abstract java.lang.String updateStatus(java.lang.String sensor) throws Exception;
	protected abstract java.lang.String preparePhotoPack() throws Exception;
	protected abstract void sendPhotoToHeadQuarter(java.lang.String packet) throws Exception;
	protected abstract boolean fuelEmpty() throws Exception;
	/* --- USER DEFINED STATE ACTIONS --- */
	/* --- USER DEFINED TASKS --- */
	/* 
		Each state acquires some input and performs some action 
		Each state is mapped into a void method 
	*/
	//Variable behavior declarations
	protected 
	String from = "";
	protected 
	String photoPack = "";
	protected 
	boolean fuel = true;
	protected 
	String s = "";
	public  java.lang.String get_from(){ return from; }
	public  java.lang.String get_photoPack(){ return photoPack; }
	public  boolean get_fuel(){ return fuel; }
	public  java.lang.String get_s(){ return s; }
	
	protected boolean endStateControl = false;
	protected String curstate ="st_drone_init";
	protected void stateControl( ) throws Exception{
		boolean debugMode = System.getProperty("debugMode" ) != null;
	 		while( ! endStateControl ){
	 			//DEBUG 
	 			if(debugMode) debugNextState(); 
	 			//END DEBUG
			/* REQUIRES Java Compiler 1.7
			switch( curstate ){
				case "st_drone_init" : st_drone_init(); break; 
				case "st_drone_started" : st_drone_started(); break; 
				case "st_drone_sensorsHandler" : st_drone_sensorsHandler(); break; 
				case "st_drone_sendPhoto" : st_drone_sendPhoto(); break; 
				case "st_drone_endState" : st_drone_endState(); break; 
			}//switch	
			*/
			if( curstate.equals("st_drone_init")){ st_drone_init(); }
			else if( curstate.equals("st_drone_started")){ st_drone_started(); }
			else if( curstate.equals("st_drone_sensorsHandler")){ st_drone_sensorsHandler(); }
			else if( curstate.equals("st_drone_sendPhoto")){ st_drone_sendPhoto(); }
			else if( curstate.equals("st_drone_endState")){ st_drone_endState(); }
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
	
	protected void st_drone_started()  throws Exception{
		
		//[it.unibo.indigo.contact.impl.SignalImpl@67a33a8c (name: tic) (var: null), it.unibo.indigo.contact.impl.SignalImpl@d58f27c (name: photo) (var: null), it.unibo.indigo.contact.impl.SignalImpl@4a97b1bc (name: stop) (var: null)] | tic isSignal=true
		resCheckMsg = checkSignal("ANY","tic",false);
		if(resCheckMsg != null){
			curstate = "st_drone_sensorsHandler";
			return;}
		//[it.unibo.indigo.contact.impl.SignalImpl@67a33a8c (name: tic) (var: null), it.unibo.indigo.contact.impl.SignalImpl@d58f27c (name: photo) (var: null), it.unibo.indigo.contact.impl.SignalImpl@4a97b1bc (name: stop) (var: null)] | photo isSignal=true
		resCheckMsg = checkSignal("ANY","photo",false);
		if(resCheckMsg != null){
			curstate = "st_drone_sendPhoto";
			return;}
		fuel =fuelEmpty() ;
		
		{//XBlockcode
		boolean _fuel = fuel;
		boolean _operator_not = BooleanExtensions.operator_not(_fuel);
		expXabseResult=_operator_not;
		}//XBlockcode
		if(  (Boolean)expXabseResult ){ //cond
		curstate = "st_drone_endState"; 
		//resetCurVars(); //leave the current values on
		return;
		}//if cond
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_drone_sensorsHandler()  throws Exception{
		
		inputMessageList=new String[]{  "tic"  };
		curInputMsg=selectWithPriority(false, inputMessageList);
		curInputMsgContent = curInputMsg.msgContent();
		from =curInputMsg.msgEmitter() ;
		showMsg("Received message from "+from);
		s =updateStatus(from) ;
		showMsg("Sensors updated");
		showMsg(s);
		curstate = "st_drone_started"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_drone_sendPhoto()  throws Exception{
		
		showMsg("Received photo");
		inputMessageList=new String[]{  "photo"  };
		curInputMsg=selectWithPriority(false, inputMessageList);
		curInputMsgContent = curInputMsg.msgContent();
		photoPack =preparePhotoPack() ;
		showMsg(photoPack);
		photoPack =photoPack+curInputMsg.msgContent() ;
		sendPhotoToHeadQuarter(photoPack);curstate = "st_drone_started"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	protected void st_drone_endState()  throws Exception{
		
		hl_drone_emit_stop( "stop" );
		/* --- TRANSITION TO NEXT STATE --- */
		resetCurVars();
		do_terminationState();
		endStateControl=true;
	}
	protected void st_drone_init()  throws Exception{
		
		showMsg("Starting Drone");
		initDrone();curstate = "st_drone_started"; 
		//resetCurVars(); //leave the current values on
		return;
		/* --- TRANSITION TO NEXT STATE --- */
	}
	
   	
 	/* -------------------------------------
	* COMMUNICATION CORE OPERATIONS for drone
	* --------------------------------------
	*/
 
	protected IMessage hl_drone_sense_tic(   ) throws Exception {
	IMessage m = new Message("signal(ANYx1y2,tic,M,N)");
	IMessage inMsg = comSup.rdw( getName() ,"tic",  lastMsgRdMemo,m );
		return inMsg;
	
	}
	protected IMessage hl_drone_sense_tic( boolean mostRecent  ) throws Exception {
	if( ! mostRecent) return hl_drone_sense_tic ();
	else{
	IMessage m = new Message("signal(ANYx1y2,tic,M,N)");
	IMessage inMsg = comSup.rdwMostRecent(getName() ,"tic",  lastMsgRdMemo,m );
		return inMsg;
	}
	
	}
	
	protected IMessage hl_drone_sense_photo(   ) throws Exception {
	IMessage m = new Message("signal(ANYx1y2,photo,M,N)");
	IMessage inMsg = comSup.rdw( getName() ,"photo",  lastMsgRdMemo,m );
		return inMsg;
	
	}
	protected IMessage hl_drone_sense_photo( boolean mostRecent  ) throws Exception {
	if( ! mostRecent) return hl_drone_sense_photo ();
	else{
	IMessage m = new Message("signal(ANYx1y2,photo,M,N)");
	IMessage inMsg = comSup.rdwMostRecent(getName() ,"photo",  lastMsgRdMemo,m );
		return inMsg;
	}
	
	}
	
	protected void hl_drone_emit_stop( String M  ) throws Exception {
	M = MsgUtil.putInEnvelope(M);
	IMessage m = new Message("signal("+getName()+",stop,"+M+","+msgNum+")");
	comSup.outOnly( getName() ,"stop",  m );
	msgNum++;
	
	}
	
	
 	/* -------------------------------------
	* CONNECTION OPERATIONS for drone
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
		droneEmit_stopEnd();
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
	protected void droneEmit_stopEnd() throws Exception{
		//No operation is done at subject level. The SenseRemote threads are terminates 
		//when the main application is closed
	//		PlatformExpert.findOutSupportToEnd("space","coreCmd","coreToDSpace", view);		
	//		showMsg("terminate signal support");
	}
}//DroneSupport
