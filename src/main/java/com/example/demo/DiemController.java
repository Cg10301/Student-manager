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
import javafx.util.StringConverter;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DiemController implements Initializable {

    private ArrayList<BangDiem> listTempBD = new ArrayList<BangDiem>();
    private ArrayList<MonHoc> listTempMH;
    private ArrayList<SinhVien> listTempSV;

    @FXML
    private TableView<BangDiem> tableBD;

    @FXML
    private TableColumn<BangDiem, String> maSVColumn;

    @FXML
    private TableColumn<BangDiem, String> tenSVColumn;

    @FXML
    private TableColumn<BangDiem, Double> diemQTColumn;

    @FXML
    private TableColumn<BangDiem, Double> diemHKColumn;

    @FXML
    private TableColumn<BangDiem, Double> diemTKColumn;

    @FXML
    private TableColumn<BangDiem, String> maMHColumn;

    @FXML
    private TextField txtDiemQT;

    @FXML
    private TextField txtDiemHK;


    @FXML
    private Label lbMaSV;

    @FXML
    private Label lbTenSV;

    @FXML
    private Label lbMaMH;

    @FXML
    private ComboBox<MonHoc> cbxMonHoc;

    private ObservableList<BangDiem> bdList;
    private ObservableList<MonHoc> mhList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        BangDiem bd1 = new BangDiem("A34921", "Dao Minh Cuong", "CTI001");
//        BangDiem bd2 = new BangDiem("A34921", "Dao Minh Cuong", "CTI002");
//        listTempBD.add(bd1);
//        listTempBD.add(bd2);
//        write(listTempBD);
        listTempBD = (ArrayList<BangDiem>)read("bangdiem.dat");
        listTempMH = (ArrayList<MonHoc>)read("mydataMH.dat") ;


        mhList = FXCollections.observableArrayList(listTempMH);
        cbxMonHoc.setItems(mhList);
        cbxMonHoc.setConverter(new StringConverter<MonHoc>() {
            @Override
            public String toString(MonHoc object) {
                if(object != null){
                    return object.getMaMonHoc()+" "+object.getTenMonHoc();
                }
                return "";
            }

            @Override
            public MonHoc fromString(String string) {
                return null;
            }
        });

        bdList = FXCollections.observableArrayList(listTempBD);
        maSVColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, String>("maSV"));
        tenSVColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, String>("tenSV"));
        maMHColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, String>("maMH"));
        diemQTColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, Double>("diemQT"));
        diemHKColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, Double>("diemHK"));
        diemTKColumn.setCellValueFactory(new PropertyValueFactory<BangDiem, Double>("diemTK"));
        tableBD .setItems(bdList);

        maSVColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBD .getSortOrder().add(maSVColumn);
        tableBD.sort();
    }

    public void selectItem(MouseEvent e){
       if(tableBD.getSelectionModel().getSelectedItem() != null) {
           BangDiem selected = tableBD.getSelectionModel().getSelectedItem();
           lbMaSV.setText(selected.getMaSV());
           lbTenSV.setText(selected.getTenSV());
           lbMaMH.setText(selected.getMaMH());
           if (selected.getDiemQT() == null)
               txtDiemQT.setText(null);
           if (selected.getDiemHK() == null)
               txtDiemHK.setText(null);
           txtDiemQT.setText(selected.getDiemQT().toString());
           txtDiemHK.setText(selected.getDiemHK().toString());
       }
    }


    public void nhapDiem(ActionEvent e){
        boolean check = true;
        if(txtDiemQT.getText() == null || txtDiemHK.getText() == null
        || Objects.equals(lbMaSV.getText(), "Mã sinh viên") || Objects.equals(lbTenSV.getText(), "Họ tên")
                || Objects.equals(lbMaMH.getText(), "Mã môn")){
            check = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ các thông tin!" );
            alert.showAndWait();
        }

        if(check == true) {
            BangDiem newBD = new BangDiem();
            newBD.setMaSV(lbMaSV.getText());
            newBD.setTenSV(lbTenSV.getText());
            newBD.setMaMH(lbMaMH.getText());
            newBD.setDiemQT(Double.parseDouble(txtDiemQT.getText()));
            newBD.setDiemHK(Double.parseDouble(txtDiemHK.getText()));
            Double dtb = Double.parseDouble(txtDiemQT.getText())*0.4+Double.parseDouble(txtDiemHK.getText())*0.6;
            newBD.setDiemTK((double) Math.round(dtb * 100) / 100);
            for(int i = 0; i< bdList.size(); i++){
                if(Objects.equals(bdList.get(i).getMaSV(), newBD.getMaSV())
                        && Objects.equals(bdList.get(i).getMaMH(), newBD.getMaMH())){
                    bdList.set(i, newBD);
                    break;
                }
            }


            List<BangDiem> list = bdList.stream().collect(Collectors.toList());
            // converting list of string into string[] array.
            write(list);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Nhập điểm thành công!");
            alert.showAndWait();

            lbMaSV.setText("Mã sinh viên");
            lbTenSV.setText("Họ tên");
            lbMaMH.setText("Mã môn");
            txtDiemQT.setText("");
            txtDiemQT.setPromptText("Điểm quá trình");
            txtDiemHK.setText("");
            txtDiemHK.setPromptText("Điểm học kì");
        }
    }

    public void capNhatDiem(ActionEvent e){
        listTempSV = (ArrayList<SinhVien>)read("mydataSV.dat") ;
        listTempBD = (ArrayList<BangDiem>)read("bangdiem.dat") ;
        for(int i =0;  i< listTempSV.size();i++){
            List<Double> listDiem =  new ArrayList<Double>();
            for(BangDiem bd : listTempBD){
                if(Objects.equals(bd.getMaSV(), listTempSV.get(i).getMaSV())){
                    listDiem.add(bd.getDiemTK());
                }
            }
            Double dtb  = 0d;
            for (Double aDouble : listDiem) dtb += aDouble;
            dtb = dtb/listDiem.size();
            listTempSV.get(i).setDiemTB((double) Math.round(dtb * 100) / 100);
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mydataSV.dat"));
            oos.writeObject(listTempSV);
            oos.close();

        } catch (IOException ex) {
            System.out.println("Loi ghi file: "+ex);
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Cập nhật điểm thành công!" );
        alert.showAndWait();
    }
//    public void delete(ActionEvent e){
//        BangDiem selected = tableBD.getSelectionModel().getSelectedItem();
//        bdList.remove(selected);
//
//        List<BangDiem> list = bdList.stream().collect(Collectors.toList());
//
//        write(list);
//    }

    public void dsSVtheoMH(ActionEvent e){
        ArrayList<BangDiem> listTempBD = (ArrayList<BangDiem>)read("bangdiem.dat");
        String maMH = cbxMonHoc.getValue().getMaMonHoc();
        ArrayList<BangDiem> dsSV = new ArrayList<>();
        for(BangDiem bd : listTempBD){
            if(Objects.equals(bd.getMaMH(), maMH))
                dsSV.add(bd);
        }
        ObservableList<BangDiem> bdListTemp = FXCollections.observableArrayList(dsSV);
        tableBD.setItems(bdListTemp);
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
    public static void write(List<BangDiem> list){
        try {
            //Bước 1: Tạo đối tượng luồng và liên kết nguồn dữ liệu
            ;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bangdiem.dat"));

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
