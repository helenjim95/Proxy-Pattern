package de.tum.in.ase.eist;

import java.net.URL;
import java.util.Set;

public class SchoolProxy implements ConnectionInterface {

    // TODO: Implement the SchoolProxy
    private Set<String> denylistedHosts;
    private URL redirectPage;
    private Set<Integer> teacherIDs;
    private boolean authorized;
    private NetworkConnection networkConnection;

    public SchoolProxy(Set<String> denylistedHosts, URL redirectPage, Set<Integer> teacherIDs) {
        this.denylistedHosts = denylistedHosts;
        this.redirectPage = redirectPage;
        this.teacherIDs = teacherIDs;
        networkConnection = new NetworkConnection();
        authorized = false;
    }

    public void connect(URL url) {
        String domain = url.toString().toLowerCase().strip().replace("https://", "").split("/")[0];

        if (!denylistedHosts.contains(domain)) {
            networkConnection.connect(url);
        } else {
            if (authorized) {
                System.err.printf("Connection to '%s' was rejected!", domain);
            } else {
                System.out.printf("redirecting to %s", redirectPage.toString());
            }
        }
    }

    public void disconnect() {
        networkConnection.disconnect();
    }

    public boolean isConnected() {
        return networkConnection.isConnected();
    }

    public void login(int teacherID) {
        authorized = true;
    }

    public void logout() {
        authorized = false;
    }
}
