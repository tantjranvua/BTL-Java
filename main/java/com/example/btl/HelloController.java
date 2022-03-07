package com.example.btl_kien;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.FileFilter;
import java.io.FilenameFilter;
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


    /**
     * Initializable treeview
     * set root
     */
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

    /**
     * Clear data listview
     */
    public void clearListView(){
        listView.getItems().clear();
    }
    /**
     *
     */
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
    /**
     *
     * @param arr Folder select in treeview
     *  get folder, file of Folder selected in treeview
     */
    public void handleGetFile(File[] arr) {
        if(arr.length ==0) return;
        for (File f : arr) {
            String name = f.getName();
            String path = f.getPath();
//            System.out.println(check(name,inputText));
            if (f.isDirectory()  ) {
//                listView.getItems().add(name);
                listView.getItems().add("Folder: "+ path);
//                handleGetFile(f.listFiles());
            }
            else{
//                listView.getItems().add(name);
                listView.getItems().add("File: "+path);
            }
            listView.getItems().add("");
        }
    }

    /**
     * button show
     */
    public void show() {
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File tmp = item.getValue();
            clearListView();
            handleGetFile(tmp.listFiles());
        } catch(Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * Delete folder when click button delete
     */
    public void deleteFile(){
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            TreeItem<File> parentItem = treeView.getSelectionModel().getSelectedItem().getParent();
            File tmp = item.getValue();
            Files.delete(tmp.toPath());
            // Remove item in treeview
            parentItem.getChildren().remove(item);
            show();
        } catch(Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * Create folder when click button new
     */
    public void newFile() {
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File Parent = item.getValue();
//            File new_file = new File(Parent.getPath()+"/"+input_new.getText()); Create file
            Path path = Path.of(Parent.getPath() + "/" + input_new.getText());
            String s = path.toString();
            File file = new File(s);
            System.out.println(file.isFile());
//            if(file.isFile()) {
                file.createNewFile();
//            }
//            else{
//            File file = new File(String.valueOf(path));
//                Files.createDirectories(path);  // Create Folder
//                file = path.toFile();

//            }

//            if (new_file.createNewFile()) {
//                System.out.println("File created: " + new_file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            TreeItem<File> node = new TreeItem<File>(file);
            item.getChildren().add(node);
            show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void newFolder() {
        try {
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
            File Parent = item.getValue();
//            File new_file = new File(Parent.getPath()+"/"+input_new.getText()); Create file
            Path path = Path.of(Parent.getPath() + "/" + input_new.getText());
            String s = path.toString();



            Files.createDirectories(path);  // Create Folder
            File file = path.toFile();


//            if (new_file.createNewFile()) {
//                System.out.println("File created: " + new_file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            TreeItem<File> node = new TreeItem<File>(file);
            item.getChildren().add(node);
            show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void restart() {
        InitTreeView();
        clearListView();
    }
    /**
     * initialize  application
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InitTreeView();
        clearListView();
    }
    public void find(String st, File dir){
        try{
        File[] files_ = dir.listFiles(new Filter_BTL(st));
        handleGetFile(files_);
        File[] files = dir.listFiles();
        if(files.length!=0){
            for (File file:files){
//                System.out.println(file.getName());
                if(file.isDirectory()){
                    find(st,file);
                }
            }
        }
        }
        catch (Exception ex) {
            System.out.println(dir);
        }

    }

    public void search() {
        clearListView();
        String input = input_search.getText();
        find(input,new File("/home/tantanmanh"));

    }
}
