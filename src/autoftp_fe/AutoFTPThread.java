/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoftp_fe;

import autoftp.AutoFTP;

/**
 *
 * @author pedro
 */
public class AutoFTPThread extends Thread {

    public void run() {
        String[] a = new String[1];
        a[0] = "false";

        AutoFTP.main(a);

    }//end run

}
