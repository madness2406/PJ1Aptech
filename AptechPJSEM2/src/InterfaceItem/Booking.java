package InterfaceItem;

import Process.ChucNang;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Interface.MainMenu;
import Process.BookManager;
import Process.BookingManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Admin
 */
public class Booking extends javax.swing.JInternalFrame {

    private final JDesktopPane DesktopPane1;
    private final String employeeId;
    DefaultTableModel dfTableModel;
    int chucNangDaChon = ChucNang.NONE;

    /**
     * Creates new form QuanLyHoaDon
     *
     * @param employeeId
     * @param DesktopPane1
     */
    public Booking(String employeeId, JDesktopPane DesktopPane1) {
        initComponents();
        this.DesktopPane1 = DesktopPane1;
        this.employeeId = employeeId;
        retrieve();
    }

    private void retrieve(){
        txtId.setVisible(false);
        cbStatus.addItem("Mượn");
        cbStatus.addItem("Trả");
        cbStatus.addItem("Hủy");
        cbFilter.addItem("Tất cả");
        cbFilter.addItem("Ngày mượn");
        cbFilter.addItem("Ngày hẹn trả");
        cbFilter.addItem("Ngày trả");
        dfTableModel = (DefaultTableModel) tbBooking.getModel();
        DisplayData();
    }
    
