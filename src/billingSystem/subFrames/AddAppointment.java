package billingSystem.subFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import billingSystem.jdbc.DbConnection;

public class AddAppointment extends JFrame implements ActionListener {

    JLabel lb,PatientName,apDate,apSession,apDoctor;
    JTextField adt;
    JComboBox adoc,pnm,ases;
    JButton addA;



    public AddAppointment(){
        setTitle("Add Appointments");
        getContentPane().setBackground(new Color(131,193,233));
        lb=new JLabel("ADD APPOINTMENT");
        lb.setBounds(280,0,200,40);
        lb.setFont(new Font("Tahoma",Font.BOLD,17));
        add(lb);

        PatientName=new JLabel("Patient Name:");
        PatientName.setBounds(20,80,80,30);
        add(PatientName);
        DbConnection obj=new DbConnection();
        pnm=new JComboBox(obj.getPatients());
        pnm.setBounds(180,80,200,30);
        add(pnm);

        apDate=new JLabel("Appointment Date:");
        apDate.setBounds(20,120,180,30);
        add(apDate);
        adt=new JTextField();
        adt.setBounds(180,120,200,30);
        add(adt);

        apDoctor=new JLabel("Appointment Doctor:");
        apDoctor.setBounds(20,170,180,30);
        add(apDoctor);
        DbConnection obj2=new DbConnection();
        adoc=new JComboBox(obj2.getDoctors());
        adoc.setBounds(180,170,200,30);
        add(adoc);


        apSession=new JLabel("Appointment Session:");
        apSession.setBounds(20,220,180,30);
        add(apSession);
        String apsession[]={"Morning","Afternoon","Evening"};
        ases=new JComboBox(apsession);
        ases.setBounds(180,220,100,30);
        add(ases);


        addA=new JButton("Add Appointment");
        addA.setBackground(Color.BLACK);
        addA.setForeground(Color.white);
        addA.setBounds(280,400,160,40);
        addA.addActionListener(this);
        add(addA);


        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String ptname,apdate,session,apDoctor;

        ptname=pnm.getSelectedItem().toString();
        apdate=adt.getText();
        session=ases.getSelectedItem().toString();
        apDoctor=adoc.getSelectedItem().toString();

        String msg=new DbConnection().addIntoAppointment(ptname,apdate,session,apDoctor);
        JOptionPane.showMessageDialog(getContentPane(),msg);

    }

    public static void main(String[] args) {

        new AddAppointment().setVisible(true);


    }

}

