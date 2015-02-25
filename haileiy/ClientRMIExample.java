// place holder

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.io.*;

public class ClientRMIExample {

    public static IServer getServerInstance(String ip, int port) {
        String url = String.format("//%s:%d/ServerService", ip, port);
        try {
            return (IServer) Naming.lookup (url);
        } catch (MalformedURLException e) {
            //you probably want to do logging more properly
            System.err.println("Bad URL" + e);
        } catch (RemoteException e) {
            System.err.println("Remote connection refused to url "+ url + " " + e);
        } catch (NotBoundException e) {
            System.err.println("Not bound " + e);
        }
        return null;
    }

    public void getFileFromServer(String path) {

    }

    /**
     * test the getVersion function in server
     */
    public void testGetVersion(IServer server) {
        int ver = server.getVersion("test.txt");
        System.out.println(ver);

        String uploadpath = clientprefix + "upload.txt";
        File uploadfile = new File(uploadpath);

        byte[] uploadb = new byte[(int) uploadfile.length()];
        FileInputStream fileInputStream = new FileInputStream(uploadfile);
        fileInputStream.read(uploadb);
        server.writeToServer("test.txt", uploadb);

        ver = server.getVersion("test.txt");
        System.out.println(ver);
    }

    /**
     * test the open function
     * when client calls open, the proxy should first check if there is a local copy
     * if so, check if the version is up-to-date. if not, fetch the latest version from
     * server
     *
     */
    public void testOpen(IServer server) {

    }

    /**
     * test the close function
     * when client calls close, the proxy should upload the modified version to server
     * i should implement a dirty flag, which denotes if the local copy has been
     * modified or not.
     */
    public void testClose(IServer server) {

    }

    // public void

    public static void main(String [] args) {
        int port = 12345; //you should get port from args
        IServer server = null;
        try {
            server = getServerInstance("127.0.0.1", port);
        }
        catch(Exception e) {
            e.printStackTrace(); //you should actually handle exceptions properly
        }

        if(server == null) System.exit(1); //You should handle errors properly.
        String clientprefix = "../cache/";
        try {
            /*
            String path = "test.txt";
            System.out.println("Try to get a file from the server");
            byte[] b = server.getFileContent(path);

            String newpath = clientprefix + path;
            FileOutputStream fos = new FileOutputStream(newpath);

            fos.write(b);
            fos.close();

            System.out.println("Then we try to upload something to the server");
            String uploadpath = clientprefix + "upload.txt";
            File uploadfile = new File(uploadpath);

            byte[] uploadb = new byte[(int) uploadfile.length()];
            FileInputStream fileInputStream = new FileInputStream(uploadfile);
            fileInputStream.read(uploadb);
            server.writeToServer("upload.txt", uploadb);
            */



        } catch (Exception e) {
            System.err.println(e);
        }
    }
}