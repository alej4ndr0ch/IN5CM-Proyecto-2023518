/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.alejandrocuxun.dao.Conexion;
import org.alejandrocuxun.models.DetalleCompra;
import org.alejandrocuxun.models.Compras;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuDetalleCompraController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfDetalleCompraId, tfCantidad;
    @FXML
    ComboBox cmbProductoId, cmbCompraId;
    @FXML
    TableView tblDetalleCompra;
    @FXML
    TableColumn colDetalleCompraId, colCantidad, colProductoId, colCompraId;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfDetalleCompraId.getText().equals("")){
                agregarDetalleCompra();
                cargarDatos();
            }else{
                editarDetalleCompra();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarCmbProductoId();
        cargarCmbCompraId();
    }    

    public void cargarDatos(){
        tblDetalleCompra.setItems(listarDetalleCompra());
        colDetalleCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("detalleCompraId"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("Cantidad"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("productoId"));
        colCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("compraId"));
    }
    
    public void cargarCmbProductoId(){
        cmbProductoId.getItems().add("1");
    }
    
    public void cargarCmbCompraId(){
        cmbCompraId.getItems().add("1");
    }
    
    public void vaciarForm(){
        tfDetalleCompraId.clear();
        tfCantidad.clear();
        cmbProductoId.getSelectionModel().clearSelection();
        cmbCompraId.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        DetalleCompra ts = (DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfDetalleCompraId.setText(Integer.toString(ts.getDetalleCompraId()));
            tfCantidad.setText(Integer.toString(ts.getDetalleCompraId()));
            cmbProductoId.getSelectionModel().select(obtenerIndexCliente());
            cmbCompraId.getSelectionModel().select(obtenerIndexCliente());
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        int compraTbl = ((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCompraId(); 
        for(int i = 0 ; i <= cmbCompraId.getItems().size() ; i++){
            String compraCmb = cmbCompraId.getItems().get(i).toString();
           
        }
        
        return index;
    }
    
    public ObservableList<DetalleCompra> listarDetalleCompra(){
        ArrayList<DetalleCompra> detalleCompra = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int detalleCompraId = resultSet.getInt("detalleCompraId");
                int Cantidad = resultSet.getInt("Cantidad");
                int productoId = resultSet.getInt("productoId");
                int compraId = resultSet.getInt("compraId");
                
                detalleCompra.add(new DetalleCompra(detalleCompraId, Cantidad, productoId, compraId));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableList(detalleCompra);
    }
    
    public void agregarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleCompra(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfDetalleCompraId.getText());
            statement.setString(1, tfCantidad.getText());
            statement.setInt(3, Integer.parseInt(cmbProductoId.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(3, Integer.parseInt(cmbCompraId.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDetalleCompraId.getText()));
            statement.setString(2, tfCantidad.getText());
            statement.setString(3, (cmbProductoId.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(5, Integer.parseInt(cmbCompraId.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
