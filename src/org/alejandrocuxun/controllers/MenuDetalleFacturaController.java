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
import org.alejandrocuxun.models.DetalleFactura;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuDetalleFacturaController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfDetalleFacturaId, tfFacturaId, tfProductoId;
    @FXML
    TableView tblDetalleFactura;
    @FXML
    TableColumn colDetalleFacturaId, colFacturaId, colProductoId;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfDetalleFacturaId.getText().equals("")){
                agregarDetalleFactura();
                cargarDatos();
            }else{
                editarDetalleFactura();
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
        tblDetalleFactura.setItems(listarDetalleFactura());
        colDetalleFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("colDetalleFacturaId"));
        colFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("colFacturaId"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("colProductoId"));
    }
    
    public void vaciarForm(){
        tfDetalleFacturaId.clear();
        tfFacturaId.clear();
        tfProductoId.clear();
    }
    
    @FXML
    public void cargarForm(){
        DetalleFactura ts = (DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfDetalleFacturaId.setText(Integer.toString(ts.getDetalleFacturaId()));
            tfFacturaId.setText(Integer.toString(ts.getDetalleFacturaId()));
            tfProductoId.setText(Integer.toString(ts.getDetalleFacturaId()));
        }
    }
    
    public ObservableList<DetalleFactura> listarDetalleFactura(){
        ArrayList<DetalleFactura> detalleFactura = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int detalleFacturaId = resultSet.getInt("tfDetalleFacturaId");
                int facturaId = resultSet.getInt("facturaId");
                int productoId = resultSet.getInt("tfProductoId");
                
                detalleFactura.add(new DetalleFactura(detalleFacturaId, facturaId, productoId));
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
        
        return FXCollections.observableList(detalleFactura);
    }
        
    public void agregarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleFactura(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfDetalleFacturaId.getText());
            statement.setString(1, tfFacturaId.getText());
            statement.setString(1, tfProductoId.getText());
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
    
    public void editarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDetalleFacturaId.getText()));
            statement.setInt(2, Integer.parseInt(tfFacturaId.getText()));
            statement.setInt(3, Integer.parseInt(tfProductoId.getText()));
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
