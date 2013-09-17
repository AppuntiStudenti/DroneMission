/*
*  Generated by AN Unibo 
*/
package it.unibo.contact.DroneMissionSystem;
import it.unibo.contact.platformuv.*;
import it.unibo.baseEnv.basicFrame.EnvFrame;
import it.unibo.contact.platformuv.LindaLike;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IContactSystem;
import it.unibo.is.interfaces.IMessage;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.platforms.IAcquireOneReply;
import it.unibo.is.interfaces.platforms.ILindaLike;
import it.unibo.is.interfaces.protocols.IConnInteraction;
public class SubSystemHeadQuarterMain implements IContactSystem{
	protected IBasicEnvAwt env = null;
	protected IOutputView view = null;
 	protected LindaLike core = null;
	protected SmartdeviceSupport smartdevice;
	protected DroneSupport drone;
	protected HeadQuarterSupport headQuarter;
	public void doJob(){
		initProperty();
		init();
		configure();
		start();
	}
	protected void initProperty(){
	//Properties used by the system  (TODO)
	//System.setProperty("observeSpace", "unset");	//automatically set by selectInput
	//Properties to set communication parameters 
	//System.setProperty("numOfConnectionAttempts", "15");
	//System.setProperty("inputTimeOut", "20000");
	//Properties to show the internal behavior 
	System.setProperty("coreTrace", "unset");
	System.setProperty("medclTrace", "unset");
	System.setProperty("connTrace", "unset"); 
	System.setProperty("signalTrace", "unset");
	System.setProperty("obsTrace", "unset");	
	System.setProperty("ConnProtLindaLike", "unset"); 
	System.setProperty("ConnProtIn", "unset");
	System.setProperty("ConnProtOut", "unset");
	System.setProperty("tcpLowTrace", "unset");
	  }
	protected void init(){
		initGui();
		initSupport();
	}
	protected void initSupport(){
		MsgUtil.init(view);
		core = ((LindaLike)LindaLike.initSpace(view,"subSystemHeadQuarter"));
	}
	protected void initGui(){
	 env = new EnvFrame( "SubSystemHeadQuarter", this );
	 env.init();
	 env.writeOnStatusBar( Thread.currentThread() +  " | SubSystemHeadQuarter working ...",14);
	 view = env.getOutputView();
	}		
	//For debug purpose
	public Smartdevice get_smartdevice()throws Exception{while(smartdevice==null)Thread.sleep(100);return (Smartdevice)smartdevice; }
	public Drone get_drone()throws Exception{while(drone==null)Thread.sleep(100);return (Drone)drone; }
	public HeadQuarter get_headQuarter()throws Exception{while(headQuarter==null)Thread.sleep(100);return (HeadQuarter)headQuarter; }
	protected void configureSystem(){		
		RunTimeKb.init(view);
	//Protocols for application messages
		RunTimeKb.addSubject("TCP" , "headQuarter" , "photo","localhost",4060 );   	
		RunTimeKb.addSubject("TCP" , "drone" , "command","localhost",4050 );   	
	//Application messages
		RunTimeKb.addInputConnMsg( "coreCmd", false); //system dispatch
	  		RunTimeKb.addInputConnMsg( "photo", false);
	  		RunTimeKb.addInputConnMsg( "command", true);
	}
	protected void configureSubjects(){
	try {
	registerObservers();
	}catch(Exception e){e.printStackTrace();}
	}
	protected void configure(){
	 	configureSystem();
		configureSubjects();  
	}
	protected void registerObservers(){
	}
	protected void start(){
	}
   	public boolean isPassive() { return true; }
	public void terminate() {
	System.out.println("SubSystemHeadQuarter TERMINATES");
	try {
	 	
	 	} catch (Exception e) {
		e.printStackTrace();
	}	
	System.exit(1);//The radical solution
	}
	public static void main(String args[]) throws Exception {
	SubSystemHeadQuarterMain system = new SubSystemHeadQuarterMain( );
	system.doJob();
	}
}//SubSystemHeadQuarterSupportMain
