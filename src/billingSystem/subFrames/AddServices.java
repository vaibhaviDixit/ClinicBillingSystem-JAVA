package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddServices extends JFrame implements ActionListener {

    JLabel lb,serName,serPrice;
    JTextField ser,price;
    JButton addS;

    public AddServices(){
        setTitle("Add Services");
        getContentPane().setBackground(new Color(131,193,233));
        setLayout(null);

        lb=new JLabel("ADD SERVICES");
        lb.setBounds(280,0,250,40);
        lb.setFont(new Font("Tahoma",Font.BOLD,17));
        add(lb);

        serName=new JLabel("Service Name:");
        serName.setBounds(20,80,120,30);
        add(serName);
        ser=new JTextField();
        ser.setBounds(150,80,250,30);
        add(ser);

        serPrice=new JLabel("Service Price:");
        serPrice.setBounds(20,140,250,30);
        add(serPrice);

        price=new JTextField();
        price.setBounds(150,140,250,30);
        add(price);

        addS=new JButton("Add Service");
        addS.setBackground(Color.BLACK);
        addS.setForeground(Color.white);
        addS.setBounds(200,200,130,40);
        addS.addActionListener(this);
        add(addS);

        setVisible(true);
        setBounds(300,120,700,500);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

           String msg=new DbConnection().addIntoService(ser.getText(),Integer.parseInt(price.getText()));
           JOptionPane.showMessageDialog(getContentPane(),msg);
    }


    public static void main(String[] args) {
        new AddServices();
    }


}
