package view;

import Service.ChatLieuService;
import Service.DanhMucService;
import Service.PhanLoaiService;
import Service.SanPhamService;
import Service.ThuongHieuService;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.DanhMuc;
import model.PhanLoai;
import model.SanPham;
import model.ThuongHieu;
import repository.Validated;

public class Form_QlSanPham extends javax.swing.JPanel {

    private final SanPhamService service = new SanPhamService();
    private final ChatLieuService clService = new ChatLieuService();
    private final DanhMucService dmService = new DanhMucService();
    private final ThuongHieuService thService = new ThuongHieuService();
    private final PhanLoaiService plService = new PhanLoaiService();
    private int row = -1;
    private int pages = 1;
    private final int limit = 5;
    private int numberOfPages;
    private int check;
    private int canExecute = 0;

    public Form_QlSanPham() {
        initComponents();
        this.fillTable();
        this.fillCbbChatLieu();
        this.fillCbbDanhMuc();
        this.fillCbbPhanLoai();
        this.fillCbbThuongHieu();
        this.loadSearch();
        this.fillCbbChatLieuFilter();
        this.fillCbbDanhMucFilter();
        this.fillCbbThuongHieuFilter();
        this.updateStatusFilter();
    }

    private void getPages(List<SanPham> list) {
        if (list.size() % limit == 0) {
            numberOfPages = list.size() / limit;
        } else {
            numberOfPages = (list.size() / limit) + 1;
        }

        lblPages.setText("1");
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        try {
            String keyword = txtSearch.getText();
            List<SanPham> listPage = service.selectByKeyWord(keyword);
            this.getPages(listPage);

            List<SanPham> list = service.searchKeyWord(keyword, pages, limit);
            for (SanPham sp : list) {
                model.addRow(new Object[]{
                    sp.getId(),
                    sp.getMa(),
                    sp.getTen(),
                    sp.getNgayThem(),
                    sp.getNgaySua(),
                    sp.getDanhMuc(),
                    sp.getThuongHieu(),
                    sp.getPhanLoai(),
                    sp.getChatLieu()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void filterData() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        try {
            String chatLieu = (String) cbbChatLieuFilter.getSelectedItem();
            String danhMuc = (String) cbbDanhMucFilter.getSelectedItem();
            String thuongHieu = (String) cbbThuongHieuFilter.getSelectedItem();
            String keyword = txtSearch.getText();

            List<SanPham> listPage = service.selectPagesFilter(danhMuc, thuongHieu, chatLieu, keyword);
            this.getPages(listPage);

            List<SanPham> list = service.Filter(danhMuc, thuongHieu, chatLieu, keyword, pages, limit);
            for (SanPham sp : list) {
                model.addRow(new Object[]{
                    sp.getId(),
                    sp.getMa(),
                    sp.getTen(),
                    sp.getNgayThem(),
                    sp.getNgaySua(),
                    sp.getDanhMuc(),
                    sp.getThuongHieu(),
                    sp.getPhanLoai(),
                    sp.getChatLieu()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void fillCbbChatLieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChatLieu.getModel();
        model.removeAllElements();

        List<ChatLieu> listCbb = clService.selectAll();
        for (ChatLieu chatLieu : listCbb) {
            model.addElement(chatLieu.getTen());
        }
    }

    private void fillCbbDanhMuc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbDanhMuc.getModel();
        model.removeAllElements();

        List<DanhMuc> listCbb = dmService.selectAll();
        for (DanhMuc danhMuc : listCbb) {
            model.addElement(danhMuc.getTen());
        }
    }

    private void fillCbbThuongHieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbThuongHieu.getModel();
        model.removeAllElements();

        List<ThuongHieu> listCbb = thService.selectAll();
        for (ThuongHieu th : listCbb) {
            model.addElement(th.getTen());
        }
    }

    private void fillCbbPhanLoai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbPhanLoai.getModel();
        model.removeAllElements();

        List<PhanLoai> listCbb = plService.selectAll();
        for (PhanLoai pl : listCbb) {
            model.addElement(pl.getTen());
        }
    }

    private void fillCbbChatLieuFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChatLieuFilter.getModel();
        model.removeAllElements();
        model.addElement("");

        List<ChatLieu> listCbb = clService.selectAll();
        for (ChatLieu chatLieu : listCbb) {
            model.addElement(chatLieu.getTen());
        }
    }

    private void fillCbbDanhMucFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbDanhMucFilter.getModel();
        model.removeAllElements();
        model.addElement("");

        List<DanhMuc> listCbb = dmService.selectAll();
        for (DanhMuc danhMuc : listCbb) {
            model.addElement(danhMuc.getTen());
        }
    }

    private void fillCbbThuongHieuFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbThuongHieuFilter.getModel();
        model.removeAllElements();
        model.addElement("");

        List<ThuongHieu> listCbb = thService.selectAll();
        for (ThuongHieu th : listCbb) {
            model.addElement(th.getTen());
        }
    }

    private void setDataForm(SanPham sp) {
        txtMa.setText(sp.getMa());
        txtTen.setText(sp.getTen());
        cbbChatLieu.setSelectedItem(sp.getChatLieu().getTen());
        cbbDanhMuc.setSelectedItem(sp.getDanhMuc().getTen());
        cbbPhanLoai.setSelectedItem(sp.getPhanLoai().getTen());
        cbbThuongHieu.setSelectedItem(sp.getThuongHieu().getTen());
    }

    private void editForm() {
        Integer id = (Integer) tblSanPham.getValueAt(row, 0);
        SanPham sp = service.selectById(id);
        this.setDataForm(sp);
    }

    //Start phân trang---
    private void firstPage() {
        pages = 1;
        if (canExecute == 0) {
            this.fillTable();
        } else {
            this.filterData();
        }

        lblPages.setText("1");
    }

    private void prevPage() {
        if (pages > 1) {
            pages--;
            if (canExecute == 0) {
                this.fillTable();
            } else {
                this.filterData();
            }

            lblPages.setText("" + pages);
        }
    }

    private void nextPage() {
        if (pages < numberOfPages) {
            pages++;
            if (canExecute == 0) {
                this.fillTable();
            } else {
                this.filterData();
            }

            lblPages.setText("" + pages);
        }
    }

    private void lastPage() {
        pages = numberOfPages;
        if (canExecute == 0) {
            this.fillTable();
        } else {
            this.filterData();
        }

        lblPages.setText("" + pages);
    }

    private void loadSearch() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillTable();
                firstPage();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTable();
                firstPage();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fillTable();
                firstPage();
            }
        });
    }

    private SanPham getData() {
        if (!Validated.checkEmpty(txtMa.getText(), txtTen.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống form!");
            return null;
        }

        SanPham sp = new SanPham();

        sp.setMa(txtMa.getText());
        sp.setTen(txtTen.getText());
        Date currentDate = new Date();
        sp.setNgayThem(new java.sql.Date(currentDate.getTime()));
        sp.setNgaySua(new java.sql.Date(currentDate.getTime()));

        String cl = (String) cbbChatLieu.getSelectedItem();
        ChatLieu chatLieu = clService.selectByTen(cl);
        sp.setId_cl(chatLieu.getId());

        String th = (String) cbbThuongHieu.getSelectedItem();
        ThuongHieu thuongHieu = thService.selectByTen(th);
        sp.setId_th(thuongHieu.getId());

        String dm = (String) cbbDanhMuc.getSelectedItem();
        DanhMuc danhMuc = dmService.selectByTen(dm);
        sp.setId_dm(danhMuc.getId());

        String pl = (String) cbbPhanLoai.getSelectedItem();
        PhanLoai phanLoai = plService.selectByTen(pl);
        sp.setId_pl(phanLoai.getId());
        return sp;
    }

    private void insert() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            SanPham sp = this.getData();

            if (sp == null) {
                return;
            }

            service.insert(sp);
            this.fillTable();
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

        try {
            SanPham sp = this.getData();

            if (sp == null) {
                return;
            }

            Integer id = (Integer) tblSanPham.getValueAt(row, 0);
            sp.setId(id);
            service.update(sp);
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Sửa dữ liệu thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void updateStatusFilter() {
        Boolean checkStatus = (canExecute == 1);
        btnCleanFilter.setEnabled(checkStatus);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnFirstPages = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbbDanhMuc = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbbThuongHieu = new javax.swing.JComboBox<>();
        btnAdd_DanhMuc = new javax.swing.JButton();
        btnAdd_ThuongHieu = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbbPhanLoai = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbbChatLieu = new javax.swing.JComboBox<>();
        btnAdd_PhanLoai = new javax.swing.JButton();
        btnAdd_ChatLieu = new javax.swing.JButton();
        btnBackPages = new javax.swing.JButton();
        lblPages = new javax.swing.JLabel();
        btnNextPages = new javax.swing.JButton();
        btnLastPages = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnCTSP = new javax.swing.JButton();
        cbbChatLieuFilter = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbbDanhMucFilter = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbbThuongHieuFilter = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        btnCleanFilter = new javax.swing.JButton();

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Update.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Quản Lý Sản Phẩm");

        btnFirstPages.setText("<<");
        btnFirstPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPagesActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setText("Mã sản phẩm:");

        jLabel3.setText("Tên sản phẩm:");

        jLabel4.setText("Danh mục:");

        jLabel5.setText("Thương hiệu:");

        btnAdd_DanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAdd_DanhMuc.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd_DanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_DanhMucActionPerformed(evt);
            }
        });

        btnAdd_ThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAdd_ThuongHieu.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd_ThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_ThuongHieuActionPerformed(evt);
            }
        });

        jLabel6.setText("Phân loại:");

        jLabel7.setText("Chất liệu:");

        btnAdd_PhanLoai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAdd_PhanLoai.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd_PhanLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_PhanLoaiActionPerformed(evt);
            }
        });

        btnAdd_ChatLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAdd_ChatLieu.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd_ChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_ChatLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd_DanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTen)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbPhanLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(btnAdd_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAdd_PhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd_ChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAdd_ThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cbbPhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAdd_PhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAdd_ChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd_DanhMuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))))
        );

        btnBackPages.setText("<");
        btnBackPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackPagesActionPerformed(evt);
            }
        });

        lblPages.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Tìm kiếm");

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã", "Tên", "Ngày thêm", "Ngày sửa", "Danh mục", "Thương hiệu", "Phân loại", "Chất liệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnCTSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCTSP.setText("Xem chi tiết");
        btnCTSP.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCTSPActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Chất liệu:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Danh mục:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Thương hiệu:");

        btnFilter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Filters.png"))); // NOI18N
        btnFilter.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        btnCleanFilter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCleanFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clean.png"))); // NOI18N
        btnCleanFilter.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCleanFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(cbbChatLieuFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addComponent(cbbDanhMucFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addGap(4, 4, 4)
                        .addComponent(cbbThuongHieuFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCleanFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnFirstPages)
                        .addGap(10, 10, 10)
                        .addComponent(btnBackPages)
                        .addGap(18, 18, 18)
                        .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnNextPages)
                        .addGap(12, 12, 12)
                        .addComponent(btnLastPages)))
                .addGap(0, 35, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8))
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cbbChatLieuFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cbbDanhMucFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cbbThuongHieuFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCleanFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirstPages)
                    .addComponent(btnBackPages)
                    .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextPages)
                    .addComponent(btnLastPages))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.row = tblSanPham.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }

