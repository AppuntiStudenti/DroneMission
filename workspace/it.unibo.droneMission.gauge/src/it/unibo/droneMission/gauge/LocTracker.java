package it.unibo.droneMission.gauge;

import java.util.Observer;

import it.unibo.droneMission.interfaces.IGaugeValue;
import it.unibo.droneMission.interfaces.IGaugeValueDouble;
import it.unibo.droneMission.interfaces.ILocTracker;

public class LocTracker implements ILocTracker {
	
	protected IGaugeValueDouble latitude;
	protected IGaugeValueDouble longitude;
	
	public LocTracker(){
		latitude = new GaugeValueDouble(LocTracker.INITLAT);
		longitude = new GaugeValueDouble(LocTracker.INITLON);
	}
	
	public LocTracker(GaugeValueDouble la, GaugeValueDouble lo){
		latitude.set(la.valAsDouble());
		longitude.set(lo.valAsDouble());
	}

	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObserver(Observer o) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurValRepDisplayed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurValRepDisplayed(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public IGaugeValue getVal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVal(IGaugeValue value) {
		// TODO Auto-generated method stub

	}
	
	protected boolean check(IGaugeValue v){
		return ((v.valAsDouble()>=-90)&&(v.valAsDouble()<=90));
	}

	@Override
	public void update(IGaugeValue lat, IGaugeValue lon) {
		if ((check(lat))&&(check(lon))){
			latitude.set(lat.valAsDouble());
			longitude.set(lon.valAsDouble());
		}
	}

	public IGaugeValue getLat() {
		// TODO Auto-generated method stub
		return latitude;
	}

	public IGaugeValue getLon() {
		// TODO Auto-generated method stub
		return longitude;
	}

}
