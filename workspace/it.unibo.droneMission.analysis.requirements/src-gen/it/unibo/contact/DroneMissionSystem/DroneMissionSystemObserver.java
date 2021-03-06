/*
*  Generated by AN Unibo
*/
package it.unibo.contact.DroneMissionSystem;
import it.unibo.is.interfaces.*; 
import java.util.Observable;
import java.util.Vector;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.baseEnv.basicFrame.EnvFrame;
import it.unibo.contact.platformuv.BasicMessage;
import it.unibo.contact.platformuv.LindaLike;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
//import java.awt.Color;

public  class  DroneMissionSystemObserver extends Subject implements java.util.Observer{
	protected int lastMsgNum = 0; 
    protected String statusStr =  getName() + " | DroneMissionSystemObserver" ;
  
	public DroneMissionSystemObserver( ) throws Exception{
		super();	
		initGui();
	}
	
	public void doJob() throws Exception {
	}
	
	protected void initGui(){
	    env = new EnvFrame( "DroneMissionSystemObserver", this, java.awt.Color.yellow, java.awt.Color.blue );
	    env.init();
	    env.writeOnStatusBar(statusStr + " working ..." + " | message num="+ numOfMsg(),14);
	    view = env.getOutputView();
	}

 	public  void update(Observable arg0, Object arg1) {
 	 	String msg = (String)arg1;
	 	try {
 	 	if( msg.startsWith("IN") ){ 
   	 		Struct mr = (Struct) Term.createTerm( msg.substring(2) );
   	 		Struct mrc = (Struct) mr.getArg(0);
 	 		doprintln("				retract msg N=" + mrc.getArg(1) );
  	 	}else if( msg.startsWith("RD") || msg.startsWith("RW")  ){ 
//	 		doprintln("		" +msg.substring(0,2)+ " " +msg.substring(2));
  	 	}else if(  msg.startsWith("OM")){
//// 	 		doprintln("		... "  );
	 	}else{
			IBasicMessage bm = new BasicMessage(msg);
			String content = bm.getMsgContent();
			if( content.startsWith("outgoing") ){
				doprintln("N="+ bm.getMsgSeqNum() + "		" + bm.getMsgContent()  );
				LindaLike.getEngine().solve("retract("+ bm.basicToString() +").");
			}else if(  content.startsWith("coreToDSpace_") 
					|| content.contains("_update") || content.contains("_space_setConnChannel") )
				doprintln("N="+ bm.getMsgSeqNum() + "		" + bm.getMsgContent() );
			else
				doprintln("N="+ bm.getMsgSeqNum() + "	" + bm.getMsgContent() );		
 		}
	    env.writeOnStatusBar(statusStr + " | message num="+ numOfMsg(),14);
	 	}catch (Exception e) {
			println(" ERROR "+ msg + " " +e.getMessage());
 		}
 	}//update

 	protected int numOfMsg()  {
 	int nmsg = 0; 		
 	try{
 		SolveInfo sol = LindaLike.getEngine().solve( "mmm(X,Y)." );		
 		while( sol.isSuccess() && sol.hasOpenAlternatives() ){
 			nmsg++;
 			sol = LindaLike.getEngine().solveNext();
 		}
 		if( sol.isSuccess() ) nmsg++;
 		return nmsg;
 	}catch(Exception e){
 		return -1; //means error
 	}
 	}

	@Override
	protected IMessage acquire(String msgId) throws Exception {
		return null;
	}
	
	public void terminate() throws Exception{
	}

	protected  void doprintln( String msg){
		if( view != null )
			view.addOutput( msg );
		else System.out.println(msg);		
	}

	protected void println( String msg){
		String m = "llo-> "+msg;
		doprintln(m);
	}

}//DroneMissionSystemObserve
