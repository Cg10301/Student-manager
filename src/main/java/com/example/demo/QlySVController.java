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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class QlySVController implements Initializable {

    //private List<SinhVien> listTemp =  new ArrayList<SinhVien>() ;
    @FXML
    private TableView<SinhVien> table;

    @FXML
    private TableColumn<SinhVien, String> maSVColumn;

    @FXML
    private TableColumn<SinhVien, String> hoTenColumn;

    @FXML
    private TableColumn<SinhVien, String> ngaySinhColumn;

    @FXML
    private TableColumn<SinhVien, String> gioiTinhColumn;

    @FXML
    private TableColumn<SinhVien, String> diaChiColumn;

    @FXML
    private TableColumn<SinhVien, Double> diemTBColumn;

    @FXML
    private TextField txtMaSV;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtNgaySinh;

    @FXML
    private TextField txtDiaChi;

    @FXML TextField txtDiemTB;

    @FXML
    private TextField txtGioiTinh;

    @FXML
    private TextField txtLocDiem;

    private ObservableList<SinhVien> svList;
    private ObservableList<SinhVien> svList1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<SinhVien> listTemp = new ArrayList<SinhVien>();
            listTemp = (ArrayList<SinhVien>)read();

            svList = FXCollections.observableArrayList(listTemp);
            svList1=svList;
            maSVColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("maSV"));
            hoTenColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("hoTen"));
            ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("ngaySinh"));
            gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("gioiTinh"));
            diaChiColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, String>("diaChi"));
            diemTBColumn.setCellValueFactory(new PropertyValueFactory<SinhVien, Double>("diemTB"));
            table.setItems(svList);
        }catch (Exception ex){
            System.out.println("Loi : " + ex);
            ex.printStackTrace();
        }
        maSVColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(maSVColumn);
        table.sort();

