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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.alejandrocuxun.dao.Conexion;
import org.alejandrocuxun.models.Compras;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuComprasController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfCompraId, tfFechaCompra, tfTotalCompra;
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId, colFechaCompra, colTotalCompra;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfCompraId.getText().equals("")){
                agregarCompras();
                cargarDatos();
            }else{
                editarCompras();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    

    public void cargarDatos(){
        tblCompras.setItems(listarCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("ticketSoporteId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, String>("estatus"));
    }
    
    public void vaciarForm(){
        tfCompraId.clear();
        tfFechaCompra.clear();
        tfTotalCompra.clear();
    }
    
    @FXML
    public void cargarForm(){
        Compras ts = (Compras)tblCompras.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfCompraId.setText(Integer.toString(ts.getCompraId()));
            tfFechaCompra.setText(Integer.toString(ts.getCompraId()));
            tfTotalCompra.setText(Integer.toString(ts.getCompraId()));
        }
    }
    
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("tfCompraId");
                int fechaCompra = resultSet.getInt("tfFechaCompra");
                double totalCompra = resultSet.getDouble("tfTotalCompra");
                
                compras.add(new Compras(compraId, fechaCompra, totalCompra));
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
        
        return FXCollections.observableList(compras);
    }
    
    public void agregarCompras(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompras(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCompraId.getText());
            statement.setString(1, tfFechaCompra.getText());
            statement.setString(1, tfTotalCompra.getText());
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
    
    
    public void editarCompras(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCompras(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setInt(2, Integer.parseInt(tfFechaCompra.getText()));
            statement.setDouble(1, Integer.parseInt(tfTotalCompra.getText()));
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
