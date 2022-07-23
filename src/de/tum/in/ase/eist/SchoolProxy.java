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
        networkConnection = new NetworkConnection();
        authorized = false;
    }

//    TODO: delegate method calls to the actual service object
    public void connect(URL url) {
        String domain = url.toString().toLowerCase().strip().replace("https://", "")
                            .split("/")[0];

        boolean isDenied = false;
        for (String deniedHost : denylistedHosts) {
            if (deniedHost.equals(domain)) {
                isDenied = true;
                break;
            }
        }
        if (!isDenied || authorized) {
            networkConnection.connect(url);
        } else {
            System.err.printf("The request to %s was rejected!", domain);
            System.out.printf("The request to %s was rejected!%n", domain);
            System.out.printf("redirecting to %s", redirectPage.toString());
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
        for (int id : teacherIDs) {
            if (id == teacherID) {
                authorized = true;
                break;
            }
        }
    }

    public void logout() {
        authorized = false;
    }
}
