package billingSystem.subFrames;
import billingSystem.jdbc.DbConnection;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ListPatients extends JFrame {

    JTable table = new JTable();
    DefaultTableModel model=null;
    Object[] row=new Object[8];
    JButton delete,update;

    public ListPatients(){
        setTitle("Patients");
        getContentPane().setBackground(new Color(131,193,233));
        JLabel title=new JLabel("Patients");
        title.setBounds(280,10,200,30);
        title.setFont(new Font("Tahoma",Font.BOLD,16));
        add(title);

        // create a table model and set a column.
        Object[] columns = {"Reg. No","Name","DOB","Age","Gender","Mobile","Address","Reason"};
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

            ResultSet resultSet=new DbConnection().stmt.executeQuery("select * from patients");
            while (resultSet.next()){

                    row[0]=resultSet.getInt(1);
                    row[1]=resultSet.getString(2);
                    row[2]=resultSet.getString(3);
                    row[3]=resultSet.getString(4);
                    row[4]=resultSet.getString(5);
                    row[5]=resultSet.getString(6);
                    row[6]=resultSet.getString(7);
                    row[7]=resultSet.getString(8);
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
                        ob.pstmt=ob.con.prepareStatement("delete from patients where reg_no=?");
                        ob.pstmt.setInt(1, (Integer) model.getValueAt(i,0));
                        ob.pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(getContentPane(),"Patient deleted successfully");
                        model.removeRow(i);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(getContentPane(),"Unable to delete patient");
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
        String s1,s2,s3,s4,s5,s6,s7;
        int id;
        if(i>=0) {
            id = (int) model.getValueAt(i, 0);
            s1 = model.getValueAt(i, 1).toString();
            s2 = model.getValueAt(i, 2).toString();
            s3 = model.getValueAt(i, 3).toString();
            s4 = model.getValueAt(i, 4).toString();
            s5 = model.getValueAt(i, 5).toString();
            s6 = model.getValueAt(i, 6).toString();
            s7 = model.getValueAt(i, 7).toString();


            JFrame update = new JFrame("Edit patient details");
            JLabel lb = new JLabel("Edit Patient Details");
            lb.setBounds(280, 0, 200, 40);
            update.setBackground(new Color(131,193,233));
            lb.setFont(new Font("Tahoma", Font.BOLD, 17));
            update.add(lb);
            JLabel regNo=new JLabel("Reg No: "+id);
            regNo.setBounds(20,45,100,30);
            update.add(regNo);

            JLabel name = new JLabel("Patient Name:");
            name.setBounds(20, 80, 80, 30);
            update.add(name);
            JTextField nm = new JTextField(s1);
            nm.setBounds(120, 80, 200, 30);
            update.add(nm);

            JLabel dob = new JLabel("Patient DOB:");
            dob.setBounds(350, 80, 80, 30);
            update.add(dob);
            JTextField db = new JTextField(s2);
            db.setBounds(450, 80, 100, 30);
            update.add(db);

            JLabel age = new JLabel("Patient Age:");
            age.setBounds(20, 120, 80, 30);
            update.add(age);
            JTextField ag = new JTextField(s3);
            ag.setBounds(120, 120, 100, 30);
            update.add(ag);


            JLabel mobile = new JLabel("Patient Mobile:");
            mobile.setBounds(350, 120, 100, 30);
            update.add(mobile);
            JTextField mb = new JTextField(s5);
            mb.setBounds(450, 120, 100, 30);
            update.add(mb);

            JLabel gender = new JLabel("Gender:");
            gender.setBounds(20, 160, 80, 30);
            update.add(gender);

            JRadioButton m = new JRadioButton("Male");
            m.setBounds(130, 160, 100, 30);
            JRadioButton f = new JRadioButton("Female");

            if(s4.equals("Male")){
                m.setSelected(true);
            }
            if (s4.equals("Female")){
                f.setSelected(true);
            }

            f.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (f.isSelected()) {
                        m.setSelected(false);
                    }
                }
            });
            m.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (m.isSelected()) {
                        f.setSelected(false);
                    }
                }
            });
            f.setBounds(230, 160, 100, 30);
            update.add(m);
            update.add(f);


            JLabel address = new JLabel("Address:");
            address.setBounds(20, 220, 80, 30);
            update.add(address);

            JTextArea addr = new JTextArea(s6,10, 100);
            addr.setBounds(120, 220, 300, 60);
            update.add(addr);

            JLabel reason = new JLabel("Reason to consult doctor:");
            reason.setBounds(20, 290, 250, 30);
            update.add(reason);

            JTextArea rs = new JTextArea(s7,10, 100);
            rs.setBounds(120, 320, 300, 60);
            update.add(rs);

            JButton addP = new JButton("Edit");
            addP.setBackground(Color.BLACK);
            addP.setForeground(Color.white);
            addP.setBounds(280, 400, 100, 40);
            addP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //updte into database
                    String gd=m.isSelected()?"Male":"Female";
                    String msg= new DbConnection().updatePatient(id,nm.getText(),db.getText(),ag.getText(),gd,mb.getText(),addr.getText(),rs.getText());
                    JOptionPane.showMessageDialog(update,msg);
                }
            });
            update.add(addP);
            update.setLayout(null);
            update.setBounds(300, 120, 700, 500);
            update.setVisible(true);

        }
    }

    public static void main(String[] args) {
        new ListPatients().setVisible(true);
    }


}
