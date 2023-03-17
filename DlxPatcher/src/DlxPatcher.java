import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DlxPatcher {
    private static final String JAVA6_PORTABLE_PATH = "C:/Users/menendezj/Documents/jre1.5.0_05";
    private static final String[] WEBSITE_URLS = {"http://bratislava01p.logistics.corp/", "http://glpdvlapp465.logistics.corp/#"};
    
    public static void main(String[] args) {
        // set the path to the Java 6 portable installation directory
        System.setProperty("java.home", JAVA6_PORTABLE_PATH);
        
        // create a new JFrame and JPanel
        JFrame frame = new JFrame("Open Web Pages");
        JPanel panel = new JPanel();
        
        // create a button for each website
        for (String url : WEBSITE_URLS) {
            JButton button = new JButton(url);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openWebPage(url);
                }
            });
            panel.add(button);
        }
        
        // add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        
        // set the size and visibility of the frame
        frame.pack();
        frame.setVisible(true);
    }
    
    // method for opening a webpage using the compatible browser
    private static void openWebPage(String url) {
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Internet Explorer\\iexplore.exe", url);
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

