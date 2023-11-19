package view;

import Service.ChatLieuService;
import Service.DanhMucService;
import Service.HinhAnhService;
import Service.HinhDangService;
import Service.MauSacService;
import Service.PhanLoaiService;
import Service.ThuongHieuService;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.DanhMuc;
import model.HinhAnh;
import model.HinhDang;
import model.MauSac;
import model.PhanLoai;
import model.ThuongHieu;
import repository.XImage;

public class QL_ThuocTinh extends javax.swing.JDialog {

    private final MauSacService msService = new MauSacService();
    private final ChatLieuService clService = new ChatLieuService();
    private final DanhMucService dmService = new DanhMucService();
    private final ThuongHieuService thService = new ThuongHieuService();
    private final HinhAnhService haService = new HinhAnhService();
    private final HinhDangService hdService = new HinhDangService();
    private final PhanLoaiService plService = new PhanLoaiService();
    private int row = -1;
    private int pages = 1;
    private final int limit = 2;
    private int numberOfPages;
    private int check;
    private int canExecute = 0;

    public QL_ThuocTinh(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.fillTableDM();
    }

    //Stat hình ảnh
    private Integer getPagesHA(List<HinhAnh> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableHA() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<HinhAnh> listPag = haService.selectAll();
        this.getPagesHA(listPag);

        List<HinhAnh> listTable = haService.selectPages(pages, limit);

        for (HinhAnh entity : listTable) {
            tableModel.addRow(new Object[]{
                entity.getId(),
                entity.getTen()
            });
        }
    }
//End hình ảnh

    //Stat Thương hiệu
    private Integer getPagesTH(List<ThuongHieu> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableTH() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<ThuongHieu> listPag = thService.selectAll();
        this.getPagesTH(listPag);

        List<ThuongHieu> listTable = thService.searchPages(pages, limit);

        for (ThuongHieu th : listTable) {
            tableModel.addRow(new Object[]{
                th.getId(),
                th.getTen()
            });
        }
    }
//End thương hiệu

    //Stat phân loại
    private Integer getPagesPL(List<PhanLoai> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTablePL() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<PhanLoai> listPag = plService.selectAll();
        this.getPagesPL(listPag);

        List<PhanLoai> listTable = plService.searchPages(pages, limit);

        for (PhanLoai pl : listTable) {
            tableModel.addRow(new Object[]{
                pl.getId(),
                pl.getTen()
            });
        }
    }
//End phân loại

    //Stat màu sắc
    private Integer getPagesMS(List<MauSac> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableMS() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<MauSac> listPag = msService.selectAll();
        this.getPagesMS(listPag);

        List<MauSac> listTable = msService.selectPages(pages, limit);

        for (MauSac ms : listTable) {
            tableModel.addRow(new Object[]{
                ms.getId(),
                ms.getTen()
            });
        }
    }
//End màu sắc

    //Stat hình dạng
    private Integer getPagesHD(List<HinhDang> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableHD() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<HinhDang> listPag = hdService.selectAll();
        this.getPagesHD(listPag);

        List<HinhDang> listTable = hdService.searchPages(pages, limit);

        for (HinhDang entity : listTable) {
            tableModel.addRow(new Object[]{
                entity.getId(),
                entity.getTen()
            });
        }
    }
//End hình dạnh

    //Stat danh mục
    private Integer getPagesDM(List<DanhMuc> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableDM() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<DanhMuc> listPag = dmService.selectAll();
        this.getPagesDM(listPag);

        List<DanhMuc> listTable = dmService.selectPages(pages, limit);

        for (DanhMuc dm : listTable) {
            tableModel.addRow(new Object[]{
                dm.getId(),
                dm.getTen()
            });
        }
    }
//End danh mục

    //stat chất liệu
    private Integer getPagesCL(List<ChatLieu> listPag) {
        if (listPag.size() % limit == 0) {
            numberOfPages = listPag.size() / limit;
        } else {
            numberOfPages = (listPag.size() / limit) + 1;
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);

        return numberOfPages;
    }

