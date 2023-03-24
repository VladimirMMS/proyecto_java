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

public final class typeArticle extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    int articule_type_id;
    public typeArticle() {
        initComponents();
        nombre_type.requestFocus();
        addArticuleToTbale();
    }
    void addArticuleToTbale() {
        DefaultTableModel table = (DefaultTableModel) type_art_t.getModel();
        String [] registros = new String[2];
        table.getDataVector().clear();
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name FROM Articule_Type";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("name");
                table.addRow(registros);
            }     
            type_art_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void adminArticuleType() {
        if(crear.getText().equals("Crear Tipo de Articulo")) {
            crearProductType();
            return; 
        }
        updateTypeArticule();
    }
    
    void crearProductType() {
        try {
             String sqlC="INSERT INTO Articule_Type(name) " +"VALUES("+"'"+nombre_type.getText()+"')";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                addArticuleToTbale();
                JOptionPane.showMessageDialog(null, "Tipo de Articulo creado");   
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void updateTypeArticule() {
        try {
             String sqlC="UPDATE Articule_Type SET name='"+nombre_type.getText()+"' WHERE id='"+articule_type_id+"'";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Tipo Articulo Actualizado");
                addArticuleToTbale();
                nombre_type.setText("");
                resetNameButton();
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void resetNameButton() {
        crear.setText("Crear Tipo de Articulo");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nombre_type = new javax.swing.JTextField();
        crear = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        type_art_t = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setText("Tipos de Articulo");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Nombre del tipo de articulo:");

        nombre_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombre_typeActionPerformed(evt);
            }
        });

        crear.setText("Crear Tipo de Articulo");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });

        eliminar.setText("Eliminar Tipo de Articulo");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(nombre_type, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(crear, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_type, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        type_art_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre del tipo de articulo"
            }
        ));
        type_art_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                type_art_tMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(type_art_t);
        if (type_art_t.getColumnModel().getColumnCount() > 0) {
            type_art_t.getColumnModel().getColumn(0).setMinWidth(150);
            type_art_t.getColumnModel().getColumn(0).setPreferredWidth(150);
            type_art_t.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(650, 650, 650)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(475, 475, 475)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombre_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombre_typeActionPerformed
        //
    }//GEN-LAST:event_nombre_typeActionPerformed

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        if(nombre_type.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nombre esta en blanco");
            return;
        }
        adminArticuleType();
    }//GEN-LAST:event_crearActionPerformed

    private void type_art_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_type_art_tMouseClicked
        int fila=type_art_t.getSelectedRow();
        if(fila>=0) {
            articule_type_id= Integer.parseInt((String) type_art_t.getValueAt(fila, 0));
            nombre_type.setText(type_art_t.getValueAt(fila, 1).toString());
            crear.setText("Actualizar Tipo de Articulo");
        }
    }//GEN-LAST:event_type_art_tMouseClicked

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if(articule_type_id > 0) {
            try {
             String sqlC="DELETE FROM Articule_Type WHERE id='"+articule_type_id+"' ";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Tipo de Articulo eliminado");
                addArticuleToTbale();
                nombre_type.setText("");
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
              
        }
    }//GEN-LAST:event_eliminarActionPerformed

    public static void main(String args[]) {
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new typeArticle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton crear;
    private javax.swing.JButton eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombre_type;
    private javax.swing.JTable type_art_t;
    // End of variables declaration//GEN-END:variables
}
