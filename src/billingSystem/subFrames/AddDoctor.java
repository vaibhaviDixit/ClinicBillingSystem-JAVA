package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddDoctor extends JFrame implements ActionListener {

    JLabel lb,name,mobile,email,quali,speciality;
    JTextField nm,mb,em;
    JComboBox qu;
    JTextArea spl;
    JButton addD;

    public AddDoctor(){
        setTitle("Add Doctor");
        getContentPane().setBackground(new Color(131,193,233));
        lb=new JLabel("ADD Doctor");
        lb.setBounds(280,0,200,40);
        lb.setFont(new Font("Tahoma",Font.BOLD,17));
        add(lb);

        name=new JLabel(" Doctor Name:");
        name.setBounds(20,80,100,30);
        add(name);
        nm=new JTextField();
        nm.setBounds(130,80,200,30);
        add(nm);

        email=new JLabel("Doctor email:");
        email.setBounds(360,80,80,30);
        add(email);
        em=new JTextField();
        em.setBounds(450,80,200,30);
        add(em);

        quali=new JLabel("Doctor qualification:");
        quali.setBounds(20,120,120,30);
        add(quali);

        String[] degree={"BHMS","MBBS","BAMS","BYNS"};
        qu=new JComboBox(degree);
        qu.setBounds(160,120,160,30);
        add(qu);


        mobile=new JLabel(" Doctor Mobile:");
        mobile.setBounds(360,120,100,30);
        add(mobile);
        mb=new JTextField();
        mb.setBounds(460,120,200,30);
        add(mb);

        speciality=new JLabel("Doctor speciality :");
        speciality.setBounds(20,170,150,30);
        add(speciality);
        spl=new JTextArea();
        spl.setBounds(160,170,300,60);
        add(spl);



        addD=new JButton("Add Doctor");
        addD.setBackground(Color.BLACK);
        addD.setForeground(Color.white);
        addD.setBounds(280,260,100,40);
        addD.addActionListener(this);
        add(addD);


        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String dname,dmobile,demail,dquali,dspeciality;

        dname=nm.getText();
        demail=em.getText();
        dmobile=mb.getText();
        dquali=qu.getSelectedItem().toString();
        dspeciality=spl.getText();
        String msg=new DbConnection().addIntoDoctor(dname,dmobile,demail,dquali,dspeciality);
        JOptionPane.showMessageDialog(getContentPane(),msg);

    }

    public static void main(String[] args) {
        new AddDoctor().setVisible(true);
    }

}