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

        if (!authorized && denylistedHosts.contains(domain)) {
            System.err.print(String.format("Connection to '%s' was rejected!", url.toString().toLowerCase().strip().replace("https://", "")));
            System.out.print(String.format("redirecting to %s", redirectPage.toString()));
            networkConnection.connect(redirectPage);
        }
        else if (!denylistedHosts.contains(domain)) {
            networkConnection.connect(url);
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
            this.authorized = true;
        } else {
            this.authorized = false;
        }
    }

    public void logout() {
        this.authorized = false;
    }
}
