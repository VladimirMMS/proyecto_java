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
import javaapplication1.conectar1;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class customer extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    int customer_id;
    public customer() {
        initComponents();
        addCustomerToTable();
    }
    void addCustomerToTable() {
        DefaultTableModel table = (DefaultTableModel) customer_t.getModel();
        String [] registros = new String[6];
        table.getDataVector().clear();
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name, lastname, card_id, phone, credit FROM Customer";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                System.out.println(rs.getString("id"));
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("name");
                registros[2]=rs.getString("lastname");
                registros[3]=rs.getString("card_id");
                registros[4]=rs.getString("phone");
                registros[5]=rs.getString("credit");
                table.addRow(registros);
            }     
            customer_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void customerAction() {
        if(crear.getText().equals("Crear Cliente")) {
            crearClient();
        }
        else {
            updateClient();
        }
    }
    void crearClient(){
         try {
             String sqlC="INSERT INTO Customer(name, lastname,card_id, phone, credit) "+ "VALUES("+ "'"+nombre.getText()+"', "+ 
                     "'"+apellido.getText()+"', "+ 
                     "'"+cedula.getText()+"',"+ 
                     "'"+phone.getText()+"',"+ "'"+credit.getText()+"' )";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Customer creado");
                addCustomerToTable();
                nombre.setText("");
                apellido.setText("");
                cedula.setText("");
                phone.setText("");
                credit.setText("");
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void updateClient() {
        try {
             String sqlC="UPDATE Customer SET name='"+nombre.getText()+"', lastname= '"+apellido.getText()+"', "
                     + "card_id='"+cedula.getText()+"', "
                     + "phone='"+phone.getText()+"', credit= '"+credit.getText()+"' WHERE id='"+customer_id+"'";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Actualizado");
                addCustomerToTable();
                nombre.setText("");
                apellido.setText("");
                cedula.setText("");
                phone.setText("");
                credit.setText("");
                resetButtonName();
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void resetButtonName() {
        crear.setText("Crear Cliente");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        apellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        credit = new javax.swing.JTextField();
        crear = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        customer_t = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Clientes");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Apellido:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Nombre:");

        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
            }
        });

        apellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Cedula:");

        cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cedulaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Telefono:");

        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Credito:");

        credit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditActionPerformed(evt);
            }
        });

        crear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        crear.setText("Crear Cliente");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        delete.setText("Eliminar Cliente");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        customer_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido", "Cedula", "Telefono", "Credito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customer_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customer_tMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(customer_t);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cedula, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(apellido, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombre, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(credit, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(268, 268, 268))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(crear, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(218, 218, 218))))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(credit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(683, 683, 683)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customer_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_tMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_customer_tMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        if(customer_id >= 0) {
            try {
                String sqlC="DELETE FROM Customer WHERE id='"+customer_id+"' ";
                PreparedStatement insert_user = cn.prepareStatement(sqlC);
                int n = insert_user.executeUpdate();
                if(n > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado");
                    nombre.setText("");
                    apellido.setText("");
                    cedula.setText("");
                    phone.setText("");
                    credit.setText("");
                    addCustomerToTable();
                }
            } catch(SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }
    }//GEN-LAST:event_deleteActionPerformed

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        if(nombre.getText().equals("") || apellido.getText().equals("")
            || cedula.getText().equals("")
            || phone.getText().equals("") || credit.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Los campos deben tener caracteres");
            return;
        } else {
        }
        customerAction();
    }//GEN-LAST:event_crearActionPerformed

    private void creditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditActionPerformed
        if(credit.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Credito esta en blanco");
            credit.requestFocus();
        }
    }//GEN-LAST:event_creditActionPerformed

    private void phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneActionPerformed
        if(phone.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Telefono esta en blanco");
            phone.requestFocus();
            return;
        }
        credit.requestFocus();
    }//GEN-LAST:event_phoneActionPerformed

    private void cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulaActionPerformed
        if(cedula.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Cedula esta en blanco");
            cedula.requestFocus();
            return;
        }
        phone.requestFocus();
    }//GEN-LAST:event_cedulaActionPerformed

    private void apellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoActionPerformed
        if(apellido.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Apellido esta en blanco");
            apellido.requestFocus();
            return;
        }
        cedula.requestFocus();
    }//GEN-LAST:event_apellidoActionPerformed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        if(nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nombre esta en blanco");
            nombre.requestFocus();
            return;
        }
        apellido.requestFocus();
    }//GEN-LAST:event_nombreActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new customer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellido;
    private javax.swing.JTextField cedula;
    private javax.swing.JButton crear;
    private javax.swing.JTextField credit;
    private javax.swing.JTable customer_t;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField phone;
    // End of variables declaration//GEN-END:variables
}
