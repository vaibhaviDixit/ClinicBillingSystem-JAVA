package billingSystem.clinic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;
    JButton login;
    
    Login(){
        setTitle("HappyLife Clinic");
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        JLabel header=new JLabel("HappyLife Clinic");
        header.setFont(new Font("monospace",Font.BOLD,30));
        header.setBounds(300,50,300,50);
        add(header);

        ImageIcon img=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/home.png"));
        JLabel l1=new JLabel(img);
        l1.setBounds(20,100,400,400);
        add(l1);



        JPanel p1=new JPanel();
        p1.setLayout(null);
        p1.setBounds(450,150,400,300);
        JLabel loginText=new JLabel("Login Here");
        loginText.setBounds(80,10,200,30);
        loginText.setFont(new Font("monospace",Font.BOLD,20));
        username=new JTextField();
        username.setBounds(10,60,250,30);
        password=new JPasswordField();
        password.setBounds(10,120,250,30);
        login=new JButton("Login");
        login.setFont(new Font("monospace",Font.BOLD,17));
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        login.setBounds(60,180,150,40);


        p1.add(loginText);
        p1.add(username);
        p1.add(password);
        p1.add(login);
        p1.setBackground(Color.WHITE);
        add(p1);

        setVisible(true);
        setSize(900,600);
        setBounds(300,120,900,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nm=username.getText();
        String ps=password.getText();
        if(nm.equals("apple") && ps.equals("healthy")){
            setVisible(false);
            new DashBoard().setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(getContentPane(),"Invalid username or password!");

        }

    }


    public static void main(String[] args) {
        new Login().setVisible(true);
    }


}
