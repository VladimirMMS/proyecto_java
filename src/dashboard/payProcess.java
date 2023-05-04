/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javaapplication1.Articule_List;
import javaapplication1.conectar1;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author vladi
 */
public final class payProcess extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    float pay;
    float itebis;
    float debt=0;
    DefaultTableModel billing_t;
    String customerN;
    String userN;
    String address = "F7PR+333, Santiago De Los Caballeros";
    String phone = "8097757685";
    String total;
    boolean isPay=false;
    public payProcess(float toPay, float totalItebis, DefaultTableModel table, String customer, String user, String total) {
        initComponents();
        total_pay.setEditable(false);
        devolver.setEditable(false);
        rnc.setText("55678304547");
        rnc.setEditable(false);
        this.pay=toPay;
        this.itebis=totalItebis;
        this.billing_t=table;
        this.customerN=customer;
        this.userN=user;
        this.total=total;
        total_pay.setText(String.valueOf(toPay));
        
    }
    
    
    void clarData() {
        billing_t.getDataVector().clear();
    }
    void print_billing() {
        ArrayList list = new ArrayList();
 
        for (int i = 0; i < billing_t.getRowCount(); i++) {
            String nombre=(String)billing_t.getValueAt(i, 0);
            System.out.println(nombre);
            String precio=(String)billing_t.getValueAt(i, 1);
            String cantidad=(String)billing_t.getValueAt(i, 2);
            String itebis_e=(String)billing_t.getValueAt(i, 4);
            String subtotal=(String)billing_t.getValueAt(i, 5);
            Articule_List list_art = new Articule_List(nombre, precio, cantidad,itebis_e, subtotal);
            list.add(list_art);
        }
        JasperReport jr;
        try {
            jr = (JasperReport) JRLoader.loadObjectFromFile("src/report/billing_print.jasper");
            HashMap param = new HashMap();
            param.put("company", company_name.getText());
            param.put("billing_id", rnc.getText());
            param.put("subtotal", total);
            String ite =  String.valueOf(itebis);
            param.put("itebis", ite);
            param.put("total", total_pay.getText());
            JasperPrint jp = JasperFillManager.fillReport(jr, param, new JRBeanCollectionDataSource(list));
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            
        }
        catch(JRException ex) {
            System.out.println(ex);
        
        }
    }
        

    void quantityToReturn() {
        float quant =Float.parseFloat(quantity_d.getText())-pay;
        devolver.setText(String.valueOf(quant));
        
    }
    
    String getCurrentDate() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now);
    }
    
    int getEmployee() {
        int id=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id FROM User" +" WHERE username = + '"+userN+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
    int getCustomer() {
        int id=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id FROM Customer" +" WHERE name = + '"+customerN+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
    float verifyCustomerCredit() {
        float credit=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT credit FROM Customer" +" WHERE name = + '"+customerN+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                credit = rs.getFloat("credit");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return credit;
        
    }
    int getArticleId(String article) {
        int id=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id FROM Articule" +" WHERE name = + '"+article+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
     int getArticuleQuantity(int articleId) {
        int quantity=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT quantity FROM Articule" +" WHERE id = + '"+articleId+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                quantity = rs.getInt("quantity");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return quantity;
    }
    
    void updateArticule(int quantity_param, int art_id) {
        try {
             String sqlC="UPDATE Articule SET quantity='"+quantity_param+"' WHERE id='"+art_id+"'";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                return;
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    int insertBillingDetails(int billing_id) {
        int count=0;
        for (int i = 0; i < billing_t.getRowCount(); i++) {
            String article =(String) billing_t.getValueAt(i, 0);
            int articleId = getArticleId(article);
            String quantity =(String) billing_t.getValueAt(i, 2);
            int quantity_int = (Integer) Integer.parseInt(quantity);
            int quantity_db = getArticuleQuantity(articleId);
            String sub_total = (String) billing_t.getValueAt(i, 5);
            updateArticule(quantity_db-quantity_int, articleId);
            try {
             String sqlC="INSERT INTO Billing_Details(quantity, billing_id, articule_id, sub_total) "+ "VALUES("+ "'"+quantity_int+"', "+ 
                     "'"+billing_id+"',"+ 
                     "'"+articleId+"',"+
                     "'"+sub_total+"')";
             
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n >0) {
                count=count+1;
              
            }
        } catch(SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        }
        if(count==billing_t.getRowCount()) {
            isPay=true;
            JOptionPane.showMessageDialog(null, "La factura se ha pagado correctamente");
            return count;
        }
        return count;
    }
    
    void addBillingToDb() {
       String date = getCurrentDate();
       int employeId=getEmployee();
       int customerId=getCustomer();
       float total_p = Float.parseFloat(total_pay.getText());
       try {
             String sqlC="INSERT INTO Billing(company_name, address,company_phone, date_created, total, itebis_total, debt, user_id, customer_id, pay_type) "+ "VALUES("+ "'"+company_name.getText()+"', "+ 
                     "'"+address+"',"+ 
                     "'"+phone+"',"+ 
                     "'"+date+"',"+ 
                     "'"+total_p+"',"+
                     "'"+itebis+"',"+
                     "'"+debt+"',"+
                     "'"+employeId+"',"+
                     "'"+customerId+"',"+
                     "'"+articule_b.getSelectedItem()+"')";
             
            PreparedStatement insert_user = cn.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
            int n = insert_user.executeUpdate();
            if(n >0) {
                ResultSet rs = insert_user.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    insertBillingDetails(id);
                    
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        total_pay = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        articule_b = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        quantity_d = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        devolver = new javax.swing.JTextField();
        add_button1 = new javax.swing.JButton();
        add_button2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        rnc = new javax.swing.JTextField();
        company_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Procesar Pago de Factura");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Total a Pagar:");

        total_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                total_payActionPerformed(evt);
            }
        });
        total_pay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                total_payKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Tipo de pago:");

        articule_b.setBackground(new java.awt.Color(242, 242, 242));
        articule_b.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contado", "Credito" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Cantidad a depositar:");

        quantity_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantity_dActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Cantidad a devolver:");

        devolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devolverActionPerformed(evt);
            }
        });

        add_button1.setBackground(new java.awt.Color(242, 242, 242));
        add_button1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add_button1.setText("Pagar");
        add_button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_button1MouseClicked(evt);
            }
        });
        add_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_button1ActionPerformed(evt);
            }
        });

        add_button2.setBackground(new java.awt.Color(242, 242, 242));
        add_button2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add_button2.setText("Imprimir Factura");
        add_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(add_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(articule_b, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(devolver, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantity_d, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(add_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(total_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(quantity_d, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(articule_b, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(devolver, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("RNC");

        rnc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rncActionPerformed(evt);
            }
        });

        company_name.setBackground(new java.awt.Color(255, 255, 255));
        company_name.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        company_name.setForeground(new java.awt.Color(255, 255, 255));
        company_name.setText("Vladimirs Market");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(321, 321, 321))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(company_name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(236, 236, 236)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rnc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(rnc, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(company_name, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void total_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_total_payActionPerformed

    }//GEN-LAST:event_total_payActionPerformed

    private void quantity_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantity_dActionPerformed

    }//GEN-LAST:event_quantity_dActionPerformed

    private void devolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_devolverActionPerformed

    private void add_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_button1ActionPerformed
        if(quantity_d.getText().equals("") && articule_b.getSelectedItem().equals("Contado")) {
            JOptionPane.showMessageDialog(null, "Por favor digite digite la cantidad a pagar");
            return;
        }
        
        if(articule_b.getSelectedItem().equals("Contado")) {
             if(Float.parseFloat(quantity_d.getText()) >=pay) {
                quantityToReturn();
                addBillingToDb();
             }
             else {
                 JOptionPane.showMessageDialog(null, "La cantidad de dinero que ingreso es menor a la que tiene que pagar");
             }
        }
        else {
                float credit = verifyCustomerCredit();
                if(credit >= pay) {
                    debt= pay;
                    addBillingToDb();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Su credito no es suficiente para realizar la compra");
                }
            }
        
        
        
            
      
    }//GEN-LAST:event_add_button1ActionPerformed

    private void add_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_button2ActionPerformed
        if(isPay) {
            print_billing();
            return;
        }
        JOptionPane.showMessageDialog(null, "Tiene que proceder al pago antes de imprimir");
        
    }//GEN-LAST:event_add_button2ActionPerformed

    private void total_payKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_payKeyPressed
        total_pay.setText(String.valueOf(pay));
    }//GEN-LAST:event_total_payKeyPressed

    private void add_button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_button1MouseClicked
      
    }//GEN-LAST:event_add_button1MouseClicked

    private void rncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rncActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rncActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(payProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(payProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(payProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(payProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button1;
    private javax.swing.JButton add_button2;
    private javax.swing.JComboBox<String> articule_b;
    private javax.swing.JLabel company_name;
    private javax.swing.JTextField devolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField quantity_d;
    private javax.swing.JTextField rnc;
    private javax.swing.JTextField total_pay;
    // End of variables declaration//GEN-END:variables
}
