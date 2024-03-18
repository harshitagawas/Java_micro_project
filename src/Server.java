import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;


class Server implements ActionListener {

    JTextField text;
    JPanel p2;

    static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();

    static JFrame obj = new JFrame();
    Server(){

        //green panel
        obj.setLayout(null);
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        obj.add(p1);


        // back arrow
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/back_arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);


        // click arrow to exit
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });


        //profile pic
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("images/flower-removebg-preview.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile= new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);


        //video call pic
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("images/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel video= new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);


        //call pic
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("images/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(365,20,25,30);
        p1.add(call);


        //ellipes
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("images/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);


        //profile name
        JLabel name = new JLabel("Harshita");
        name.setBounds(110,15,100,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SANS_SERIF",Font.BOLD,18));
        p1.add(name);


        //status(active now)
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,20,100,60);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SANS_SERIF",Font.BOLD,12));
        p1.add(status);


        // text area panel
        p2= new JPanel();
        p2.setBounds(5,75,440,570);
        obj.setUndecorated(true);
        obj.add(p2);


        //texting field
        text= new JTextField();
        text.setBounds(5,655,315,40);
        text.setFont(new Font("SANS_SERIF",Font.PLAIN,14));
        obj.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        obj.add(send);


        obj.setSize(450,700);
        obj.getContentPane().setBackground(Color.WHITE);
        obj.setUndecorated(true);
        obj.setVisible(true);
        obj.setLocation(200,50); // Move setLocation after setVisible
    }


    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JPanel p3 = formatLabel(out);


            p2.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p2.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            obj.repaint();
            obj.invalidate();
            obj.validate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));


        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }
    public static void main(String[] args){
        new Server();

        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s= skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

            while(true){
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                obj.validate();
    }
}
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
