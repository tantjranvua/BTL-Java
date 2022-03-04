package com.example.btl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.nio.file.DirectoryIteratorException;
import  java.nio.file.Files;
import java.io.File;
import java.security.PublicKey;

public class HelloController {
    @FXML
    private ListView<String> listView;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private TextField input;
    @FXML
    private Button search;

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
            TreeItem<File> item = treeView.getSelectionModel().getSelectedItem(); // Đối tượng được select
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
        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }
    public  void handleGetFile(File disk){
        File[] listFiles = disk.listFiles();
        for(File file:listFiles){
            if(file.isFile()){
                listView.getItems().add(file.getPath());
            }
            else{
                handleGetFile(file);
            }
        }
    }

    public void search() {
//        String text = input.getText();

        File[] disks = File.listRoots();
        for(File disk:disks){
                handleGetFile(disk);
        }

    }
}
