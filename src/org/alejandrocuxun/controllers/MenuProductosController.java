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
import org.alejandrocuxun.models.Productos;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuProductosController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfNombreProducto, tfDescripcionProducto, tfCantidadProducto, tfPrecioVentaUnitario, tfPrecioVentaMayor, tfPrecioCompra;
    @FXML
    ComboBox cmbDistribuidorId, cmbCategoriaProductoId;
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colNombreProducto, colDescripcionProducto, colCantidad, colPrecioUnitario, colPrecioMayor, colPrecioCompra, colDistribuidorId, colCategoriaProductoId;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfNombreProducto.getText().equals("")){
                agregarProductos();
                cargarDatos();
            }else{
                editarProductos();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarCmbDistribuidorId();
        cargarCmbCategoriaProductoId();
    }    

    public void cargarDatos(){
        tblProductos.setItems(listarProductos());
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("nombreProducto"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos, String>("cantidadProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, String>("precioVentaUnitaria"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("precioVentaMayor"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("precioCompra"));
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("distribuidorId"));
        colCategoriaProductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("categoriaProductoId"));
    }
    
    public void cargarCmbDistribuidorId(){
        cmbDistribuidorId.getItems().add("En proceso");
        cmbDistribuidorId.getItems().add("Finalizado");
    }
    
    public void cargarCmbCategoriaProductoId(){
        cmbCategoriaProductoId.getItems().add("En proceso");
        cmbCategoriaProductoId.getItems().add("Finalizado");
    }
    
    public void vaciarForm(){
        tfNombreProducto.clear();
        tfDescripcionProducto.clear();
        tfCantidadProducto.clear();
        tfPrecioVentaUnitario.clear();
        tfPrecioVentaMayor.clear();
        tfPrecioCompra.clear();
        cmbDistribuidorId.getSelectionModel().clearSelection();
        cmbCategoriaProductoId.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        Productos ts = (Productos)tblProductos.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfNombreProducto.setText(Integer.toString(ts.getCantidadProducto()));
            tfDescripcionProducto.setText(Integer.toString(ts.getCantidadProducto()));
            tfCantidadProducto.setText(Integer.toString(ts.getCantidadProducto()));
            tfPrecioVentaUnitario.setText(Integer.toString(ts.getCantidadProducto()));
            tfPrecioVentaMayor.setText(Integer.toString(ts.getCantidadProducto()));
            tfPrecioCompra.setText(Integer.toString(ts.getCantidadProducto()));
            cmbDistribuidorId.getSelectionModel().select(0);
            cmbCategoriaProductoId.getSelectionModel().select(0);
        }
    }
    
    public ObservableList<Productos> listarProductos(){
        ArrayList<Productos> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadProducto = resultSet.getInt("cantidadProducto");
                int precioVentaUnitaria = resultSet.getInt("precioVentaUnitaria");
                int precioVentaMayor = resultSet.getInt("precioVentaMayor");
                int precioCompra = resultSet.getInt("precioCompra");
                int distribuidorId = resultSet.getInt("distribuidorId");
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                
                productos.add(new Productos(nombreProducto, descripcionProducto, cantidadProducto, precioVentaUnitaria, precioVentaMayor, precioCompra, distribuidorId, categoriaProductoId));
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
        
        return FXCollections.observableList(productos);
    }
    
    public void agregarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProductos(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreProducto.getText());
            statement.setString(2, tfDescripcionProducto.getText());
            statement.setString(3, tfCantidadProducto.getText());
            statement.setString(4, tfPrecioVentaUnitario.getText());
            statement.setString(5, tfPrecioVentaMayor.getText());
            statement.setString(6, tfPrecioCompra.getText());
            statement.setInt(7, Integer.parseInt(cmbDistribuidorId.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(8, Integer.parseInt(cmbCategoriaProductoId.getSelectionModel().getSelectedItem().toString()));
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
    
    public void editarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarProductos(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreProducto.getText());
            statement.setString(2, tfDescripcionProducto.getText());
            statement.setString(3, tfCantidadProducto.getText());
            statement.setString(4, tfPrecioVentaUnitario.getText());
            statement.setString(5, tfPrecioVentaMayor.getText());
            statement.setString(6, tfPrecioCompra.getText());
            statement.setString(7, (cmbDistribuidorId.getSelectionModel().getSelectedItem().toString()));
            statement.setString(8, (cmbCategoriaProductoId.getSelectionModel().getSelectedItem().toString()));
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
