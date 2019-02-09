/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Controllers.Libraries.Effects;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.dto.Producto;
import model.interfaces.ProductoImp;
import view.ListaProducto;
import view.Main;

/**
 *
 * @author MrRainx
 */
public class ListaProductosCntrl {

    private Main main;
    private ListaProducto view;
    private ProductoImp model;

    private List<Producto> Lista = null;
    private DefaultTableModel modelT;

    public ListaProductosCntrl(Main main, ListaProducto view, ProductoImp model) {
        this.main = main;
        this.view = view;
        this.model = model;
    }

    //Inits
    public void Init() {

        modelT = (DefaultTableModel) view.getTabProductos().getModel();

        main.getContainer().removeAll();
        main.getContainer().add(view);
        main.getContainer().updateUI();

        
        view.getBtnBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnBuscarOnMouseClicked(e);
            }
        });
        
        
        
        cargarTabla();
        InitEfectos();

        view.setVisible(true);
    }

    private void InitEfectos() {
        Color colorSalida = view.getBtnAgregar().getBackground();
        Color colorEntrada = new Color(0, 102, 102);

        Effects.colorHover(view.getBtnAgregar(), colorEntrada, colorSalida);
        Effects.colorHover(view.getBtnBuscar(), colorEntrada, colorSalida);
        Effects.colorHover(view.getBtnEditar(), colorEntrada, colorSalida);
        Effects.colorHover(view.getBtnEliminar(), colorEntrada, colorSalida);
        Effects.colorHover(view.getLbEstado(), colorEntrada, colorSalida);

        Effects.moveableFrame(view);

    }

    //Metodos de Apoyo
    private void cargarTabla() {
        Lista = model.SelectAll();

        Lista.forEach((obj) -> {
            agregarFila(obj);
        });
        view.getLbResultados().setText(Lista.size() + " Resultados");
    }

    private void borrarTabla() {
        for (int i = 0; i < modelT.getDataVector().size() + 1; i++) {
            modelT.removeRow(0);
        }
    }

    private void cargarTablaBusqueda(String Aguja) {
        Lista = model.SelectOne(Aguja);
        Lista.forEach((obj) -> {
            agregarFila(obj);
        });
    }

    private void agregarFila(Producto obj) {
        modelT.addRow(new Object[]{
            obj.getCodigo(),
            obj.getNombre(),
            obj.getDescripcion(),
            obj.getPrecio(),
            obj.getStock()
        });
    }

    //Procesadores de Eventos
    private void btnBuscarOnMouseClicked(MouseEvent E) {
        borrarTabla();
        cargarTablaBusqueda(view.getTxtBuscar().getText());
        
    }

}
