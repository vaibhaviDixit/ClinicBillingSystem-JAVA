package billingSystem.subFrames;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import billingSystem.jdbc.DbConnection;

public class AddPatients extends JFrame implements ActionListener {

    JLabel lb,name,dob,age,gender,mobile,address,reason;
    JTextField nm,ag,mb,db;
    JTextArea rs,addr;
    JButton addP;
    JRadioButton m,f;

    public AddPatients(){
        setTitle("Add Patients");
        getContentPane().setBackground(new Color(131,193,233));
        lb=new JLabel("ADD PATIENTS");
        lb.setBounds(280,0,200,40);
        lb.setFont(new Font("Tahoma",Font.BOLD,17));
        add(lb);

        name=new JLabel("Patient Name:");
        name.setBounds(20,80,80,30);
        add(name);
        nm=new JTextField();
        nm.setBounds(120,80,200,30);
        add(nm);

        dob=new JLabel("Patient DOB:");
        dob.setBounds(350,80,80,30);
        add(dob);
        db=new JTextField();
        db.setBounds(450,80,100,30);
        add(db);

        age=new JLabel("Patient Age:");
        age.setBounds(20,120,80,30);
        add(age);
        ag=new JTextField();
        ag.setBounds(120,120,100,30);
        add(ag);


        mobile=new JLabel("Patient Mobile:");
        mobile.setBounds(350,120,100,30);
        add(mobile);
        mb=new JTextField();
        mb.setBounds(450,120,100,30);
        add(mb);

        gender=new JLabel("Gender:");
        gender.setBounds(20,160,80,30);
        add(gender);
        m=new JRadioButton("Male");
        m.setBackground(new Color(131,193,233));
        m.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(m.isSelected()){
                    f.setSelected(false);
                }
            }
        });
        m.setBounds(130,160,100,30);
        add(m);
        f=new JRadioButton("Female");
        f.setBackground(new Color(131,193,233));
        f.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(f.isSelected()){
                    m.setSelected(false);
                }
            }
        });
        f.setBounds(230,160,100,30);
        add(f);

        address=new JLabel("Address:");
        address.setBounds(20,220,80,30);
        add(address);

        addr=new JTextArea(10,100);
        addr.setBounds(120,220,300,60);
        add(addr);

        reason=new JLabel("Reason to consult doctor:");
        reason.setBounds(20,290,250,30);
        add(reason);

        rs=new JTextArea(10,100);
        rs.setBounds(120,320,300,60);
        add(rs);

        addP=new JButton("Add Patient");
        addP.setBackground(Color.BLACK);
        addP.setForeground(Color.white);
        addP.setBounds(280,400,100,40);
        addP.addActionListener(this);
        add(addP);


        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String ptname,ptdob,ptage,ptmobile,ptaddress,ptreason;
        String ptgender=m.isSelected()?"Male":"Female";
        ptname=nm.getText();
        ptdob=db.getText();
        ptage=ag.getText();
        ptmobile=mb.getText();
        ptaddress=addr.getText();
        ptreason=rs.getText();
        String msg=new DbConnection().addIntoPatients(ptname,ptdob,ptage,ptgender,ptmobile,ptaddress,ptreason);
        JOptionPane.showMessageDialog(getContentPane(),msg);

    }

    public static void main(String[] args) {
        new AddPatients().setVisible(true);
    }

}
