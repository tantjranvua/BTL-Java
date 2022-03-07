package com.example.btl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    public TextField input_new;
    public TextField input_search;
    @FXML
    private ListView<String> listView;
    @FXML
    private TreeView<File> treeView;

    public void InitTreeView(){
        File tmp = new File("/");
        TreeItem<File> root = new TreeItem<File>(tmp);
        File[] files = File.listRoots();
        for(File file:files){
           TreeItem<File> node = new TreeItem<File>(file);
           root.getChildren().add(node);
        }
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }
    public void Select(){
        try{
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File tmp = item.getValue();
            if(item.isLeaf()){
                if(!tmp.isFile()){
                    File[] files = tmp.listFiles();
                    for(File file:files){
                        TreeItem<File> node = new TreeItem<File>(file);
                        item.getChildren().add(node);
                    }
                }
            }
            else{
                if(item.isExpanded()){
                    item.setExpanded(false);
                }
                else{
                    item.setExpanded(true);
                }
            }
        } catch(Exception ex){
            System.out.println(ex);
        }
    }
//    public boolean check (String str1, String str2){
//        if(str1.contains(str2))
//            return true;
//        else
//            return false;
//    }
    public void handleGetFile(File[] arr) {

        for (File f : arr) {
            String name = f.getName();
            String path = f.getPath();
//            System.out.println(check(name,inputText));
                if (f.isDirectory()  ) {
                    listView.getItems().add(name);
                    listView.getItems().add("Folder: "+ path);
                    handleGetFile(f.listFiles());
                }
                else{
                    listView.getItems().add(name);
                    listView.getItems().add("File: "+path);
                }
        }
    }
    public void search() {
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File tmp = item.getValue();
            listView.getItems().clear();
            handleGetFile(tmp.listFiles());
        } catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void deleteFile(){
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File tmp = item.getValue();
            Files.delete(tmp.toPath());
            InitTreeView();
        } catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void newFile() {
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File Parent = item.getValue();
//            File new_file = new File(Parent.getPath()+"/"+input_new.getText()); Create file

            Files.createDirectories(Path.of(Parent.getPath() + "/" + input_new.getText()));  // Create Folder
//            if (new_file.createNewFile()) {
//                System.out.println("File created: " + new_file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            InitTreeView();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InitTreeView();
    }
}