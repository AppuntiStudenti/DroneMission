/* ======================================================
* Generated by AN 
* University of Bologna
* ======================================================-
*/
package  it.unibo.droneMission.smartDevice.android;
import android.os.Bundle;
import android.os.Message;
import it.unibo.is.interfaces.IOutputView;

public class OutView implements IOutputView {

	protected BaseActivity myActivity;
	protected int nm = 1;
	protected String curVal = "";

	public OutView(BaseActivity myActivity) {
		this.myActivity = myActivity;
	}
	
	public String getCurVal() {
		return curVal;
	}

	public synchronized void addOutput(String msg) {
		curVal = msg ;
 		Message m = myActivity.myHandler.obtainMessage();
 		Bundle data = new Bundle();
 		data.putString("addOutputMsg", msg);
 		m.setData(data);
 		myActivity.myHandler.sendMessage(m);
	}
	
	public synchronized void setOutput(String msg) {
		curVal = msg ;
 		Message m = myActivity.myHandler.obtainMessage();
 		Bundle data = new Bundle();
 		data.putString("setOutputMsg", msg);
 		m.setData(data);
 		myActivity.myHandler.sendMessage(m);
	}
	
} 