//        String cl = (String) cbbChatLieu.getSelectedItem();
//
//        String th = (String) cbbThuongHieu.getSelectedItem();
//
//        String dm = (String) cbbDanhMuc.getSelectedItem();
//
//        String pl = (String) cbbPhanLoai.getSelectedItem();
//
//        List<SanPham> list = service.selectAll();
//        for (SanPham sanPham : list) {
//            if (cl.equals(sanPham.getChatLieu().getTen())
//                    && th.equals(sanPham.getThuongHieu().getTen())
//                    && dm.equals(sanPham.getDanhMuc().getTen())
//                    && pl.equals(sanPham.getPhanLoai().getTen())
//                    && txtTen.getText().equals(sanPham.getTen())) {
//                JOptionPane.showMessageDialog(this,
//                        "Thêm dữ liệu thất bại. Do sản phẩm đã tồn tại!");
//                return;
//            }
//        }
        
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        List<SanPham> list = service.selectAll();
        for (SanPham sanPham : list) {
            if (sanPham.getMa().equals(txtMa.getText())) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại, vui lòng nhập lại mã mới!");
                return;
            }
        }
        String cl = (String) cbbChatLieu.getSelectedItem();

        String th = (String) cbbThuongHieu.getSelectedItem();

        String dm = (String) cbbDanhMuc.getSelectedItem();

        String pl = (String) cbbPhanLoai.getSelectedItem();

        for (SanPham sanPham : list) {
            if (cl.equals(sanPham.getChatLieu().getTen())
                    && th.equals(sanPham.getThuongHieu().getTen())
                    && dm.equals(sanPham.getDanhMuc().getTen())
                    && pl.equals(sanPham.getPhanLoai().getTen())
                    && txtTen.getText().equals(sanPham.getTen())) {
                JOptionPane.showMessageDialog(this,
                        "Thêm dữ liệu thất bại. Do sản phẩm đã tồn tại!");
                return;
            }
        }
        this.insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnFirstPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPagesActionPerformed
        // TODO add your handling code here:
        this.firstPage();
    }//GEN-LAST:event_btnFirstPagesActionPerformed

    private void btnAdd_DanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_DanhMucActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<DanhMuc> listBanDau = dmService.selectAll();
            new DanhMucJDialog(frame, true).setVisible(true);
            this.fillCbbDanhMuc();
            List<DanhMuc> listSau = dmService.selectAll();
            if (listSau.size() > listBanDau.size()) {
                cbbDanhMuc.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnAdd_DanhMucActionPerformed

    private void btnAdd_ThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_ThuongHieuActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<ThuongHieu> listDau = thService.selectAll();
            new ThuongHieuJDialog(frame, true).setVisible(true);
            this.fillCbbThuongHieu();
            List<ThuongHieu> listSau = thService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbThuongHieu.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnAdd_ThuongHieuActionPerformed

    private void btnAdd_PhanLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_PhanLoaiActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<PhanLoai> listDau = plService.selectAll();
            new PhanLoaijDialog(frame, true).setVisible(true);
            this.fillCbbPhanLoai();
            List<PhanLoai> listSau = plService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbPhanLoai.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnAdd_PhanLoaiActionPerformed

    private void btnAdd_ChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_ChatLieuActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            List<ChatLieu> listDau = clService.selectAll();
            new ChatLieuJDialog(frame, true).setVisible(true);
            this.fillCbbChatLieu();
            List<ChatLieu> listSau = clService.selectAll();
            if (listSau.size() > listDau.size()) {
                cbbChatLieu.setSelectedIndex(listSau.size() - 1);
            }
        }
    }//GEN-LAST:event_btnAdd_ChatLieuActionPerformed

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

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        this.row = tblSanPham.getSelectedRow();
        this.editForm();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCTSPActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            this.row = tblSanPham.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xem chi tiết!");
                return;
            }
            String maSP = (String) tblSanPham.getValueAt(row, 1);
            new SanPhamChiTiet(frame, true, maSP).setVisible(true);

        }
    }//GEN-LAST:event_btnCTSPActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        canExecute = 1;
        String chatLieu = (String) cbbChatLieuFilter.getSelectedItem();
        String danhMuc = (String) cbbDanhMucFilter.getSelectedItem();
        String thuongHieu = (String) cbbThuongHieuFilter.getSelectedItem();

        if (chatLieu.trim().isEmpty()
                && danhMuc.trim().isEmpty()
                && thuongHieu.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầu mục muốn lọc!");
            return;
        }
        this.filterData();
        this.firstPage();
        this.updateStatusFilter();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnCleanFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanFilterActionPerformed
        // TODO add your handling code here:
        canExecute = 0;
        this.fillTable();
        this.firstPage();
        cbbChatLieuFilter.setSelectedIndex(0);
        cbbDanhMucFilter.setSelectedIndex(0);
        cbbThuongHieuFilter.setSelectedIndex(0);
        this.updateStatusFilter();
    }//GEN-LAST:event_btnCleanFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd_ChatLieu;
    private javax.swing.JButton btnAdd_DanhMuc;
    private javax.swing.JButton btnAdd_PhanLoai;
    private javax.swing.JButton btnAdd_ThuongHieu;
    private javax.swing.JButton btnBackPages;
    private javax.swing.JButton btnCTSP;
    private javax.swing.JButton btnCleanFilter;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnFirstPages;
    private javax.swing.JButton btnLastPages;
    private javax.swing.JButton btnNextPages;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbChatLieuFilter;
    private javax.swing.JComboBox<String> cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbDanhMucFilter;
    private javax.swing.JComboBox<String> cbbPhanLoai;
    private javax.swing.JComboBox<String> cbbThuongHieu;
    private javax.swing.JComboBox<String> cbbThuongHieuFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPages;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
