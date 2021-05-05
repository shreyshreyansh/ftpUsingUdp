import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Path;

public class Client
{
    public static void main(String[] args)
    {

        JFrame frame = new JFrame("Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,700);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.black);

        JPanel title = new JPanel();
        JLabel header = new JLabel("<html><span style='color: white;'>FTP USING UDP</span></html>");
        header.setFont(header.getFont().deriveFont(32.0F));
        title.add(header);
        title.setMaximumSize(new Dimension(300, 100));
        title.setBackground(Color.black);
        title.setForeground(Color.white);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(200));

        JPanel menu = new JPanel();
        menu.setBackground(Color.black);
        JButton button1 = new JButton("Create a File");

        button1.setPreferredSize(new Dimension(300, 50));
        button1.setFocusPainted(false);
        button1.setBackground(Color.black);
        button1.setForeground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.white, 2);
        button1.setBorder(border);
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button1.setBackground(Color.white);
                button1.setForeground(Color.black);
                Border border = BorderFactory.createLineBorder(Color.black, 2);
                button1.setBorder(border);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button1.setBackground(Color.black);
                button1.setForeground(Color.white);
                Border border = BorderFactory.createLineBorder(Color.white, 2);
                button1.setBorder(border);
            }
        });
        menu.add(button1);
        mainPanel.add(menu);
        mainPanel.add(Box.createVerticalStrut(20));

        menu = new JPanel();
        menu.setBackground(Color.black);
        JButton button2 = new JButton("Send a File");

        button2.setPreferredSize(new Dimension(300, 50));
        button2.setFocusPainted(false);
        button2.setBackground(Color.black);
        button2.setForeground(Color.white);
        button2.setBorder(border);
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button2.setBackground(Color.white);
                button2.setForeground(Color.black);
                Border border = BorderFactory.createLineBorder(Color.black, 2);
                button2.setBorder(border);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button2.setBackground(Color.black);
                button2.setForeground(Color.white);
                Border border = BorderFactory.createLineBorder(Color.white, 2);
                button2.setBorder(border);
            }
        });
        menu.add(button2);
        mainPanel.add(menu);
        mainPanel.add(Box.createVerticalStrut(200));

        JPanel info = new JPanel();
        info.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JLabel version = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version 1.0<br>Created by Shreyansh Shrey</html>");
        info.add(version);
        info.setBackground(Color.black);
        info.setForeground(Color.white);

        mainPanel.add(info, BorderLayout.PAGE_END);

        //mainPanel.add(Box.createVerticalStrut(20));
        //JButton button = new JButton("Press");
        //frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        button1.addActionListener(e -> {
            editor d = new editor();
        });
        button2.addActionListener(e -> {
            Path path = getInputPath("");
                    byte b[]=new byte[1024];
                    FileInputStream f= null;
                    try {
                        f = new FileInputStream(String.valueOf(path));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    DatagramSocket dsoc= null;
                    try {
                        dsoc = new DatagramSocket(9712);
                    } catch (SocketException socketException) {
                        socketException.printStackTrace();
                    }

                    int i=0;
                    while(true)
                    {
                        try {
                            assert f != null;
                            if (f.available() == 0) break;
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        try {
                            b[i]=(byte)f.read();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.print(b[i]);
                        i++;
                    }
                    try {
                        f.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        assert dsoc != null;
                        dsoc.send(new DatagramPacket(b,i, InetAddress.getLocalHost(),1000));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JFrame msg = new JFrame("Message");
                    JOptionPane.showMessageDialog(msg, "File Sent Successfully");
        });
    }
    public static Path getInputPath(String s) {
        JFileChooser jd= s == null ? new JFileChooser() : new JFileChooser(s);
        jd.setDialogTitle("Choose input file");
        int returnVal= jd.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) return null;
        return jd.getSelectedFile().toPath();
    }
    static class editor extends JFrame implements ActionListener {
        // Text component
        JTextArea t;

        // Frame
        JFrame f;

        // Constructor
        editor() {
            // Create a frame
            f = new JFrame("Text Editor");

            try {
                // Set metal look and feel
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

                // Set theme to ocean
                MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            } catch (Exception ignored) {
            }

            // Text component
            t = new JTextArea();

            // Create a menubar
            JMenuBar mb = new JMenuBar();

            // Create amenu for menu
            JMenu m1 = new JMenu("File");

            // Create menu items
            JMenuItem mi1 = new JMenuItem("New");
            JMenuItem mi2 = new JMenuItem("Open");
            JMenuItem mi3 = new JMenuItem("Save");
            JMenuItem mi9 = new JMenuItem("Print");

            // Add action listener
            mi1.addActionListener(this);
            mi2.addActionListener(this);
            mi3.addActionListener(this);
            mi9.addActionListener(this);

            m1.add(mi1);
            m1.add(mi2);
            m1.add(mi3);
            m1.add(mi9);

            // Create amenu for menu
            JMenu m2 = new JMenu("Edit");

            // Create menu items
            JMenuItem mi4 = new JMenuItem("cut");
            JMenuItem mi5 = new JMenuItem("copy");
            JMenuItem mi6 = new JMenuItem("paste");

            // Add action listener
            mi4.addActionListener(this);
            mi5.addActionListener(this);
            mi6.addActionListener(this);

            m2.add(mi4);
            m2.add(mi5);
            m2.add(mi6);

            JMenuItem mc = new JMenuItem("close");

            mc.addActionListener(this);

            mb.add(m1);
            mb.add(m2);
            mb.add(mc);

            f.setJMenuBar(mb);
            f.add(t);
            f.setSize(700, 700);
            f.show();
        }

        // If a button is pressed
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();

            switch (s) {
                case "cut":
                    t.cut();
                    break;
                case "copy":
                    t.copy();
                    break;
                case "paste":
                    t.paste();
                    break;
                case "Save": {
                    // Create an object of JFileChooser class
                    JFileChooser j = new JFileChooser("f:");

                    // Invoke the showsSaveDialog function to show the save dialog
                    int r = j.showSaveDialog(null);

                    if (r == JFileChooser.APPROVE_OPTION) {

                        // Set the label to the path of the selected directory
                        File fi = new File(j.getSelectedFile().getAbsolutePath());

                        try {
                            // Create a file writer
                            FileWriter wr = new FileWriter(fi, false);

                            // Create buffered writer to write
                            BufferedWriter w = new BufferedWriter(wr);

                            // Write
                            w.write(t.getText());

                            w.flush();
                            w.close();
                        } catch (Exception evt) {
                            JOptionPane.showMessageDialog(f, evt.getMessage());
                        }
                    }
                    // If the user cancelled the operation
                    else
                        JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                    break;
                }
                case "Print":
                    try {
                        // print the file
                        t.print();
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                    break;
                case "Open": {
                    // Create an object of JFileChooser class
                    JFileChooser j = new JFileChooser("f:");

                    // Invoke the showsOpenDialog function to show the save dialog
                    int r = j.showOpenDialog(null);

                    // If the user selects a file
                    if (r == JFileChooser.APPROVE_OPTION) {
                        // Set the label to the path of the selected directory
                        File fi = new File(j.getSelectedFile().getAbsolutePath());

                        try {
                            // String
                            String s1;
                            StringBuilder sl;

                            // File reader
                            FileReader fr = new FileReader(fi);

                            // Buffered reader
                            BufferedReader br = new BufferedReader(fr);

                            // Initialize sl
                            sl = new StringBuilder(br.readLine());

                            // Take the input from the file
                            while ((s1 = br.readLine()) != null) {
                                sl.append("\n").append(s1);
                            }

                            // Set the text
                            t.setText(sl.toString());
                        } catch (Exception evt) {
                            JOptionPane.showMessageDialog(f, evt.getMessage());
                        }
                    }
                    // If the user cancelled the operation
                    else
                        JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                    break;
                }
                case "New":
                    t.setText("");
                    break;
                case "close":
                    f.setVisible(false);
                    break;
            }
        }
    }

}
