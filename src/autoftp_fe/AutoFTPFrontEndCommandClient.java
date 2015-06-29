/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoftp_fe;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pena
 */
public class AutoFTPFrontEndCommandClient extends Thread {

    String address;
    int port;
    String theReplyPattern = "(?i)(<CMDREPLY.*?>)(.+?)(</CMDREPLY>)";
    String theTransmitterMessagePattern = "(?i)(<FTPSTATUS.*?>)(.+?)(</FTPSTATUS>)";
    String theQueueMessagePattern = "(?i)(<QUEUE.*?>)(.+?)(</QUEUE>)";
    String queue = "";
    String ftpStatusMessage = "";
    String queueStatusMessage = "";
    String queuePath;
    String logFilePath;
    String uploadPath;
    String ftpServerName;
    String username;
    String password;
    String queueRefresh;
    String phoneBookEntry;
    String fileSizeLimit;
    String socketAddress;
    int socketPort;
    boolean transmitEnable;
    boolean useModem;
    boolean configRead;
    boolean newFTPStatusMessage = false;
    boolean newQueueStatusMessage = false;
    private boolean fillQueue = false;
    PrintWriter out;

    public AutoFTPFrontEndCommandClient(String a, int p) {
        address = a;
        port = p;
        setConfigRead(false);
    }

    ;
    public void run() {

        try {
            Pattern replyPattern;
            Pattern transmitterMessagePattern;
            Pattern queueMessagePattern;
            Pattern queueMessagePattern2;
            Matcher replyMatcher;
            Matcher transmitterMessageMatcher;
            Matcher queueMessageMatcher;
            Matcher queueMessageMatcher2;
            Socket s = new Socket(address, port);
            String msg = "@#@#@#@";
            out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            Long currentTime, previousTime;
            currentTime = System.currentTimeMillis();
            previousTime = System.currentTimeMillis();
            out.println("<NAME>command</NAME>");
            out.println("ACK");

            while (msg != null) {
                Thread.sleep(20);

                if (in.ready()) {
                    msg = in.readLine();
                }//end if

                if (msg.equals("ENQ")) {
                    out.println("ACK");
                    //out.print("ACK");
                    //out.flush();                        
                    currentTime = System.currentTimeMillis();
                    previousTime = currentTime;
                    msg = "@#@#@#@";

                }

                if ((currentTime - previousTime >= 10000) && false) {
                    System.out.println("connection timed out\n Attempting to reconnect");
                    s = null;
                    s = new Socket(address, port);
                    out = new PrintWriter(s.getOutputStream(), true);
                    out.println("<NAME>command</NAME>");

                    out.println("ACK");
                    //msg = null;
                    previousTime = System.currentTimeMillis();

                }//end if

                if (msg != null && !msg.equals("@#@#@#@") && !msg.equals("ENQ")) {
                    if (msg.equals("<QUEUE>") || fillQueue) {
                        synchronized (this) {
                            fillQueue = true;
                            queue += msg;
                        }
                        if (queue.contains("</QUEUE>")) {
                            synchronized (this) {
                                fillQueue = false;
                            }
                        }//end if 
                        //out.println("ACK");
                    }//end if

                    //System.out.println(msg);
                    replyPattern = Pattern.compile(theReplyPattern);
                    replyMatcher = replyPattern.matcher(msg);
                    transmitterMessagePattern = Pattern.compile(theTransmitterMessagePattern);
                    transmitterMessageMatcher = transmitterMessagePattern.matcher(msg);
                    queueMessagePattern = Pattern.compile(theQueueMessagePattern, Pattern.DOTALL);
                    queueMessageMatcher = queueMessagePattern.matcher(queue);

                    if (queueMessageMatcher.find()) {

                        queue = queue.replaceAll("<QUEUE>", "\n");
                        queue = queue.replaceAll("</QUEUE>", "");
                        queue = queue.replaceAll("<FILE>", "");
                        queue = queue.replaceAll("<FILES>", "");
                        queue = queue.replaceAll("</FILES>", "");
                        queue = queue.replaceAll("</FILE>", "\n");
                        //System.out.println(queue);
                        setQueueStatusMessage(queue);
                        queue = "";
                        out.println("ACK");

                    }//end if                    

                    if (transmitterMessageMatcher.find()) {

                        msg = msg.replaceAll(theTransmitterMessagePattern, "$2");
                        setFTPStatusMessage(msg);
                        out.println("ACK");

                    }//end if

                    if (replyMatcher.find()) {
                        msg = msg.replaceAll(theReplyPattern, "$2");
                        String[] config = msg.split("::");
                        if (config.length >= 13) {
                            queueRefresh = config[0];
                            queuePath = config[1];
                            logFilePath = config[2];
                            ftpServerName = config[3];
                            username = config[4];
                            password = config[5];
                            uploadPath = config[6];
                            transmitEnable = new Boolean(config[7]);
                            phoneBookEntry = config[8];
                            useModem = new Boolean(config[9]);
                            socketAddress = config[10];
                            socketPort = new Integer(config[11]);
                            fileSizeLimit = config[12];
                            setConfigRead(true);

                        }

                    }//end if
                    out.println("ACK");

                    msg = "@#@#@#@";
                }

                currentTime = System.currentTimeMillis();

            }//end while

        }//end try
        catch (Exception e) {
            e.printStackTrace();
        }//end catch        

    }//end run

