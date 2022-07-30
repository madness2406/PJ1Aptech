/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceItem;

import Process.ChucNang;
import Process.DatabaseManager;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Interface.*;

/**
 *
 * @author Admin
 */
public class Book extends javax.swing.JInternalFrame {

    DefaultTableModel dfTableModel;
    int chucNangDaChon = ChucNang.NONE;
    

    /**
     * Creates new form HangHoa1
     */
    public Book() {
        initComponents();
        dfTableModel  = (DefaultTableModel) tbHangHoa.getModel();
    }

    void TbHangHoa_SelectionChanged() {
        int row = tbHangHoa.getSelectedRow();
        if (row >= 0) {
            String mahh = (String) dfTableModel.getValueAt(row, 0);
            String tenhh = (String) dfTableModel.getValueAt(row, 1);
            String quicach = (String) dfTableModel.getValueAt(row, 2);
            String donvitinh = (String) dfTableModel.getValueAt(row, 3);
            String dongia = (String) dfTableModel.getValueAt(row, 4);
            txtmahh.setText(mahh.trim());
            txttenhh.setText(tenhh.trim());
            txtquicach.setText(quicach.trim());
            txtdvi.setText(donvitinh.trim());
            txtdongia.setText(dongia.trim());
        } else {

            txtmahh.setText("");
            txttenhh.setText("");
            txtquicach.setText("");
            txtdvi.setText("");
            txtdongia.setText("");
        }
        ReloadLblIndexTBHanghoa();
    }

    void ReloadTaleHangHoa() {
        if (DatabaseManager.HangHoaToTable(tbHangHoa) == false) {
            JOptionPane.showMessageDialog(null, "Lấy dữ liệu khách hàng có lỗi", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }

    }

    void ReloadLblIndexTBHanghoa() {
        int rowSelected = tbHangHoa.getSelectedRow();
        int totalRow = tbHangHoa.getRowCount();
        lblIndexTbHanghoa.setText((rowSelected + 1) + "/" + totalRow);
    }

    void SwitchMode(int chucNang) {
        chucNangDaChon = chucNang;
        switch (chucNang) {
            case ChucNang.ADD: {
                boolean trangThai = true;
                txttenhh.setEnabled(trangThai);
                txtquicach.setEnabled(trangThai);
                txtdvi.setEnabled(trangThai);
                txtdongia.setEnabled(trangThai);
                txtmahh.setEnabled(trangThai);
                txtmahh.requestFocus();
                btnluu.setEnabled(trangThai);
                btnsua.setEnabled(!trangThai);
                btnxoa.setEnabled(!trangThai);
                txtmahh.setText("");
                txttenhh.setText("");
                txtquicach.setText("");
                txtdvi.setText("");
                txtdongia.setText("");
                btnthem.setText("Hủy");
                break;
            }
            case ChucNang.UPDATE: {
                boolean trangThai = true;
                txttenhh.setEnabled(trangThai);
                txtquicach.setEnabled(trangThai);
                txtdvi.setEnabled(trangThai);
                txtdongia.setEnabled(trangThai);
                txttenhh.requestFocus();
                btnluu.setEnabled(trangThai);
                btnthem.setEnabled(!trangThai);
                btnxoa.setEnabled(!trangThai);
                btnsua.setText("Hủy");
                break;
            }
            case ChucNang.NONE: {
                boolean trangThai = false;
                txtmahh.setEnabled(trangThai);
                txttenhh.setEnabled(trangThai);
                txtquicach.setEnabled(trangThai);
                txtdvi.setEnabled(trangThai);
                txtdongia.setEnabled(trangThai);
                btnluu.setEnabled(trangThai);
                btnthem.setEnabled(true);
                btnsua.setEnabled(true);
                btnxoa.setEnabled(true);
                btnthem.setText("Thêm");
                btnsua.setText("Sửa");
            }
        }
    }

    boolean CheckInput1() {
        String mahh = txtmahh.getText().trim();
        String tenhh = txttenhh.getText().trim();
        String quicach = txtquicach.getText().trim();
        String Donvi = txtdvi.getText().trim();
        String Dongia = txtdongia.getText().trim();
        if (mahh.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hàng hoá", "Chưa nhập mã hàng hoá", JOptionPane.WARNING_MESSAGE);
            txtmahh.requestFocus();
            return false;
        }
        if (tenhh.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hàng hoá", "Chưa nhập tên hàng hoá", JOptionPane.WARNING_MESSAGE);
            txttenhh.requestFocus();
            return false;
        }
        if (quicach.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập qui cách", "Chưa nhập qui cách",
                    JOptionPane.WARNING_MESSAGE);
            txtquicach.requestFocus();
            return false;
        }
        if (Donvi.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn vị", "Chưa nhập don vi",
                    JOptionPane.WARNING_MESSAGE);
            txtdvi.requestFocus();
            return false;
        }
        if (Dongia.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá", "Chưa nhập đơn giá",
                    JOptionPane.WARNING_MESSAGE);
            txtdongia.requestFocus();
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
        tbHangHoa = new javax.swing.JTable();
        txtmahh = new javax.swing.JTextField();
        txttenhh = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        txtquicach = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        txtdvi = new javax.swing.JTextField();
        btnLast = new javax.swing.JButton();
        txtdongia = new javax.swing.JTextField();
        lblIndexTbHanghoa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnexit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnluu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnsua = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnHienthi = new javax.swing.JButton();

