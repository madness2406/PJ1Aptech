/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceItem;

import Process.ChucNang;
import Process.BookManager;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class Book extends javax.swing.JInternalFrame {

    DefaultTableModel dfTableModel;
    int chucNangDaChon = ChucNang.NONE;
    String employeeId;

    /**
     * Creates new form HangHoa1
     * @throws java.sql.SQLException
     */
    public Book(String employeeId) throws SQLException {
        initComponents();
        this.employeeId = employeeId;
        dfTableModel = (DefaultTableModel) tbBook.getModel();
        retrieve();
    }
    
    private void retrieve() throws SQLException {
        DefaultComboBoxModel dm = BookManager.GetCategoryId();
        txtId.setVisible(false);
        cbCategoryId.setModel(dm);
    }

    void TbHangHoa_SelectionChanged() {
        int row = tbBook.getSelectedRow();
        if (row >= 0) {
            String bookId = (String) dfTableModel.getValueAt(row, 0);
            String name = (String) dfTableModel.getValueAt(row, 1);
            String author = (String) dfTableModel.getValueAt(row, 2);
            String categoryId = (String) dfTableModel.getValueAt(row, 3);
            String publishYear = (String) dfTableModel.getValueAt(row, 4);
            String quantity = (String) dfTableModel.getValueAt(row, 5);
            String price = (String) dfTableModel.getValueAt(row, 6);
            String note = (String) dfTableModel.getValueAt(row, 7);
            txtId.setText(bookId);
            txtName.setText(name.trim());
            txtAuthor.setText(author.trim());
            txtPublishYear.setText(publishYear.trim());
            cbCategoryId.setSelectedItem(categoryId.trim());
            txtQuantity.setText(quantity.trim());
            txtPrice.setText(price.trim());
            txtNote.setText(note);
        } else {
            txtName.setText("");
            txtAuthor.setText("");
            txtNote.setText("");
            txtPublishYear.setText("");
            txtQuantity.setText("");
            txtPrice.setText("");
        }
        ReloadLblIndexTBBook();
    }

    void ReloadTableBook() {
        if (BookManager.BookToTable(tbBook) == false) {
            JOptionPane.showMessageDialog(null, "Lấy dữ liệu khách hàng có lỗi", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }

    }

    void ReloadLblIndexTBBook() {
        int rowSelected = tbBook.getSelectedRow();
        int totalRow = tbBook.getRowCount();
        lblIndexTbBook.setText((rowSelected + 1) + "/" + totalRow);
    }

    void SwitchMode(int chucNang) {
        chucNangDaChon = chucNang;
        switch (chucNang) {
            case ChucNang.ADD: {
                boolean trangThai = true;
                txtName.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                txtAuthor.setEnabled(trangThai);
                txtPrice.setEnabled(trangThai);
                txtNote.setEnabled(trangThai);
                txtPublishYear.setEnabled(trangThai);
                cbCategoryId.setEnabled(trangThai);
                txtName.requestFocus();
                btnSave.setEnabled(trangThai);
                btnEdit.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                txtName.setText("");
                txtAuthor.setText("");
                txtQuantity.setText("");
                txtPublishYear.setText("");
                txtNote.setText("");
                txtPrice.setText("");
                btnAdd.setText("Hủy");
                break;
            }
            case ChucNang.UPDATE: {
                boolean trangThai = true;
                txtName.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                txtAuthor.setEnabled(trangThai);
                txtPrice.setEnabled(trangThai);
                txtNote.setEnabled(trangThai);
                cbCategoryId.setEnabled(trangThai);
                txtPublishYear.setEnabled(trangThai);
                txtName.requestFocus();
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                btnEdit.setText("Hủy");
                break;
            }
            case ChucNang.NONE: {
                boolean trangThai = false;
                txtName.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                txtAuthor.setEnabled(trangThai);
                txtPrice.setEnabled(trangThai);
                txtNote.setEnabled(trangThai);
                cbCategoryId.setEnabled(trangThai);
                txtPublishYear.setEnabled(trangThai);
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                btnAdd.setText("Thêm");
                btnEdit.setText("Sửa");
            }
        }
    }

    boolean CheckInput1() {
        String name = txtName.getText().trim();
        String author = txtAuthor.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String publishYear = txtPublishYear.getText().trim();
        String price = txtPrice.getText().trim();
        String categoryId = String.valueOf(cbCategoryId.getSelectedItem());
        if (name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sách", "Chưa nhập tên sách",
                    JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return false;
        }
        if (author.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên tác giả", "Chưa nhập tên tác giả",
                    JOptionPane.WARNING_MESSAGE);
            txtAuthor.requestFocus();
            return false;
        }
        if (quantity.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng", "Chưa nhập số lượng",
                    JOptionPane.WARNING_MESSAGE);
            txtQuantity.requestFocus();
            return false;
        }
        if (categoryId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thể loại", "Chưa chọn thể loại",
                    JOptionPane.WARNING_MESSAGE);
            cbCategoryId.requestFocus();
            return false;
        }
        if (price.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá", "Chưa nhập đơn giá",
                    JOptionPane.WARNING_MESSAGE);
            txtPrice.requestFocus();
            return false;
        }
        if (publishYear.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập năm xuất bản", "Chưa nhập năm xuất bản",
                    JOptionPane.WARNING_MESSAGE);
            txtPublishYear.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbBook = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        txtQuantity = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtPrice = new javax.swing.JTextField();
        lblIndexTbBook = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnDisplay = new javax.swing.JButton();
        txtAuthor = new javax.swing.JTextField();
        txtPublishYear = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbCategoryId = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        txtId = new javax.swing.JTextField();

        tbBook.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tbBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Tác giả", "Năm xuất bản", "Mã thể loại", "Số lượng", "Đơn giá", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbBook);

        txtName.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtName.setEnabled(false);

        btnBack.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnBack.setText("<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        txtQuantity.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtQuantity.setEnabled(false);

        btnNext.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        txtPrice.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtPrice.setEnabled(false);

        lblIndexTbBook.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblIndexTbBook.setText("0/0");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin Sách");

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnDelete.setText("Xoá");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setText("Tên Sách");

        btnExit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnExit.setText("Quay lại");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("Số lượng");

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setText("Đơn giá");

        btnDisplay.setText("Hiển thị");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        txtAuthor.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtAuthor.setEnabled(false);

        txtPublishYear.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtPublishYear.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Tác giả");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setText("Mã thể loại");

        cbCategoryId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategoryId.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Năm xuất bản");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("Ghi chú");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane2.setViewportView(txtNote);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack)
                        .addGap(20, 20, 20)
                        .addComponent(lblIndexTbBook)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(121, 121, 121)
                                    .addComponent(jLabel4)
                                    .addGap(29, 29, 29)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAuthor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPublishYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(62, 62, 62)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(173, 173, 173)
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisplay, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(111, 111, 111))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDisplay)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit)
                            .addComponent(txtPublishYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete)
                            .addComponent(cbCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addGap(14, 14, 14)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnBack)
                    .addComponent(btnLast)
                    .addComponent(lblIndexTbBook))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbBook.getSelectedRow();
        if (rowSelected > 0) {
            rowSelected--;
            tbBook.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbBook.getSelectedRow();
        if (rowSelected < tbBook.getRowCount() - 1) {
            rowSelected++;
            tbBook.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (tbBook.getRowCount() > 0) {
            int lastRowIndex = tbBook.getRowCount() - 1;
            tbBook.getSelectionModel().setSelectionInterval(lastRowIndex,
                    lastRowIndex);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (chucNangDaChon == ChucNang.NONE) {
            SwitchMode(ChucNang.ADD);
        } else {
            SwitchMode(ChucNang.NONE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (tbBook.getRowCount() > 0) {
            tbBook.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tbBook.getSelectedRow();
        String bookId = (String) dfTableModel.getValueAt(selectedRow, 0);
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn cuốn sách nào để xóa", "Chưa chọn cuốn sách", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa cuốn sách này không", "Xoá sách?",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (BookManager.Delete1(bookId)) {
            btnAdd.requestFocus();
            SwitchMode(ChucNang.NONE);
            ReloadTableBook();
            JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
       
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String bookId = txtId.getText().trim();
        String name = txtName.getText().trim();
        String author = txtAuthor.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String price = txtPrice.getText().trim();
        String publishYear = txtPublishYear.getText().trim();
        String note = txtNote.getText().trim();
        String categoryId = String.valueOf(cbCategoryId.getSelectedItem()).trim();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        //float dongia =Float.parseFloat(txtdongia.getText().trim()) ;
        if (chucNangDaChon == ChucNang.ADD) {
            if (CheckInput1() == false) {
                return;
            }
            if (BookManager.Count("book", "Name", "Author", name, author) > 0) {
                txtName.requestFocus();
                JOptionPane.showMessageDialog(null, "Mã hàng hoá bạn nhập đã tồn tại trong csdl", "Trùng mã", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (BookManager.Add(name, author, categoryId, publishYear, quantity, price, employeeId, note)) {
                btnAdd.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTableBook();
                JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (chucNangDaChon == ChucNang.UPDATE) {
            if (CheckInput1() == false) {
                return;
            }
            if (BookManager.Edit(bookId, name, author, categoryId, publishYear, quantity, price, dtf.format(now), employeeId, note)) {
                btnEdit.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTableBook();
                JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Sửa thất bại", "Có lỗi ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (tbBook.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Chưa chọn Hàng hoá để sửa", "Chưa chọn hàng hoá", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (chucNangDaChon == ChucNang.NONE)
            SwitchMode(ChucNang.UPDATE);
        else
            SwitchMode(ChucNang.NONE);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        tbBook.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            TbHangHoa_SelectionChanged();
        });
        ReloadTableBook();
    }//GEN-LAST:event_btnDisplayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbCategoryId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIndexTbBook;
    private javax.swing.JTable tbBook;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtPublishYear;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
