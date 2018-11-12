/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ClientDeviceControl;
import Control.ClientLoginControl;
import Model.Device;
import Model.UpdateDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sun.misc.ObjectInputFilter;

/**
 *
 * @author Bv ptop 268
 */
public class ViewDevice extends javax.swing.JFrame implements Runnable {

    final static String fileName = "DEVICE.DAT";
    final static String fileAdd = "ADD.DAT";
    final static String fileUpdate = "UPDATE.DAT";
    final static String fileDelete = "DELETE.DAT";
    DefaultTableModel model;
    private int idBeforUpdate;
    String serverHost = "118.68.80.160";

    //private ClientDeviceControl control;
    /**
     * Creates new form ViewDevice
     */
    public ViewDevice() {
        setLocation(250, 20);
        initComponents();
        model = (DefaultTableModel) table.getModel();
    }

    // runnable
    @Override
    // ktra mỗi 1 giây kết nối thì chuyển thành on - offline
    public void run() {
        while (true) {
            InetAddress address;
            try {
                address = InetAddress.getByName(serverHost);
                if (address.isReachable(1000)) {
                    rdOnline.setSelected(true);
                    btnSynchronize.setEnabled(true);
                } else {
                    rdOffline.setSelected(true);
                    btnSynchronize.setEnabled(false);
                }
            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
            }
        }

    }

