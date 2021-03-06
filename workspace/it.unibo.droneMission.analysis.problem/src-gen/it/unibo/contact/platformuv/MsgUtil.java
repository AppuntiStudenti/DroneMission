/*
*  Generated by AN Unibo
*/
package it.unibo.contact.platformuv;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.IMessage;
import java.util.Vector;
import it.unibo.is.interfaces.IPolicy;
import alice.tuprolog.*;

public class MsgUtil {
protected static IOutputView view = null;

	public static void init(IOutputView aview){
		view = aview;
 	}
		
	public static boolean isVar( String v){
		try {
			Term tv = Term.createTerm(v);
			return tv instanceof Var;
		} catch (InvalidTermException e) {
			return false;
		}
	}

	public static String bmm( String channelId, String workerName, 
			String msgId, String content, String msgNum  ){
 		String cc =  ( isVar(content) ) ? content : "\""+content+"\"";
		return channelId+"("+workerName+" , "+msgId+" , " + cc + " , "+msgNum+")";
	}	

	public static String bm( String channelId, String workerName, 
			String msgId, String content, String msgNum  ){
 		String cc =  ( isVar(content) ) ? content : "\'"+content+"\'";
		return channelId+"("+workerName+" , "+msgId+" , " + cc + " , "+msgNum+")";
	}	
	
	public static  String channel3( String a1, String a2, String a3  ){
		return a1+"_"+a2+"_"+a3;
	}	
	
	public static  String channel2( String a1, String a2   ){
		return a1+"_"+a2  ;
	}		
	
	public static void showMsg(IOutputView view, String msg){
		  if(  view != null )	view.addOutput(msg);
		  else System.out.println("*** "+msg);		 		  
	}  

	public static String buildExceptionTerm( String query, String msg){
			Term t = Term.createTerm(query);
			String tName = ((Struct) t).getName();
			Term t0 = ((Struct) t).getArg(0);
			Term t1 = ((Struct) t).getArg(1);
//			Term t2 = ((Struct) t).getArg(2);
			Term t3 = ((Struct) t).getArg(3);
			String exceptionTerm = 
					tName+"("+t0+","+t1+", '"+ msg + "' ,"+ t3 +" )";
			return exceptionTerm;
	}
	public static String buildExceptionReplyTerm( String query, String msg){
		Term t = Term.createTerm(query);
		String tName = ((Struct) t).getName();
		Term t0 = ((Struct) t).getArg(0);
		Term t1 = ((Struct) t).getArg(1);
//		Term t2 = ((Struct) t).getArg(2);
		Term t3 = ((Struct) t).getArg(3);
		String exceptionTerm = 
				 "proxy"+t0+"_"+tName+"("+t0+","+t1+", '"+ msg + "' ,"+ t3 +" )";
		return exceptionTerm;
	}	
	
/*     
* Called by perpareInput
*/	
	public static void addInput( Vector<IMessage> inpList,
	    String receiver,String msgId,IPolicy policy, boolean withAnswer ) throws Exception{
			IMessage m = new Message(
				 MsgUtil.bm(channelInWithPolicy(policy,receiver, msgId), 
				 	"SOURCE", msgId, "ANYx1y2", "N")) ;
			inpList.add( m );
 	}	
	
	public static void addSignalInput( Vector<IMessage> inpList,
		 String receiver,String msgId, boolean withAnswer ) throws Exception{
			IMessage m = new Message( MsgUtil.bm( msgId, "SOURCE", msgId, "ANYx1y2", "N")) ;
			inpList.add( m );
 	}	
	
	public static  String channelInWithPolicy( IPolicy policy, String subjName, String msgId   ){
 		if(policy != null && policy.getPolicy().equals("protected"))
				return subjName+"_"+hiddenName(subjName)+"_"+msgId  ;
		else 	return MsgUtil.channel2( subjName,  msgId);
	}		
	
	public static  String hiddenName( String name ){
		return "h"+name;
	}

	public static String putInEnvelope(String M){
		try{
			alice.tuprolog.Term mt = alice.tuprolog.Term.createTerm(M); //If Prolog syntax then no ' '
			if( mt.isVar() ) M =  "'"+M+"'";
			M = alice.tuprolog.Term.createTerm("envelope("+ M + " )").toString(); 
		}catch(Exception e){
			M = alice.tuprolog.Term.createTerm("envelope('"+ M.replace("'", "\"") +" ')").toString(); 
		}
		return M;
	}
	public static String clean( String s){
		if( s.startsWith("'"))
			return s.substring(1,s.length()-1);
		else return s;
	}	
}
