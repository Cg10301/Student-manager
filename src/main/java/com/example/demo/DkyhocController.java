package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DkyhocController implements Initializable {
    private ArrayList<SinhVien> listTempSV;
    private ArrayList<MonHoc> listTempMH;
    @FXML
    private TableView<SinhVien> tableSV;
    @FXML
    private TableView<MonHoc> tableMH;

    @FXML
    private TableColumn<SinhVien, String> maSVColumn;

    @FXML
    private TableColumn<SinhVien, String> tenSVColumn;

    private ObservableList<SinhVien> svList;

    @FXML
    private TableColumn<MonHoc, String> maMHColumn;

    @FXML
    private TableColumn<MonHoc, String> tenMHColumn;

    private ObservableList<MonHoc> mhList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listTempSV = (ArrayList<SinhVien>)read("mydataSV.dat");
        listTempMH = (ArrayList<MonHoc>)read("mydataMH.dat");

        //Hien bang sinh vien
        svList = FXCollections.observableArrayList(listTempSV);
        maSVColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("maSV"));
        tenSVColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("hoTen"));
        tableSV .setItems(svList);

        maSVColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableSV .getSortOrder().add(maSVColumn);
        tableSV.sort();
        //Hien bang mon hoc
        mhList = FXCollections.observableArrayList(listTempMH);
        maMHColumn.setCellValueFactory(new PropertyValueFactory<MonHoc, String>("maMonHoc"));
        tenMHColumn.setCellValueFactory(new PropertyValueFactory<MonHoc, String>("tenMonHoc"));
        tableMH .setItems(mhList);

        maMHColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableMH .getSortOrder().add(maMHColumn);
        tableMH.sort();
    }

    public void dkHoc(ActionEvent e){
        if(tableSV.getSelectionModel().getSelectedItem()  != null && tableMH.getSelectionModel().getSelectedItem() != null) {
            SinhVien svSelected = tableSV.getSelectionModel().getSelectedItem();
            MonHoc mhSelected = tableMH.getSelectionModel().getSelectedItem();
            ArrayList<BangDiem> listTempBD = (ArrayList<BangDiem>)read("bangdiem.dat");

            BangDiem newBD = new BangDiem();
            newBD.setMaSV(svSelected.getMaSV());
            newBD.setTenSV(svSelected.getHoTen());
            newBD.setMaMH(mhSelected.getMaMonHoc());
            listTempBD.add(newBD);
            // converting list of string into string[] array.
            write(listTempBD);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Đăng ký thành công!");
            alert.showAndWait();
            locMonHoc(null);

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("Chọn đối tượng để đăng ký học!");
            alert.showAndWait();
        }
    }


    public void locMonHoc(MouseEvent e){
        ArrayList<String> maMHColumn = new ArrayList<>();
        for(MonHoc mh : listTempMH){
            maMHColumn.add(mh.getMaMonHoc());
        }
        if(tableSV.getSelectionModel().getSelectedItem() != null) {
            String maSV = tableSV.getSelectionModel().getSelectedItem().getMaSV();
            ArrayList<BangDiem> listTempBD = (ArrayList<BangDiem>) read("bangdiem.dat");
            ArrayList<BangDiem> listBDtheoMaSV = new ArrayList<BangDiem>();
            for (BangDiem bd : listTempBD) {
                if (Objects.equals(bd.getMaSV(), maSV)) {
                    listBDtheoMaSV.add(bd);
                }
            }
            ArrayList<MonHoc> monSVdaHoc = new ArrayList<>();
            for (BangDiem bd : listBDtheoMaSV) {
                if (maMHColumn.contains(bd.getMaMH())) {
                    for (MonHoc mh : mhList) {
                        if (Objects.equals(mh.getMaMonHoc(), bd.getMaMH())) {
                            monSVdaHoc.add(mh);
                        }
                    }
                }
            }

            ArrayList<MonHoc> monSVchuaHoc = new ArrayList<>();
            for (MonHoc mh : listTempMH) {
                monSVchuaHoc.add(mh);
            }
            monSVchuaHoc.removeIf(t -> monSVdaHoc.contains(t));
            ObservableList<MonHoc> mhListTemp = FXCollections.observableArrayList(monSVchuaHoc);
            tableMH.setItems(mhListTemp);
        }
    }

    public void exit(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene tableViewScene =  new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public static Object read(String fileName){
        FileInputStream FIn = null;
        ObjectInputStream OIn = null;
        Object read = null;
        try {
            //Khở tạo đối tượng với địa chỉ truyền vào
            FIn = new FileInputStream(fileName);
            OIn = new ObjectInputStream(FIn);
            read = OIn.readObject();
            return read;
        } catch (Exception e) {
            System.err.println(e);
        }
        finally {if (read != null)
            System.out.println("DOC FILE THANH CONG!");
        else System.out.println("DOC FILE THAT BAI");
            try {
                FIn.close();
                OIn.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return null;
    }
    public static void write(List<BangDiem> list){
        try {
            //Bước 1: Tạo đối tượng luồng và liên kết nguồn dữ liệu
            ;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bangdiem.dat"));
            //sua sv

            //Bước 2: Ghi mảng đối tượng vào file
            oos.writeObject(list);
            //Bước 3: Đóng luồng
            oos.close();

        } catch (IOException ex) {
            System.out.println("Loi ghi file: "+ex);
            ex.printStackTrace();
        }

    }
}
