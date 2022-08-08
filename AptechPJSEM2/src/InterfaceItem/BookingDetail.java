/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceItem;

import Process.BookingDetailManager;
import Process.ChucNang;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class BookingDetail extends javax.swing.JInternalFrame {

    DefaultTableModel tbmBookingDetail;
    DefaultTableModel tbmBook;
    int chucNangDaChon = ChucNang.NONE;
    String bookingId;

    /**
     * Creates new form ChiTietHoaDon
     *
     * @param bookingId
     */
    public BookingDetail(String bookingId) {
        initComponents();
        this.bookingId = bookingId;
        retrieve();
    }

    private void retrieve() {
        tbmBookingDetail = (DefaultTableModel) tblBookingDetailList.getModel();
        tbmBook = (DefaultTableModel) tbBook.getModel();
        LoadBookTable();
        tbBook.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            TblBook_SelectionChanged();
        });
    }

    void TblBook_SelectionChanged() {
        int row = tbBook.getSelectedRow();
        if (row >= 0) {
            String bookId = (String) tbmBook.getValueAt(row, 0);
            String bookName = (String) tbmBook.getValueAt(row, 1);
            String author = (String) tbmBook.getValueAt(row, 2);
            String totalQuantity = BookingDetailManager.GetData("book", "Quantity", "BookId", bookId);
            String price = BookingDetailManager.GetData("book", "Price", "BookId", bookId);
            txtBookId.setText(bookId.trim());
            txtBookName.setText(bookName.trim());
            txtAuthor.setText(author.trim());
            txtBookQuantity.setText(totalQuantity);
            txtBookPrice.setText(price);
        } else {
            txtBookId.setText("");
            txtBookName.setText("");
            txtAuthor.setText("");
            txtBookQuantity.setText("");
            txtBookPrice.setText("");
        }
    }

    void TblBookingDetail_SelectionChanged() {
        int row = tblBookingDetailList.getSelectedRow();

        if (row >= 0) {
            String bookId = (String) tbmBookingDetail.getValueAt(row, 0);
            String bookName = (String) tbmBookingDetail.getValueAt(row, 1);
            String quantity = (String) tbmBookingDetail.getValueAt(row, 2);
            String totalPrice = (String) tbmBookingDetail.getValueAt(row, 3);
            String totalQuantity = BookingDetailManager.GetData("book", "Quantity", "BookId", bookId);
            String author = BookingDetailManager.GetData("book", "Author", "BookId", bookId);
            String price = BookingDetailManager.GetData("book", "Price", "BookId", bookId);
            txtBookId.setText(bookId.trim());
            txtBookName.setText(bookName.trim());
            txtAuthor.setText(author.trim());
            txtBookQuantity.setText(totalQuantity);
            txtQuantity.setText(quantity);
            txtBookPrice.setText(price);
            txtTotalPrice.setText(totalPrice);
        } else {
            txtBookId.setText("");
            txtBookName.setText("");
            txtAuthor.setText("");
            txtBookQuantity.setText("");
            txtQuantity.setText("");
            txtBookPrice.setText("");
            txtTotalPrice.setText("");
        }
    }
    //Lấy dữ liệu cho bảng

    void ReloadTableBookingDetail() {
        if (BookingDetailManager.BookingDetailToTable(tblBookingDetailList, bookingId) == false) {
            JOptionPane.showMessageDialog(null, "Lấy dữ liệu chi tiết có lỗi", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            ReloadLblIndexTblBookingDetail();
        }
    }

    void LoadBookTable() {
        if (BookingDetailManager.BookToTable(tbBook) == false) {
            JOptionPane.showMessageDialog(null, "Lấy dữ liệu chi tiết có lỗi", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }
    }

    void LoadSearchingBookTable(String constraint) {
        if (BookingDetailManager.SearchBookToTable(tbBook, constraint) == false) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tên sách vừa nhập", "Không tìm thấy", JOptionPane.ERROR_MESSAGE);
        }
    }

    void ReloadLblIndexTblBookingDetail() {
        int rowSelected = tblBookingDetailList.getSelectedRow();
        int totalRow = tblBookingDetailList.getRowCount();
        lblIndexTblBookingDetail.setText((rowSelected + 1) + "/" + totalRow);
    }

    void SwitchMode(int chucNang) {
        chucNangDaChon = chucNang;
        switch (chucNang) {
            case ChucNang.ADD: {
                boolean trangThai = true;
                tbBook.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                txtQuantity.requestFocus();
                btnSave.setEnabled(trangThai);
                btnUpdate.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                txtQuantity.setText("");
                btnAdd.setText("Hủy");
                break;
            }
            case ChucNang.UPDATE: {
                boolean trangThai = true;
                tbBook.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                txtQuantity.requestFocus();
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                btnUpdate.setText("Hủy");
                break;
            }
            case ChucNang.NONE: {
                boolean trangThai = false;
                tbBook.setEnabled(trangThai);
                txtBookId.setEnabled(trangThai);
                txtQuantity.setEnabled(trangThai);
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(true);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnAdd.setText("Thêm");
                btnUpdate.setText("Sửa");
            }
        }
    }

    boolean CheckInput() {
        String bookId = txtBookId.getText().trim();
        String quantity = txtQuantity.getText().trim();
        if (bookId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách cần thêm", "Chưa chọn sách", JOptionPane.WARNING_MESSAGE);
            tbBook.requestFocus();
            return false;
        }
        if (quantity.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng", "Chưa nhập số lượng", JOptionPane.WARNING_MESSAGE);
            txtQuantity.requestFocus();
            return false;
        }
        return true;
    }

    void exit() {
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Thoát ?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnReturn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblIndexTblBookingDetail = new javax.swing.JLabel();
        btnDisplay = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBookingDetailList = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbBook = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtBookName = new javax.swing.JTextField();
        txtBookId = new javax.swing.JTextField();
        txtAuthor = new javax.swing.JTextField();
        txtBookQuantity = new javax.swing.JTextField();
        txtBookPrice = new javax.swing.JTextField();
        txtTotalPrice = new javax.swing.JTextField();

        btnReturn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReturn.setText("Quay lại");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số lượng mượn");

        txtQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtQuantity.setEnabled(false);
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityKeyReleased(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setText("<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Thành tiền");

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblIndexTblBookingDetail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblIndexTblBookingDetail.setText("0/0");

        btnDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDisplay.setText("Hiển thị");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        tblBookingDetailList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBookingDetailList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookingDetailList.setAutoscrolls(false);
        jScrollPane2.setViewportView(tblBookingDetailList);
        if (tblBookingDetailList.getColumnModel().getColumnCount() > 0) {
            tblBookingDetailList.getColumnModel().getColumn(0).setMinWidth(70);
            tblBookingDetailList.getColumnModel().getColumn(0).setMaxWidth(70);
            tblBookingDetailList.getColumnModel().getColumn(1).setMinWidth(300);
            tblBookingDetailList.getColumnModel().getColumn(1).setMaxWidth(300);
            tblBookingDetailList.getColumnModel().getColumn(2).setMinWidth(70);
            tblBookingDetailList.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên sách");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin chi tiết");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Mã sách");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Tác giả");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Số lượng tồn");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Đơn giá");

        tbBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Book Name", "Author"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbBook.setEnabled(false);
        jScrollPane1.setViewportView(tbBook);
        if (tbBook.getColumnModel().getColumnCount() > 0) {
            tbBook.getColumnModel().getColumn(0).setMinWidth(50);
            tbBook.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtBookName.setEnabled(false);

        txtBookId.setEnabled(false);

        txtAuthor.setEnabled(false);

        txtBookQuantity.setEnabled(false);

        txtBookPrice.setEnabled(false);

        txtTotalPrice.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtBookQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                            .addComponent(txtBookId))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBookPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnFirst)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBack)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblIndexTblBookingDetail)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNext)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnLast)
                                        .addGap(57, 57, 57)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtBookName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtBookId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtBookQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtBookPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(btnDisplay)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnSave)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirst)
                            .addComponent(btnBack)
                            .addComponent(lblIndexTblBookingDetail)
                            .addComponent(btnNext)
                            .addComponent(btnLast)
                            .addComponent(btnReturn))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        exit();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (tblBookingDetailList.getRowCount() > 0) {
            tblBookingDetailList.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        int rowSelected = tblBookingDetailList.getSelectedRow();
        if (rowSelected > 0) {
            rowSelected--;
            tblBookingDetailList.getSelectionModel().setSelectionInterval(rowSelected, rowSelected);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int rowSelected = tblBookingDetailList.getSelectedRow();
        if (rowSelected < tblBookingDetailList.getRowCount() - 1) {
            rowSelected++;
            tblBookingDetailList.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
//GEN-LAST:event_btnNextActionPerformed
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (chucNangDaChon == ChucNang.NONE) {
            SwitchMode(ChucNang.ADD);
        } else {
            SwitchMode(ChucNang.NONE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (tblBookingDetailList.getRowCount() > 0) {
            int lastRowIndex = tblBookingDetailList.getRowCount() - 1;
            tblBookingDetailList.getSelectionModel().setSelectionInterval(lastRowIndex, lastRowIndex);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String bookId = txtBookId.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String money = txtTotalPrice.getText().trim();
        String bookQuantity = txtBookQuantity.getText().trim();
        String _quantity = BookingDetailManager.GetData("bookingdetail", "Quantity", "BookId", bookId, "BookBookingId", bookingId);

        if (chucNangDaChon == ChucNang.ADD) {
            if (CheckInput() == false) {
                return;
            }
            if (BookingDetailManager.Count("bookingdetail", "BookBookingId", bookingId, "BookId", bookId) > 0) {
                tbBook.requestFocus();
                JOptionPane.showMessageDialog(null, "Sách bạn nhập đã tồn tại", "Trùng sách", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (Integer.parseInt(quantity) > Integer.parseInt(bookQuantity)) {
                tbBook.requestFocus();
                JOptionPane.showMessageDialog(null, "Số lượng mượn phải nhỏ hơn số lượng tồn kho", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (BookingDetailManager.Add(bookingId, bookId, quantity, money, bookQuantity)) {
                String _totalMoney = BookingDetailManager.TotalValue("SUM(Money)", "bookingdetail", "BookBookingId", bookingId);
                float deposit = Math.round(Float.parseFloat(_totalMoney) / 1000 / 3) * 1000;
                String _deposit = String.valueOf(deposit);
                if (BookingDetailManager.UpdateToBooking(bookingId, "Deposit", _deposit, "TotalMoney", _totalMoney)) {
                    btnAdd.requestFocus();
                }
                SwitchMode(ChucNang.NONE);
                ReloadTableBookingDetail();
                JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (chucNangDaChon == ChucNang.UPDATE) {
            if (CheckInput() == false) {
                return;
            }
            if (BookingDetailManager.Edit(bookingId, bookId, quantity, money, bookQuantity, _quantity)) {
                String _totalMoney = BookingDetailManager.TotalValue("SUM(Money)", "bookingdetail", "BookBookingId", bookingId);
                float deposit = Math.round(Float.parseFloat(_totalMoney) / 1000 / 3) * 1000;
                String _deposit = String.valueOf(deposit);
                if (BookingDetailManager.UpdateToBooking(bookingId, "Deposit", _deposit, "TotalMoney", _totalMoney)) {
                    btnUpdate.requestFocus();
                }
                SwitchMode(ChucNang.NONE);
                ReloadTableBookingDetail();
                JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Sửa thất bại", "Có lỗi ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (tblBookingDetailList.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Chưa chọn chi tiết hoá đơn để sửa", "Chưa chọn chi tiết hoá đơn", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (chucNangDaChon == ChucNang.NONE) {
            SwitchMode(ChucNang.UPDATE);
        } else {
            SwitchMode(ChucNang.NONE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblBookingDetailList.getSelectedRow();
        String quantity = txtQuantity.getText().trim();
        String bookQuantity = txtBookQuantity.getText().trim();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn mục nào để xóa", "Chưa chọn", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa mục này không", "Xóa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        String bookId = (String) tblBookingDetailList.getValueAt(selectedRow, 0);
        if (BookingDetailManager.Delete(bookingId, bookId, quantity, bookQuantity)) {
            String _totalMoney = BookingDetailManager.TotalValue("SUM(Money)", "bookingdetail", "BookBookingId", bookingId);
                float deposit = Math.round(Float.parseFloat(_totalMoney) / 1000 / 3) * 1000;
                String _deposit = String.valueOf(deposit);
                if (BookingDetailManager.UpdateToBooking(bookingId, "Deposit", _deposit, "TotalMoney", _totalMoney)) {
                    btnAdd.requestFocus();
                }
            SwitchMode(ChucNang.NONE);
            ReloadTableBookingDetail();
            JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        tblBookingDetailList.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            TblBookingDetail_SelectionChanged();
        });
        ReloadTableBookingDetail();
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String constraint = txtSearch.getText().trim();
        LoadSearchingBookTable(constraint);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyReleased
        // TODO add your handling code here:
        int quantity;
        if (txtQuantity.getText().trim().equals("")) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(txtQuantity.getText().trim());
        }

        int price = Integer.parseInt(txtBookPrice.getText().trim());
        txtTotalPrice.setText("" + (quantity * price));
    }//GEN-LAST:event_txtQuantityKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIndexTblBookingDetail;
    private javax.swing.JTable tbBook;
    private javax.swing.JTable tblBookingDetailList;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtBookId;
    private javax.swing.JTextField txtBookName;
    private javax.swing.JTextField txtBookPrice;
    private javax.swing.JTextField txtBookQuantity;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}