    private void DisplayData() {
        tbBooking.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            TbBooking_SelectionChanged();
        });

        ReloadTaleHD();
    }
    
    void TbBooking_SelectionChanged() {
        int row = tbBooking.getSelectedRow();
        if (row >= 0) {
            String id = (String) dfTableModel.getValueAt(row, 0);
            String renderId = (String) dfTableModel.getValueAt(row, 1);
            String expriedDay = (String) dfTableModel.getValueAt(row, 4);
            String status = (String) dfTableModel.getValueAt(row, 6);
            String deposit = (String) dfTableModel.getValueAt(row, 7);
            String totalPrice = BookingManager.TotalMoney(id);
            
            txtId.setText(id);
            txtRenderId.setText(renderId.trim());
            cbStatus.setSelectedItem(status.trim());
            txtExpiredDay.setText(expriedDay.trim());
            txtDeposit.setText(deposit.trim());
            txtTotalPrice.setText(totalPrice);
        } else {
            txtTotalPrice.setText("");
            txtExpiredDay.setText("");
            txtRenderId.setText("");
            cbStatus.setSelectedItem("");
            txtDeposit.setText("");
        }
        ReloadLblIndexTbBooking();
    }
    //Lấy dữ liệu cho bảng lớp học

    void ReloadTaleHD() {
        if (BookingManager.BookingToTable(tbBooking) == false) {
            JOptionPane.showMessageDialog(null, "Lấy dữ liệu hóa đơn có lỗi", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            ReloadLblIndexTbBooking();
        }
    }

    private void LoadSearchingBookingTable(String constraint) {
        if (BookingManager.SearchBookingToTable(tbBooking, constraint) == false) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy", "Không tìm thấy", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void LoadFilterBookingTable(String column, String constraint1, String constraint2) {
        if (BookingManager.FilterBookingToTable(tbBooking, constraint1, constraint2, column) == false) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy", "Không tìm thấy", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void ReloadLblIndexTbBooking() {
        int rowSelected = tbBooking.getSelectedRow();
        int totalRow = tbBooking.getRowCount();
        lblIndexTblBooking.setText((rowSelected + 1) + "/" + totalRow);
    }

    void ReturnQuantityToBook(String bookingId) {
        ArrayList<String[]> arr = new ArrayList();
        if (BookingManager.BookBookingToList(bookingId, arr)) {
            for (int i = 0; i < arr.size(); i++) {
                QuantityToBook(arr.get(i)[0], arr.get(i)[2], arr.get(i)[1]);
            }
        }
    }

    void QuantityToBook(String bookId, String bookQuantity, String quantity){
        int _bookQuantity = Integer.parseInt(bookQuantity);
        int _quantity = Integer.parseInt(quantity);
        int sum = _bookQuantity + _quantity;
        BookManager.Edit(""+sum, bookId);
    }
    
    void SwitchMode(int chucNang) {
        chucNangDaChon = chucNang;
        switch (chucNang) {
            case ChucNang.ADD: {
                boolean trangThai = true;
                txtRenderId.setEnabled(trangThai);
                txtExpiredDay.setEnabled(trangThai);
                txtNote.setEnabled(trangThai);
                cbStatus.setEnabled(trangThai);
                txtRenderId.requestFocus();
                btnSave.setEnabled(trangThai);
                btnUpdate.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                txtRenderId.setText("");
                txtExpiredDay.setText("");
                txtDeposit.setText("");
                btnAdd.setText("Hủy");
                break;
            }
            case ChucNang.UPDATE: {
                boolean trangThai = true;
                txtRenderId.setEnabled(trangThai);
                txtExpiredDay.setEnabled(trangThai);
                cbStatus.setEnabled(false);
                txtNote.setEnabled(trangThai);
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(!trangThai);
                btnDelete.setEnabled(!trangThai);
                btnUpdate.setText("Hủy");
                break;
            }
            case ChucNang.NONE: {
                boolean trangThai = false;
                txtRenderId.setEnabled(trangThai);
                txtExpiredDay.setEnabled(trangThai);
                txtNote.setEnabled(trangThai);
                cbStatus.setEnabled(true);
                txtTotalPrice.setEnabled(trangThai);
                btnSave.setEnabled(trangThai);
                btnAdd.setEnabled(true);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnAdd.setText("Thêm");
                btnUpdate.setText("Cập nhật");
            }
        }
    }

    boolean CheckInput() {
        String renderId = txtRenderId.getText().trim();
        String day = txtExpiredDay.getText().trim();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 

        if (renderId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập id người mượn", "Chưa nhập id người mượn", JOptionPane.WARNING_MESSAGE);
            txtRenderId.requestFocus();
            return false;
        }
        if (day.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ngày trả", "Chưa nhập ngày trả", JOptionPane.WARNING_MESSAGE);
            txtExpiredDay.requestFocus();
            return false;
        }
        
        if(day.compareTo(dtf.format(now)) < 0){
            JOptionPane.showMessageDialog(null, "Ngày trả phải sau ngày " + dtf.format(now), "Nhập ngày trả lỗi", JOptionPane.WARNING_MESSAGE);
            txtExpiredDay.requestFocus();
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        txtRenderId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblIndexTblBooking = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        txtTotalPrice = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbBooking = new javax.swing.JTable();
        btnLast = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnquaylai = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtDeposit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtExpiredDay = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        btnDetail = new javax.swing.JButton();
        btnChangeTracking = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtStartDate = new javax.swing.JTextField();
        txtEndDate = new javax.swing.JTextField();
        cbFilter = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        txtRenderId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRenderId.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tiền cọc");

        lblIndexTblBooking.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblIndexTblBooking.setText("0/0");

        btnNext.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtTotalPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotalPrice.setEnabled(false);
        txtTotalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalPriceKeyReleased(evt);
            }
        });

        tbBooking.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Người mượn", "Thủ thư", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Trạng thái", "Tiền cọc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbBooking);
        if (tbBooking.getColumnModel().getColumnCount() > 0) {
            tbBooking.getColumnModel().getColumn(0).setMinWidth(50);
            tbBooking.getColumnModel().getColumn(0).setMaxWidth(50);
            tbBooking.getColumnModel().getColumn(1).setMinWidth(100);
            tbBooking.getColumnModel().getColumn(1).setMaxWidth(100);
            tbBooking.getColumnModel().getColumn(2).setMinWidth(100);
            tbBooking.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        btnLast.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã người mượn");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Trạng thái");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Danh sách");

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

        btnquaylai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnquaylai.setText("Trở lại");
        btnquaylai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setText("Sửa thông tin");
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

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Trị giá");

        txtDeposit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDeposit.setEnabled(false);
        txtDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepositActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Ngày hẹn trả");

        txtExpiredDay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtExpiredDay.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Mượn - Trả sách");

        txtId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtId.setActionCommand("<Not Set>");
        txtId.setEnabled(false);
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdKeyReleased(evt);
            }
        });

        txtNote.setColumns(20);
        txtNote.setRows(5);
        txtNote.setEnabled(false);
        jScrollPane1.setViewportView(txtNote);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ghi chú");

        btnDetail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDetail.setText("Nhập chi tiết");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        btnChangeTracking.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnChangeTracking.setText("Trả / Hủy");
        btnChangeTracking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeTrackingActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFilterItemStateChanged(evt);
            }
        });

        jLabel3.setText("Ngày bắt đầu");

        jLabel10.setText("Ngày kết thúc");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(jLabel5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(154, 154, 154)
                                        .addComponent(btnChangeTracking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDeposit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTotalPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtRenderId, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(btnFirst)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(btnBack))
                                                .addComponent(jLabel3))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(20, 20, 20)
                                                    .addComponent(lblIndexTblBooking)
                                                    .addGap(24, 24, 24)
                                                    .addComponent(btnNext)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(btnLast))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGap(6, 6, 6)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(156, 156, 156))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtExpiredDay, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)
                                .addGap(11, 11, 11))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnquaylai, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel4)
                        .addGap(284, 284, 284)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(47, 47, 47)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(250, 250, 250))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnSave)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(btnquaylai)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRenderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtExpiredDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(btnChangeTracking))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(cbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnBack)
                    .addComponent(btnLast)
                    .addComponent(lblIndexTblBooking)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbBooking.getSelectedRow();
        if (rowSelected < tbBooking.getRowCount() - 1) {
            rowSelected++;
            tbBooking.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (tbBooking.getRowCount() > 0) {
            int lastRowIndex = tbBooking.getRowCount() - 1;
            tbBooking.getSelectionModel().setSelectionInterval(lastRowIndex,
                    lastRowIndex);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (tbBooking.getRowCount() > 0) {
            tbBooking.getSelectionModel().setSelectionInterval(0, 0);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        int rowSelected = tbBooking.getSelectedRow();
        if (rowSelected > 0) {
            rowSelected--;
            tbBooking.getSelectionModel().setSelectionInterval(rowSelected,
                    rowSelected);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        exit();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (chucNangDaChon == ChucNang.NONE) {
            SwitchMode(ChucNang.ADD);
        } else {
            SwitchMode(ChucNang.NONE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String renderId = txtRenderId.getText().trim();
        String day = txtExpiredDay.getText().trim();
        String status = cbStatus.getSelectedItem().toString();
        String totalPrice = txtTotalPrice.getText().trim();
        if (totalPrice.length() == 0) {
            txtTotalPrice.setText("0.0");
        }
        String note = txtNote.getText().trim();
        if (chucNangDaChon == ChucNang.ADD) {
            if (CheckInput() == false) {
                return;
            }
            if (BookingManager.Count("student", "Id", renderId) == 0) {
                txtRenderId.requestFocus();
                JOptionPane.showMessageDialog(null, "Người mượn không tồn tại", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (BookingManager.Count("booking", "RenderId", renderId, "Status", "Mượn") > 0) {
                txtRenderId.requestFocus();
                JOptionPane.showMessageDialog(null, "Người mượn chưa hoàn thành lần mượn trước", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (BookingManager.Add(renderId, String.valueOf(employeeId), day, status, note)) {
                btnAdd.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTaleHD();
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
            if (BookingManager.Edit(renderId, day, note)) {
                btnUpdate.requestFocus();
                SwitchMode(ChucNang.NONE);
                ReloadTaleHD();
                JOptionPane.showMessageDialog(null, "Sửa thành công",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Sửa thất bại", "Có lỗi ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (tbBooking.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn để sửa",
                    "Chưa chọn hóa đơn", JOptionPane.WARNING_MESSAGE);
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
        int selectedRow = tbBooking.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hóa đơn nào để xóa", "Chưa chọn hóa đơn", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa hóa đơn này không", "Xóa hóa đơn?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        String bookingId = (String) tbBooking.getValueAt(selectedRow, 0);
        if (BookingManager.Delete(bookingId)) {
            btnAdd.requestFocus();
            SwitchMode(ChucNang.NONE);
            ReloadTaleHD();
            JOptionPane.showMessageDialog(null, "Xóa thành công",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Có lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepositActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepositActionPerformed

    private void txtTotalPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalPriceKeyReleased
        String totalPrice = txtTotalPrice.getText().trim();
        if (totalPrice.equals("0.0"))
            txtDeposit.setText("0.0");
        else
            txtDeposit.setText(BookingManager.TotalDeposits(Float.parseFloat(totalPrice), 30));
    }//GEN-LAST:event_txtTotalPriceKeyReleased

    private void txtIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyReleased
        String id = txtId.getText().trim();
        if (id.length() > 0)
            txtTotalPrice.setText(BookingManager.TotalMoney(id));
        else
            txtTotalPrice.setText("0.0");
    }//GEN-LAST:event_txtIdKeyReleased

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        String bookingId = txtId.getText().trim();
        if (bookingId.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn", "Chưa chọn hóa đơn", JOptionPane.WARNING_MESSAGE);
            tbBooking.requestFocus();
        } else {
            BookingDetail bookingDetail = new BookingDetail(bookingId);
            DesktopPane1.add(bookingDetail);
            this.setVisible(false);
            bookingDetail.setVisible(true);
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnChangeTrackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeTrackingActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(null, "Lưu thay đổi không?", "Thay đổi?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        String id = txtId.getText().trim();
        String status = cbStatus.getSelectedItem().toString().trim();
        String statusDb = BookingManager.GetData("booking", "Status", "BookingId", id);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        if (id.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn", "Chưa chọn hóa đơn", JOptionPane.WARNING_MESSAGE);
            tbBooking.requestFocus();
        } else {
            if((status.equals("Trả")||status.equals("Hủy")) && statusDb.equals("Mượn")){
                if (BookingManager.Edit(status, dtf.format(now))) {
                    ReturnQuantityToBook(id);
                    ReloadTaleHD();
                    JOptionPane.showMessageDialog(null, "Sửa thành công",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa thất bại", "Có lỗi ", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Không thể dùng lại hóa đơn cũ", "Sửa thất bại", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnChangeTrackingActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String constraint = txtSearch.getText().trim();
        LoadSearchingBookingTable(constraint);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cbFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFilterItemStateChanged
        String startDate = txtStartDate.getText().trim();
        String endDate = txtEndDate.getText().trim();
        String column = cbFilter.getSelectedItem().toString();
        String columnName = "";

        if(column.equals("Ngày mượn"))
            columnName = "DateOfRent";
        if(column.equals("Ngày hẹn trả"))
            columnName = "ExpiredDay";
        if(column.equals("ReturnDate"))
            columnName = "Tất cả";
        
        LoadFilterBookingTable(columnName,startDate, endDate);
    }//GEN-LAST:event_cbFilterItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnChangeTracking;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnquaylai;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIndexTblBooking;
    private javax.swing.JTable tbBooking;
    private javax.swing.JTextField txtDeposit;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtExpiredDay;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtRenderId;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStartDate;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables

}