    private void fillTableCL() {
        DefaultTableModel tableModel = (DefaultTableModel) tblThuocTinh.getModel();
        tableModel.setRowCount(0);

        List<ChatLieu> listPag = clService.selectAll();
        this.getPagesCL(listPag);

        List<ChatLieu> listTable = clService.selectPages(pages, limit);

        for (ChatLieu cl : listTable) {
            tableModel.addRow(new Object[]{
                cl.getId(),
                cl.getTen()
            });
        }
    }
//End chất liệu

    private void clearForm() {
        lblAnh.setToolTipText("");
        txtTen.setText("");
    }

    private void insertDM() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        DanhMuc entity = new DanhMuc();
        entity.setTen(txtTen.getText());
        try {
            dmService.insert(entity);
            this.fillTableDM();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertTH() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        ThuongHieu entity = new ThuongHieu();
        entity.setTen(txtTen.getText());
        try {
            thService.insert(entity);
            this.fillTableTH();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertCL() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        ChatLieu entity = new ChatLieu();
        entity.setTen(txtTen.getText());
        try {
            clService.insert(entity);
            this.fillTableCL();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertMS() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        MauSac entity = new MauSac();
        entity.setTen(txtTen.getText());
        try {
            msService.insert(entity);
            this.fillTableMS();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertHA() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        HinhAnh entity = new HinhAnh();
        entity.setTen(lblAnh.getToolTipText());
        try {
            haService.insert(entity);
            this.fillTableHA();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertKD() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        HinhDang entity = new HinhDang();
        entity.setTen(txtTen.getText());
        try {
            hdService.insert(entity);
            this.fillTableHD();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void insertPL() {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận thêm dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        PhanLoai entity = new PhanLoai();
        entity.setTen(txtTen.getText());
        try {
            plService.insert(entity);
            this.fillTablePL();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateDM(DanhMuc dm) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        DanhMuc entity = new DanhMuc();
        entity.setTen(txtTen.getText());
        entity.setId(dm.getId());
        try {
            dmService.update(entity);
            this.fillTableDM();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateTH(ThuongHieu th) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        ThuongHieu entity = new ThuongHieu();
        entity.setTen(txtTen.getText());
        entity.setId(th.getId());
        try {
            thService.update(entity);
            this.fillTableTH();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateCL(ChatLieu cl) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        ChatLieu entity = new ChatLieu();
        entity.setTen(txtTen.getText());
        entity.setId(cl.getId());
        try {
            clService.update(entity);
            this.fillTableCL();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateMS(MauSac ms) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        MauSac entity = new MauSac();
        entity.setTen(txtTen.getText());
        entity.setId(ms.getId());
        try {
            msService.update(entity);
            this.fillTableMS();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateHA(HinhAnh ha) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        HinhAnh entity = new HinhAnh();
        entity.setTen(lblAnh.getToolTipText());
        entity.setId(ha.getId());
        try {
            haService.update(entity);
            this.fillTableHA();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updateKD(HinhDang kd) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        HinhDang entity = new HinhDang();
        entity.setTen(txtTen.getText());
        entity.setId(kd.getId());
        try {
            hdService.update(entity);
            this.fillTableHD();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void updatePL(PhanLoai pl) {
        check = JOptionPane.showConfirmDialog(this, "Xác nhận Sửa dữ liệu?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        PhanLoai entity = new PhanLoai();
        entity.setTen(txtTen.getText());
        entity.setId(pl.getId());
        try {
            plService.update(entity);
            this.fillTablePL();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi try vấn dữ liệu!");
        }
    }

    private void firstPage() {
        pages = 1;
        switch (canExecute) {
            case 0 ->
                this.fillTableDM();
            case 1 ->
                this.fillTableTH();
            case 2 ->
                this.fillTableCL();
            case 3 ->
                this.fillTableMS();
            case 4 ->
                this.fillTableHA();
            case 5 ->
                this.fillTableHD();
            default ->
                this.fillTablePL();
        }

        lblPages.setText("1");
        lblNumberOfPage.setText("1/" + numberOfPages);
    }

    private void prevPage() {
        if (pages > 1) {
            pages--;
            switch (canExecute) {
                case 0 ->
                    this.fillTableDM();
                case 1 ->
                    this.fillTableTH();
                case 2 ->
                    this.fillTableCL();
                case 3 ->
                    this.fillTableMS();
                case 4 ->
                    this.fillTableHA();
                case 5 ->
                    this.fillTableHD();
                default ->
                    this.fillTablePL();
            }

            lblPages.setText("" + pages);
            lblNumberOfPage.setText(pages + "/" + numberOfPages);
        }
    }

    private void nextPage() {
        if (pages < numberOfPages) {
            pages++;
            switch (canExecute) {
                case 0 ->
                    this.fillTableDM();
                case 1 ->
                    this.fillTableTH();
                case 2 ->
                    this.fillTableCL();
                case 3 ->
                    this.fillTableMS();
                case 4 ->
                    this.fillTableHA();
                case 5 ->
                    this.fillTableHD();
                default ->
                    this.fillTablePL();
            }

            lblPages.setText("" + pages);
            lblNumberOfPage.setText(pages + "/" + numberOfPages);
        }
    }

    private void lastPage() {
        pages = numberOfPages;
        switch (canExecute) {
            case 0 ->
                this.fillTableDM();
            case 1 ->
                this.fillTableTH();
            case 2 ->
                this.fillTableCL();
            case 3 ->
                this.fillTableMS();
            case 4 ->
                this.fillTableHA();
            case 5 ->
                this.fillTableHD();
            default ->
                this.fillTablePL();
        }

        lblPages.setText("" + pages);
        lblNumberOfPage.setText(pages + "/" + numberOfPages);
    }

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);//lưu file vào thư mục 
            ImageIcon icon = XImage.read(file.getName());
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblPages = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblNumberOfPage = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rdoDanhMuc = new javax.swing.JRadioButton();
        rdoThuongHieu = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoHinhAnh = new javax.swing.JRadioButton();
        rdoKieuDang = new javax.swing.JRadioButton();
        rdoPhanLoai = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblThuocTinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThuocTinh);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Tên");

        txtTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Quản Lý Thuộc Tính");

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBack.setText("<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblPages.setBackground(new java.awt.Color(255, 255, 255));
        lblPages.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPages.setText("  ");
        lblPages.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lblPages.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPages.setOpaque(true);

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLast.setText(">>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblNumberOfPage.setBackground(new java.awt.Color(255, 255, 255));
        lblNumberOfPage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNumberOfPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfPage.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lblNumberOfPage.setOpaque(true);

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Hỉnh ảnh:");

        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Chọn thuộc tính:");

        buttonGroup1.add(rdoDanhMuc);
        rdoDanhMuc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoDanhMuc.setSelected(true);
        rdoDanhMuc.setText("Danh mục");
        rdoDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDanhMucMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoThuongHieu);
        rdoThuongHieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoThuongHieu.setText("Thương hiệu");
        rdoThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoThuongHieuMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChatLieuMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoHinhAnh);
        rdoHinhAnh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoHinhAnh.setText("Hỉnh ảnh");
        rdoHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoHinhAnhMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoKieuDang);
        rdoKieuDang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoKieuDang.setText("Kiểu dáng");
        rdoKieuDang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoKieuDangMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoPhanLoai);
        rdoPhanLoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoPhanLoai.setText("Phân loại");
        rdoPhanLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoPhanLoaiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(rdoDanhMuc)
                                                    .addComponent(jLabel4))
                                                .addGap(11, 11, 11))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rdoThuongHieu)
                                                .addComponent(rdoHinhAnh)
                                                .addComponent(rdoMauSac)
                                                .addComponent(rdoKieuDang)
                                                .addComponent(rdoChatLieu)
                                                .addComponent(rdoPhanLoai))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(235, 235, 235))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNumberOfPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(166, 166, 166))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNumberOfPage, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFirst)
                                .addComponent(btnBack)
                                .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNext)
                                .addComponent(btnLast))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoDanhMuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoThuongHieu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoChatLieu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoMauSac)
                                .addGap(21, 21, 21))
                            .addComponent(rdoHinhAnh, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoKieuDang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoPhanLoai)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        this.row = tblThuocTinh.getSelectedRow();
        switch (canExecute) {
            case 0 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                DanhMuc entity = dmService.selectById(id);
                this.updateDM(entity);
            }
            case 1 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                ThuongHieu entity = thService.selectById(id);
                this.updateTH(entity);
            }
            case 2 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                ChatLieu entity = clService.selectById(id);
                this.updateCL(entity);
            }
            case 3 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                MauSac entity = msService.selectById(id);
                this.updateMS(entity);
            }
            case 4 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                HinhAnh entity = haService.selectById(id);
                this.updateHA(entity);
            }
            case 5 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                HinhDang entity = hdService.selectById(id);
                this.updateKD(entity);
            }
            default -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                PhanLoai entity = plService.selectById(id);
                this.updatePL(entity);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        // TODO add your handling code here:
        this.row = tblThuocTinh.getSelectedRow();
        switch (canExecute) {
            case 0 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                DanhMuc entity = dmService.selectById(id);
                txtTen.setText(entity.getTen());
            }
            case 1 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                ThuongHieu entity = thService.selectById(id);
                txtTen.setText(entity.getTen());
            }
            case 2 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                ChatLieu entity = clService.selectById(id);
                txtTen.setText(entity.getTen());
            }
            case 3 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                MauSac entity = msService.selectById(id);
                txtTen.setText(entity.getTen());
            }
            case 4 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                HinhAnh entity = haService.selectById(id);
                if (entity.getTen() != null) {
                    lblAnh.setToolTipText(entity.getTen());
                    lblAnh.setIcon(XImage.read(entity.getTen()));
                }
            }
            case 5 -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                HinhDang entity = hdService.selectById(id);
                txtTen.setText(entity.getTen());
            }
            default -> {
                Integer id = (Integer) tblThuocTinh.getValueAt(row, 0);
                PhanLoai entity = plService.selectById(id);
                txtTen.setText(entity.getTen());
            }
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.firstPage();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.prevPage();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.nextPage();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        this.lastPage();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        switch (canExecute) {
            case 0 -> {
                this.insertDM();
            }
            case 1 -> {
                this.insertTH();
            }
            case 2 -> {
                this.insertCL();
            }
            case 3 -> {
                this.insertMS();
            }
            case 4 -> {
                this.insertHA();
            }
            case 5 -> {
                this.insertKD();
            }
            default -> {
                this.insertPL();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void rdoDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDanhMucMouseClicked
        // TODO add your handling code here:
        this.canExecute = 0;
        this.fillTableDM();
        this.firstPage();
    }//GEN-LAST:event_rdoDanhMucMouseClicked

    private void rdoThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoThuongHieuMouseClicked
        // TODO add your handling code here:
        this.canExecute = 1;
        this.fillTableTH();
        this.firstPage();
    }//GEN-LAST:event_rdoThuongHieuMouseClicked

    private void rdoChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChatLieuMouseClicked
        // TODO add your handling code here:
        this.canExecute = 2;
        this.fillTableCL();
        this.firstPage();
    }//GEN-LAST:event_rdoChatLieuMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        // TODO add your handling code here:
        this.canExecute = 3;
        this.fillTableMS();
        this.firstPage();
    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoHinhAnhMouseClicked
        // TODO add your handling code here:
        this.canExecute = 4;
        this.fillTableHA();
        this.firstPage();
    }//GEN-LAST:event_rdoHinhAnhMouseClicked

    private void rdoKieuDangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoKieuDangMouseClicked
        // TODO add your handling code here:
        this.canExecute = 5;
        this.fillTableHD();
        this.firstPage();
    }//GEN-LAST:event_rdoKieuDangMouseClicked

    private void rdoPhanLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoPhanLoaiMouseClicked
        // TODO add your handling code here:
        this.canExecute = 6;
        this.fillTablePL();
        this.firstPage();
    }//GEN-LAST:event_rdoPhanLoaiMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        this.chonAnh();
    }//GEN-LAST:event_lblAnhMouseClicked

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
            java.util.logging.Logger.getLogger(QL_ThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QL_ThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QL_ThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QL_ThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QL_ThuocTinh dialog = new QL_ThuocTinh(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblNumberOfPage;
    private javax.swing.JLabel lblPages;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoDanhMuc;
    private javax.swing.JRadioButton rdoHinhAnh;
    private javax.swing.JRadioButton rdoKieuDang;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoPhanLoai;
    private javax.swing.JRadioButton rdoThuongHieu;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
