package View;

import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimento;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimentoDAO;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.ColorPicker;
import View.Components.FrameSetter;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.security.PrivilegedAction;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

public class NuovoClassDiagramPage {

    /*Questi qua son tutti gli elementi della pagina*/
    private JPanel view; //questo è il principale, in swing gli elementi si appendono ad altri, questo è tipo il super contenitore
    private JButton add;
    private JTextField classDiagramName;
    private JLabel nameLabel;
    private JLabel commentLabel;
    private JTextArea classDiagramComment;
    private JCheckBox putInSpecificPackage;
    private JComboBox<String> packageJList;
    private JLabel errMessageForNome;
    private JLabel errMessageForComment;
    private JPanel errMessageWrapperForNome;
    private JPanel errMessageWrapperForComment;
    private JScrollPane commentContainer;
    private JPanel errMessageWrapperForMissingPackages;
    private JLabel errMessageForMissingPackages;
    private JLabel errMessageForExistingDiagrams;
    private JPanel errMessageForExistingDiagramsWrapper;
    private JCheckBox addExistingDiagram;
    private JComboBox<String> existingDiagrams;
    private JButton aggiungiAPackage;


    private boolean areThereExceptions = false;
    private boolean areThereErrors = false;

    /*questa qua è la classe che gestisce la pagina. */
    private class NuovoClassDiagramPageController{

        /*Vari casi d'errore*/
        private static final int NAME_TOO_LONG = -1;
        private static final int COMMENT_TOO_LONG = -2;
        private static final int MISSING_NAME = -3;
        private static final int MISSING_PACKAGE = -4;

        /*un controllo per vedere se ci sono package diversi da quello di default*/
        private boolean areTherePackages = false;

        //Variabili di storage che servono e il dao per i diagram
        private ClassDiagramDAO cdDao;
        private List<Package> l;

        /*Il metodo controlla che i dati immessi siano validi*/
        private int checkIfValidData(){
            String a = classDiagramName.getText();
            String b = classDiagramComment.getText();
            String p = (String) packageJList.getSelectedItem();
            if (a.length()>=200) {
                return NAME_TOO_LONG;
            }else if(b.length()>=4000){
                return COMMENT_TOO_LONG;
            }else if (a.length()==0){
                return MISSING_NAME;
            }else if(p.length()==0 && putInSpecificPackage.isSelected()){
                return MISSING_PACKAGE;
            }else{
                return 0;
            }
        }

        /*Questi due metodi vengono chiamati quando uno clicca sui campi testuali. Come? aggiungendo un listener, vedi sotto*/
        private void cleanOnFocus(JTextField a){
            a.setText("");
            add.setEnabled(true);

            return;
        }

        private void cleanOnFocus(JTextArea a){
            a.setText("");
            add.setEnabled(true);
            return;
        }

