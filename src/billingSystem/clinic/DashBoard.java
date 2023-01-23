package billingSystem.clinic;
import billingSystem.jdbc.DbConnection;
import billingSystem.subFrames.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

public class DashBoard extends JFrame implements ActionListener {

    JMenuBar mb=null;
    JMenu patients,doctors,appoint,bill;
    JMenuItem addP,dash,listP,addD,listD,addAp,listAp,gBill,listBill,logout,addSer;
    DashBoard(){
        setTitle("HappyLife Clinic");
        getContentPane().setBackground(WHITE);


        mb=new JMenuBar();
        mb.setBackground(black);


        dash=new JMenuItem("Dashboard");
        dash.setForeground(WHITE);
        dash.setBackground(BLACK);
        dash.setFont(new Font("Tahoma",Font.BOLD,14));
        dash.addActionListener(this);
        mb.add(dash);

       patients=new JMenu("Patients");
       Image i=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/pt.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
       patients.setIcon(new ImageIcon(i));
       patients.setForeground(WHITE);
       patients.setFont(new Font("Tahoma",Font.BOLD,14));
       mb.add(patients);
       addP=new JMenuItem("Add Patients");
       addP.addActionListener(this);
       addP.setBackground(white);
       addP.setFont(new Font("Tahoma",Font.BOLD,16));
       patients.add(addP);
       listP=new JMenuItem("List Patients");
       listP.addActionListener(this);
       listP.setBackground(white);
       listP.setFont(new Font("Tahoma",Font.BOLD,16));
       patients.add(listP);


        doctors=new JMenu("Doctors");
        Image dt=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/doc.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        doctors.setIcon(new ImageIcon(dt));
        doctors.setForeground(WHITE);
        doctors.setFont(new Font("Tahoma",Font.BOLD,14));
        mb.add(doctors);
        addD=new JMenuItem("Add Doctors");
        addD.addActionListener(this);
        addD.setFont(new Font("Tahoma",Font.BOLD,16));
        doctors.add(addD);
        listD=new JMenuItem("List Doctors");
        listD.addActionListener(this);
        listD.setFont(new Font("Tahoma",Font.BOLD,16));
        doctors.add(listD);

        appoint=new JMenu("Appointments");
        Image app=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/app.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        appoint.setIcon(new ImageIcon(app));
        appoint.setForeground(WHITE);
        appoint.setFont(new Font("Tahoma",Font.BOLD,14));
        mb.add(appoint);

        addAp=new JMenuItem("Add Appointment");
        addAp.addActionListener(this);
        addAp.setFont(new Font("Tahoma",Font.BOLD,16));
        appoint.add(addAp);
        listAp=new JMenuItem("List Appointments");
        listAp.addActionListener(this);
        listAp.setFont(new Font("Tahoma",Font.BOLD,16));
        appoint.add(listAp);

        bill=new JMenu("Bills");
        Image b=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/bi.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        bill.setIcon(new ImageIcon(b));
        bill.setForeground(WHITE);
        bill.setFont(new Font("Tahoma",Font.BOLD,14));
        mb.add(bill);

        gBill=new JMenuItem("Generate Bill");
        gBill.addActionListener(this);
        gBill.setFont(new Font("Tahoma",Font.BOLD,16));
        bill.add(gBill);
        listBill=new JMenuItem("List Bills");
        listBill.addActionListener(this);
        listBill.setFont(new Font("Tahoma",Font.BOLD,16));
        bill.add(listBill);

        addSer=new JMenuItem("Add Services");
        Image ser=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/service.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        addSer.setIcon(new ImageIcon(ser));
        addSer.setForeground(WHITE);
        addSer.setBackground(BLACK);
        addSer.setFont(new Font("Tahoma",Font.BOLD,14));
        addSer.addActionListener(this);
        mb.add(addSer);

        logout=new JMenuItem("Logout");
        Image log=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/lout.png")).getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        logout.setIcon(new ImageIcon(log));
        logout.setForeground(WHITE);
        logout.setBackground(BLACK);
        logout.setFont(new Font("Tahoma",Font.BOLD,14));
        logout.addActionListener(this);
        mb.add(logout);

        mb.setBounds(0,0,900,50);
        setJMenuBar(mb);

        //background image
        ImageIcon img=new ImageIcon(ClassLoader.getSystemResource("billingSystem/img/dashboard.jpg"));
        Image i1=img.getImage().getScaledInstance(900,600,Image.SCALE_DEFAULT);
        ImageIcon i2=new ImageIcon(i1);
        JLabel l1=new JLabel(img);
        l1.setBounds(0,0,900,600);
        add(l1);





        setLayout(null);
        setVisible(true);
        setBounds(200,40,900,650);
        setSize(900,600);
        setResizable(false);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Dashboard")){
            new DashStatus().setVisible(true);
        }
        else if(e.getActionCommand().equals("Add Patients")){
            new AddPatients().setVisible(true);
        }
        else if(e.getActionCommand().equals("List Patients")){
            new ListPatients().setVisible(true);
        }
        else if(e.getActionCommand().equals("Add Doctors")){
            new AddDoctor().setVisible(true);
        }
        else if(e.getActionCommand().equals("List Doctors")){
            new ListDoctors().setVisible(true);
        }
        else if(e.getActionCommand().equals("Add Appointment")){
            new AddAppointment().setVisible(true);
        }
        else if(e.getActionCommand().equals("List Appointments")){
            new ListAppointments().setVisible(true);
        }
        else if(e.getActionCommand().equals("Generate Bill")){
            new AddBills().setVisible(true);
        }
        else if(e.getActionCommand().equals("List Bills")){
            new ListBills().setVisible(true);
        }
        else if(e.getActionCommand().equals("Add Services")){
            new AddServices().setVisible(true);
        }
        else if(e.getActionCommand().equals("Logout")){
            setVisible(false);
            new Login().setVisible(true);
        }


    }

    public static void main(String[] args) {
        new DashBoard().setVisible(true);
    }
}
