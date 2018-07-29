package my.remote.desktop.client;

import java.io.ObjectOutputStream;

import my.remote.desktop.common.Constants;

public class RequestSender {
	private ObjectOutputStream oos = null;
	
	public RequestSender(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void sendName(String name) throws Exception {
		Object [] request = {Constants.CMD_SET_USER_NAME, name};
		oos.writeObject(request);
	}
}