        /*Questo metodo popola la lista di packages dentro la combo box, il menu a tendina*/
        private void populatePackageList(JComboBox j){
            try {
                PackageDAO packageDAO = new PackageDAO();
                l = packageDAO.readAll();
                int i = 0;
                for(Package p: l){
                    if(p.getId()!= 0){
                        j.addItem(p.getNome());
                    }
                }
                if(i-1 != 0){
                    /*se ne trova almeno uno diverso da quello di default setta areTherePackages a true*/
                    areTherePackages = true;
                }

            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }

        /*Se premo il bottone aggiungi diagramma*/
        private void addClassDiagramButtonPressed(){
            //controlla se i dati sono validi
            areThereErrors = false;
            areThereExceptions = false;
            int err = checkIfValidData();
            if(err<0){//se no scrivo vari messaggi d'errore
                areThereErrors  =true;
                if(err==NAME_TOO_LONG){

                    errMessageForNome.setText("Il nome non può superare i 200 caratteri");
                    errMessageForNome.setVisible(true);
                    errMessageWrapperForNome.setVisible(true);
                }
                if(err == COMMENT_TOO_LONG){
                    errMessageForComment.setText("Il commento non può superare i 4000 caratteri");
                    errMessageForComment.setVisible(true);
                    errMessageWrapperForComment.setVisible(true);
                }
                if(err == MISSING_NAME){
                    errMessageForNome.setText("Devi inserire un nome");
                    errMessageForNome.setVisible(true);
                    errMessageWrapperForNome.setVisible(true);
                }
                if(err == MISSING_PACKAGE){
                    errMessageForMissingPackages.setText("Devi selezionare un package o deselezionare la chechkbox");
                    errMessageForMissingPackages.setVisible(true);
                    errMessageWrapperForMissingPackages.setVisible(true);
                }
            }else{//altrimenti
                try{
                    //aggiungo il diagramma
                    ClassDiagram cd = new ClassDiagram(0,classDiagramName.getText(), classDiagramComment.getText());
                    cdDao = new ClassDiagramDAO();
                    cdDao.createClassDiagram(cd);
                    //dato che l'id è automatico mi riprendo l'ultimo inserito
                    int idCD = cdDao.getLastInsertedId();

                    //aggiungo nel package di riferimento
                    ClassDiagramRiferimentoDAO riferimentoDAO = new ClassDiagramRiferimentoDAO();
                    if(putInSpecificPackage.isSelected()){ //se la checkbox è selezionata
                        PackageDAO pDao = new PackageDAO();
                        //mi prendo il package corretto dal nome salvato nella lista affianco
                        Package p = pDao.readPackageByName((String) packageJList.getSelectedItem() );
                        //aggiungo il collegamento tra package e class diagram
                        riferimentoDAO.addClassDiagramToPackage(new ClassDiagramRiferimento(p.getId(),idCD));

                        FrameSetter.getjFrame().setContentPane(new ModificaClassDiagramPage(cd.getNome(), p.getNome()).getView());
                        FrameSetter.getjFrame().validate();
                    }else{
                        //se non è in quello standard lo metto nel default
                        riferimentoDAO.addClassDiagramToPackage(new ClassDiagramRiferimento(0,idCD));

                        FrameSetter.getjFrame().setContentPane(new ModificaClassDiagramPage(cd.getNome(),"Default" ).getView());
                        FrameSetter.getjFrame().validate();
                    }
                }catch(SQLException e){
                    areThereExceptions = true;
                    //gestisco le eccezioni
                    int errorCode = e.getErrorCode();
                    switch(errorCode){
                        case 6510:
                            errMessageForMissingPackages.setText("<html>Il nome selezionato è già presente <br>nel package selezionato.<br>Inserisci un altro nome.</html>\n");
                            errMessageForMissingPackages.setVisible(true);
                            errMessageWrapperForMissingPackages.setVisible(true);
                        default:
                             System.out.println(e.getErrorCode());
                    }
                }
            }
        }

        public void populateDiagramsList(JComboBox j) {
            try {

                boolean check = true;
                int count = 0;

                ClassDiagramDAO classDiagramDAO = new ClassDiagramDAO();
                PackageDAO packageDAO = new PackageDAO();
                ClassDiagramRiferimentoDAO classDiagramRiferimentoDAO = new ClassDiagramRiferimentoDAO();

                Package packageToInsertInto = packageDAO.readPackageByName((String) packageJList.getSelectedItem());
                List<Package> packagesWhereItAlreadyIs = new ArrayList<>();
                List<ClassDiagramRiferimento> classDiagramRiferimentoList;
                List<ClassDiagram> diagrams = classDiagramDAO.readAllNotInPackage(packageToInsertInto);

                if(diagrams.isEmpty()){
                    errMessageForExistingDiagrams.setText("<html>Non esistono Class Diagram non <br> presenti nel package selezionato.<b");
                    errMessageForExistingDiagrams.setVisible(true);
                    errMessageForExistingDiagramsWrapper.setVisible(true);
                }else{
                    for(ClassDiagram cd : diagrams){
                        classDiagramRiferimentoList = classDiagramRiferimentoDAO.readAllPackagesOfAClassDiagram(cd);
                        for(ClassDiagramRiferimento rif: classDiagramRiferimentoList) {
                            if(rif.getIdPackage() == packageToInsertInto.getId()){
                                check = false;
                            }else {
                                packagesWhereItAlreadyIs.add(packageDAO.readPackageById(rif.getIdPackage()));
                            }
                        }
                        if(check == true){
                            count ++;
                            for(Package toAdd : packagesWhereItAlreadyIs) {
                                System.out.println(cd.getNome() + " nel package " + toAdd.getNome());
                                j.addItem(cd.getNome() + " nel package " + toAdd.getNome());
                            }
                        }
                        check = true;
                    }
                    if(count == 0){
                        errMessageForExistingDiagrams.setText("<html>Non esistono Class Diagram non <br> presenti nel package selezionato.<b");
                        errMessageForExistingDiagrams.setVisible(true);
                        errMessageForExistingDiagramsWrapper.setVisible(true);
                    }
                    FrameSetter.getjFrame().validate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void addSelectedDiagramToPackage() {
            ClassDiagramRiferimentoDAO classDiagramRiferimentoDAO;
            ClassDiagramDAO  classDiagramDAO;
            PackageDAO packageDAO;
            List<ClassDiagram> c;
            Package origin;
            Package dest;
            ClassDiagramRiferimento cdrif;
            List<ClassDiagramRiferimento> listofreferences;
            String selected = ((String) existingDiagrams.getSelectedItem()).replaceAll(" nel package ","%");
            String classDiagramName = selected.substring(0,selected.indexOf("%"));
            String packageName = selected.substring(selected.indexOf("%") + 1 , selected.length());


            try{
                classDiagramDAO = new ClassDiagramDAO();
                classDiagramRiferimentoDAO = new ClassDiagramRiferimentoDAO();
                packageDAO = new PackageDAO();
                List<ClassDiagramRiferimento> riferimentoList;
                origin= packageDAO.readPackageByName(packageName);
                dest = packageDAO.readPackageByName((String) packageJList.getSelectedItem());
                c = classDiagramDAO.readByName(classDiagramName);
                for(ClassDiagram classDiagram: c){
                    riferimentoList = classDiagramRiferimentoDAO.readAllPackagesOfAClassDiagram(classDiagram);
                    for(ClassDiagramRiferimento rif: riferimentoList){
                        if(origin.getId() == rif.getIdPackage()){
                            classDiagramRiferimentoDAO.addClassDiagramToPackage(new ClassDiagramRiferimento(dest.getId(),classDiagram.getId()));
                            break;
                        }
                    }
                    break;
                }
            }catch(SQLException e){

            }
        }
    }

    /*Il costruttore della pagina imposta tutto la vista*/
    public NuovoClassDiagramPage(){

        //creo il controllor
        NuovoClassDiagramPageController controller = new NuovoClassDiagramPageController();

        /*In Java Swing i componenti hanno un layout, tale layout decide come le cose sono messe nella pagina.
        * Ora quello SpringLayout funziona che i componenti vengono messi in posizioni relative l'una all'altra.
        * Più avanti ci sta il metodo che lo fa.*/
        SpringLayout layout = new SpringLayout();

        /*Inizializzo tutte le componenti*/
        view = new JPanel();
        add = new JButton("Crea Diagramma");
        classDiagramName = new JTextField("MAX 300 caratteri", 20);
        classDiagramComment = new JTextArea("MAX 4000 caratteri", 10, 20);
        putInSpecificPackage = new JCheckBox("Vuoi inserire in un package specifico?");
        packageJList = new JComboBox<>();
        classDiagramComment = new JTextArea("MAX 4000 caratteri", 10, 20);
        errMessageWrapperForNome = new JPanel();
        errMessageWrapperForComment = new JPanel();
        errMessageWrapperForMissingPackages = new JPanel();
        errMessageForNome = new JLabel("");
        errMessageForComment = new JLabel("");
        errMessageForMissingPackages= new JLabel("");

        nameLabel =    new JLabel("   Inserisci il nome del Class Diagram:");
        commentLabel = new JLabel("Inserisci il commento al Class Diagram:");

        /*aggiungo una linea vuota e poi popolo la lista di package*/
        packageJList.addItem("");
        controller.populatePackageList(packageJList);

        /*Imposta la dimensione della lista*/
        packageJList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        /*Tutti questi metodi servono ad impostare cose grafiche, ad esempio questo mette il testo della checkbox a sinistra*/
        putInSpecificPackage.setHorizontalTextPosition(SwingConstants.LEFT);

        //metodo i messaggi d'errore invisibili con sfondo rosso
        errMessageWrapperForNome.setVisible(false);
        errMessageWrapperForNome.setBackground(ColorPicker.getColor("red"));
        errMessageWrapperForComment.setVisible(false);
        errMessageWrapperForComment.setBackground(ColorPicker.getColor("red"));
        errMessageWrapperForMissingPackages.setVisible(false);
        errMessageWrapperForMissingPackages.setBackground(ColorPicker.getColor("red"));

        errMessageWrapperForMissingPackages.add(errMessageForMissingPackages);
        errMessageWrapperForComment.add(errMessageForComment);
        errMessageWrapperForNome.add(errMessageForNome);

        add.setEnabled(false);

        errMessageForNome.setVisible(false);
        errMessageForComment.setVisible(false);
        errMessageWrapperForMissingPackages.setVisible(false);

        classDiagramComment.setLineWrap(true);
        classDiagramComment.setWrapStyleWord(true);
        classDiagramComment.setAutoscrolls(true);

        /*L'area del commento sta dentro uno scroll pane, cioè con la barra, questo perché senno aggiungeva una linea sotto ogni
        * volta che uno superava il limite inferiore*/
        commentContainer = new JScrollPane(classDiagramComment);

        //disabilito la checkbox, rendedola non cliccabile
        packageJList.setEnabled(false);

        /*Questo è un event listener, quando l'oggetto classDiagramContent guadagna il focus, cioè qualcuno ci clicca sopra
        * quello che fa è richiamare il metodo del controller cleanOnFocus*/
        classDiagramComment.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(classDiagramComment);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        /*Come sopra
        * */
        classDiagramName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(classDiagramName);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addClassDiagramButtonPressed();
            }
        });

        /*Come sopra, solo che abilita anche la combobox se era selezionata e la disabilita in caso contrario*/
        putInSpecificPackage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (packageJList.isEnabled()) {
                    packageJList.setEnabled(false);
                    if(errMessageWrapperForMissingPackages.isVisible()){
                        errMessageWrapperForMissingPackages.setVisible(false);
                    }
                }else {
                    /*Se ci sono dei package nella lista*/
                    if(controller.areTherePackages) {
                        packageJList.setEnabled(true); // abilito la checbox
                    }else{//altrimenti scrivo un messaggio d'errore e disabilito la checbox
                        errMessageForMissingPackages.setText("Non esistono packages diversi da quello di default.");
                        errMessageForMissingPackages.setVisible(true);
                        errMessageWrapperForMissingPackages.setVisible(true);
                    }
                }
            }
        });

        /*questi qua sono i metodi che spostano gli elementi:
        * per il primo:
        * dice l'angolo Ovest dell'oggetto nameLabel sta a tanti pixel quanti sono la larghezza della finestra fratto tre dall'angolo ovest dell'oggetto view
        * e via così.
        * poi metti uno a tanti pixel da quello a cui hai già messo un constraint etc etc.
        * Se cerchi puoi trovare tutorial su come funziona lo springlayout*/
        layout.putConstraint(SpringLayout.WEST,nameLabel, (int) (FrameSetter.getjFrame().getWidth()/4), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH,nameLabel, 30, SpringLayout.NORTH, view);

        layout.putConstraint(SpringLayout.WEST, commentLabel, 0 , SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH,commentLabel, 30, SpringLayout.NORTH, errMessageWrapperForNome);

        layout.putConstraint(SpringLayout.WEST, classDiagramName, 220, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH,classDiagramName, 30 , SpringLayout.NORTH, view);

        layout.putConstraint(SpringLayout.WEST, errMessageWrapperForNome, 0, SpringLayout.WEST, classDiagramName);
        layout.putConstraint(SpringLayout.NORTH,errMessageWrapperForNome, 30 , SpringLayout.NORTH, classDiagramName);

        layout.putConstraint(SpringLayout.WEST,commentContainer, 220, SpringLayout.WEST, commentLabel);
        layout.putConstraint(SpringLayout.NORTH, commentContainer, 30, SpringLayout.NORTH, errMessageWrapperForNome);

        layout.putConstraint(SpringLayout.WEST, errMessageWrapperForComment, 0, SpringLayout.WEST, commentContainer);
        layout.putConstraint(SpringLayout.NORTH,errMessageWrapperForComment, 10,SpringLayout.SOUTH, commentContainer);

        layout.putConstraint(SpringLayout.WEST, putInSpecificPackage, -40,SpringLayout.WEST, commentLabel);
        layout.putConstraint(SpringLayout.SOUTH, putInSpecificPackage, 30 , SpringLayout.SOUTH, errMessageWrapperForComment);

        layout.putConstraint(SpringLayout.WEST, packageJList, 0, SpringLayout.WEST, errMessageWrapperForComment);
        layout.putConstraint(SpringLayout.NORTH, packageJList, -5, SpringLayout.NORTH, putInSpecificPackage );

        layout.putConstraint(SpringLayout.WEST, errMessageWrapperForMissingPackages, 0,SpringLayout.WEST, putInSpecificPackage);
        layout.putConstraint(SpringLayout.NORTH, errMessageWrapperForMissingPackages, 30, SpringLayout.NORTH, putInSpecificPackage);

        layout.putConstraint(SpringLayout.WEST, add, (FrameSetter.getjFrame().getWidth())-(FrameSetter.getjFrame().getWidth()/4), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.SOUTH, add, -30, SpringLayout.SOUTH, view);

        //aggiungo tutti gli elementi al pane principale
        view.add(nameLabel);
        view.add(commentLabel);
        view.add(errMessageWrapperForMissingPackages);
        view.add(errMessageWrapperForComment);
        view.add(errMessageWrapperForNome);
        view.add(classDiagramName);
        view.add(commentContainer);
        view.add(add);

        view.add(putInSpecificPackage);
        view.add(packageJList);

        //setto il layout a quello che ho creato e a cui ho messo i constraint
        view.setLayout(layout);

        view.setVisible(true);
        return;
    }

    public JPanel getView(){return view;}


    public NuovoClassDiagramPage(String p){

        //creo il controllor
        NuovoClassDiagramPageController controller = new NuovoClassDiagramPageController();


        /*Inizializzo tutte le componenti*/
        view = new JPanel();
        add = new JButton("Crea Diagramma");
        classDiagramName = new JTextField("MAX 300 caratteri", 20);
        classDiagramComment = new JTextArea("MAX 4000 caratteri", 10, 20);
        addExistingDiagram = new JCheckBox("Vuoi inserire un Class Diagram esistente?");
        putInSpecificPackage = new JCheckBox("Vuoi inserire in un package specifico?");
        packageJList = new JComboBox<>();
        classDiagramComment = new JTextArea("MAX 4000 caratteri", 10, 20);
        errMessageWrapperForNome = new JPanel();
        errMessageWrapperForComment = new JPanel();
        errMessageWrapperForMissingPackages = new JPanel();
        errMessageForNome = new JLabel("");
        errMessageForComment = new JLabel("");
        errMessageForMissingPackages= new JLabel("");
        errMessageForExistingDiagrams = new JLabel("");
        errMessageForExistingDiagramsWrapper= new JPanel();
        existingDiagrams = new JComboBox<>();
        aggiungiAPackage = new JButton("Aggiungi Diagramma");

        nameLabel =    new JLabel("   Inserisci il nome del Class Diagram:");
        commentLabel = new JLabel("Inserisci il commento al Class Diagram:");

        aggiungiAPackage.setVisible(true);
        aggiungiAPackage.setEnabled(false);

        packageJList.addItem(p);

        existingDiagrams.addItem("");

        /*Imposta la dimensione della lista*/
        packageJList.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        existingDiagrams.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        putInSpecificPackage.setHorizontalTextPosition(SwingConstants.LEFT);
        addExistingDiagram.setHorizontalTextPosition(SwingConstants.LEFT);

        classDiagramName.setEnabled(true);
        classDiagramComment.setEnabled(true);

        putInSpecificPackage.setEnabled(false);
        putInSpecificPackage.setSelected(true);
        existingDiagrams.setEnabled(false);
        //metodo i messaggi d'errore invisibili con sfondo rosso
        errMessageWrapperForNome.setVisible(false);
        errMessageWrapperForNome.setBackground(ColorPicker.getColor("red"));
        errMessageWrapperForComment.setVisible(false);
        errMessageWrapperForComment.setBackground(ColorPicker.getColor("red"));
        errMessageWrapperForMissingPackages.setVisible(false);
        errMessageWrapperForMissingPackages.setBackground(ColorPicker.getColor("red"));
        errMessageForExistingDiagrams.setVisible(false);
        errMessageForExistingDiagramsWrapper.setVisible(false);
        errMessageForExistingDiagramsWrapper.setBackground(ColorPicker.getColor("red"));

        errMessageForExistingDiagramsWrapper.add(errMessageForExistingDiagrams);
        errMessageWrapperForMissingPackages.add(errMessageForMissingPackages);
        errMessageWrapperForComment.add(errMessageForComment);
        errMessageWrapperForNome.add(errMessageForNome);


        errMessageForNome.setVisible(false);
        errMessageForComment.setVisible(false);
        errMessageWrapperForMissingPackages.setVisible(false);

        classDiagramComment.setLineWrap(true);
        classDiagramComment.setWrapStyleWord(true);
        classDiagramComment.setAutoscrolls(true);


        /*L'area del commento sta dentro uno scroll pane, cioè con la barra, questo perché senno aggiungeva una linea sotto ogni
         * volta che uno superava il limite inferiore*/
        commentContainer = new JScrollPane(classDiagramComment);

        //disabilito la checkbox, rendedola non cliccabile
        packageJList.setEnabled(false);

        /*Questo è un event listener, quando l'oggetto classDiagramContent guadagna il focus, cioè qualcuno ci clicca sopra
         * quello che fa è richiamare il metodo del controller cleanOnFocus*/
        classDiagramComment.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(classDiagramComment);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        /*Come sopra
         * */
        classDiagramName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(classDiagramName);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        addExistingDiagram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(addExistingDiagram.isSelected()) {
                    classDiagramName.setEnabled(false);
                    classDiagramComment.setEnabled(false);
                    add.setEnabled(false);
                    aggiungiAPackage.setEnabled(true);
                    controller.populateDiagramsList(existingDiagrams);
                    existingDiagrams.setEnabled(true);
                    FrameSetter.getjFrame().validate();

                }else{
                    if(errMessageForExistingDiagrams.isVisible()){
                        errMessageForExistingDiagrams.setVisible(false);
                        errMessageForExistingDiagramsWrapper.setVisible(false);
                    }
                    add.setEnabled(true);
                    existingDiagrams.setEnabled(false);
                    aggiungiAPackage.setEnabled(false);
                    classDiagramName.setEnabled(true);
                    classDiagramComment.setEnabled(true);
                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addClassDiagramButtonPressed();

                if(!areThereExceptions && !areThereErrors){
                    JDialog parent = (JDialog) SwingUtilities.getWindowAncestor(view);
                    parent.dispose();
                }
            }

        });

        aggiungiAPackage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(addExistingDiagram.isSelected()){
                    if(!errMessageForExistingDiagramsWrapper.isVisible() || (((String) existingDiagrams.getSelectedItem()).compareTo("")==0)) {
                        controller.addSelectedDiagramToPackage();
                    }else{
                        errMessageForExistingDiagrams.setText("Non hai selezionato un Class Diagram");
                    }
                }
            }
        });

        //aggiungo tutti gli elementi al pane principale
        view.add(addExistingDiagram);
        view.add(existingDiagrams);
        view.add(errMessageForExistingDiagramsWrapper);
        view.add(nameLabel);
        view.add(classDiagramName);
        view.add(errMessageWrapperForNome);
        view.add(commentLabel);
        view.add(commentContainer);
        view.add(errMessageWrapperForComment);
        view.add(errMessageWrapperForMissingPackages);
        view.add(putInSpecificPackage);
        view.add(packageJList);
        view.add(add);
        view.add(aggiungiAPackage);

        //setto il layout a quello che ho creato e a cui ho messo i constraint

        view.setVisible(true);
        return;
    }



}
