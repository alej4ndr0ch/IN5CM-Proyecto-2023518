/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.controllers;

import java.net.URL;
<<<<<<< HEAD
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
>>>>>>> e2186e805825bd595c799466183545218cd6968b
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
<<<<<<< HEAD
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
=======
>>>>>>> e2186e805825bd595c799466183545218cd6968b
import javafx.scene.control.TextField;
import org.alejandrocuxun.dao.Conexion;
import org.alejandrocuxun.models.Compras;
import org.alejandrocuxun.models.Productos;
import org.alejandrocuxun.systems.Main;
import org.alejandrocuxun.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
<<<<<<< HEAD
 * @author aleja
 */
public class FormDetalleCompraController implements Initializable {
    Main stage;
    int op;
=======
 * @author informatica
 */
public class FormDetalleCompraController implements Initializable {
    private Main stage;
    private int op;
>>>>>>> e2186e805825bd595c799466183545218cd6968b
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
<<<<<<< HEAD
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
=======
   @FXML
    Button btnRegresar,btnGuardar;
   
   @FXML
   TextField tfDetalleFacturaId,tfCantidadCompra;
   
   @FXML
   ComboBox cmbCompras,cmbProductos;
   
   @FXML
>>>>>>> e2186e805825bd595c799466183545218cd6968b
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuComprasView();
        }else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                agregarDetalleCompra();
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                stage.menuComprasView();
            }
        }
    }
    
    
    public void agregarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
<<<<<<< HEAD
            String sql = "CALL sp_AgregarDetalleCompras(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCantidad.getText());
            statement.setInt(2,((Productos)cmbProductoId.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3,((Compras)cmbCompraId.getSelectionModel().getSelectedItem()).getCompraId());
=======
            String sql = "CALL sp_agregarDetalleCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCantidadCompra.getText());
            statement.setInt(2,((Productos)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3,((Compras)cmbCompras.getSelectionModel().getSelectedItem()).getCompraId());
>>>>>>> e2186e805825bd595c799466183545218cd6968b
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
        cmbCompraId.setItems(listarCompras());
        cmbProductoId.setItems(listarProductos());
        
    }
    
    public ObservableList<Productos> listarProductos(){
        ArrayList<Productos> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarDetalleCompras()";
=======
        cmbCompras.setItems(listarCompras());
        cmbProductos.setItems(listarProductos());
        
    }
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarProductos()";
>>>>>>> e2186e805825bd595c799466183545218cd6968b
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
<<<<<<< HEAD
                int cantidadProducto = resultSet.getInt("cantidadProducto");
=======
                int cantidadStock = resultSet.getInt("cantidadStock");
>>>>>>> e2186e805825bd595c799466183545218cd6968b
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
<<<<<<< HEAD
                String distribuidorId = resultSet.getString("distribuidorId");
                String categoriaProductoId = resultSet.getString("categoriaId");
            
                productos.add(new Productos(productoId, nombreProducto, descripcionProducto, cantidadProducto, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductoId));
=======
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
            
                productos.add(new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoria));
>>>>>>> e2186e805825bd595c799466183545218cd6968b
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
    
<<<<<<< HEAD
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
=======
    public ObservableList<Compra> listarCompras(){
        ArrayList<Compra> compras = new ArrayList<>();
>>>>>>> e2186e805825bd595c799466183545218cd6968b
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
<<<<<<< HEAD
                Date fechaCompra = resultSet.getDate("fechaCompra");
                Double totalCompra = resultSet.getDouble("totalCompra");
            
                compras.add(new Compras(compraId, fechaCompra, totalCompra));
=======
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
            
                compras.add(new Compra(compraId, fecha,total));
>>>>>>> e2186e805825bd595c799466183545218cd6968b
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
<<<<<<< HEAD
                if(statement != null){
                    statement.close();
                }
=======
                
                if(statement != null){
                    statement.close();
                }
                
>>>>>>> e2186e805825bd595c799466183545218cd6968b
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        
        return FXCollections.observableList(compras);
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
}
