package View;

import Entity.Associazione.Associazione;
import Entity.Associazione.AssociazioneDAO;
import Entity.Attributo.Attributo;
import Entity.Attributo.AttributoDAO;
import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimento;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimentoDAO;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.Metodo.Metodo;
import Entity.Metodo.MetodoDAO;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import Entity.Parametro.Parametro;
import Entity.Parametro.ParametroDAO;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;
import View.Components.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ModificaClassDiagramPage {
    private ClassDiagram cd = null;
    SpringLayout layout;

    private SideMenu sideMenu;
    private JPanel view;
    private ModifyClasseOptionPane currentPage;
    private JPanel currentPageView;
    private JPanel sideMenuView;

    /*This is the listener for the tree in the side menu of the page. What it does is as follows:
    * 1. Gets the click event;
    * 2. On double click gets the clicked object;
    * 3. Accordingly to the clicked object type shows the right view, either class or association;
     */
    private class TreeListener extends MouseAdapter {
        private JTree tree;
        private boolean singleClick  = true;
        private int doubleClickDelay = 300;
        private Timer timer;
        private SpringLayout layout;
        public TreeListener(JTree tree, SpringLayout layout) {
            this.tree = tree;
            this.layout= layout;
            ActionListener actionListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    if (singleClick) {
                        singleClickHandler(e);
                    } else {
                        try {
                            doubleClickHandler(e);
                        } catch (ParseException ex) {
                            Logger.getLogger(ex.getMessage());
                        }
                    }
                }
            };
            timer = new javax.swing.Timer(doubleClickDelay, actionListener);
            timer.setRepeats(false);
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                singleClick = true;
                timer.start();
            } else {
                singleClick = false;
            }
        }

        private void singleClickHandler(ActionEvent e) {
            System.out.println("-- single click --");
        }

        private void doubleClickHandler(ActionEvent e) throws ParseException {
            System.out.println("--double click");
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if(node.isRoot()){
                System.out.println("root");
                return;
            }else{

                Object o = node.getUserObject();
                if(o.getClass() == new Classe().getClass()){
                    Classe c = (Classe) o;
                    System.out.println("classe nome: " + c.getNome());
                    ClassViewPanel classViewPanel = new ClassViewPanel(c);
                    ClassViewPanel.ClassViewPanelController classViewPanelController = classViewPanel.getController();
                    JButton addAttributo = classViewPanel.getAggiungiAttributo();
                    addAttributo.addActionListener(actionEvent -> classViewPanelController.addAttributoPressed(c));
                    if(currentPageView != null){
                        currentPageView.removeAll();
                    }
                    currentPageView = classViewPanel.getView();
                    layout.putConstraint(SpringLayout.WEST, currentPageView, 5, SpringLayout.EAST, sideMenuView);
                    view.add(currentPageView);
                    view.setLayout(layout);
                    view.repaint();
                    view.revalidate();
                }
            }
        }
    }

    private class ModificaClassDiagramPageController{

        private static final int MISSING_NAME = -1;
        private static final int MISSING_VISIBILITA = -2;
        private static final int MISSING_ROLE_IN_SELECTED_ASSOCIAZIONE = -3;
        private static final int MISSING_ASSOCIAZIONE_FOR_ROLE = -4;
        private static final int MISSING_TIPO_DI_CLASSE = -5;

        private ClassDiagramDAO classDiagramDAO;
        private MetodoDAO metodoDAO;
        private ParametroDAO parametroDAO;
        private ClasseDAO classeDAO;
        private AssociazioneDAO associazioneDAO;
        private AttributoDAO attributoDAO;
        private PackageDAO packageDAO;
        private ClassDiagramRiferimentoDAO riferimentoDAO;

        public DefaultMutableTreeNode populateTreeView(String aClassDiagramName, String aPackageName){


            List<Attributo> attributes;
            List<Classe> classes;
            List<ClassDiagramRiferimento> riferimentoList;
            List<ClassDiagram> classDiagramList;
            List<Metodo> methods;
            List<Associazione> associations = null;
            List<Parametro> parametros;
            Package p;
            List<ClassDiagramRiferimento> rif;

            DefaultMutableTreeNode root = new DefaultMutableTreeNode(aClassDiagramName);
            DefaultMutableTreeNode associazioni = new DefaultMutableTreeNode("Associazioni");
            DefaultMutableTreeNode classi = new DefaultMutableTreeNode("Classi");
            root.add(associazioni);
            root.add(classi);
            try {

                    classDiagramDAO = new ClassDiagramDAO();
                    metodoDAO = new MetodoDAO();
                    parametroDAO = new ParametroDAO();
                    associazioneDAO = new AssociazioneDAO();
                    attributoDAO = new AttributoDAO();
                    packageDAO = new PackageDAO();
                    riferimentoDAO = new ClassDiagramRiferimentoDAO();
                    classeDAO = new ClasseDAO();

                    p = packageDAO.readPackageByName(aPackageName);

                    classDiagramList = classDiagramDAO.readByName(aClassDiagramName);

                    for(ClassDiagram i: classDiagramList) {
                        riferimentoList = riferimentoDAO.readAllPackagesOfAClassDiagram(i);
                        for(ClassDiagramRiferimento j: riferimentoList){
                            if(i.getId()==j.getIdClassDiagram() && j.getIdPackage()==p.getId()){
                                cd = i;
                            }
                        }
                    }
                    if(cd!=null) {

                        classes = classeDAO.readAllInCLassDIagram(cd);
                        associations=associazioneDAO.readAllInClassDiagram(cd);

                        DefaultMutableTreeNode classeNode;
                        for(Classe c : classes){
                            classeNode = new DefaultMutableTreeNode(c);
                            attributes = attributoDAO.getAllAttributesInClass(c);
                            if(!attributes.isEmpty()){
                                DefaultMutableTreeNode attributi = new DefaultMutableTreeNode("Attributi");
                                for(Attributo a: attributes){
                                    attributi.add(new DefaultMutableTreeNode(a));
                                }
                                classeNode.add(attributi);
                            }
                            methods = metodoDAO.getAllMethodsInClass(c);
                            if(!methods.isEmpty()) {
                                DefaultMutableTreeNode metodi = new DefaultMutableTreeNode("Metodi");
                                for (Metodo m : methods) {
                                    DefaultMutableTreeNode paramRoot = new DefaultMutableTreeNode(m.getNome());
                                    metodi.add(paramRoot);
                                    parametros = parametroDAO.getAllParametersInMethod(m);
                                    for (Parametro param : parametros) {
                                        paramRoot.add(new DefaultMutableTreeNode(param));
                                    }
                                }
                                classeNode.add(metodi);
                            }
                            classi.add(classeNode);
                        }
                        for(Associazione a: associations){
                            associazioni.add(new DefaultMutableTreeNode(a));
                        }
                    }else{

                    }
                    return root;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        private boolean aggiungiClasse(ClassDiagram ofClassDiagram) {
            int err = checkIfValidData();
            
            if(err<0){
                System.out.println(err);
            }else{
                JComboBox visibilita = currentPage.getVisibilita() ;
                JComboBox rappresenta = currentPage.getVisibilita();


                JComboBox associazioni = currentPage.getAssociazioni();
                JTextField nome = currentPage.getNome();
                JTextField stereotipo = currentPage.getStereotipo();
                JComboBox tipoDiClasse = currentPage.getTipoDiClasse();
                System.out.println((String) tipoDiClasse.getSelectedItem());
                Classe c = new Classe(-1, nome.getText(), TipoDiVisibilita.getTipoDiVisibilitaByName((String) visibilita.getSelectedItem()), stereotipo.getText(), RuoloAssociazione.getRuoloAssociazioneByName((String) rappresenta.getSelectedItem()),-1,-1, ofClassDiagram.getId(), TipoDiClasse.getTipoDiClasseByName((String) tipoDiClasse.getSelectedItem()));
                try {
                    ClasseDAO classeDAO = new ClasseDAO();
                    return classeDAO.addClassToDiagram(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        private int checkIfValidData(){
            JComboBox visibilita = currentPage.getVisibilita() ;
            JComboBox rappresenta = currentPage.getRappresenta();
            JComboBox associazioni = currentPage.getAssociazioni();
            JComboBox tipoDiClasse = currentPage.getTipoDiClasse();
            JTextField nome = currentPage.getNome();
            
            String a =(String) visibilita.getSelectedItem();
            String b = (String) rappresenta.getSelectedItem();
            String c = (String) associazioni.getSelectedItem();
            String d = (String) tipoDiClasse.getSelectedItem();

            if(nome.getText().compareTo("Max 300 caratteri")==0 || nome.getText().compareTo("")==0 || nome == null){
                return MISSING_NAME;
            }else if( a==null || a.compareTo("")==0 ){
                return MISSING_VISIBILITA;
            }else if( b==null || b.compareTo("")==0 ){
                if( c!=null)  {
                    return MISSING_ROLE_IN_SELECTED_ASSOCIAZIONE;
                }
            }else if(c==null || c.compareTo("")==0){
                if(b.compareTo("")!=0 || b!=null){
                    return MISSING_ASSOCIAZIONE_FOR_ROLE;
                }

            }else if( d==null || d.compareTo("")==0 ){
                return MISSING_TIPO_DI_CLASSE;
            }
            return 0;
        }

    }

    public JPanel getView() {
        return view;
    }

    public ModificaClassDiagramPage(String classDiagramName, String aPackageName){
       //initializer
        ModificaClassDiagramPageController controller = new ModificaClassDiagramPageController();

        view = new JPanel();
        sideMenu = new SideMenu();
        currentPageView = new JPanel();
        SpringLayout layout = new SpringLayout();

        sideMenuView = sideMenu.getView();


        JScrollPane scrollPaneForTree = sideMenu.getClassDiagramContainer();

        JTree t = new JTree( controller.populateTreeView(classDiagramName, aPackageName));
        t.addMouseListener(new TreeListener(t, layout));
        scrollPaneForTree.setViewportView(t);

        JButton addClasseButton = sideMenu.getAddClasse();
        addClasseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(cd!=null) {
                    currentPage = new ModifyClasseOptionPane(cd);
                    if(currentPageView != null){
                        currentPageView.removeAll();
                    }
                    currentPageView = currentPage.getView();
                    JCheckBox hasSuperclasses = currentPage.getHasSuperClasses();
                    layout.putConstraint(SpringLayout.WEST, currentPageView, 5, SpringLayout.EAST, sideMenuView);
                    JButton addClasse = currentPage.getAggiungi();
                    addClasse.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            controller.aggiungiClasse(cd);
                            if(hasSuperclasses.isSelected()){
                                JDialog addExtendingClasses = new JDialog(FrameSetter.getjFrame(), Dialog.ModalityType.APPLICATION_MODAL);
                                JTextField nomeclasse =currentPage.getNome();
                                addExtendingClasses.setContentPane(new GerarchiaClassiOption(nomeclasse.getText(), cd).getView());
                                addExtendingClasses.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                addExtendingClasses.setResizable(false);
                                addExtendingClasses.setAlwaysOnTop(true);
                                addExtendingClasses.setLocation((int) (dim.width/2.5),dim.height/7);
                                addExtendingClasses.setSize(FrameSetter.getjFrame().getWidth()/3, FrameSetter.getjFrame().getHeight());
                                addExtendingClasses.setVisible(true);
                            }
                            JTree t = new JTree(controller.populateTreeView(classDiagramName, aPackageName));
                            t.addMouseListener(new TreeListener(t, layout));
                            scrollPaneForTree.setViewportView(t);
                            FrameSetter.getjFrame().revalidate();
                        }
                    });

                    view.add(currentPageView);
                    view.setLayout(layout);
                    view.revalidate();
                    view.repaint();
                }
            }
        });

        layout.putConstraint(SpringLayout.NORTH, sideMenuView, 0, SpringLayout.NORTH, view);
        layout.putConstraint(SpringLayout.WEST, sideMenuView, 0, SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.WEST, currentPageView, 5, SpringLayout.EAST, sideMenuView);

        view.add(sideMenuView);
        view.add(currentPageView);
        view.setLayout(layout);
        view.setVisible(true);
    }
}
