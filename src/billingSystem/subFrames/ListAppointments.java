package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListAppointments extends JFrame {

    JTable table = new JTable();
    DefaultTableModel model=null;
    Object[] row=new Object[5];
    JButton delete,update;

    public ListAppointments(){
        setTitle("Appointments");
        getContentPane().setBackground(new Color(131,193,233));
        JLabel title=new JLabel("Appointments");
        title.setBounds(280,10,200,30);
        title.setFont(new Font("Tahoma",Font.BOLD,16));
        add(title);

        // create a table model and set a column.
        Object[] columns = {"Id","Patient_Name","Appointment Date","Appointment Session","Consult Doctor"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);


        // set the model to the table
        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.white);
        table.setForeground(Color.black);
        Font font = new Font("Tahoma", Font.PLAIN, 15);
        table.setFont(font);
        table.setRowHeight(30);

        try {

            ResultSet resultSet=new DbConnection().stmt.executeQuery("select * from appointments");
            while (resultSet.next()){

                row[0]=resultSet.getInt(1);
                row[1]=resultSet.getString(2);
                row[2]=resultSet.getString(3);
                row[3]=resultSet.getString(4);
                row[4]=resultSet.getString(5);
                // add row to the model
                model.addRow(row);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 50, 690, 350);
        add(pane);

        delete=new JButton("Cancel Appointment");
        delete.setBounds(30,400,200,30);
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.BLACK);
        add(delete);

        // button remove row - Clicked on Delete Button
        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = index of selected number.
                int i = table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable

                    DbConnection ob=new DbConnection();
                    try {
                        ob.pstmt=ob.con.prepareStatement("delete from appointments where id=?");
                        ob.pstmt.setInt(1, (Integer) model.getValueAt(i,0));
                        ob.pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(getContentPane(),"Appointment cancelled successfully");
                        model.removeRow(i);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(getContentPane(),"Unable to cancel appointment");
                }
            }
        });
        update=new JButton("Edit");
        update.setBounds(260,400,100,30);
        update.setForeground(Color.WHITE);
        update.setBackground(Color.BLACK);
        add(update);

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                edit();

            }
        });



        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    public void edit(){
        int i = table.getSelectedRow();
        String s1,s2,s3,s4,s5;
        int id;
        if(i>=0) {
            id = (int) model.getValueAt(i, 0);
            s1 = model.getValueAt(i, 1).toString();
            s2 = model.getValueAt(i, 2).toString();
            s3 = model.getValueAt(i, 3).toString();
            s4 = model.getValueAt(i, 4).toString();


            JFrame update = new JFrame("Edit Appointment details");
            update.setBackground(new Color(131,193,233));
            JLabel lb = new JLabel("Edit Appointment Details");
            lb.setBounds(280, 0, 300, 40);
            lb.setFont(new Font("Tahoma", Font.BOLD, 17));
            update.add(lb);
            JLabel regNo=new JLabel("Appointment No: "+id);
            regNo.setBounds(20,45,190,30);
            update.add(regNo);

            JLabel PatientName=new JLabel("Patient Name:");
            PatientName.setBounds(20,80,80,30);
            update.add(PatientName);
            DbConnection obj=new DbConnection();
            JComboBox pnm=new JComboBox(obj.getPatients());
            pnm.setBounds(180,80,200,30);
            update.add(pnm);

            JLabel apDate=new JLabel("Appointment Date:");
            apDate.setBounds(20,120,180,30);
            update.add(apDate);
            JTextField adt=new JTextField(s2);
            adt.setBounds(180,120,200,30);
            update.add(adt);

            JLabel apDoctor=new JLabel("Appointment Doctor:");
            apDoctor.setBounds(20,170,180,30);
            update.add(apDoctor);
            DbConnection obj2=new DbConnection();
            JComboBox adoc=new JComboBox(obj2.getDoctors());
            adoc.setSelectedItem(s4);
            adoc.setBounds(180,170,200,30);
            update.add(adoc);


            JLabel apSession=new JLabel("Appointment Session:");
            apSession.setBounds(20,220,180,30);
            update.add(apSession);
            String apsession[]={"Morning","Afternoon","Evening"};
            JComboBox ases=new JComboBox(apsession);
            ases.setSelectedItem(s3);
            ases.setBounds(180,220,100,30);
            update.add(ases);


            JButton addA=new JButton("Update Appointment");
            addA.setBackground(Color.BLACK);
            addA.setForeground(Color.white);
            addA.setBounds(280,400,160,40);
            update.add(addA);


            addA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //updte into database

                    String msg= new DbConnection().updateAppointment(id, pnm.getSelectedItem().toString(),adt.getText(), ases.getSelectedItem().toString() ,adoc.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(update,msg);
                }
            });
            update.add(addA);
            update.setLayout(null);
            update.setBounds(300, 120, 700, 500);
            update.setVisible(true);

        }
    }

    public static void main(String[] args) {

        new ListAppointments().setVisible(true);
    }
}
