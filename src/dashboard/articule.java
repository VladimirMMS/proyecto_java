/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javaapplication1.ArticuleReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javaapplication1.conectar1;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public final class articule extends javax.swing.JFrame {
    conectar1 cc = new conectar1();
    Connection cn = cc.conexion();
    int article_type_id;
    int articule_itbis_id;
    int articule_id;
    public articule() {
        initComponents();
        getArticuleType();
        getItebisType();
        addArticuleToTbale();  
        nombre_art.requestFocus();
    }
    
    void doReport() {
        ArrayList list = new ArrayList();
        DefaultTableModel table = (DefaultTableModel) articule_t.getModel();
        for (int i = 0; i < table.getRowCount(); i++) {
            String nombre=(String)table.getValueAt(i, 1);
            String precio=(String)table.getValueAt(i, 2);
            String cantidad=(String)table.getValueAt(i, 3);
            String tipo=(String)table.getValueAt(i, 4);
            String itebis=(String)table.getValueAt(i, 5);
            ArticuleReport list_art = new ArticuleReport(nombre, precio, cantidad, tipo, itebis);
            list.add(list_art);
        }
        JasperReport jr;
        try {
            jr = (JasperReport) JRLoader.loadObjectFromFile("src/report/articule_print.jasper");
            HashMap param = new HashMap();
            JasperPrint jp = JasperFillManager.fillReport(jr, param, new JRBeanCollectionDataSource(list));
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            
        }
        catch(JRException ex) {
            System.out.println(ex);
        
        }
        
    }
    
    void getTypeArticuleByName(String typeName) {
        
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,name FROM Articule_Type" +" WHERE name LIKE'%"+typeName+"%'";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                search(rs.getInt("id"));
                
            }     
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void search(int typeId) {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name, price, quantity, itbis_id, articule_type_id FROM Articule"+" WHERE articule_type_id="+typeId+"";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            DefaultTableModel table = (DefaultTableModel) articule_t.getModel();
            String [] registros = new String[6];
            table.getDataVector().clear();
            while(rs.next()) {
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("name");
                registros[2]=rs.getString("price");
                registros[3]=rs.getString("quantity");
                registros[4]=rs.getString("articule_type_id");
                registros[5]=rs.getString("itbis_id");
                table.addRow(registros);  
            }
            articule_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }  
    }
    
   
    void getArticuleType() {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,name FROM Articule_Type";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                articule_type.addItem(rs.getString("name"));
                if(articule_type.getSelectedItem().equals(rs.getString("name"))) {
                    article_type_id=rs.getInt("id");        
                }
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void addArticuleToTbale() {
        DefaultTableModel table = (DefaultTableModel) articule_t.getModel();
        String [] registros = new String[6];
        table.getDataVector().clear();
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id, name, price, quantity, itbis_id, articule_type_id FROM Articule";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            while(rs.next()) {
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("name");
                registros[2]=rs.getString("price");
                registros[3]=rs.getString("quantity");
                registros[4]=rs.getString("articule_type_id");
                registros[5]=rs.getString("itbis_id");
                table.addRow(registros);
            }     
            articule_t.setModel(table);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void getItebisType() {
        try {
            Statement sqlQuery = cn.createStatement();
            String sqlC = "SELECT id,percent FROM Itebis";
            ResultSet rs = sqlQuery.executeQuery(sqlC);
            
            while(rs.next()) {
                articule_itebis.addItem(rs.getString("percent"));
                if(articule_itebis.getSelectedItem().equals(rs.getString("percent"))) {
                    articule_itbis_id=rs.getInt("id");
                }
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void createArticule() {
        getArticuleType();
        getItebisType();
        
          if(articlebuttom.getText().equals("Crear Articulo")) {
               createArticulo();
          }
          else {
              updateArticulo();
          }        
    }
    void updateArticulo() {
        
        try {
             String sqlC="UPDATE Articule SET name='"+nombre_art.getText()+"', price= '"+price_art.getText()+"', "
                     + "quantity='"+quantity_art.getText()+"', "
                     + "itbis_id='"+articule_itbis_id+"', articule_type_id= '"+article_type_id+"' WHERE id='"+articule_id+"'";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Articulo Actualizado");
                addArticuleToTbale();
                nombre_art.setText("");
                price_art.setText("");
                quantity_art.setText("");
                resetNameButton();
                cleanTable();
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void resetNameButton() {
        articlebuttom.setText("Crear Articulo");
    }
    void cleanTable() {
         String labels[] = {};
         String labels2[] = {};
         final DefaultComboBoxModel model = new DefaultComboBoxModel(labels);
         final DefaultComboBoxModel model2 = new DefaultComboBoxModel(labels2);
          articule_itebis.setModel(model);
          articule_type.setModel(model2);
          getArticuleType();
          getItebisType();
    }
    
    void createArticulo() {
         try {
             String sqlC="INSERT INTO Articule(name, price, quantity, itbis_id, articule_type_id) "+ 
                     "VALUES("+ "'"+nombre_art.getText()+"', "+ 
                     "'"+price_art.getText()+"', "+ 
                     "'"+quantity_art.getText()+"',"+ 
                     "'"+articule_itbis_id+"',"+ "'"+article_type_id+"' )";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Articulo creado");
               
                nombre_art.setText("");
                price_art.setText("");
                quantity_art.setText("");
                cleanTable();
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        quantity_art = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nombre_art = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        price_art = new javax.swing.JTextField();
        articule_itebis = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        articule_type = new javax.swing.JComboBox<>();
        articlebuttom = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        articule_t = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Nombre del Aritculo:");

        quantity_art.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantity_artActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Cantidad de Articulos:");

        nombre_art.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombre_artActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Precio del Articulo:");

        price_art.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                price_artActionPerformed(evt);
            }
        });

        articule_itebis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                articule_itebisActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Itebis del Aritculo:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tipo de Aritculo:");

        articule_type.setToolTipText("");
        articule_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                articule_typeActionPerformed(evt);
            }
        });

        articlebuttom.setText("Crear Articulo");
        articlebuttom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                articlebuttomMouseClicked(evt);
            }
        });
        articlebuttom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                articlebuttomActionPerformed(evt);
            }
        });

        deleteButton.setText("Eliminar Articulo");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        articule_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Cantidad", "Tipo", "Itebis"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        articule_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                articule_tMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(articule_t);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Buscar por tipo:");

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                searchPropertyChange(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        jButton1.setText("Crear Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombre_art, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(quantity_art)
                            .addComponent(price_art))
                        .addGap(174, 174, 174)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(articlebuttom, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(articule_type, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(articule_itebis, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(361, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre_art, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(articule_type, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(price_art, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(articule_itebis, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantity_art, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(articlebuttom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Articulos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(622, 622, 622))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quantity_artActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantity_artActionPerformed
        if(quantity_art.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Cantidad esta en blanco");
            quantity_art.requestFocus();
            return;
        }
        articule_type.requestFocus();
    }//GEN-LAST:event_quantity_artActionPerformed

    private void nombre_artActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombre_artActionPerformed
        if(nombre_art.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nombre esta en blanco");
            nombre_art.requestFocus();
            return;
        }
        price_art.requestFocus();
    }//GEN-LAST:event_nombre_artActionPerformed

    private void price_artActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_price_artActionPerformed
        if(price_art.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "precio esta en blanco");
            price_art.requestFocus();
            return;
        }
        quantity_art.requestFocus();
    }//GEN-LAST:event_price_artActionPerformed

    private void articule_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_articule_typeActionPerformed
        if(articule_type.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "precio esta en blanco");
            price_art.requestFocus();
            return;
        }
        articule_itebis.requestFocus();
    }//GEN-LAST:event_articule_typeActionPerformed

    private void articule_itebisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_articule_itebisActionPerformed
        if(articule_itebis.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "precio esta en blanco");
            articule_itebis.requestFocus();
        }
    }//GEN-LAST:event_articule_itebisActionPerformed

    private void articlebuttomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_articlebuttomActionPerformed

        if(nombre_art.getText().equals("") || price_art.getText().equals("") 
                || quantity_art.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Los campos deben tener caracteres");
            return;
        }
        createArticule();
        addArticuleToTbale();
    }//GEN-LAST:event_articlebuttomActionPerformed

    private void articule_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_articule_tMouseClicked
        int fila=articule_t.getSelectedRow();
        if(fila>=0) {
            articule_id= Integer.parseInt((String) articule_t.getValueAt(fila, 0));
            int index_article_t = Integer.parseInt((String) articule_t.getValueAt(fila, 4));
            int index_article_itbis = Integer.parseInt((String) articule_t.getValueAt(fila, 5));
            nombre_art.setText(articule_t.getValueAt(fila, 1).toString());
            price_art.setText(articule_t.getValueAt(fila, 2).toString());
            quantity_art.setText(articule_t.getValueAt(fila, 3).toString());
            articule_type.setSelectedIndex(index_article_t-1);
            articule_itebis.setSelectedIndex(index_article_itbis-1);
            articlebuttom.setText("Actualizar Articulo");
        }
    }//GEN-LAST:event_articule_tMouseClicked

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if(articule_id > 0) {
            try {
             String sqlC="DELETE FROM Articule WHERE id='"+articule_id+"' ";
            PreparedStatement insert_user = cn.prepareStatement(sqlC);
            int n = insert_user.executeUpdate();
            if(n > 0) {
                JOptionPane.showMessageDialog(null, "Articulo eliminado");
                addArticuleToTbale();
                nombre_art.setText("");
                price_art.setText("");
                quantity_art.setText("");
                cleanTable();
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
              
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchActionPerformed

    private void searchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_searchPropertyChange

    }//GEN-LAST:event_searchPropertyChange

    private void articlebuttomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_articlebuttomMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_articlebuttomMouseClicked

    private void searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyPressed
        
    }//GEN-LAST:event_searchKeyPressed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
       if(search.getText().equals(" ") || search.getText().equals("")) {
           addArticuleToTbale();
           return;
       }
       getTypeArticuleByName(search.getText());
    }//GEN-LAST:event_searchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doReport();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new articule().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton articlebuttom;
    private javax.swing.JComboBox<String> articule_itebis;
    private javax.swing.JTable articule_t;
    private javax.swing.JComboBox<String> articule_type;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombre_art;
    private javax.swing.JTextField price_art;
    private javax.swing.JTextField quantity_art;
    private javax.swing.JTextField search;
    // End of variables declaration//GEN-END:variables
}