    public void setFTPServer(String s) {

    }//end setFTPServer

    public String getFTPServer() {
        return ftpServerName;
    }//end getFTPServer

    public void setUploadPath(String s) {

    }//end set

    public String getUploadPath() {
        return uploadPath;
    }//end get
    
        public void setQueuePath(String s) {

    }//end set

    public String getQueuePath() {
        return queuePath;
    }//end get
    
    public void setlogFilePath(String s) {

    }//end set

    public String getlogFilePath() {
        return logFilePath;
    }//end get    

    public void setUsername(String s) {

    }//end set

    public String getuserName() {
        return username;
    }//end get     

    public void setPassword(String s) {

    }//end set

    public String getPaswword() {
        return password;
    }//end get     

    public void setQueueRefresh(String s) {

    }//end set

    public String getQueueRefresh() {
        return queueRefresh;
    }//end get 

    public void setPhoneBookEntry(String s) {

    }//end set

    public String getPhoneBookEntry() {
        return phoneBookEntry;
    }//end get  

    public void setFileSizeLimit(String s) {

    }//end set

    public String getFileSizeLimit() {
        return fileSizeLimit;
    }//end get    

    public void setSocketServer(String s) {

    }//end set

    public String getRemoteSocketServerAddress() {
        return socketAddress;
    }//end get     

    public void setSocketServerPort(String s) {

    }//end set

    public int getRemoteSocketServerPort() {
        return socketPort;
    }//end get     

    public void setTransmitEnable(boolean b) {

    }//end set

    public void setUseModem(boolean b) {

    }//end set

    public boolean getTransmitEnable() {
        return transmitEnable;
    }//end set

    public boolean getUseModem() {
        return useModem;
    }//end set

    public void setConfigRead(boolean c) {
        synchronized(this){
        configRead = c;
        }
    }//end set

    public synchronized boolean getConfigRead() {
        return configRead;
    }//end get

    public void SetConfig(String cfg) throws Exception {
        //System.out.println(cfg);
        /*String[] config = cfg.split("::");
        if (config.length >= 13) {

            config[10] = socketAddress;
            config[11] = socketPort + "";
            cfg = config[0];
            for (int i = 1; i < config.length; i++) {
                cfg = cfg + "::" + config[i];
            }//end for
        }*/

        out.println("<CMD>setConfig=" + cfg + "</CMD>");
        out.println("ACK");
        //System.out.println(cfg);
    }

    public String getFTPStatusMessage() {
        if (!newFTPStatusMessage) {
            return "";
        }
        String temp = "";
        synchronized (this) {
            newFTPStatusMessage = false;
        
        temp = ftpStatusMessage;
        ftpStatusMessage = "";
        }
        return temp;
    }

    public  void setFTPStatusMessage(String msg) {
        synchronized (this) {
            newFTPStatusMessage = true;
            ftpStatusMessage = msg + "\n";
        }
    }

    public  boolean isNewFTPStatusMessage() {
        return newFTPStatusMessage;
    }

    public  String getQueueStatusMessage() {
        if (!newQueueStatusMessage) {
            return "";
        }

        synchronized (this) {
            String temp = "";
            newQueueStatusMessage = false;
            temp = queueStatusMessage;
            queueStatusMessage = "";
            return temp;
        }

    }

    public void setQueueStatusMessage(String msg) {
        synchronized (this) {
            newQueueStatusMessage = true;
            queueStatusMessage = msg;
            if (msg.contains(";EMPTY;")) {
                queueStatusMessage = "";
            }
            //queueStatusMessage = queueStatusMessage + msg + "\n";

        }
    }

    public  boolean isNewQueueStatusMessage() {
        return newQueueStatusMessage;
    }
   public void setNewQueueStatusMessage(boolean b){
       synchronized(this){
           newQueueStatusMessage=b;
       }
   }
   
   public void setNewFTPStatusMessage(boolean b){
       synchronized(this){
           newFTPStatusMessage=b;
       }
   }
   

}
