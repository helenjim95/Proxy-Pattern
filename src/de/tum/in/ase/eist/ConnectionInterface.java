package de.tum.in.ase.eist;

import java.net.URL;

// TODO: service interface
public interface ConnectionInterface {

	void connect(URL url);

	void disconnect();

	boolean isConnected();

}
