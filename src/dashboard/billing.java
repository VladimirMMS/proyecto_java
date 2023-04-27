/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dashboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javaapplication1.conectar1;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class billing extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    int row_table;
    public billing() { 
        initComponents();
        getArticules();
        getEmployee();
        getCustomer();
    }
    void clear() {
        articule_b.setSelectedIndex(0);
        employe_b.setSelectedIndex(0);
        client_b.setSelectedIndex(0);
        quantity.setText("");
    }
    void chancheButtomName() {
        if(add_button.getText().equals("Agregar Articulo")) {
            add_button.setText("Actualizar Facturacion");
        }
        else {
            add_button.setText("Agregar Articulo");
        }
    }
    void getArticules() {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,name FROM Articule" +" WHERE quantity > + '"+0+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              articule_b.addItem(rs.getString("name"));
            }     
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    int getArticuleQuantity(String artname) {
        int art_quantity=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,quantity FROM Articule" +" WHERE name = + '"+artname+"'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              art_quantity=rs.getInt("quantity");
            }  
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return art_quantity;
    }
    void deleteRow() {
        DefaultTableModel table = (DefaultTableModel) billing_t.getModel();
        String row_total=table.getValueAt(row_table, 5).toString();
        float sub_to =(float) Float.parseFloat(row_total);
        float total_got= Float.parseFloat(total.getText());
       
            
        float itebis_to_money=(sub_to*Float.parseFloat((String) table.getValueAt(row_table, 3).toString()))/100;
        float total_got_t= Float.parseFloat(itebis_t.getText());
        float itebis_total = Float.parseFloat(total_con.getText());
        float totoal_from_t = Float.parseFloat((String) table.getValueAt(row_table, 5));
        total_con.setText(String.valueOf(itebis_total-(itebis_to_money+totoal_from_t)));
        total_got_t=total_got_t-itebis_to_money;
        itebis_t.setText(String.valueOf(total_got_t));
        total_got=total_got-sub_to;
        total.setText(String.valueOf(total_got));
        table.removeRow(row_table);
        chancheButtomName();
    }
    void getEmployee() {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, username FROM User";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              employe_b.addItem(rs.getString("username"));
            }     
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void getCustomer() {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name FROM Customer";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              client_b.addItem(rs.getString("name"));
            }     
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void verifyArticule() {
        int quanty = Integer.parseInt(quantity.getText());
        String item =(String) articule_b.getSelectedItem(); 
        int quantity_a = getArticuleQuantity(item);
        if(quantity_a< quanty) {
            JOptionPane.showMessageDialog(null, "La catidad de articulo que quiere agregar no la tenemos en inventario");
            JOptionPane.showMessageDialog(null, "Actualmente tenemos "+quantity_a);
            if(add_button.getText().equals("Actualizar Facturacion")) {
                add_button.setText("Agregar Facturacion");
        }
            return;
        }
        if(billing_t.getRowCount() == 0) {
            addArticule();
            return;
        }
        for (int i = 0; i < billing_t.getRowCount(); i++) {
            if(articule_b.getSelectedItem().equals(billing_t.getValueAt(i, 0))) {
                
                String quan_t=String.valueOf(billing_t.getValueAt(i, 2));
                int row_quantity = Integer.parseInt(quan_t);
                int last_quantity = quanty+row_quantity;
                billing_t.setValueAt(last_quantity, i, 2);
                float row_total;
                String price_column =(String) billing_t.getValueAt(i, 1);
                row_total = Float.parseFloat((String) price_column)*last_quantity;
                billing_t.setValueAt(row_total, i, 5);
                
                float itebis_row;
                itebis_row=Float.parseFloat((String) billing_t.getValueAt(i, 3))*last_quantity;
                billing_t.setValueAt(String.valueOf(itebis_row), i, 4);
                float total_to_float=Float.parseFloat((String) total.getText())+row_total;
                total.setText(String.valueOf(total_to_float));
                float total_itebis_to_float=Float.parseFloat((String) itebis_t.getText()); 
                float itebis_to_money=Float.parseFloat((String) price_column)*Float.parseFloat((String) billing_t.getValueAt(i, 3))/100;
                float total_mul = (total_itebis_to_float+itebis_to_money)*quanty;
                itebis_t.setText(String.valueOf(total_mul));
                
                total_con.setText(String.valueOf(total_mul+total_to_float));
                return;
            }
        }
        addArticule();
        
    }
    void addArticule() {
        DefaultTableModel table = (DefaultTableModel) billing_t.getModel();
        String [] registros = new String[6];
        float total_sum_itebis=0;
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT name,price,percent,price FROM Articule INNER JOIN Itebis WHERE Articule.name='"+articule_b.getSelectedItem()+"' AND Itebis.id=Articule.itbis_id";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
              registros[0]=(String) articule_b.getSelectedItem();
              registros[1]=rs.getString("price");
              registros[2]=quantity.getText();
              float percent_ite = Float.parseFloat((String) rs.getString("percent"));
              registros[3]=String.valueOf(percent_ite);
              int quant_int=Integer.parseInt(quantity.getText());
              registros[4]=String.valueOf(quant_int*percent_ite);
              float price_subtotal= rs.getFloat("price");
              float subtotal = price_subtotal*  quant_int;
              float total_got;
              float total_sum;
              
              registros[5]=String.valueOf(subtotal);
              if(!total.getText().equals("")) {
                  total_got=Float.parseFloat(total.getText());
                  total_sum=total_got+ subtotal;
                total.setText(String.valueOf(total_sum));
              }
              else {
                  total_sum=subtotal;
                  total.setText(String.valueOf(subtotal));
              }
              float precioXq=price_subtotal* Integer.parseInt(quantity.getText());
                
              float itebis_to_money=(precioXq*Float.parseFloat((String) rs.getString("percent")))/100;
               
              if(!itebis_t.getText().equals("")) {
         
                float total_itebis_got = Float.parseFloat(itebis_t.getText());
                total_sum_itebis= itebis_to_money+ total_itebis_got;
                itebis_t.setText(String.valueOf(total_sum_itebis));
              }
              else {
                  itebis_t.setText(String.valueOf(itebis_to_money));
                  total_sum_itebis=itebis_to_money;
              }
       
              total_con.setText(String.valueOf(total_sum_itebis+total_sum));
            }     
            table.addRow(registros);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        add_button = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        client_b = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        billing_t = new javax.swing.JTable();
        none = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        articule_b = new javax.swing.JComboBox<>();
        employe_b = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        itebis_t = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        total_con = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Seleccione el Articulo:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Cantidad:");

        quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityActionPerformed(evt);
            }
        });

        add_button.setBackground(new java.awt.Color(242, 242, 242));
        add_button.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add_button.setText("Agregar Articulo");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(242, 242, 242));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Eliminar Factura");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Vendedor:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Clientes:");

        client_b.setBackground(new java.awt.Color(242, 242, 242));

        billing_t.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        billing_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Articulo", "Precio", "Cantidad", "Itebis del Articulo", "Subtotal Itebis", "Subtotal"
            }
        ));
        billing_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billing_tMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(billing_t);

        none.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        none.setText("Total Itebis a pagar:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Total sin itebis:");

        articule_b.setBackground(new java.awt.Color(242, 242, 242));

        employe_b.setBackground(new java.awt.Color(242, 242, 242));

        jButton4.setBackground(new java.awt.Color(242, 242, 242));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("Proceder Al Pago");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        total.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        itebis_t.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Total:");

        total_con.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(204, 204, 204)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(articule_b, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(485, 485, 485)
                        .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(client_b, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employe_b, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(345, 345, 345)
                                    .addComponent(jLabel8))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(none)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(404, 404, 404)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(itebis_t, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(total_con, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(165, 165, 165))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(articule_b, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employe_b, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(none, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(itebis_t, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(client_b, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(total_con, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Facturacion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(818, 818, 818)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityActionPerformed

    }//GEN-LAST:event_quantityActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        
       if(quantity.getText().equals("")) {
           JOptionPane.showMessageDialog(null, "Los campos deben estar completados");
           return;
       }
        if(add_button.getText().equals("Actualizar Facturacion")) {
            deleteRow();
            chancheButtomName();   
        }
        verifyArticule();     
        clear();
    }//GEN-LAST:event_add_buttonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       if(quantity.getText().equals("")) {
           JOptionPane.showMessageDialog(null, "Los campos deben estar completados");
           return;
       }
        deleteRow();
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         DefaultTableModel table = (DefaultTableModel) billing_t.getModel();
         if(table.getRowCount() ==0) {
             JOptionPane.showMessageDialog(null, "Debes tener agregar articulo a la tabla");
             return;
         }
         String employe = (String) employe_b.getSelectedItem();
         String customer =(String) client_b.getSelectedItem();
        new payProcess(Float.parseFloat(total_con.getText()), 
                Float.parseFloat(itebis_t.getText()), 
                (DefaultTableModel) billing_t.getModel(), customer,employe, total.getText() ).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void billing_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billing_tMouseClicked
        int fila=billing_t.getSelectedRow();
        row_table=fila;
        if(fila>=0) {
            Object art_t =billing_t.getValueAt(fila, 0);
            articule_b.setSelectedItem(art_t);
            String art_quantity =billing_t.getValueAt(fila, 2).toString();
            quantity.setText(art_quantity);
            chancheButtomName();
        }
    }//GEN-LAST:event_billing_tMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new billing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JComboBox<String> articule_b;
    private javax.swing.JTable billing_t;
    private javax.swing.JComboBox<String> client_b;
    private javax.swing.JComboBox<String> employe_b;
    private javax.swing.JLabel itebis_t;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel none;
    private javax.swing.JTextField quantity;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total_con;
    // End of variables declaration//GEN-END:variables
}
