/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoftp_fe;

import java.util.prefs.*;



/**
 *
 * @author pena
 */
public class AutoFTP_fe extends Thread {
 

    Preferences prefs;
    String socketServerAddress = "";
    int socketServerPort = -1;

    public AutoFTP_fe() {
        init();
    }//end constructor

    public static void main(String[] args) {
        AutoFTP_fe abc = new AutoFTP_fe();
        abc.start();

    }//end main

    private void init() {
        prefs = Preferences.userNodeForPackage(getClass());
        
        
        socketServerAddress = prefs.get("socketServerAddress", "@@@");
        if (socketServerAddress.equals("@@@")) {
            prefs.put("socketServerAddress", "127.0.0.1");
            socketServerAddress = "127.0.0.1";
        }//end if

        socketServerPort = prefs.getInt("socketServerPort", -1);
        if (socketServerPort == -1) {
                prefs.putInt("socketServerPort", 25000);
                socketServerPort=25000;
            }//end if  


}//end init
    public void run() {

        try {
            long previousTime=0;
            long currentTime=0;

            AutoFTPFrontEndForm autoFTPFrontEnd;
            AutoFTPFrontEndCommandClient aftpcc;
            AutoFTPThread aftpT = new AutoFTPThread();
            aftpT.start();
            Thread.sleep(4000);

            autoFTPFrontEnd = new AutoFTPFrontEndForm();
            autoFTPFrontEnd.setLocalSocketServerAddress(socketServerAddress);
            autoFTPFrontEnd.seLocalSocketServerPort(socketServerPort + "");
            aftpcc = new AutoFTPFrontEndCommandClient(socketServerAddress, socketServerPort);
            aftpcc.start();

            currentTime = System.currentTimeMillis();
            previousTime = currentTime;
            while (true) {
                Thread.sleep(50);
                currentTime = System.currentTimeMillis();
                if (currentTime - previousTime >= 5000) {
                    previousTime = currentTime;

                }//end if

                if (aftpcc.getConfigRead()) {
                    autoFTPFrontEnd.setFTPServer(aftpcc.getFTPServer());
                    autoFTPFrontEnd.setUsername(aftpcc.getuserName());
                    autoFTPFrontEnd.setUploadPath(aftpcc.getUploadPath());
                    autoFTPFrontEnd.setLogFilePath(aftpcc.getlogFilePath());
                    autoFTPFrontEnd.setQueuePath(aftpcc.getQueuePath());
                    autoFTPFrontEnd.setFileSizeLimit(aftpcc.getFileSizeLimit());
                    autoFTPFrontEnd.setRemoteSocketServerAddress(aftpcc.getRemoteSocketServerAddress());
                    autoFTPFrontEnd.setRemoteSocketServerPort(aftpcc.getRemoteSocketServerPort()+"");
                    autoFTPFrontEnd.setPassword(aftpcc.getPaswword());
                    autoFTPFrontEnd.setTransmitEnable(aftpcc.getTransmitEnable());
                    autoFTPFrontEnd.setUseModem(aftpcc.getUseModem());
                    autoFTPFrontEnd.setPhoneBookEntry(aftpcc.getPhoneBookEntry());
                    autoFTPFrontEnd.setQueueRefresh(aftpcc.getQueueRefresh());
                    aftpcc.setConfigRead(false);
                    autoFTPFrontEnd.setVisible(true);

                }//end if

                if (autoFTPFrontEnd.isFormSaved()) {
                    aftpcc.SetConfig(autoFTPFrontEnd.getCurrentConfig());
                    prefs.put("socketServerAddress", autoFTPFrontEnd.getLocalSocketServerAddress());
                    prefs.put("socketServerPort", autoFTPFrontEnd.getLocalSocketServerPort());

                }//end if

                if (aftpcc.isNewFTPStatusMessage()) {
                    autoFTPFrontEnd.appendFTPStatusMessage(aftpcc.getFTPStatusMessage());
                    aftpcc.setNewFTPStatusMessage(false);
                    
                }//end if

                if (aftpcc.isNewQueueStatusMessage()) {
                    autoFTPFrontEnd.setQueueStatusMessage(aftpcc.getQueueStatusMessage());
                    
                }//end if

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
