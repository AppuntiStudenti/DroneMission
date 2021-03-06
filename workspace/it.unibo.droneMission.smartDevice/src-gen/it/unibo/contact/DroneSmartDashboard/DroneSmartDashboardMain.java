/*
*  Generated by AN Unibo 
*/
package it.unibo.contact.DroneSmartDashboard;
import it.unibo.contact.platformuv.*;
import it.unibo.contact.platformuv.LindaLike;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IContactSystem;
import it.unibo.is.interfaces.IMessage;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.platforms.IAcquireOneReply;
import it.unibo.is.interfaces.platforms.ILindaLike;
import it.unibo.is.interfaces.protocols.IConnInteraction;
public abstract class DroneSmartDashboardMain implements IContactSystem{
	protected IBasicEnvAwt env = null;
	protected IOutputView view = null;
 	protected LindaLike core = null;
	protected SmartdeviceSupport smartdevice;
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
		initSupport();
	}
	protected void initSupport(){
		MsgUtil.init(view);
		core = ((LindaLike)LindaLike.initSpace(view,"droneSmartDashboard"));
	}
	//For debug purpose
	public Smartdevice get_smartdevice()throws Exception{while(smartdevice==null)Thread.sleep(100);return (Smartdevice)smartdevice; }
	protected void configureSystem(){		
		RunTimeKb.init(view);
	//Protocols for application messages
		RunTimeKb.addSubject("TCP","space","setConnChannel", "10.0.2.2", 8070  );		 
			RunTimeKb.addSubject("TCP", "space","coreCmd", "10.0.2.2",8070);	
			RunTimeKb.addInputConnMsg( "update", false);
			//RunTimeKb.addSubject("TCP", "space","outCmd", "10.0.2.2" ,8070);	
		RunTimeKb.addSubject("TCP" , "headQuarter" , "photo","localhost",4060 );   	
		RunTimeKb.addSubject("TCP" , "drone" , "command","localhost",4050 );   	
	//Application messages
		RunTimeKb.addInputConnMsg( "coreCmd", false); //system dispatch
	  		RunTimeKb.addInputConnMsg( "photo", false);
	  		RunTimeKb.addInputConnMsg( "command", true);
	}
	protected void configureSubjects(){
	try {
	smartdevice = SmartdeviceSupport.create("smartdevice");  
	 	smartdevice.setEnv(env);
	smartdevice.initInputSupports();	 
	registerObservers();
	}catch(Exception e){e.printStackTrace();}
	}
	protected void configure(){
	 	configureSystem();
		configureSubjects();  
			try {
				if( env != null) env.writeOnStatusBar( Thread.currentThread() +  " |  DroneSmartDashboard connecting ...",14);			
	 			ask_setConnChannel_space();
				serveUpdateDispatchThread();
				if( env != null) env.writeOnStatusBar( Thread.currentThread() +  " |  DroneSmartDashboard working ...",14);
			} catch (Exception e) {
	 			//e.printStackTrace();
	 			if(view!=null) view.addOutput("configure ERROR " + e.getMessage() );
			}
	}	
	protected void registerObservers(){
	}
	protected void start(){
		smartdevice.start();
	}
   	public boolean isPassive() { return true; }
	public void terminate() {
	System.out.println("DroneSmartDashboard TERMINATES");
	try {
	 	
	 	} catch (Exception e) {
		e.printStackTrace();
	}	
	System.exit(1);//The radical solution
	}
	protected void ask_setConnChannel_space()  {
		try{
	  		ILindaLike support = PlatformExpert.findOutSupport("space","setConnChannel","droneSmartDashboard",view);
		 	RunTimeKb.addSubjectConnectionSupport("droneSmartDashboard", support, view );
		 	String msgOut = "space_setConnChannel(droneSmartDashboard, setConnChannel, connect, 0) "  ;
			support.out( msgOut );
	/*		
			System.out.println( " ask_setConnChannel_space: Now create a ConnReceiver input");
			IConnInteraction conn = ((ConnProtOut)support).getConnection(); //could wait
			ConnReceiver cr = new ConnReceiver("inConn_space", conn, view);			
			cr.start();
	*/	
			IAcquireOneReply answer = new AcquireOneReply("droneSmartDashboard", "space","setConnChannel",core, 
				"droneSmartDashboard"+"_space_setConnChannel(space,setConnChannel,M,0)",view);
			IMessage reply = answer.acquireReply();
			if( reply.msgContent().contains("exception")) throw new Exception("connection not possible");
			System.out.println(" ask_setConnChannel_space: reply= " + reply.msgContent() + " from " + reply.msgEmitter() );
	 	}catch( Exception e){
			System.out.println(" ask_setConnChannel_space: ERROR " + e.getMessage() );	
		}
	 }
	 
	 protected void serveUpdateDispatchThread() throws Exception {
			new Thread(){
				protected boolean goon = true;
				protected CommLogic comSup = new CommLogic(view);
				
				protected IMessage hl_node_serve_update(   ) throws Exception {
					IMessage m = new Message("droneSmartDashboard"+"_update"+"(ANY,update,M,N)");
					IMessage inMsg = comSup.inOnly( "droneSmartDashboard" ,"update", m );				
					return inMsg;				
				}		
		
				public void run(){
					System.out.println("droneSmartDashboard serveUpdateDispatchThread started");
					while(goon)
					try {
						IMessage m =  hl_node_serve_update();
	 					System.out.println("droneSmartDashboard storing content of: " + m   );
						LindaLike.getSpace().out( m.msgContent() );					 
					} catch (Exception e) {
						goon=false;
	 					e.printStackTrace();
					}
					
				}
			}.start();
	 }
	public static void main(String args[]) throws Exception {
	DroneSmartDashboard system = new DroneSmartDashboard( );
	system.doJob();
	}
}//DroneSmartDashboardSupportMain
