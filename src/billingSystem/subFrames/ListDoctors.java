package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListDoctors extends JFrame {

    JTable table = new JTable();
    DefaultTableModel model=null;
    Object[] row=new Object[6];
    JButton delete,update;

    public ListDoctors(){
        setTitle("Doctors");
        getContentPane().setBackground(new Color(131,193,233));
        JLabel title=new JLabel("Doctors");
        title.setBounds(280,10,200,30);
        title.setFont(new Font("Tahoma",Font.BOLD,16));
        add(title);

        // create a table model and set a column.
        Object[] columns = {"Reg. No","Name","Mobile","Email","Qualification","Speciality"};
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

            ResultSet resultSet=new DbConnection().stmt.executeQuery("select * from doctors");
            while (resultSet.next()){

                row[0]=resultSet.getInt(1);
                row[1]=resultSet.getString(2);
                row[2]=resultSet.getString(3);
                row[3]=resultSet.getString(4);
                row[4]=resultSet.getString(5);
                row[5]=resultSet.getString(6);
                // add row to the model
                model.addRow(row);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 50, 690, 350);
        add(pane);

        delete=new JButton("Delete");
        delete.setBounds(30,400,100,30);
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
                        ob.pstmt=ob.con.prepareStatement("delete from doctors where id=?");
                        ob.pstmt.setInt(1, (Integer) model.getValueAt(i,0));
                        ob.pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(getContentPane(),"Doctor deleted successfully");
                        model.removeRow(i);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(getContentPane(),"Unable to delete Doctor");
                }
            }
        });
        update=new JButton("Edit");
        update.setBounds(160,400,100,30);
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
            s5 = model.getValueAt(i, 5).toString();


            JFrame update = new JFrame("Edit doctor details");
            update.setBackground(new Color(131,193,233));
            JLabel lb = new JLabel("Edit doctor Details");
            lb.setBounds(280, 0, 200, 40);
            lb.setFont(new Font("Tahoma", Font.BOLD, 17));
            update.add(lb);
            JLabel regNo=new JLabel("Reg No: "+id);
            regNo.setBounds(20,45,100,30);
            update.add(regNo);

            JLabel name=new JLabel(" Doctor Name:");
            name.setBounds(20,80,100,30);
            update.add(name);
            JTextField nm=new JTextField(s1);
            nm.setBounds(130,80,200,30);
            update.add(nm);

            JLabel email=new JLabel("Doctor email:");
            email.setBounds(360,80,80,30);
            update.add(email);
            JTextField em=new JTextField(s3);
            em.setBounds(450,80,200,30);
            update.add(em);

            JLabel quali=new JLabel("Doctor qualification:");
            quali.setBounds(20,120,120,30);
            update.add(quali);

            String[] degree={"BHMS","MBBS","BAMS","BYNS"};
            JComboBox qu=new JComboBox(degree);
            qu.setSelectedItem(s4);
            qu.setBounds(160,120,160,30);
            update.add(qu);


            JLabel mobile=new JLabel(" Doctor Mobile:");
            mobile.setBounds(360,120,100,30);
            update.add(mobile);
            JTextField mb=new JTextField(s2);
            mb.setBounds(460,120,200,30);
            update.add(mb);

            JLabel speciality=new JLabel("Doctor speciality :");
            speciality.setBounds(20,170,150,30);
            update.add(speciality);
            JTextArea spl=new JTextArea(s5);
            spl.setBounds(160,170,300,60);
            update.add(spl);



            JButton addD=new JButton("Edit");
            addD.setBackground(Color.BLACK);
            addD.setForeground(Color.white);
            addD.setBounds(280,260,100,40);

            addD.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //updte into database

                    String msg= new DbConnection().updateDoctor(id,nm.getText(),mb.getText(),em.getText(),qu.getSelectedItem().toString(),spl.getText());
                    JOptionPane.showMessageDialog(update,msg);
                }
            });
            update.add(addD);
            update.setLayout(null);
            update.setBounds(300, 120, 700, 500);
            update.setVisible(true);


        }
    }

    public static void main(String[] args) {
        new ListDoctors().setVisible(true);
    }
}
