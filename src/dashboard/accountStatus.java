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
import java.util.ArrayList;
import java.util.HashMap;
import javaapplication1.AccountReport;
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

public final class accountStatus extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    String customer_n="";
    String customer_l="";
    String cedula="";
    int id_billing;
    public accountStatus() {
        initComponents();
        getAllBillings();
        monto.setEditable(false);
    }
    void print_report() {
        ArrayList list = new ArrayList();
        DefaultTableModel table = (DefaultTableModel) account_s_t.getModel();
        for (int i = 0; i < table.getRowCount(); i++) {
            String company=(String)table.getValueAt(i, 1);
            String address=(String)table.getValueAt(i, 2);
            String date=(String)table.getValueAt(i, 3);
            String total=(String)table.getValueAt(i, 4);
            String empleado=(String)table.getValueAt(i, 6);
            String deuda=(String)table.getValueAt(i, 7);
            String tipo=(String)table.getValueAt(i, 8);
            AccountReport list_art = new AccountReport(company,address, date, total, empleado, deuda, tipo);
            list.add(list_art);
        }
        JasperReport jr;
        try {
            jr = (JasperReport) JRLoader.loadObjectFromFile("src/report/accountStatus.jasper");
            HashMap param = new HashMap();
            param.put("cliente", customer_n+" "+customer_l);
            param.put("cedula", cedula);
         
            JasperPrint jp = JasperFillManager.fillReport(jr, param, new JRBeanCollectionDataSource(list));
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            
        }
        catch(JRException ex) {
            System.out.println(ex);
        
        }
    }
    float calculateDebt() {
        float depositar =Float.parseFloat(monto_p.getText());
        float mont = Float.parseFloat(monto.getText());
        if(!monto_p.getText().equals("")) {
            if(depositar>=mont) {
                return 0;
            }
            else {
                float rest = mont-depositar;
                return rest;
            }
        }
        JOptionPane.showMessageDialog(null, "Solo puedes digitar numeros");
        return mont;
        
    }
    
    void updateBilling(int id) {
        float debt= calculateDebt();
        try {
             String sqlC="UPDATE Billing SET debt='"+debt+"' '"+"' WHERE id='"+id+"'";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Deuda Actualizada");
                monto_p.setText("");
                monto.setText("");
                if(!search.getText().equals("")) {
                    String id_card = search.getText();
                    int id_c = getCustomerByCardId(id_card);
                    getBillingByCustomer(id_c);
                }
                else {
                    getAllBillings();
                }
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    int getCustomerByCardId(String card_id) {
        int id=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name, lastname FROM Customer" +" WHERE card_id = + '"+card_id+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              cedula=card_id;
              id= rs.getInt("id");
              customer_n=rs.getString("name");
              customer_l=rs.getString("lastname");
              getBillingByCustomer(id);
              
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
        
    }
    String getEmployeeName(Integer id) {
        String name = "";
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,name, username, password FROM User" +" WHERE id = + '"+id+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              name = rs.getString("name");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return name;
    }
    void getAllBillings() {
     
        DefaultTableModel table = (DefaultTableModel) account_s_t.getModel();
        String [] registros = new String[9];
        table.getDataVector().clear();
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,company_name, address, date_created, total, itebis_total, debt, user_id, pay_type, customer_id FROM Billing";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                String employee = getEmployeeName(rs.getInt("user_id"));
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("company_name");
                registros[2]=rs.getString("address");
                registros[3]=rs.getString("date_created");
                registros[4]=rs.getString("total");
                registros[5]=rs.getString("itebis_total");
                registros[6]=employee;
                registros[7]=rs.getString("debt");
                registros[8]=rs.getString("pay_type");
                table.addRow(registros);
            }     
            account_s_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
     void getBillingByCustomer(int customer_id) {
        DefaultTableModel table = (DefaultTableModel) account_s_t.getModel();
        String [] registros = new String[9];
        table.getDataVector().clear();
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, company_name, address, date_created, total, itebis_total, debt, user_id, pay_type, customer_id FROM Billing" +" WHERE customer_id = + '"+customer_id+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                String employee = getEmployeeName(rs.getInt("user_id"));
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("company_name");
                registros[2]=rs.getString("address");
                registros[3]=rs.getString("date_created");
                registros[4]=rs.getString("total");
                registros[5]=rs.getString("itebis_total");
                registros[6]=employee;
                registros[7]=rs.getString("debt");
                registros[8]=rs.getString("pay_type");
                table.addRow(registros);
            } 
            
            account_s_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        account_s_t = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        monto = new javax.swing.JTextField();
        pagar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        monto_p = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Estado de cuenta");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Buscar por cedula:");

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(242, 242, 242));
        jButton1.setText("Crear Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        account_s_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codigo", "Sucursal", "Dirrecion", "Fecha", "Total", "Itebis", "Empleado", "Deuda", "Tipo de pago"
            }
        ));
        account_s_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                account_s_tMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(account_s_t);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setText("Monto a depositar:");

        monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                montoKeyReleased(evt);
            }
        });

        pagar.setBackground(new java.awt.Color(242, 242, 242));
        pagar.setText("Pagar");
        pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(242, 242, 242));
        jLabel4.setText("Monto:");

        monto_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monto_pKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(496, 496, 496))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 84, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(monto, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(monto_p, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(pagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monto_p, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
       if(search.getText().equals(" ") || search.getText().equals("")) {
           getAllBillings();
           return;
       }
       getCustomerByCardId(search.getText());
    }//GEN-LAST:event_searchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        print_report();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void montoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_montoKeyReleased

    private void pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagarActionPerformed
       updateBilling(id_billing);
    }//GEN-LAST:event_pagarActionPerformed

    private void monto_pKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monto_pKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_monto_pKeyReleased

    private void account_s_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_account_s_tMouseClicked
      int fila=account_s_t.getSelectedRow();
      if(fila>=0) {
          String art_t =(String) account_s_t.getValueAt(fila, 8);
          String deuda =(String) account_s_t.getValueAt(fila, 7);
          if(art_t.equals("Credito")) {
              if(Float.parseFloat(deuda)>0){
                  String id_t = (String)account_s_t.getValueAt(fila, 0);
                  id_billing=Integer.parseInt(id_t);
                  monto.setText(deuda);
              }
              
          }
      }
    }//GEN-LAST:event_account_s_tMouseClicked

    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accountStatus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable account_s_t;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField monto;
    private javax.swing.JTextField monto_p;
    private javax.swing.JButton pagar;
    private javax.swing.JTextField search;
    // End of variables declaration//GEN-END:variables
}
