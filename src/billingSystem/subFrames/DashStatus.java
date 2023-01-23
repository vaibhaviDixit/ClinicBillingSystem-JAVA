package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.darkGray;
import static java.awt.Color.white;

public class DashStatus extends JFrame{
    public DashStatus(){
        setTitle("DashBoard");
        getContentPane().setBackground(new Color(3, 1, 13));

        JPanel totalPatients=new JPanel();
        totalPatients.setBackground(darkGray);
        totalPatients.setBounds(30,100,200,150);

        JLabel label1=new JLabel("Total Registered");
        label1.setFont(new Font("Tahoma",Font.BOLD,16));
        label1.setForeground(white);

        JLabel label2=new JLabel("Patients");
        label2.setFont(new Font("Tahoma",Font.BOLD,16));
        label2.setForeground(white);

        JLabel label3=new JLabel(String.valueOf(new DbConnection().getTotalPatients() ) );
        label3.setFont(new Font("Tahoma",Font.BOLD,18));
        label3.setForeground(white);

        label1.setBounds(30,50,200,50);
        label2.setBounds(70,70,100,50);
        label3.setBounds(90,100,100,50);

        ImageIcon patients=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/pt.png"));
        Image pt=patients.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon ptt=new ImageIcon(pt);
        JLabel ptimg=new JLabel(ptt);
        ptimg.setBounds(80,10,50,50);

        totalPatients.add(label1);
        totalPatients.add(label2);
        totalPatients.add(label3);

        totalPatients.add(ptimg);
        add(totalPatients);

        totalPatients.setLayout(null);


        JPanel totalApp=new JPanel();
        totalApp.setBackground(darkGray);
        totalApp.setBounds(240,100,200,150);

        JLabel label4=new JLabel("Total");
        label4.setFont(new Font("Tahoma",Font.BOLD,16));
        label4.setForeground(white);

        JLabel label5=new JLabel("Appointments");
        label5.setFont(new Font("Tahoma",Font.BOLD,16));
        label5.setForeground(white);

        JLabel label6=new JLabel(String.valueOf(new DbConnection().getTotalAppointments() ) );
        label6.setFont(new Font("Tahoma",Font.BOLD,18));
        label6.setForeground(white);

        label4.setBounds(85,50,200,50);
        label5.setBounds(50,70,200,50);
        label6.setBounds(90,100,100,50);

        ImageIcon apps=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/app.png"));
        Image at=apps.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon apt=new ImageIcon(at);
        JLabel aptimg=new JLabel(apt);
        aptimg.setBounds(80,10,50,50);

        totalApp.add(label4);
        totalApp.add(label5);
        totalApp.add(label6);

        totalApp.add(aptimg);
        add(totalApp);

        totalApp.setLayout(null);


        JPanel totalBills=new JPanel();
        totalBills.setBackground(darkGray);
        totalBills.setBounds(450,100,200,150);

        JLabel label7=new JLabel("Total");
        label7.setFont(new Font("Tahoma",Font.BOLD,16));
        label7.setForeground(white);

        JLabel label8=new JLabel("Bills Generated");
        label8.setFont(new Font("Tahoma",Font.BOLD,16));
        label8.setForeground(white);

        JLabel label9=new JLabel(String.valueOf(new DbConnection().getTotalBills() ) );
        label9.setFont(new Font("Tahoma",Font.BOLD,18));
        label9.setForeground(white);

        label7.setBounds(85,50,200,50);
        label8.setBounds(50,70,200,50);
        label9.setBounds(90,100,100,50);

        ImageIcon bills=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/bi.png"));
        Image bl=bills.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        JLabel billimg=new JLabel(new ImageIcon(bl));
        billimg.setBounds(80,10,50,50);

        totalBills.add(label7);
        totalBills.add(label8);
        totalBills.add(label9);

        totalBills.add(billimg);
        add(totalBills);

        totalBills.setLayout(null);

        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
}
