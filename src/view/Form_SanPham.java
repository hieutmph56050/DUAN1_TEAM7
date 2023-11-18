/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.DanhMuc;
import model.HinhAnh;
import model.HinhDang;
import model.MauSac;
import model.PhanLoai;
import model.SanPham;
import model.SanPhamCT;
import model.ThuongHieu;
import Service.ChatLieuService;
import Service.DanhMucService;
import Service.HinhAnhService;
import Service.HinhDangService;
import Service.MauSacService;
import Service.PhanLoaiService;
import Service.SanPhamCTService;
import Service.SanPhamService;
import Service.ThuongHieuService;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Form_SanPham extends javax.swing.JPanel {

    private final SanPhamCTService service = new SanPhamCTService();
    private final SanPhamService spService = new SanPhamService();
    private final MauSacService msService = new MauSacService();
    private final ChatLieuService clService = new ChatLieuService();
    private final DanhMucService dmService = new DanhMucService();
    private final ThuongHieuService thService = new ThuongHieuService();
    private final HinhAnhService haService = new HinhAnhService();
    private final HinhDangService hdService = new HinhDangService();
    private final PhanLoaiService plService = new PhanLoaiService();
    private int row = -1;
    private int pages = 1;
    private final int limit = 5;
    private int numberOfPages;
    private int check;
    private int canExecute = 0;

    public Form_SanPham() {
        initComponents();
        this.fillTable();
        this.loadSearch();
        this.row = -1;
        this.updateStatus();
        this.fillCbbTT();
        this.fillCbbHinhDang();
        this.fillCbbMauSac();
        this.fillCbbChatLieu();
        this.fillCbbDanhMuc();
        this.fillCbbThuongHieu();
        this.fillCbbPhanLoai();
        this.fillCbbHinhAnh();
        this.loadMa();
//        this.loadTitleText();
        this.fillCbbChatLieuFilter();
        this.fillCbbHinhDangFilter();
        this.fillCbbMauSacFilter();
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
            String keyword = txtSearch.getText();
            List<SanPhamCT> listPage = service.selectByKeyWord(keyword);
            this.getPages(listPage);

            List<SanPhamCT> list = service.searchKeyWord(keyword, pages, limit);
            for (SanPhamCT spct : list) {
                model.addRow(new Object[]{
                    spct.getId(),
                    spct.getSanPham().getMa(),
                    spct.getSanPham().getTen(),
                    spct.getGia(),
                    spct.getSoLuong(),
                    spct.getSanPham().getDanhMuc().getTen(),
                    spct.getSanPham().getPhanLoai().getTen(),
                    spct.getSanPham().getChatLieu().getTen(),
                    spct.getSanPham().getThuongHieu().getTen(),
                    spct.getMauSac().getTen(),
                    spct.getHinhDang().getTen(),
                    spct.getHinhAnh().getTen(),
                    spct.loadTrangThai()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    //Start filter---
    private void filter() {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCT.getModel();
        model.setRowCount(0);

        Integer giaMin = null;
        if (!txtThapNhat.getText().trim().isEmpty()) {
            giaMin = Integer.parseInt(txtThapNhat.getText());
        }
        Integer giaMax = null;
        if (!txtCaoNhat.getText().trim().isEmpty()) {
            giaMax = Integer.parseInt(txtCaoNhat.getText());
        }

        String  mau = (String) cbbFilter_Mau.getSelectedItem();
        List<SanPhamCT> listPages = service.FilterPage(giaMin, giaMax, mau);
        this.getPages(listPages);

        List<SanPhamCT> list = service.FilterData(giaMin, giaMax, mau, pages, limit);

        for (SanPhamCT spct : list) {
            model.addRow(new Object[]{
                spct.getId(),
                spct.getSanPham().getMa(),
                spct.getSanPham().getTen(),
                spct.getGia(),
                spct.getSoLuong(),
                spct.getSanPham().getDanhMuc().getTen(),
                spct.getSanPham().getPhanLoai().getTen(),
                spct.getSanPham().getChatLieu().getTen(),
                spct.getSanPham().getThuongHieu().getTen(),
                spct.getMauSac().getTen(),
                spct.getHinhDang().getTen(),
                spct.getHinhAnh().getTen(),
                spct.loadTrangThai()
            });
        }
    }
//End filter---

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

    private void loadMa() {
        txtMaSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                List<SanPham> list = spService.selectAll();
                for (SanPham sanPham : list) {
                    if (txtMaSP.getText().equalsIgnoreCase(sanPham.getMa())) {
                        txtTenSP.setText(sanPham.getTen());
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                List<SanPham> list = spService.selectAll();
                for (SanPham sanPham : list) {
                    if (txtMaSP.getText().equalsIgnoreCase(sanPham.getMa())) {
                        txtTenSP.setText(sanPham.getTen());
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                List<SanPham> list = spService.selectAll();
                for (SanPham sanPham : list) {
                    if (txtMaSP.getText().equalsIgnoreCase(sanPham.getMa())) {
                        txtTenSP.setText(sanPham.getTen());
                    }
                }
            }
        });
    }

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
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbHinhDang.getModel();
        model.removeAllElements();

        List<HinhDang> listCbb = hdService.selectAll();
        for (HinhDang hd : listCbb) {
            model.addElement(hd);
        }
    }

    private void fillCbbMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbMauSac.getModel();
        model.removeAllElements();

        List<MauSac> listCbb = msService.selectAll();
        for (MauSac mauSac : listCbb) {
            model.addElement(mauSac);
        }
    }

    private void fillCbbChatLieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChatLieu.getModel();
        model.removeAllElements();

        List<ChatLieu> listCbb = clService.selectAll();
        for (ChatLieu chatLieu : listCbb) {
            model.addElement(chatLieu);
        }
    }

    private void fillCbbDanhMuc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbDanhMuc.getModel();
        model.removeAllElements();

        List<DanhMuc> listCbb = dmService.selectAll();
        for (DanhMuc danhMuc : listCbb) {
            model.addElement(danhMuc);
        }
    }

    private void fillCbbThuongHieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbThuongHieu.getModel();
        model.removeAllElements();

        List<ThuongHieu> listCbb = thService.selectAll();
        for (ThuongHieu th : listCbb) {
            model.addElement(th);
        }
    }

    private void fillCbbPhanLoai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbPhanLoai.getModel();
        model.removeAllElements();

        List<PhanLoai> listCbb = plService.selectAll();
        for (PhanLoai pl : listCbb) {
            model.addElement(pl);
        }
    }

    private void fillCbbHinhAnh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbHinhAnh.getModel();
        model.removeAllElements();

        List<HinhAnh> listCbb = haService.selectAll();
        for (HinhAnh ha : listCbb) {
            model.addElement(ha);
        }
    }

    private void fillCbbMauSacFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbFilter_Mau.getModel();
        model.removeAllElements();

        List<MauSac> listCbb = msService.selectAll();
        for (MauSac mauSac : listCbb) {
            model.addElement(mauSac.getTen());
        }
    }

    private void fillCbbChatLieuFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbFilter_CL.getModel();
        model.removeAllElements();

        List<ChatLieu> listCbb = clService.selectAll();
        for (ChatLieu chatLieu : listCbb) {
            model.addElement(chatLieu.getTen());
        }
    }

    private void fillCbbHinhDangFilter() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbFilter_HD.getModel();
        model.removeAllElements();

        List<HinhDang> listCbb = hdService.selectAll();
        for (HinhDang hd : listCbb) {
            model.addElement(hd.getTen());
        }
    }

    private void updateStatus() {
        Boolean edit = this.row >= 0;

        btnAdd.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
    }

    private void setDataForm(SanPhamCT spct) {
        txtGia.setText(String.valueOf(spct.getGia()));
        txtSoLuong.setText(String.valueOf(spct.getSoLuong()));
        cbbTrangThai.setSelectedItem(spct.loadTrangThai());
        txtMaSP.setText(spct.getSanPham().getMa());
    }

    private void editForm() {
        Integer id = (Integer) tblSanPhamCT.getValueAt(row, 0);
        SanPhamCT spct = service.selectById(id);
        this.setDataForm(spct);
        this.updateStatus();
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

    private SanPhamCT getDataForm_spct(List<SanPham> list) {
        SanPhamCT spct = new SanPhamCT();

        for (SanPham sanPham : list) {
            if (txtMaSP.getText().equalsIgnoreCase(sanPham.getMa())) {
                spct.setId_sanPham(sanPham.getId());
            }
        }
        spct.setGia(Integer.valueOf(txtGia.getText()));
        spct.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        String status = (String) cbbTrangThai.getSelectedItem();
        Integer trangThai;
        if (status.equals("Đang bán")) {
            trangThai = 1;
        } else {
            trangThai = 2;
        }
        spct.setTrangThai(trangThai);
        HinhAnh ha = (HinhAnh) cbbHinhAnh.getSelectedItem();
        spct.setId_Anh(ha.getId());
        HinhDang hd = (HinhDang) cbbHinhDang.getSelectedItem();
        spct.setId_HinhDang(hd.getId());
        MauSac ms = (MauSac) cbbMauSac.getSelectedItem();
        spct.setId_mauSac(ms.getId());

        return spct;
    }

    private SanPham getData_SP() {
        SanPham sp = new SanPham();

        sp.setMa(txtMaSP.getText());
        sp.setTen(txtTenSP.getText());
        Date currentDate = new Date();
        sp.setNgayThem(new java.sql.Date(currentDate.getTime()));
        sp.setNgaySua(new java.sql.Date(currentDate.getTime()));
        ThuongHieu th = (ThuongHieu) cbbThuongHieu.getSelectedItem();
        sp.setId_th(th.getId());
        DanhMuc dm = (DanhMuc) cbbDanhMuc.getSelectedItem();
        sp.setId_dm(dm.getId());
        PhanLoai pl = (PhanLoai) cbbPhanLoai.getSelectedItem();
        sp.setId_pl(pl.getId());
        ChatLieu cl = (ChatLieu) cbbChatLieu.getSelectedItem();
        sp.setId_cl(cl.getId());
        return sp;
    }

    private void update() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        List<SanPham> listSP = spService.selectAll();
        SanPhamCT spct = this.getDataForm_spct(listSP);
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

    private void clean() {
        txtGia.setText("");
        txtMaSP.setText("");
        txtSoLuong.setText("");
        txtTenSP.setText("");

        this.row = -1;
        this.updateStatus();
    }

    private void insert_sp_spct() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        List<SanPham> list = spService.selectAll();
        for (SanPham sanPham : list) {
            if (txtMaSP.getText().trim().equalsIgnoreCase(sanPham.getMa())) {
                List<SanPham> listSP = spService.selectAll();
                SanPhamCT spct = this.getDataForm_spct(listSP);
                try {
                    service.insert(spct);
                    this.fillTable();
                    this.clean();
                    JOptionPane.showMessageDialog(this, "Thêm dữ liệu thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
                }
                return;
            }
        }

        try {
            SanPham sp = this.getData_SP();
            spService.insert(sp);
            List<SanPham> listSP = spService.selectAll();
            SanPhamCT spct = this.getDataForm_spct(listSP);
            service.insert(spct);
            this.fillTable();
            this.clean();
            JOptionPane.showMessageDialog(this, "Thêm SP thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu sp!");
        }
    }

    private void loadTitleText() {
        txtThapNhat.setText("Min");
        txtThapNhat.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtThapNhat.getText().equals("Min")) {
                    txtThapNhat.setText(null);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtThapNhat.getText().isEmpty()) {
                    txtThapNhat.setText("Min");
                }
            }
        });

        txtCaoNhat.setText("Max");
        txtCaoNhat.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCaoNhat.getText().equals("Max")) {
                    txtCaoNhat.setText(null);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCaoNhat.getText().isEmpty()) {
                    txtCaoNhat.setText("Max");
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnThuongHieu1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamCT = new javax.swing.JTable();
        btnFirstPages = new javax.swing.JButton();
        btnBackPages = new javax.swing.JButton();
        lblPages = new javax.swing.JLabel();
        btnNextPages = new javax.swing.JButton();
        btnLastPages = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbbHinhDang = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbbChatLieu = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbbDanhMuc = new javax.swing.JComboBox<>();
        btnHinhDang = new javax.swing.JButton();
        btnMauSac = new javax.swing.JButton();
        btnAddChatLieu = new javax.swing.JButton();
        btnAddDanhMuc = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbbThuongHieu = new javax.swing.JComboBox<>();
        btnThuongHieu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtThapNhat = new javax.swing.JTextField();
        txtCaoNhat = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbbFilter_Mau = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbbFilter_CL = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cbbHinhAnh = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbbPhanLoai = new javax.swing.JComboBox<>();
        btnPhanLoai = new javax.swing.JButton();
        btnHinhAnh = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        cbbFilter_HD = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        btnThuongHieu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnThuongHieu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieu1ActionPerformed(evt);
            }
        });

        setPreferredSize(new java.awt.Dimension(950, 550));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Quản Lý Sản Phẩm");

        tblSanPhamCT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblSanPhamCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã SP", "Tên SP", "Giá", "Số lượng", "Danh mục", "Phân loại", "Chất liệu", "Thương hiệu", "Màu sắc", "Hình dạng", "Hình ảnh", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Hinh dạng:");

        cbbHinhDang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Màu sắc:");

        cbbMauSac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Chất liệu:");

        cbbChatLieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Danh mục:");

        cbbDanhMuc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnHinhDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnHinhDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhDangActionPerformed(evt);
            }
        });

        btnMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        btnAddChatLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        btnAddDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnAddDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDanhMucActionPerformed(evt);
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

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Update.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clean.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tìm kiếm:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Nhập thông tin:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Thương hiệu");

        btnThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Mã SP:");

        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Tên SP:");

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Gia:");

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Số lượng:");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Trạng thái:");

        cbbTrangThai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setText("Khoảng giá");

        txtThapNhat.setName(""); // NOI18N

        jLabel12.setText("Màu");

        jLabel13.setText("Chất liệu");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Hình ảnh:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Phân loại:");

        btnPhanLoai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnPhanLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanLoaiActionPerformed(evt);
            }
        });

        btnHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/adds.png"))); // NOI18N
        btnHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnhActionPerformed(evt);
            }
        });

        jLabel21.setText("Hình dạng:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Filters.png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(cbbTrangThai, 0, 219, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(btnFirstPages)
                        .addGap(10, 10, 10)
                        .addComponent(btnBackPages)
                        .addGap(18, 18, 18)
                        .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextPages)
                        .addGap(12, 12, 12)
                        .addComponent(btnLastPages)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch)
                                .addGap(71, 71, 71)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtThapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbFilter_Mau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbFilter_CL, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(cbbHinhDang, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnHinhDang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnAddChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(cbbMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel20))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbbFilter_HD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                                .addComponent(jButton1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(cbbThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(cbbDanhMuc, 0, 225, Short.MAX_VALUE)
                                                    .addComponent(cbbPhanLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnAddDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnPhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbHinhDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnHinhDang, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel7)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAddChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel14))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(btnAddDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(cbbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20)
                                .addComponent(cbbPhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnPhanLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10))
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbFilter_CL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel21)
                        .addComponent(cbbFilter_HD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtThapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cbbFilter_Mau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFirstPages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBackPages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLastPages)
                        .addComponent(btnNextPages))
                    .addComponent(lblPages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtThapNhat.getAccessibleContext().setAccessibleName("");
        txtThapNhat.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        this.insert_sp_spct();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clean();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnHinhDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhDangActionPerformed
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new HinhDangJDialog(frame, true).setVisible(true);
            this.fillCbbHinhDang();
            List<HinhDang> listCbb = hdService.selectAll();
            cbbHinhDang.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnHinhDangActionPerformed

    private void btnThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new ThuongHieuJDialog(frame, true).setVisible(true);
            this.fillCbbThuongHieu();
            List<ThuongHieu> listCbb = thService.selectAll();
            cbbThuongHieu.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnThuongHieuActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChatLieuActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new ChatLieuJDialog(frame, true).setVisible(true);
            this.fillCbbChatLieu();
            List<ChatLieu> listCbb = clService.selectAll();
            cbbChatLieu.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnAddChatLieuActionPerformed

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new MauSacJDialog(frame, true).setVisible(true);
            this.fillCbbMauSac();
            List<MauSac> listCbb = msService.selectAll();
            cbbMauSac.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnAddDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDanhMucActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new DanhMucJDialog(frame, true).setVisible(true);
            this.fillCbbDanhMuc();
            List<DanhMuc> listCbb = dmService.selectAll();
            cbbDanhMuc.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnAddDanhMucActionPerformed

    private void btnThuongHieu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThuongHieu1ActionPerformed

    private void btnPhanLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanLoaiActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new PhanLoaijDialog(frame, true).setVisible(true);
            this.fillCbbPhanLoai();
            List<PhanLoai> listCbb = plService.selectAll();
            cbbPhanLoai.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnPhanLoaiActionPerformed

    private void btnHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnhActionPerformed
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            new HinhAnhJDialog(frame, true).setVisible(true);
            this.fillCbbHinhAnh();
            List<HinhAnh> listCbb = haService.selectAll();
            cbbHinhAnh.setSelectedIndex(listCbb.size() - 1);
        }
    }//GEN-LAST:event_btnHinhAnhActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.canExecute = 1;
        this.filter();
        this.firstPage();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddChatLieu;
    private javax.swing.JButton btnAddDanhMuc;
    private javax.swing.JButton btnBackPages;
    private javax.swing.JButton btnFirstPages;
    private javax.swing.JButton btnHinhAnh;
    private javax.swing.JButton btnHinhDang;
    private javax.swing.JButton btnLastPages;
    private javax.swing.JButton btnMauSac;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNextPages;
    private javax.swing.JButton btnPhanLoai;
    private javax.swing.JButton btnThuongHieu;
    private javax.swing.JButton btnThuongHieu1;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbFilter_CL;
    private javax.swing.JComboBox<String> cbbFilter_HD;
    private javax.swing.JComboBox<String> cbbFilter_Mau;
    private javax.swing.JComboBox<String> cbbHinhAnh;
    private javax.swing.JComboBox<String> cbbHinhDang;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbPhanLoai;
    private javax.swing.JComboBox<String> cbbThuongHieu;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPages;
    private javax.swing.JTable tblSanPhamCT;
    private javax.swing.JTextField txtCaoNhat;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThapNhat;
    // End of variables declaration//GEN-END:variables

}
