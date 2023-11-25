package view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.HinhAnh;
import model.HinhDang;
import model.MauSac;
import model.SanPhamCT;
import Service.HinhAnhService;
import Service.HinhDangService;
import Service.MauSacService;
import Service.SanPhamCTService;
import Service.SanPhamService;
import model.SanPham;
import repository.Validated;

public class SanPhamChiTiet extends javax.swing.JDialog {
    
    private final SanPhamCTService service = new SanPhamCTService();
    private final SanPhamService spService = new SanPhamService();
    private final MauSacService msService = new MauSacService();
    private final HinhAnhService haService = new HinhAnhService();
    private final HinhDangService hdService = new HinhDangService();
    private int row = -1;
    private int pages = 1;
    private final int limit = 5;
    private int numberOfPages;
    private int check;
    private int canExecute = 0;
    private String maSP;
    
    public SanPhamChiTiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.row = -1;
        this.fillCbbTT();
        this.fillCbbHinhDang();
        this.fillCbbMauSac();
        this.fillCbbHinhAnh();
        this.fillCbbHinhDangFilter();
        this.fillCbbMauSacFilter();
        this.updateStatusFilter();
    }
    
    public SanPhamChiTiet(java.awt.Frame parent, boolean modal, String maSP) {
        this(parent, modal);
        
        this.maSP = maSP;
        this.fillTable();
    }
    
    private void getPages(List<SanPhamCT> list) {
        if (list.size() % limit == 0) {
            numberOfPages = list.size() / limit;
        } else {
            numberOfPages = (list.size() / limit) + 1;
        }
        
        lblPages.setText("1");
    }
    
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCT.getModel();
        model.setRowCount(0);
        
        try {
            
            List<SanPhamCT> listPage = service.selectByKeyWord(maSP);
            this.getPages(listPage);
            
            List<SanPhamCT> list = service.searchKeyWord(maSP, pages, limit);
            for (SanPhamCT spct : list) {
                model.addRow(new Object[]{
                    spct.getId(),
                    spct.getSanPham().getMa(),
                    spct.getSanPham().getTen(),
                    spct.getGia(),
                    spct.getSoLuong(),
                    spct.getMauSac().getTen(),
                    spct.getHinhDang().getTen(),
                    spct.getHinhAnh().getTen(),
                    spct.loadTrangThai()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    //Start filter---
    private void filter() {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCT.getModel();
        model.setRowCount(0);
        
        try {
            
            Double giaMin = null;
            if (!txtMin.getText().trim().isEmpty()) {
                giaMin = Double.parseDouble(txtMin.getText());
            }
            Double giaMax = null;
            if (!txtMax.getText().trim().isEmpty()) {
                giaMax = Double.parseDouble(txtMax.getText());
            }
            
            String mau = (String) cbbFilterMau.getSelectedItem();
            
            String hinhDang = (String) cbbFilterKieuDang.getSelectedItem();
            
            List<SanPhamCT> listPage = service.FilterPageByMa(giaMin, giaMax, mau, hinhDang, maSP);
            this.getPages(listPage);
            
            List<SanPhamCT> list = service.FilterDataByMa(giaMin, giaMax, mau, hinhDang, maSP, pages, limit);
            for (SanPhamCT spct : list) {
                model.addRow(new Object[]{
                    spct.getId(),
                    spct.getSanPham().getMa(),
                    spct.getSanPham().getTen(),
                    spct.getGia(),
                    spct.getSoLuong(),
                    spct.getMauSac().getTen(),
                    spct.getHinhDang().getTen(),
                    spct.getHinhAnh().getTen(),
                    spct.loadTrangThai()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }
//End filter---

    private void fillCbbTT() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTrangThai.getModel();
        model.removeAllElements();
        
        List<SanPhamCT> listCbb = service.selectAll();
        Set<String> liSet = new HashSet<>();
        
        for (SanPhamCT spct : listCbb) {
            liSet.add(spct.loadTrangThai());
        }
        
        for (String status : liSet) {
            model.addElement(status);
        }
    }
    
    private void fillCbbHinhDang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbKieuDang.getModel();
        model.removeAllElements();
        
        List<HinhDang> listCbb = hdService.selectAll();
        for (HinhDang hd : listCbb) {
            model.addElement(hd.getTen());
        }
    }
    
    private void fillCbbMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbMauSac.getModel();
        model.removeAllElements();
        
        List<MauSac> listCbb = msService.selectAll();
        for (MauSac mauSac : listCbb) {
            model.addElement(mauSac.getTen());
        }
    }
    
    private void fillCbbHinhAnh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbHinhAnh.getModel();
        model.removeAllElements();
        
        List<HinhAnh> listCbb = haService.selectAll();
        for (HinhAnh ha : listCbb) {
            model.addElement(ha.getTen());
        }
    }
    
    private void fillCbbHinhDangFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbFilterKieuDang.getModel();
        model.removeAllElements();
        model.addElement("");
        
        List<HinhDang> listCbb = hdService.selectAll();
        for (HinhDang hd : listCbb) {
            model.addElement(hd.getTen());
        }
    }
    
    private void fillCbbMauSacFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbFilterMau.getModel();
        model.removeAllElements();
        model.addElement("");
        
        List<MauSac> listCbb = msService.selectAll();
        for (MauSac mauSac : listCbb) {
            model.addElement(mauSac.getTen());
        }
    }

    //Start phân trang---
    private void firstPage() {
        pages = 1;
        if (canExecute == 1) {
            this.filter();
        } else {
            this.fillTable();
        }
        
        lblPages.setText("1");
    }
    
    private void prevPage() {
        if (pages > 1) {
            pages--;
            if (canExecute == 1) {
                this.filter();
            } else {
                this.fillTable();
            }
            
            lblPages.setText("" + pages);
        }
    }
    
    private void nextPage() {
        if (pages < numberOfPages) {
            pages++;
            if (canExecute == 1) {
                this.filter();
            } else {
                this.fillTable();
            }
            
            lblPages.setText("" + pages);
        }
    }
    
    private void lastPage() {
        pages = numberOfPages;
        if (canExecute == 1) {
            this.filter();
        } else {
            this.fillTable();
        }
        
        lblPages.setText("" + pages);
    }
//End phân trang---

    private void setDataForm(SanPhamCT spct) {
        txtGia.setText(String.valueOf(spct.getGia()));
        txtSoLuong.setText(String.valueOf(spct.getSoLuong()));
        cbbTrangThai.setSelectedItem(spct.loadTrangThai());
        cbbHinhAnh.setSelectedItem(spct.getHinhAnh().getTen());
        cbbMauSac.setSelectedItem(spct.getMauSac().getTen());
        cbbKieuDang.setSelectedItem(spct.getHinhDang().getTen());
    }
    
    private void editForm() {
        Integer id = (Integer) tblSanPhamCT.getValueAt(row, 0);
        SanPhamCT spct = service.selectById(id);
        this.setDataForm(spct);
    }
    
    private void clean() {
        txtGia.setText("");
        txtSoLuong.setText("");
    }
    
    private SanPhamCT getDataSPCT() {
        SanPhamCT spct = new SanPhamCT();
        
        if (!Validated.checkEmpty(txtGia.getText(), txtSoLuong.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống form!");
            return null;
        }
        if (!Validated.isNumericInt(txtSoLuong.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng!");
            return null;
        }
        if (!Validated.isNumericDouble(txtGia.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại giá!");
            return null;
        }
        
        spct.setGia(Double.valueOf(txtGia.getText()));
        spct.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        Integer sl = Integer.parseInt(txtSoLuong.getText());
        String status = (String) cbbTrangThai.getSelectedItem();
        Integer trangThai;
        if (status.equals("Đang bán")) {
            trangThai = 1;
        } else {
            trangThai = 2;
        }
        if (sl == 0) {
            trangThai = 2;
        } else {
            trangThai = 1;
        }
        spct.setTrangThai(trangThai);
        
        SanPham sp = spService.selectByMa(maSP);
        spct.setId_sanPham(sp.getId());
        
        String anh = (String) cbbHinhAnh.getSelectedItem();
        HinhAnh hinhAnh = haService.selectByTen(anh);
        spct.setId_Anh(hinhAnh.getId());
        
        String kd = (String) cbbKieuDang.getSelectedItem();
        HinhDang kieuDang = hdService.selectByTen(kd);
        spct.setId_HinhDang(kieuDang.getId());
        
        String ms = (String) cbbMauSac.getSelectedItem();
        MauSac mauSac = msService.selectByTen(ms);
        spct.setId_mauSac(mauSac.getId());
        
        return spct;
    }
    
    private void insert() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        
        SanPhamCT spct = this.getDataSPCT();
        try {
            if (spct == null) {
                return;
            }
            service.insert(spct);
            this.fillTable();
            this.clean();
            JOptionPane.showMessageDialog(this, "Thêm dữ liệu thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    private void update() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        
        SanPhamCT spct = this.getDataSPCT();
        if (spct == null) {
            return;
        }
        this.row = tblSanPhamCT.getSelectedRow();
        Integer id = (Integer) tblSanPhamCT.getValueAt(row, 0);
        spct.setId(id);
        try {
            service.update(spct);
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Sửa dữ liệu thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    private void updateStatusFilter() {
        Boolean checkStatus = (canExecute == 1);
        btnClean.setEnabled(checkStatus);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbbKieuDang = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbbHinhAnh = new javax.swing.JComboBox<>();
        btnMauSac = new javax.swing.JButton();
        btnKieuDang = new javax.swing.JButton();
        btnHinhAnh = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamCT = new javax.swing.JTable();
        btnFirstPages = new javax.swing.JButton();
        btnBackPages = new javax.swing.JButton();
        lblPages = new javax.swing.JLabel();
        btnNextPages = new javax.swing.JButton();
        btnLastPages = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtMin = new javax.swing.JTextField();
        txtMax = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbbFilterMau = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbbFilterKieuDang = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Sản Phảm Chi Tiết");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Giá:");

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Số lượng:");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Trạng thái:");

        cbbTrangThai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Màu sắc:");

        cbbMauSac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Kiểu dáng:");

        cbbKieuDang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Hình ảnh:");

        cbbHinhAnh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        btnKieuDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKieuDangActionPerformed(evt);
            }
        });

        btnHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(cbbTrangThai, 0, 200, Short.MAX_VALUE))
                .addGap(200, 200, 200)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbbKieuDang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbHinhAnh, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                    .addComponent(cbbMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cbbKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(cbbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tblSanPhamCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã SP", "Tên SP", "Giá", "Số lượng", "Màu sắc", "Kiểu dáng", "hỉnh ảnh", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamCTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamCT);

        btnFirstPages.setText("<<");
        btnFirstPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPagesActionPerformed(evt);
            }
        });

        btnBackPages.setText("<");
        btnBackPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackPagesActionPerformed(evt);
            }
        });

        lblPages.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnNextPages.setText(">");
        btnNextPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPagesActionPerformed(evt);
            }
        });

        btnLastPages.setText(">>");
        btnLastPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPagesActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Khoảng giá:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Màu:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Kiểu dáng:");

        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Filters.png"))); // NOI18N
        btnFilter.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        btnClean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clean.png"))); // NOI18N
        btnClean.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnFirstPages)
                                .addGap(10, 10, 10)
                                .addComponent(btnBackPages)
                                .addGap(18, 18, 18)
                                .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(btnNextPages)
                                .addGap(12, 12, 12)
                                .addComponent(btnLastPages))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbFilterMau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbFilterKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(cbbFilterMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(cbbFilterKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirstPages)
                    .addComponent(btnBackPages)
                    .addComponent(btnNextPages)
                    .addComponent(btnLastPages)
                    .addComponent(lblPages, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(381, 381, 381)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<MauSac> listDau = msService.selectAll();
            new MauSacJDialog(frame, true).setVisible(true);
            this.fillCbbHinhDang();
            List<MauSac> listSau = msService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbMauSac.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKieuDangActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<HinhDang> listDau = hdService.selectAll();
            new HinhDangJDialog(frame, true).setVisible(true);
            this.fillCbbHinhDang();
            List<HinhDang> listSau = hdService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbKieuDang.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnKieuDangActionPerformed

    private void btnHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnhActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<HinhAnh> listDau = haService.selectAll();
            new HinhAnhJDialog(frame, true).setVisible(true);
            this.fillCbbHinhAnh();
            List<HinhAnh> listSau = haService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbHinhAnh.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnHinhAnhActionPerformed

    private void btnFirstPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPagesActionPerformed
        // TODO add your handling code here:
        this.firstPage();
    }//GEN-LAST:event_btnFirstPagesActionPerformed

    private void btnBackPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackPagesActionPerformed
        // TODO add your handling code here:
        this.prevPage();
    }//GEN-LAST:event_btnBackPagesActionPerformed

    private void btnNextPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPagesActionPerformed
        // TODO add your handling code here:
        this.nextPage();
    }//GEN-LAST:event_btnNextPagesActionPerformed

    private void btnLastPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPagesActionPerformed
        // TODO add your handling code here:
        this.lastPage();
    }//GEN-LAST:event_btnLastPagesActionPerformed

    private void tblSanPhamCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamCTMouseClicked
        // TODO add your handling code here:
        this.row = tblSanPhamCT.getSelectedRow();
        this.editForm();
    }//GEN-LAST:event_tblSanPhamCTMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        String anh = (String) cbbHinhAnh.getSelectedItem();
        
        String kieuDang = (String) cbbKieuDang.getSelectedItem();
        
        String mauSac = (String) cbbMauSac.getSelectedItem();
        
        List<SanPhamCT> list = service.selectByMa(maSP);
        for (SanPhamCT sanPhamCT : list) {
            if (anh.equals(sanPhamCT.getHinhAnh().getTen())
                    && kieuDang.equals(sanPhamCT.getHinhDang().getTen())
                    && mauSac.equals(sanPhamCT.getMauSac().getTen())) {
                JOptionPane.showMessageDialog(this,
                        "Thêm dữ liệu thất bại. Do chi tiết sản phẩm đã tồn tại!");
                return;
            }
        }
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        this.row = tblSanPhamCT.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa!");
            return;
        }
        
        this.update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        canExecute = 1;
        String mau = (String) cbbFilterMau.getSelectedItem();
        String kieuDang = (String) cbbFilterKieuDang.getSelectedItem();
        
        if (mau.trim().isEmpty()
                && kieuDang.trim().isEmpty()
                && txtMin.getText().trim().isEmpty()
                && txtMax.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầu mục muốn lọc!");
            return;
        }
        
        if (!txtMin.getText().trim().isEmpty()
                && !txtMax.getText().trim().isEmpty()) {
            if (Double.parseDouble(txtMax.getText()) < Double.parseDouble(txtMin.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập min nhỏ hơn giá max!");
                return;
            }
        }
        
        if (!txtMin.getText().trim().isEmpty()) {
            if (Double.parseDouble(txtMin.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá lớn hơn 0!");
                return;
            }
        }
        
        if (!txtMax.getText().trim().isEmpty()) {
            if (Double.parseDouble(txtMax.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá lớn hơn 0!");
                return;
            }
        }
        
        this.filter();
        this.updateStatusFilter();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        // TODO add your handling code here:
        canExecute = 0;
        this.fillTable();
        txtMax.setText("");
        txtMin.setText("");
        cbbFilterKieuDang.setSelectedIndex(0);
        cbbFilterMau.setSelectedIndex(0);
        this.updateStatusFilter();
    }//GEN-LAST:event_btnCleanActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamChiTiet.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTiet.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTiet.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTiet.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SanPhamChiTiet dialog = new SanPhamChiTiet(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackPages;
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnFirstPages;
    private javax.swing.JButton btnHinhAnh;
    private javax.swing.JButton btnKieuDang;
    private javax.swing.JButton btnLastPages;
    private javax.swing.JButton btnMauSac;
    private javax.swing.JButton btnNextPages;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbbFilterKieuDang;
    private javax.swing.JComboBox<String> cbbFilterMau;
    private javax.swing.JComboBox<String> cbbHinhAnh;
    private javax.swing.JComboBox<String> cbbKieuDang;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPages;
    private javax.swing.JTable tblSanPhamCT;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMax;
    private javax.swing.JTextField txtMin;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables

}