//        FilteredList<SinhVien> filteredData = new FilteredList<>(svList, b -> true);
//        txtSearch.textProperty().addListener((observable, oldValue, newValue) ->{
//            filteredData.setPredicate(sinhVien -> {
//                if(newValue  == null || newValue.isEmpty()){
//                    return true;
//                }
//
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if(sinhVien.getMaSV().toLowerCase().indexOf(lowerCaseFilter) != -1){
//                    return  true;
//                }else if(sinhVien.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1){
//                    return true;
//                }else{
//                    return false;
//                }
//
//            });
//        });
//
//        SortedList<SinhVien> sortedData =  new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(table.comparatorProperty());
//        table.setItems(sortedData);

    }



    public void addSV(ActionEvent e){
        boolean check = true;

        if(txtMaSV.getText() == "" || txtHoTen.getText() == ""
        || txtNgaySinh.getText() == "" || txtDiaChi.getText() == ""
        || txtGioiTinh.getText() == "" ){
            check = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ các thông tin!" );
            alert.showAndWait();
        }

        if(check == true) {
        SinhVien newSV = getNewSV();
            TableColumn<SinhVien, String> column = maSVColumn;
            List<String> columnData = new ArrayList<>();
            for (SinhVien item : table.getItems()) {
                columnData.add(column.getCellObservableValue(item).getValue());
            }
            if(columnData.contains(newSV.getMaSV())) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Mã sinh viên đã tồn tại");
                alert.showAndWait();
            }
            else {

                    svList.add(newSV);
                    svList1=svList;
                    table.setItems(svList);

                    List<SinhVien> list = svList.stream().collect(Collectors.toList());
                    write(list);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm sinh viên thành công!");
                    alert.showAndWait();

                }
                setDefault();
            }

    }
    public  void setDefault(){

        txtMaSV.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtDiemTB.setText("");
        txtGioiTinh.setText("");
        txtMaSV.setPromptText("Mã sinh viên");
        txtHoTen.setPromptText("Họ tên");
        txtNgaySinh.setPromptText("Ngày sinh");
        txtDiaChi.setPromptText("Địa chỉ");
        txtDiemTB.setPromptText("Điểm trung bình");
        txtGioiTinh.setPromptText("Giới tính");
    }
    public void deleteSV(ActionEvent e){
        SinhVien selected = table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Bạn có muốn xóa sinh viên này không?");
        alert.setContentText(null);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {

        } else if (option.get() == ButtonType.OK) {
            if(selected == null){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Warning");
                alert1.setHeaderText(null);
                alert1.setContentText("Hãy chọn sinh viên để xóa!");
                alert1.showAndWait();
            }else{

                svList.removeAll(selected);

                svList1=svList;
                table.setItems(svList);
                List<SinhVien> list = svList.stream().collect(Collectors.toList());
                write(list);


                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Xóa sinh viên thành công!");

                alert1.showAndWait();
                setDefault();
            }
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }


    }

    public void updateSV(ActionEvent e){
        boolean check = true;
        if(txtMaSV.getText() == "" || txtHoTen.getText() == ""
                || txtNgaySinh.getText() == "" || txtDiaChi.getText() == ""
                || txtGioiTinh.getText() == "" ){
            check = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ các thông tin!");
            alert.showAndWait();
        }
        if(check == true) {
            SinhVien newSV = getNewSV();
            int selected = table.getSelectionModel().getSelectedIndex();


            svList.set(selected, newSV);

            svList1=svList;
            table.setItems(svList);
            List<SinhVien> list = svList.stream().collect(Collectors.toList());
            write(list);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Sửa thông tin sinh viên thành công!");

            alert.showAndWait();
            }
            setDefault();

    }

    public SinhVien getNewSV(){
        SinhVien newSV = new SinhVien();
        newSV.setMaSV(txtMaSV.getText());
        newSV.setHoTen(txtHoTen.getText());
        newSV.setNgaySinh(txtNgaySinh.getText());
        newSV.setDiaChi(txtDiaChi.getText());
        newSV.setGioiTinh(txtGioiTinh.getText());
        if(txtDiemTB.getText() =="")
            newSV.setDiemTB(0d);
        else
            newSV.setDiemTB(Double.parseDouble(txtDiemTB.getText()));
        return newSV;
    }
    public void selectItem(MouseEvent e){
        SinhVien selected = table.getSelectionModel().getSelectedItem();
        if(selected != null){
            txtMaSV.setText(selected.getMaSV());
            txtHoTen.setText(selected.getHoTen());
            txtNgaySinh.setText(selected.getNgaySinh());
            txtDiaChi.setText(selected.getDiaChi());
            Double dtb = selected.getDiemTB();
            if(dtb == 0)
                txtDiemTB.setText("");
            else
                txtDiemTB.setText(dtb.toString());
            txtGioiTinh.setText(selected.getGioiTinh());
        }

    }

    public void mouseClick(MouseEvent e){
        setDefault();
    }

    public void locDiem(ActionEvent e){
        Double dtb = 0d;

        if(txtLocDiem.getText() != "")
        {dtb = Double.parseDouble(txtLocDiem.getText());}

        List<SinhVien> list = svList.stream().collect(Collectors.toList());
        SinhVien[] svArrTemp = list.toArray(new SinhVien[list.size()]);
        list = new ArrayList<>();
        for(int i = 0; i< svArrTemp.length;i++){
            if(svArrTemp[i].getDiemTB() >= dtb )
                list.add(svArrTemp[i]);
        }
        SinhVien[] svlist = list.toArray(new SinhVien[list.size()]);

        svList1 = FXCollections.observableArrayList(svlist);
        table.setItems(svList1);

    }

    public void exit(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene tableViewScene =  new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public static Object read(){
        FileInputStream FIn = null;
        ObjectInputStream OIn = null;
        Object read = null;
        try {
            //Khở tạo đối tượng với địa chỉ truyền vào
            FIn = new FileInputStream("mydataSV.dat");
            OIn = new ObjectInputStream(FIn);
            read = OIn.readObject();
            return read;
        } catch (Exception e) {
            System.err.println(e);
        }
        finally {if (read != null)
            System.out.println("");
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
    public static void write(List<SinhVien> list){
        try {
            //Bước 1: Tạo đối tượng luồng và liên kết nguồn dữ liệu
            ;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mydataSV.dat"));

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
