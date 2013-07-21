package it.unibo.droneMission.prototypes.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Factory {
	
	public static Command createCommand(String json) {
		// return (Command) createTypeAsIntValueAsInt(json)
		MessageTypeAsIntValueAsInt m = createTypeAsIntValueAsInt(json);
		if(m.hasValue())
			return new Command(m.getType(), m.getValue());
		else
			return new Command(m.getType());
	}
		
	public static Reply createReply(String json) {
		//return (Reply) createTypeAsIntValueAsString(json);
		MessageTypeAsIntValueAsString m = createTypeAsIntValueAsString(json);
		if(m.hasValue())
			return new Reply(m.getType(), m.getValue());
		else
			return new Reply(m.getType());
	}
	
	public static Notify createNotify(String json) {
		//return (Reply) createTypeAsIntValueAsString(json);
		MessageTypeAsIntValueAsString m = createTypeAsIntValueAsString(json);
		if(m.hasValue())
			return new Notify(m.getType(), m.getValue());
		else
			return new Notify(m.getType());
	}
	
	
	public static SensorData createSensorData(String json) throws Exception {
		MessageTypeAsIntValueAsString m = createTypeAsIntValueAsString(json);
		if (m.hasvalue) {
			return new SensorData(m.getType(), m.getValue());
		}
		else
			throw new Exception("SensordData Message has no value");
	}
	

	private static MessageTypeAsIntValueAsInt createTypeAsIntValueAsInt(String json) {
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(json).getAsJsonObject();
		
		boolean hasvalue = object.get("hasvalue").getAsBoolean();
		int value = object.get("value").getAsInt();
		int type = object.get("type").getAsInt();
		
		if(hasvalue)
			return new MessageTypeAsIntValueAsInt(type, value);
		else
			return new MessageTypeAsIntValueAsInt(type);		
	}
	
	private static MessageTypeAsIntValueAsString createTypeAsIntValueAsString(String json) {
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(json).getAsJsonObject();
		
		boolean hasvalue = object.get("hasvalue").getAsBoolean();
		String value = "";
		if (hasvalue)
			value = object.get("value").getAsString();
		int type = object.get("type").getAsInt();
		
		if(hasvalue)
			return new MessageTypeAsIntValueAsString(type, value);
		else
			return new MessageTypeAsIntValueAsString(type);			
	}
	
	
}