    public void loadData(ArrayList<Device> list) {
        for (Device device : list) {
            model.addRow(new Object[]{device.getId(), device.getName(), device.getPrice(), device.getCount(),
                device.getStartYear(), device.getDeadYear(), device.getManufacturer()});
        }
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public Device getDevice() {
        return new Device(Integer.valueOf(txtID.getText()), txtName.getText(), Float.valueOf(txtPrice.getText()),
                Integer.valueOf(txtCount.getText()), txtStartYear.getText(), txtDeadYear.getText(), txtManufacturer.getText());
    }

    public void addDevice(Device device) {
        model.addRow(new Object[]{device.getId(), device.getName(), device.getPrice(), device.getCount(),
            device.getStartYear(), device.getDeadYear(), device.getManufacturer()});
    }

    public void updateDevice(int row, Device device) {
        model.setValueAt(device.getId(), row, 0);
        model.setValueAt(device.getName(), row, 1);
        model.setValueAt(device.getPrice(), row, 2);
        model.setValueAt(device.getCount(), row, 3);
        model.setValueAt(device.getStartYear(), row, 4);
        model.setValueAt(device.getDeadYear(), row, 5);
        model.setValueAt(device.getManufacturer(), row, 6);
    }

    public void deleteDevice(int row) {
        model.removeRow(row);
    }

    public void deleteTable() {
        int size = model.getRowCount();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                model.removeRow(0);
            }
        }

    }

    // kiểm tra file có object lưu vào hay chưa
    public boolean hasObject(String filePath) {
        FileInputStream fi;
        boolean check = true;
        try {
            fi = new FileInputStream(filePath);
            ObjectInputStream inStream = new ObjectInputStream(fi);
            if (inStream.readObject() == null) {
                check = false;
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            check = false;
        } catch (IOException e) {
            check = false;
        } catch (ClassNotFoundException e) {
            check = false;
            e.printStackTrace();
        }
        return check;
    }
//them device

    public void writeDevice(Device device, String filePath) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            // nếu file chưa lưu object nào thì ghi bình thường
            if (!hasObject(filePath)) {
                fout = new FileOutputStream(filePath);
                oos = new ObjectOutputStream(fout);
            } else {
                fout = new FileOutputStream(filePath, true);
                oos = new ObjectOutputStream(fout) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }
            Date date = new Date();
            long time = date.getTime();
            oos.writeObject(time);
            oos.writeObject(device);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//sửa device

    public void writeUpdateDevice(UpdateDevice device, String filePath) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            // nếu file chưa lưu object nào thì ghi bình thường
            if (!hasObject(filePath)) {
                fout = new FileOutputStream(filePath);
                oos = new ObjectOutputStream(fout);
            } else {
                fout = new FileOutputStream(filePath, true);
                oos = new ObjectOutputStream(fout) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }
            Date date = new Date();
            long time = date.getTime();
            oos.writeObject(time);
            oos.writeObject(device);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//ghi file xóa device

    public void writeDeleteDevice(int id, String filePath) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            // nếu file chưa lưu object nào thì ghi bình thường
            if (!hasObject(filePath)) {
                fout = new FileOutputStream(filePath);
                oos = new ObjectOutputStream(fout);
            } else {
                fout = new FileOutputStream(filePath, true);
                oos = new ObjectOutputStream(fout) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }
            Date date = new Date();
            long time = date.getTime();
            oos.writeObject(time);
            oos.writeObject(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStartYear = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDeadYear = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSynchronize = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtManufacturer = new javax.swing.JTextField();
        rdOnline = new javax.swing.JRadioButton();
        rdOffline = new javax.swing.JRadioButton();
        canvas1 = new java.awt.Canvas();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "Giá", "Số lượng", "Năm sản xuất", "Năm hết  hạn", "Hãng sản xuất"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel1.setText("Mã sản phẩm");

        jLabel2.setText("Tên sản phẩm");

        jLabel3.setText("Giá");

        jLabel4.setText("Số lượng");

        jLabel5.setText("Năm sản xuất");

        jLabel6.setText("Năm hết hạn");

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSynchronize.setText("Đồng bộ dữ liệu");
        btnSynchronize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSynchronizeActionPerformed(evt);
            }
        });

        jLabel7.setText("Hãng sản xuất");

        buttonGroup1.add(rdOnline);
        rdOnline.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdOnline.setSelected(true);
        rdOnline.setText("online");
        rdOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdOnlineActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdOffline);
        rdOffline.setText("offline");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtName)
                                        .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(45, 45, 45)
                                        .addComponent(txtStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(69, 69, 69)
                                        .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDeadYear, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSynchronize))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(rdOnline)
                .addGap(38, 38, 38)
                .addComponent(rdOffline)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDeadYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSynchronize, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdOnline)
                        .addComponent(rdOffline))
                    .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (txtID.getText().isEmpty()) {
            showMessage("Không được để ID trống!");
        } else {
            Device device = getDevice();
            if (rdOnline.isSelected()) {
                if (new ClientDeviceControl(this).addAction(device)) {
                    addDevice(getDevice());
                    showMessage("Thêm sản phẩm thành công!");
                } else {
                    showMessage("Không thêm được sản phẩm vui lòng kiểm tra lại ID!");
                }
            } else {
                writeDevice(device, fileName);
                showMessage("Dữ liệu đã được lưu vào file để đồng bộ");
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            UpdateDevice update = new UpdateDevice(idBeforUpdate, getDevice());
            int row = table.getSelectedRow();
            if (rdOnline.isSelected()) {
                if (new ClientDeviceControl(this).updateAction(update)) {
                    updateDevice(row, getDevice());
                    showMessage("Update thành công!");
                } else {
                    showMessage("Update không thành công, vui lòng thử lại!");
                }
            } else {
                writeUpdateDevice(update, fileName);
                showMessage("Dữ liệu đã được lưu vào file để đồng bộ");
            }

        } catch (Exception e) {
            showMessage(e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int row = table.getSelectedRow();
        txtID.setText(Integer.toString((int) table.getValueAt(row, 0)));
        txtName.setText((String) table.getValueAt(row, 1));
        txtPrice.setText(Float.toString((float) table.getValueAt(row, 2)));
        txtCount.setText(Integer.toString((int) table.getValueAt(row, 3)));
        txtStartYear.setText((String) table.getValueAt(row, 4));
        txtDeadYear.setText((String) table.getValueAt(row, 5));
        txtManufacturer.setText((String) table.getValueAt(row, 6));

        //lấy id trước khi thay đổi để chuyển qua server xử lý (chức năng update cho phép thay đổi id)
        idBeforUpdate = (int) table.getValueAt(row, 0);
    }//GEN-LAST:event_tableMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        int click = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa không?");
        if (click == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            if (rdOnline.isSelected()) {
                if (new ClientDeviceControl(this).deleteAction(id)) {
                    deleteDevice(row);
                    showMessage("Xóa sản phẩm thành công!");
                } else {
                    showMessage("Không xóa được sản phẩm, vui lòng thử lại!");
                }
            } else {
                writeDeleteDevice(id, fileName);
                showMessage("Dữ liệu đã được lưu vào file để đồng bộ");
            }

        }
    }//GEN-LAST:event_btnDeleteActionPerformed
// đọc file vào TreeMap 

    public void readFile(Map<Long, Object> map) {
        File file = new File(fileName);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            while (true) {
                Object obTime = ois.readObject();  // đọc thời gian
                long time = (long) obTime;
                Object ob = ois.readObject();
                map.put(time, ob);
            }
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file.delete();
        }
    }
    private void btnSynchronizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSynchronizeActionPerformed
        // TODO add your handling code here:
        ClientDeviceControl clientControl = new ClientDeviceControl(this);
        // create 1 treemap để lưu thời gian và đối tượng trong file
        Map<Long, Object> map = new TreeMap<Long, Object>();
        // Đọc dữ liệu file
        readFile(map);
        //Đồng bộ lên server
        ArrayList<Device> listAdd = new ArrayList<>();
        ArrayList<Device> listErrAdd = new ArrayList<>();
        ArrayList<UpdateDevice> listUpdate = new ArrayList<>();
        ArrayList<UpdateDevice> listErrUpdate = new ArrayList<>();
        ArrayList<Integer> listDelete = new ArrayList<>();
        ArrayList<Integer> listErrDelete = new ArrayList<>();
        if (map.size() > 0) {
            for (Entry<Long, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Device) {
                    Device device = (Device) entry.getValue();
                    if (clientControl.addAction(device)) {
                        listAdd.add(device);
                    } else {
                        listErrAdd.add(device);
                    }
                }
                if (entry.getValue() instanceof UpdateDevice) {
                    UpdateDevice update = (UpdateDevice) entry.getValue();
                    if (clientControl.updateAction(update)) {
                        listUpdate.add(update);
                    } else {
                        listErrUpdate.add(update);
                    }
                }
                if (entry.getValue() instanceof Integer) {
                    int id = (int) entry.getValue();
                    if (clientControl.deleteAction(id)) {
                        listDelete.add(id);
                    } else {
                        listErrDelete.add(id);
                    }
                }
            }
            // create thông báo sau khi đồng bộ
            String message = "Đã đồng bộ dữ liệu\n";
            if (listAdd.size() != 0) {
                String id = "";
                for (Device d : listAdd) {
                    id += d.getId() + " ";
                }
                message += listAdd.size() + " thiết bị được thêm có ID: " + id + "\n";
            }
            if (listUpdate.size() != 0) {
                String id = "";
                for (UpdateDevice u : listUpdate) {
                    id += u.getDevice().getId() + " ";
                }
                message += listUpdate.size() + " thiết bị được update có ID: " + id + "\n";
            }
            if (listDelete.size() != 0) {
                String id = "";
                for (Integer i : listDelete) {
                    id += i + " ";
                }
                message += listDelete.size() + " thiết bị được xóa có ID: " + id + "\n";
            }
            if (listErrAdd.size() != 0) {
                String id = "";
                for (Device d : listErrAdd) {
                    id += d.getId() + " ";
                }
                message += "Không thêm được " + listErrAdd.size() + " thiết bị có ID: " + id + "\n";
            }
            if (listErrUpdate.size() != 0) {
                String id = "";
                for (UpdateDevice u : listErrUpdate) {
                    id += u.getDevice().getId() + " ";
                }
                message += "Không sửa được " + listErrUpdate.size() + " thiết bị có ID cũ: " + id + "\n";
            }
            if (listErrDelete.size() != 0) {
                String id = "";
                for (Integer i : listErrDelete) {
                    id += i + " ";
                }
                message += "Không xóa được " + listErrDelete.size() + " thiết bị có ID: " + id + "\n";
            }
            //hiển thị thông báo
            showMessage(message);
            //load lại dữ liệu từ server về bảng
            deleteTable();
            clientControl.load();
        } else {
            showMessage("Không có dữ liệu để đồng bộ!");
        }

    }//GEN-LAST:event_btnSynchronizeActionPerformed

    private void rdOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdOnlineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdOnlineActionPerformed

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
            java.util.logging.Logger.getLogger(ViewDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewDevice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSynchronize;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private java.awt.Canvas canvas1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdOffline;
    private javax.swing.JRadioButton rdOnline;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCount;
    private javax.swing.JTextField txtDeadYear;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtManufacturer;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStartYear;
    // End of variables declaration//GEN-END:variables

}
