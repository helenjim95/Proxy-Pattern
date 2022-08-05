package de.tum.in.ase.eist;

import java.net.URL;
import java.util.Set;

//TODO: proxy class implements service interface
public class SchoolProxy implements ConnectionInterface {

    // Implement the SchoolProxy
    private Set<String> denylistedHosts;
    private URL redirectPage;
    private Set<Integer> teacherIDs;
    private boolean authorized;

//    TODO: declare reference to the actual service object
    private NetworkConnection networkConnection;

//    TODO: initiate instance of the actual service object
    public SchoolProxy(Set<String> denylistedHosts, URL redirectPage, Set<Integer> teacherIDs) {
        this.denylistedHosts = denylistedHosts;
        this.redirectPage = redirectPage;
        this.teacherIDs = teacherIDs;
        this.networkConnection = new NetworkConnection();
        this.authorized = false;
    }

//    TODO: delegate method calls to the actual service object
    public void connect(URL url) {
        if (this.denylistedHosts.contains(url.getHost()) && !this.authorized) {
            System.err.println("Connection to " + url + " was rejected!");
            System.out.println("You will be redirected to " + this.redirectPage);
            this.networkConnection.connect(this.redirectPage);
        } else
            this.networkConnection.connect(url);
    }

    public void disconnect() {
        this.networkConnection.disconnect();
    }

    public boolean isConnected() {
        return this.networkConnection.isConnected();
    }

    public void login(int teacherID) {
        if (this.teacherIDs.contains(teacherID)) {
                this.authorized = true;
        }
    }

    public void logout() {
        this.authorized = false;
    }
}
