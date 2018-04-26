/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinep2pmessenger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import server.Client;
import server.Room;

/**
 *
 * @author koko_
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    AnchorPane Toproot;
    @FXML
    private Label label;
    @FXML
    private TextField searchTxt;
    @FXML
    private TextField ChatTxt;
    @FXML
    private ImageView i1;
    @FXML
    private ImageView i2;
    @FXML
    private ImageView i3;
    @FXML
    private ImageView i4;
    @FXML
    private ImageView i5;
    @FXML
    private ImageView i6;
    @FXML
    private ImageView i7;
    @FXML
    private ImageView i8;
    @FXML
    private ImageView i9;
    @FXML
    private ImageView i10;
    @FXML
    private ImageView i11;
    @FXML
    private ImageView i12;
    @FXML
    private ImageView i13;
    @FXML
    private ImageView i14;
    @FXML
    private ImageView i15;
    @FXML
    private ImageView i16;
//    ImageView[] imojies = new ImageView[] {i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16};
    @FXML
    private Pane emojiPane;
    private boolean emojispanevis = false;
    @FXML
    private Button AddTab;
    @FXML
    private TabPane tabs;
    ArrayList<Pair<String, VBox>> vboxes = new ArrayList<>();
    int id = 1;
    @FXML
    private VBox groupVbox;
    ArrayList<Pair<String, StackPane>> groupVboxes = new ArrayList<>();
    @FXML
    private Button TestGroup;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private VBox UsersVbox;
    ArrayList<Pair<String, StackPane>> usersVboxes = new ArrayList<>();
    @FXML
    private VBox UserTabVbox;
    ArrayList<Pair<String, StackPane>> UserTabVboxes = new ArrayList<>();
    @FXML
    private VBox GroupTabVbox;
    ArrayList<Pair<String, StackPane>> GroupTabVboxes = new ArrayList<>();
    @FXML
    private JFXButton AddRoomBtn;
    @FXML
    private Button receiveTest;
    ArrayList<Pair<String, HBox>> membersInRomPane = new ArrayList<>();
    private JFXPopup RoomPopUp = new JFXPopup();
    private JFXPopup LeftUsersPopUp = new JFXPopup();
    private JFXPopup LeftRoomsPopUp = new JFXPopup();
    private JFXPopup EmojiesPopUp = new JFXPopup();
    VBox EmojiesPopupVbox = new VBox();
    @FXML
    private ImageView emojies;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final Font f = Font.loadFont(new FileInputStream(new File("OpenSansEmoji.ttf")), 12);
             if(f == null) {
            throw new IllegalArgumentException("Can't load font for url ");
        }
            ChatTxt.setFont(f);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<ImageView> imgsv = new ArrayList<>(Arrays.asList(i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16));
        
        i1.setOnMouseClicked(e -> appendEmoji(1));
        i2.setOnMouseClicked(e -> appendEmoji(2));
        i3.setOnMouseClicked(e -> appendEmoji(3));
        i4.setOnMouseClicked(e -> appendEmoji(4));
        i5.setOnMouseClicked(e -> appendEmoji(5));
        i6.setOnMouseClicked(e -> appendEmoji(6));
        i7.setOnMouseClicked(e -> appendEmoji(7));
        i8.setOnMouseClicked(e -> appendEmoji(8));
        i9.setOnMouseClicked(e -> appendEmoji(9));
        i10.setOnMouseClicked(e -> appendEmoji(10));
        i11.setOnMouseClicked(e -> appendEmoji(11));
        i12.setOnMouseClicked(e -> appendEmoji(12));
        i13.setOnMouseClicked(e -> appendEmoji(13));
        i14.setOnMouseClicked(e -> appendEmoji(14));
        i15.setOnMouseClicked(e -> appendEmoji(15));
        i16.setOnMouseClicked(e -> appendEmoji(16));
        TestGroup.setOnMouseClicked(e -> createUserPane("u1", "staus", "kero"));
        EmojiesPopupVbox.setStyle("-fx-background-color:  #2e2f30;");
        for (int i = 0; i < 16; i+=4) {
            HBox kk = new HBox(imgsv.get(i),imgsv.get(i+1),imgsv.get(i+2),imgsv.get(i+3));
            kk.setSpacing(5);
            kk.setPadding(new Insets(5));
            EmojiesPopupVbox.getChildren().add(kk);
            
        }
        
        EmojiesPopUp.setPopupContent(EmojiesPopupVbox);
        emojies.setOnMouseClicked(e -> showEmojis(e));
        RoomOptionPopUp();
    }

    public void appendEmoji(int index) {
        byte[] emojiBytes;
        String emojiAsString;
        switch (index) {
            case 1:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 2:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x83};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 3:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x84};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 4:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x89};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 5:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 6:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 7:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x94};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 8:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x9C};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 9:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 10:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA2};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 11:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x99, (byte) 0x88};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 12:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x91};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 13:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA6};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 14:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB4};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString + "  ");
                break;
            case 15:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8E};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            case 16:
                emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0x8D};
                emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
                ChatTxt.insertText(ChatTxt.caretPositionProperty().get(), emojiAsString);
                break;
            default:
                System.out.println("error");
                break;
        }
    }

    public void showEmojis(MouseEvent e) {
//        if (emojispanevis == false) {
//            emojiPane.setVisible(true);
//            emojispanevis = true;
//        } else {
//            emojiPane.setVisible(false);
//            emojispanevis = false;
//        }
        EmojiesPopUp.show(Toproot,JFXPopup.PopupVPosition.BOTTOM,JFXPopup.PopupHPosition.LEFT,e.getX()+200,e.getY()-50);

    }

    public void AddNewUser(Client c1) {
        StackPane user = new StackPane();
        user.getStyleClass().add("group-pane");
        user.setPadding(new Insets(5));
        VBox lblsvbox = new VBox();
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(c1.getName());
        lbl.setTextFill(Color.CYAN);
        lbl.setPadding(new Insets(5));
        Label lbl2 = new Label();
        lbl2.setPadding(new Insets(5));
        lbl2.setText(c1.getStatus());
        lbl2.setTextFill(Color.BLACK);
        lblsvbox.getChildren().add(lbl);
        lblsvbox.getChildren().add(lbl2);
        user.getChildren().add(lblsvbox);
        user.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(c1.getId(), c1.getName());
                        createUserPane(c1.getId(), c1.getStatus(), c1.getName());
                    }
                }
            }
        });
        UserTabVbox.setSpacing(5);
        UserTabVbox.getChildren().add(user);
        UserTabVboxes.add(new Pair<>(c1.getId(),user));
    }

    public void AddNewGroup(Room r1) {
        StackPane group = new StackPane();
        group.getStyleClass().add("group-pane");
        group.setPadding(new Insets(5));
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(r1.getName());
        lbl.setTextFill(Color.BLACK);
        group.getChildren().add(lbl);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(r1.getId(), r1.getId());
                    }
                }
            }
        });
        GroupTabVbox.setSpacing(5);
        GroupTabVbox.getChildren().add(group);
        GroupTabVboxes.add(new Pair<>(r1.getId(),group));

    }

    public void AddTab(String ID, String UserName) {

        Tab t = new Tab(UserName);
        
        tabs.getTabs().add(t);
        t.setId(ID);
        t.setOnCloseRequest((e -> onTabClose(t.getId())));
        t.setOnSelectionChanged((e -> onTabClick(t.getId())));
        tabs.getSelectionModel().select(t);
        ScrollPane scrollPane = new ScrollPane();
        t.setContent(scrollPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.vvalueProperty().bind(root.heightProperty());
        vboxes.add(new Pair(t.getId(), root));
        if(ID.charAt(0)=='r')
        {
            
            ScrollPane MembersScroll = new ScrollPane();
            
            
            MembersScroll.setMaxHeight(100);
            MembersScroll.setMinHeight(100);
            MembersScroll.setFitToWidth(true);
            HBox MembersCircles  = new HBox();
            MembersCircles.setMaxHeight(50);
            MembersCircles.setMinHeight(50);
            MembersCircles.setStyle("-fx-border-width:5; -fx-border-color: #555; -fx-border-radius: 20px; -fx-background-radius: 20px;");
            MembersScroll.setContent(MembersCircles);
            root.getChildren().add(MembersScroll);
            JFXButton GroupOptions = new JFXButton("Group Options");
            GroupOptions.setOnMouseClicked(e -> ShowPopupRoom(GroupOptions,ID,e));
            MembersCircles.getChildren().add(GroupOptions);
            membersInRomPane.add(new Pair<>(ID,MembersCircles));
            
            
        }

        
    }
    public void RoomOptionPopUp()
    {
        JFXButton AddMember = new JFXButton("Add Member");
        JFXButton RemoveMember = new JFXButton("Remove Member");
        JFXButton MakeAdmin = new JFXButton("Make Admin");
        JFXButton LeaveRoom = new JFXButton("Leave Room");
        VBox BtnsPop = new VBox(AddMember,RemoveMember,MakeAdmin,LeaveRoom);
        RoomPopUp.setPopupContent(BtnsPop);
        
        

    }
    public void ShowPopupRoom(JFXButton GroupOptions, String ID, MouseEvent e){
        if(e.getButton()== MouseButton.SECONDARY)
            RoomPopUp.show(emojies,JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT);
    }
    public void onTabClose(String id) {
        for (int i = 0; i < vboxes.size(); i++) {
            if (vboxes.get(i).getKey() == id) {
                vboxes.remove(i);
                break;
            }
        }
        for (int i = 0; i < vboxes.size(); i++) {
            System.out.println(vboxes.get(i).getKey().toString());
        }
        System.out.println();

    }

    public void receive(String Msg, String ID, String UserName) {
        boolean found = false;
        for (int i = 0; i < vboxes.size(); i++) {
            if (vboxes.get(i).getKey() == ID) {
                found = true;
                StackPane p1 = new StackPane();
//                vboxes.get(i).getValue().getStyleClass().add("receiveMsg");
                for (int j = 0; j < tabs.getTabs().size(); j++) {
                    if(tabs.getTabs().get(j).getId().equals(ID))
                    {
                        Tab ta = tabs.getTabs().get(j);
                        if(tabs.getSelectionModel().getSelectedItem() == ta)
                            break;
                        if(!ta.getStyleClass().contains("receiveMsg"))
                        {
                            ta.setText(ta.getText()+ "!!!");
                            ta.getStyleClass().add("receiveMsg");
                        }
                        break;
                    }
                }
                p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                p1.setMinHeight(Region.USE_PREF_SIZE);
                Label lbl1 = new Label(Msg);
                lbl1.setPadding(new Insets(10));
                lbl1.setText(Msg);
                lbl1.setTextFill(Color.BLACK);
                HBox hob = new HBox();
                hob.setPrefWidth(470);
                hob.setAlignment(Pos.CENTER_RIGHT);
                p1.getChildren().add(lbl1);
                hob.getChildren().add(p1);
                vboxes.get(i).getValue().getChildren().add(hob);
                break;
            }

        }
        if (found == false) {
            AddTab(ID, UserName);
            createGroupPane(ID, UserName);
            StackPane p1 = new StackPane();

            p1.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
            p1.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            p1.setMinHeight(Region.USE_PREF_SIZE);
            Label lbl1 = new Label(Msg);
            lbl1.setPadding(new Insets(10));
            lbl1.setText(Msg);
            lbl1.setTextFill(Color.BLACK);
            HBox hob = new HBox();
            hob.setPrefWidth(470);
            hob.setAlignment(Pos.CENTER_RIGHT);
            p1.getChildren().add(lbl1);
            hob.getChildren().add(p1);
            vboxes.get(vboxes.size() - 1).getValue().getChildren().add(hob);
        }

    }

    public void sendBtn() {
        if (!ChatTxt.getText().equals("")) {
            for (int i = 0; i < vboxes.size(); i++) {
                if (vboxes.get(i).getKey() == tabs.getSelectionModel().getSelectedItem().getId()) {
                    String msg = ChatTxt.getText();

                    StackPane p = new StackPane();
                    p.setMinHeight(Region.USE_PREF_SIZE);
                    p.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    p.setStyle("-fx-background-color: #00FFFF; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width:5;");
                    
                    Label lbl = new Label(msg);
                    lbl.setPadding(new Insets(10));
                    lbl.setText(msg);
                    lbl.setTextFill(Color.BLACK);
                    lbl.setWrapText(true);
                  //= tr
                    p.getChildren().add(lbl);
                    vboxes.get(i).getValue().getChildren().add(p);
                    ChatTxt.setText("");
                    emojiPane.setVisible(false);
                    break;
                }
            }

        }
    }

    public void createUserPane(String UserID, String Status, String UserName) {
        StackPane user = new StackPane();
        user.getStyleClass().add("group-pane");
        user.setPadding(new Insets(5));
        VBox lblsvbox = new VBox();
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(UserName);
        lbl.setTextFill(Color.CYAN);

        Label lbl2 = new Label();
        lbl2.setPadding(new Insets(5));
        lbl2.setText(Status);
        lbl2.setTextFill(Color.BLACK);
        lblsvbox.getChildren().add(lbl);
        lblsvbox.getChildren().add(lbl2);
        user.getChildren().add(lblsvbox);
        user.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(UserID, UserName);
                    }
                }
            }
        });
        usersVboxes.add(new Pair<>(UserID, user));
        UsersVbox.setSpacing(5);
        UsersVbox.getChildren().add(user);

    }

    public void createGroupPane(String groupID, String GroupName) {
        StackPane group = new StackPane();
        group.getStyleClass().add("group-pane");
        group.setPadding(new Insets(5));
        Label lbl = new Label();
        lbl.setPadding(new Insets(5));
        lbl.setText(GroupName);
        lbl.setTextFill(Color.BLACK);
        group.getChildren().add(lbl);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        AddTab(groupID, GroupName);
                    }
                }
            }
        });
        groupVboxes.add(new Pair<>(groupID, group));
        groupVbox.setSpacing(5);
        groupVbox.getChildren().add(group);

    }

    public void TestBtn() throws IOException {
        Client c1 = new Client("1", "online", "kero");
        Client c2 = new Client("2", "online", "kord");
        Client c3 = new Client("3", "online", "fadi");

        AddNewUser(c1);
        AddNewUser(c2);
        AddNewUser(c3);
        //test with kyrillos
//        Socket s = new Socket("127.0.0.1",15000);
        
    }
    
    
   
    public void AddRoomDialog()
    {
        
        Parent root;
        try {
           
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddRom.fxml"));
//        final Pane rootPane = (Pane)loader.load();
//        Scene scene =  new Scene(rootPane);


            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adding Room");
            stage.setScene(new Scene(root));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2); 
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
            stage.setResizable(false);
            stage.show();
            AddRomController controller = loader.<AddRomController>getController();
            stage.setOnHidden((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                String RoomName = controller.onClose();
                if(!RoomName.equals("")){
                    System.out.println(RoomName);
//                    AddNewGroup(new Room(RoomName, admin, RoomName));
                }             
            }
        });

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void onReceive()
    {
        receive("kokokoko", "r1", "fefe");
        RemoveRoomFromTabsAndPanels("r1");
        
    }

    private void onTabClick(String id) {
        if(tabs.getSelectionModel().getSelectedItem().getStyleClass().contains("receiveMsg"))
        {Tab ta = tabs.getSelectionModel().getSelectedItem();
            ta.getStyleClass().remove("receiveMsg");
            ta.setText(ta.getText().replace("!!!", ""));
        }
        
    }
    
    public void RemoveUserFromTabsAndPanels(String ID)
    {
        for (int i = 0; i < usersVboxes.size(); i++) {
            if(ID.equals(usersVboxes.get(i).getKey()))
            {                
                UsersVbox.getChildren().remove(groupVboxes.get(i).getValue());

                
                for (int j = 0; j < tabs.getTabs().size(); j++) {
                    if(tabs.getTabs().get(j).getId().equals(ID))
                    { tabs.getTabs().remove(i);
                    break;}
                }
                for (int j = 0; j < UserTabVboxes.size(); j++) {
                    if(ID.equals(UserTabVboxes.get(j).getKey()))
                    {UserTabVboxes.remove(j);
                        break;
                    }
                }
                for (int j = 0; j < vboxes.size(); j++) {
                    if(ID.equals(vboxes.get(j).getKey()))
                    {vboxes.remove(j);
                        break;
                    }
                }
                usersVboxes.remove(i);
                
                break;
            }
        }
    }
    public void RemoveRoomFromTabsAndPanels(String ID)
    {
        for (int i = 0; i < groupVboxes.size(); i++) {
            if(ID.equals(groupVboxes.get(i).getKey()))
            {
                groupVbox.getChildren().remove(groupVboxes.get(i).getValue());
                for (int j = 0; j < tabs.getTabs().size(); j++) {
                    if(tabs.getTabs().get(j).getId().equals(ID))
                    {   
                        tabs.getTabs().remove(j);
                    break;}
                }
                for (int j = 0; j < GroupTabVboxes.size(); j++) {
                    if(ID.equals(GroupTabVboxes.get(j).getKey()))
                    {   
                            GroupTabVboxes.remove(j);
                            break;
                    }
                }
                for (int j = 0; j < vboxes.size(); j++) {
                    if(ID.equals(vboxes.get(j).getKey()))
                    {vboxes.remove(j);
                        break;
                    }
                }
                
                groupVboxes.remove(i);
                break;
            }
        }
    }
    
    
}
