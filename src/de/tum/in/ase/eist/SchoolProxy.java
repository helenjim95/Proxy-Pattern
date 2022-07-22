package de.tum.in.ase.eist;

import java.net.URL;
import java.util.Set;

public class SchoolProxy implements ConnectionInterface {

    // Implement the SchoolProxy
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
        String domain = url.toString().toLowerCase().strip().replace("https://", "")
                            .replace("http://", "").split("/")[0];

        if (!denylistedHosts.contains(domain) || authorized) {
            networkConnection.connect(url);
        } else {
            System.err.print(String.format("Connection to '%s' was rejected!", domain));
            System.out.print(String.format("redirecting to %s", redirectPage.toString()));
            System.out.println();
            networkConnection.connect(redirectPage);
        }
    }

    public void disconnect() {
        networkConnection.disconnect();
    }

    public boolean isConnected() {
        return networkConnection.isConnected();
    }

    public void login(int teacherID) {
        if (teacherIDs.contains(teacherID)) {
            authorized = true;
        } else {
            authorized = false;
        }
    }

    public void logout() {
        authorized = false;
    }
}