        tbHangHoa.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tbHangHoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HH", "Tên HH", "Quy cách", "Đơn vị", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(tbHangHoa);

        txtmahh.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtmahh.setEnabled(false);

        txttenhh.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txttenhh.setEnabled(false);

        btnBack.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnBack.setText("<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        txtquicach.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtquicach.setEnabled(false);

        btnNext.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtdvi.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtdvi.setEnabled(false);

        btnLast.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        txtdongia.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtdongia.setEnabled(false);

        lblIndexTbHanghoa.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblIndexTbHanghoa.setText("0/0");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin Sách");

        btnthem.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel2.setText("Mã hàng hoá");

        btnxoa.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnxoa.setText("Xoá");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setText("Tên hàng hoá");

        btnexit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnexit.setText("Quay lại");
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("Quy cách");

        btnluu.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Đơn vị tính");

        btnsua.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setText("Đơn giá");

        btnHienthi.setText("Hiển thị");
        btnHienthi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienthiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(366, 366, 366))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel2))
                                                .addGap(34, 34, 34))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(43, 43, 43)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtdvi, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtmahh)
                                                .addComponent(txttenhh)
                                                .addComponent(txtquicach, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(106, 106, 106)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnexit, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnluu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHienthi, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack)
                        .addGap(20, 20, 20)
                        .addComponent(lblIndexTbHanghoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnHienthi))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnthem)
                    .addComponent(txtmahh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txttenhh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtquicach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtdvi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnluu))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnexit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnBack)
                    .addComponent(btnLast)
                    .addComponent(lblIndexTbHanghoa))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbHangHoa.getSelectedRow();
        if (rowSelected > 0) {
            rowSelected--;
            tbHangHoa.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbHangHoa.getSelectedRow();
        if (rowSelected < tbHangHoa.getRowCount() - 1) {
            rowSelected++;
            tbHangHoa.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (tbHangHoa.getRowCount() > 0) {
            int lastRowIndex = tbHangHoa.getRowCount() - 1;
            tbHangHoa.getSelectionModel().setSelectionInterval(lastRowIndex,
                    lastRowIndex);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        if (chucNangDaChon == ChucNang.NONE) {
            SwitchMode(ChucNang.ADD);
        } else {
            SwitchMode(ChucNang.NONE);
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (tbHangHoa.getRowCount() > 0) {
            tbHangHoa.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        int selectedRow = tbHangHoa.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hàng hoá nào để xóa", "Chưa chọn hàng hoá", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa hàng hàng hoá này không", "Xoá hàng hoá?",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        String mahh = (String) tbHangHoa.getValueAt(selectedRow, 0);
        if (DatabaseManager.Count("Chitiethoadon", "mahh", mahh) > 0) {
            JOptionPane.showMessageDialog(null, "Đã có  hàng hoá trong hoá đơn này!", "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (DatabaseManager.XoaHangHoa(mahh)) {
            btnthem.requestFocus();
            SwitchMode(ChucNang.NONE);
            ReloadTaleHangHoa();
            JOptionPane.showMessageDialog(null, "Xóa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        this.dispose();
       
    }//GEN-LAST:event_btnexitActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        String mahh = txtmahh.getText().trim();
        String tenhh = txttenhh.getText().trim();
        String quicach = txtquicach.getText().trim();
        String donvi = txtdvi.getText().trim();
        String dongia = txtdongia.getText().trim();

        //float dongia =Float.parseFloat(txtdongia.getText().trim()) ;
        if (chucNangDaChon == ChucNang.ADD) {
            if (CheckInput1() == false) {
                return;
            }
            if (DatabaseManager.Count("Hanghoa", "mahh", mahh) > 0) {
                txtmahh.requestFocus();
                JOptionPane.showMessageDialog(null, "Mã hàng hoá bạn nhập đã tồn tại trong csdl", "Trùng mã", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (DatabaseManager.ThemHangHoa(mahh, tenhh, quicach, donvi, dongia)) {
                btnthem.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTaleHangHoa();
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
            if (DatabaseManager.SuaHangHoa(mahh, tenhh, quicach, donvi, dongia)) {
                btnsua.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTaleHangHoa();
                JOptionPane.showMessageDialog(null, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Sửa thất bại", "Có lỗi ", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_btnluuActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        if (tbHangHoa.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Chưa chọn Hàng hoá để sửa", "Chưa chọn hàng hoá", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (chucNangDaChon == ChucNang.NONE)
            SwitchMode(ChucNang.UPDATE);
        else
            SwitchMode(ChucNang.NONE);
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnHienthiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienthiActionPerformed
        tbHangHoa.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                TbHangHoa_SelectionChanged();
            }
        });
        ReloadTaleHangHoa();
    }//GEN-LAST:event_btnHienthiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnHienthi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIndexTbHanghoa;
    private javax.swing.JTable tbHangHoa;
    private javax.swing.JTextField txtdongia;
    private javax.swing.JTextField txtdvi;
    private javax.swing.JTextField txtmahh;
    private javax.swing.JTextField txtquicach;
    private javax.swing.JTextField txttenhh;
    // End of variables declaration//GEN-END:variables
}
