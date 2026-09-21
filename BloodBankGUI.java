package bloodbank;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BloodBankGUI extends Application {

    private Manager manager;
    private TableView<String[]> table;
    private ObservableList<String[]> tableData;
    private HashMap<String, String> users = new HashMap<>();
    private String currentUser = "";

    // ─────────────────────────────────────────
    //  Splash Screen
    // ─────────────────────────────────────────
    @Override
    public void start(Stage primaryStage) {
        manager = new Manager();
        tableData = FXCollections.observableArrayList();

        Stage splash = new Stage();
        splash.initStyle(StageStyle.UNDECORATED);

        Label welcome = new Label("Welcome to");
        welcome.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 22));
        welcome.setTextFill(Color.WHITE);

        Label appName = new Label("Blood Bank Management System");
        appName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        appName.setTextFill(Color.WHITE);

        Label loading = new Label("Loading...");
        loading.setFont(Font.font("Segoe UI", 15));
        loading.setTextFill(Color.WHITE);

        VBox splashBox = new VBox(20, welcome, appName, loading);
        splashBox.setAlignment(Pos.CENTER);
        splashBox.setPadding(new Insets(60, 80, 60, 80));
        splashBox.setStyle("-fx-background-color: #b40000;");

        splash.setScene(new Scene(splashBox, 1000, 650));
        splash.centerOnScreen();
        splash.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> {
            splash.close();
            showLoginScreen(primaryStage);
        });
        pause.play();
    }

    // ─────────────────────────────────────────
    //  Login Screen
    // ─────────────────────────────────────────
    private void showLoginScreen(Stage primaryStage) {

        Label title = new Label("Blood Bank Management System");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Login to your account");
        subtitle.setFont(Font.font("Segoe UI", 15));
        subtitle.setTextFill(Color.WHITE);
       

        TextField tfUser = new TextField();
        tfUser.setPromptText("Username");
        tfUser.setMaxWidth(300);
        tfUser.setPrefHeight(38);
        tfUser.setStyle("-fx-font-size: 14;");

        PasswordField tfPass = new PasswordField();
        tfPass.setPromptText("Password");
        tfPass.setMaxWidth(300);
        tfPass.setPrefHeight(38);
        tfPass.setStyle("-fx-font-size: 14;");

        Label msgLabel = new Label("");
        msgLabel.setFont(Font.font("Segoe UI", 13));
        msgLabel.setTextFill(Color.YELLOW);

        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(300);
        btnLogin.setPrefHeight(42);
        btnLogin.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        btnLogin.setStyle("-fx-background-color: white; -fx-text-fill: #b40000; -fx-cursor: hand; -fx-background-radius: 8;");

        Label createLabel = new Label("Don't have an account?");
        createLabel.setFont(Font.font("Segoe UI", 13));
        createLabel.setTextFill(Color.WHITE);
        createLabel.setOpacity(0.85);

        Button btnSignup = new Button("Create New Account");
        btnSignup.setPrefWidth(300);
        btnSignup.setPrefHeight(38);
        btnSignup.setFont(Font.font("Segoe UI", 13));
        btnSignup.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-border-color: white; -fx-border-radius: 8;");

        VBox formBox = new VBox(14, title, subtitle, tfUser, tfPass, msgLabel, btnLogin, createLabel, btnSignup);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(50, 60, 50, 60));
        formBox.setStyle("-fx-background-color: #8b0000; -fx-background-radius: 14;");
        formBox.setMaxWidth(420);

        VBox root = new VBox(formBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #b40000;");

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blood Bank - Login");
        primaryStage.centerOnScreen();
        primaryStage.show();

        btnLogin.setOnAction(e -> {
            String u = tfUser.getText().trim();
            String p = tfPass.getText().trim();

            if (u.isEmpty() || p.isEmpty()) {
                msgLabel.setText("Please fill in all fields.");
                return;
            }
            if (!users.containsKey(u)) {
                msgLabel.setText("Username not found. Please create an account.");
                return;
            }
            if (!users.get(u).equals(p)) {
                msgLabel.setText("Wrong password. Try again.");
                return;
            }
            currentUser = u;
            showRoleScreen(primaryStage);
        });

        btnSignup.setOnAction(e -> showSignupScreen(primaryStage));
    }

    // ─────────────────────────────────────────
    //  Signup Screen
    // ─────────────────────────────────────────
    private void showSignupScreen(Stage primaryStage) {

        Label title = new Label("Create New Account");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Fill in your information below");
        subtitle.setFont(Font.font("Segoe UI", 14));
        subtitle.setTextFill(Color.WHITE);
        subtitle.setOpacity(0.85);

        TextField tfName = new TextField();
        tfName.setPromptText("Full Name");
        tfName.setMaxWidth(300);
        tfName.setPrefHeight(38);
        tfName.setStyle("-fx-font-size: 14;");

        TextField tfAge = new TextField();
        tfAge.setPromptText("Age");
        tfAge.setMaxWidth(300);
        tfAge.setPrefHeight(38);
        tfAge.setStyle("-fx-font-size: 14;");

        TextField tfContact = new TextField();
        tfContact.setPromptText("Contact Number");
        tfContact.setMaxWidth(300);
        tfContact.setPrefHeight(38);
        tfContact.setStyle("-fx-font-size: 14;");

        TextField tfBlood = new TextField();
        tfBlood.setPromptText("Blood Group (e.g. A+, O-)");
        tfBlood.setMaxWidth(300);
        tfBlood.setPrefHeight(38);
        tfBlood.setStyle("-fx-font-size: 14;");

        TextField tfUser = new TextField();
        tfUser.setPromptText("Choose Username");
        tfUser.setMaxWidth(300);
        tfUser.setPrefHeight(38);
        tfUser.setStyle("-fx-font-size: 14;");

        PasswordField tfPass = new PasswordField();
        tfPass.setPromptText("Choose Password");
        tfPass.setMaxWidth(300);
        tfPass.setPrefHeight(38);
        tfPass.setStyle("-fx-font-size: 14;");

        PasswordField tfConfirm = new PasswordField();
        tfConfirm.setPromptText("Confirm Password");
        tfConfirm.setMaxWidth(300);
        tfConfirm.setPrefHeight(38);
        tfConfirm.setStyle("-fx-font-size: 14;");

        Label msgLabel = new Label("");
        msgLabel.setFont(Font.font("Segoe UI", 13));
        msgLabel.setTextFill(Color.YELLOW);

        Button btnCreate = new Button("Create Account");
        btnCreate.setPrefWidth(300);
        btnCreate.setPrefHeight(42);
        btnCreate.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        btnCreate.setStyle("-fx-background-color: white; -fx-text-fill: #1a7a3c; -fx-cursor: hand; -fx-background-radius: 8;");

        Button btnBack = new Button("Back to Login");
        btnBack.setPrefWidth(300);
        btnBack.setPrefHeight(38);
        btnBack.setFont(Font.font("Segoe UI", 13));
        btnBack.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-border-color: white; -fx-border-radius: 8;");

        VBox formBox = new VBox(10, title, subtitle,
                tfName, tfAge, tfContact, tfBlood,
                tfUser, tfPass, tfConfirm,
                msgLabel, btnCreate, btnBack);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(35, 60, 35, 60));
        formBox.setStyle("-fx-background-color: #1a5c30; -fx-background-radius: 14;");
        formBox.setMaxWidth(420);

        ScrollPane scroll = new ScrollPane(formBox);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #b40000; -fx-background-color: #b40000;");

        VBox root = new VBox(scroll);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #b40000;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blood Bank - Create Account");

        btnCreate.setOnAction(e -> {
            String name    = tfName.getText().trim();
            String ageStr  = tfAge.getText().trim();
            String contact = tfContact.getText().trim();
            String blood   = tfBlood.getText().trim();
            String user    = tfUser.getText().trim();
            String pass    = tfPass.getText().trim();
            String confirm = tfConfirm.getText().trim();

            if (name.isEmpty() || ageStr.isEmpty() || contact.isEmpty() ||
                blood.isEmpty() || user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Please fill in all fields.");
                return;
            }
            if (!name.matches("[a-zA-Z ]+")) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Name should contain letters only.");
                return;
            }
            if (!ageStr.matches("\\d+")) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Age should be a number.");
                return;
            }
            if (!contact.matches("\\d+")) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Contact should contain digits only.");
                return;
            }
            if (!pass.equals(confirm)) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Passwords do not match.");
                return;
            }
            if (users.containsKey(user)) {
                msgLabel.setTextFill(Color.YELLOW);
                msgLabel.setText("Username already exists. Try another.");
                return;
            }

            users.put(user, pass);
            msgLabel.setTextFill(Color.LIGHTGREEN);
            msgLabel.setText("Account created! Redirecting to login...");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(ev -> showLoginScreen(primaryStage));
            pause.play();
        });

        btnBack.setOnAction(e -> showLoginScreen(primaryStage));
    }

    // ─────────────────────────────────────────
    //  Role Selection Screen
    // ─────────────────────────────────────────
    private void showRoleScreen(Stage primaryStage) {

        Label welcome = new Label("Welcome, " + currentUser + "!");
        welcome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        welcome.setTextFill(Color.WHITE);

        Label question = new Label("What would you like to do today?");
        question.setFont(Font.font("Segoe UI", 18));
        question.setTextFill(Color.WHITE);
        question.setOpacity(0.9);

        Button btnDonor = new Button("I want to Donate Blood");
        btnDonor.setPrefWidth(260);
        btnDonor.setPrefHeight(55);
        btnDonor.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        btnDonor.setStyle("-fx-background-color: white; -fx-text-fill: #285ac8; -fx-cursor: hand; -fx-background-radius: 8;");

        Label donorDesc = new Label("Register as a blood donor\nand help save lives");
        donorDesc.setFont(Font.font("Segoe UI", 13));
        donorDesc.setTextFill(Color.WHITE);
        donorDesc.setOpacity(0.85);
        donorDesc.setAlignment(Pos.CENTER);

        VBox donorBox = new VBox(12, btnDonor, donorDesc);
        donorBox.setAlignment(Pos.CENTER);
        donorBox.setPadding(new Insets(30));
        donorBox.setStyle("-fx-background-color: #285ac8; -fx-background-radius: 12;");
        donorBox.setPrefWidth(310);

        Button btnReceiver = new Button("I need Blood");
        btnReceiver.setPrefWidth(260);
        btnReceiver.setPrefHeight(55);
        btnReceiver.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        btnReceiver.setStyle("-fx-background-color: white; -fx-text-fill: #1ea05a; -fx-cursor: hand; -fx-background-radius: 8;");

        Label receiverDesc = new Label("Register as a blood receiver\nand find matching donors");
        receiverDesc.setFont(Font.font("Segoe UI", 13));
        receiverDesc.setTextFill(Color.WHITE);
        receiverDesc.setOpacity(0.85);
        receiverDesc.setAlignment(Pos.CENTER);

        VBox receiverBox = new VBox(12, btnReceiver, receiverDesc);
        receiverBox.setAlignment(Pos.CENTER);
        receiverBox.setPadding(new Insets(30));
        receiverBox.setStyle("-fx-background-color: #1ea05a; -fx-background-radius: 12;");
        receiverBox.setPrefWidth(310);

        Button btnAdmin = new Button("Admin Panel");
        btnAdmin.setPrefWidth(260);
        btnAdmin.setPrefHeight(48);
        btnAdmin.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnAdmin.setStyle("-fx-background-color: white; -fx-text-fill: #b40000; -fx-cursor: hand; -fx-background-radius: 8;");

        Label adminDesc = new Label("Manage all donors, receivers and donations");
        adminDesc.setFont(Font.font("Segoe UI", 12));
        adminDesc.setTextFill(Color.WHITE);
        adminDesc.setOpacity(0.85);

        VBox adminBox = new VBox(10, btnAdmin, adminDesc);
        adminBox.setAlignment(Pos.CENTER);
        adminBox.setPadding(new Insets(25));
        adminBox.setStyle("-fx-background-color: #8b0000; -fx-background-radius: 12;");
        adminBox.setPrefWidth(310);

        HBox topCards = new HBox(30, donorBox, receiverBox);
        topCards.setAlignment(Pos.CENTER);

        VBox root = new VBox(25, welcome, question, topCards, adminBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #b40000;");

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setTitle("Blood Bank - Select Role");
        primaryStage.setScene(scene);

        btnDonor.setOnAction(e -> showMainWindow(primaryStage, "donor"));
        btnReceiver.setOnAction(e -> showMainWindow(primaryStage, "receiver"));
        btnAdmin.setOnAction(e -> showMainWindow(primaryStage, "admin"));
    }

    // ─────────────────────────────────────────
    //  Main Window
    // ─────────────────────────────────────────
    private void showMainWindow(Stage primaryStage, String role) {

        Label title = new Label("BLOOD BANK MANAGEMENT SYSTEM");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        HBox.setHgrow(title, Priority.ALWAYS);

        Label userLabel = new Label("User: " + currentUser);
        userLabel.setFont(Font.font("Segoe UI", 13));
        userLabel.setTextFill(Color.WHITE);
        userLabel.setPadding(new Insets(0, 10, 0, 0));

        Button btnLogout = new Button("Logout");
        btnLogout.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        btnLogout.setStyle("-fx-background-color: white; -fx-text-fill: #b40000; -fx-cursor: hand;");
        btnLogout.setPadding(new Insets(6, 14, 6, 14));
        btnLogout.setOnAction(e -> {
            currentUser = "";
            tableData.clear();
            showLoginScreen(primaryStage);
        });

        HBox header = new HBox(10, title, userLabel, btnLogout);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setStyle("-fx-background-color: #b40000;");
        header.setPadding(new Insets(10));

        table = buildTable();
        table.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox bottomSection;

        if (role.equals("donor")) {
            VBox donorPane = donorPanel();
            donorPane.setMaxWidth(Double.MAX_VALUE);
            bottomSection = new VBox(donorPane);
        } else if (role.equals("receiver")) {
            VBox receiverPane = receiverPanel();
            receiverPane.setMaxWidth(Double.MAX_VALUE);
            bottomSection = new VBox(receiverPane);
        } else {
            VBox donorPane    = donorPanel();
            VBox receiverPane = receiverPanel();
            HBox.setHgrow(donorPane,    Priority.ALWAYS);
            HBox.setHgrow(receiverPane, Priority.ALWAYS);
            HBox both = new HBox(donorPane, receiverPane);
            bottomSection = new VBox(both);
        }

        VBox root = new VBox(header, table, bottomSection);
        VBox.setVgrow(table, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setTitle("Blood Bank Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ─────────────────────────────────────────
    //  Table
    // ─────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private TableView<String[]> buildTable() {
        TableView<String[]> tv = new TableView<>(tableData);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        String[] headers = {"Name", "Age", "Contact", "Blood Group"};
        for (int i = 0; i < headers.length; i++) {
            final int idx = i;
            TableColumn<String[], String> col = new TableColumn<>(headers[i]);
            col.setCellValueFactory(cd ->
                    new SimpleStringProperty(cd.getValue()[idx]));
            tv.getColumns().add(col);
        }
        return tv;
    }

    private void setTableRows(List<? extends Person> people) {
        tableData.clear();
        for (Person p : people) {
            String bg = (p instanceof Donor)
                    ? ((Donor) p).getBloodGroup()
                    : ((Receiver) p).getBloodGroup();
            tableData.add(new String[]{
                    p.getName(),
                    String.valueOf(p.getAge()),
                    p.getContact(),
                    bg
            });
        }
    }

    // ─────────────────────────────────────────
    //  Donor panel
    // ─────────────────────────────────────────
    private VBox donorPanel() {
        Label lbl = panelLabel("DONOR MANAGEMENT");

        Button btnAdd    = styledButton("Add Donor");
        Button btnView   = styledButton("View Donors");
        Button btnSearch = styledButton("Search Donor");
        Button btnDelete = styledButton("Delete Donor");
        Button btnDonate = styledButton("Donate Blood");

        GridPane grid = buildButtonGrid(
                new Button[]{btnAdd, btnView, btnSearch, btnDelete, btnDonate});

        VBox panel = new VBox(lbl, grid);
        panel.setStyle("-fx-background-color: #285ac8;");
        VBox.setVgrow(grid, Priority.ALWAYS);

        btnAdd.setOnAction(e    -> addDonor());
        btnView.setOnAction(e   -> loadDonors());
        btnSearch.setOnAction(e -> searchDonorByBlood());
        btnDelete.setOnAction(e -> deleteDonor());
        btnDonate.setOnAction(e -> donateBlood());

        return panel;
    }

    // ─────────────────────────────────────────
    //  Receiver panel
    // ─────────────────────────────────────────
    private VBox receiverPanel() {
        Label lbl = panelLabel("RECEIVER MANAGEMENT");

        Button btnAdd     = styledButton("Add Receiver");
        Button btnView    = styledButton("View Receivers");
        Button btnSearch  = styledButton("Search Receiver");
        Button btnDelete  = styledButton("Delete Receiver");
        Button btnHistory = styledButton("Donation History");

        GridPane grid = buildButtonGrid(
                new Button[]{btnAdd, btnView, btnSearch, btnDelete, btnHistory});

        VBox panel = new VBox(lbl, grid);
        panel.setStyle("-fx-background-color: #1ea05a;");
        VBox.setVgrow(grid, Priority.ALWAYS);

        btnAdd.setOnAction(e     -> addReceiver());
        btnView.setOnAction(e    -> loadReceivers());
        btnSearch.setOnAction(e  -> searchReceiverByBlood());
        btnDelete.setOnAction(e  -> deleteReceiver());
        btnHistory.setOnAction(e -> showDonationHistory());

        return panel;
    }

    // ─────────────────────────────────────────
    //  Layout helpers
    // ─────────────────────────────────────────
    private GridPane buildButtonGrid(Button[] btns) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(50);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(50);

        grid.getColumnConstraints().addAll(col1, col2);

        int pairs = btns.length - 1;
        for (int i = 0; i < pairs; i++) {
            btns[i].setMaxWidth(Double.MAX_VALUE);
            grid.add(btns[i], i % 2, i / 2);
        }

        int lastRow = (pairs - 1) / 2 + 1;
        Button last = btns[btns.length - 1];
        last.setMaxWidth(Double.MAX_VALUE);
        grid.add(last, 0, lastRow, 2, 1);

        return grid;
    }

    private Label panelLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lbl.setTextFill(Color.WHITE);
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setAlignment(Pos.CENTER);
        lbl.setPadding(new Insets(8));
        return lbl;
    }

    private Button styledButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-cursor: hand;");
        return btn;
    }

    private GridPane buildForm(String[] labels, TextField[] fields) {
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(8);
        gp.setPadding(new Insets(10));
        for (int i = 0; i < labels.length; i++) {
            gp.add(new Label(labels[i]), 0, i);
            gp.add(fields[i], 1, i);
        }
        return gp;
    }

    // ─────────────────────────────────────────
    //  Donor logic
    // ─────────────────────────────────────────
    private void addDonor() {
        TextField tfName    = new TextField();
        TextField tfAge     = new TextField();
        TextField tfContact = new TextField();
        TextField tfBlood   = new TextField();

        GridPane form = buildForm(
                new String[]{"Name:", "Age:", "Contact:", "Blood Group:"},
                new TextField[]{tfName, tfAge, tfContact, tfBlood});

        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
        dlg.setTitle("Add Donor");
        dlg.setHeaderText(null);
        dlg.getDialogPane().setContent(form);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt != ButtonType.OK) return;
            if (!tfName.getText().matches("[a-zA-Z ]+")) {
                showAlert("Invalid Name — only letters allowed."); return;
            }
            if (!tfAge.getText().matches("\\d+")) {
                showAlert("Age must be a number."); return;
            }
            if (!tfContact.getText().matches("\\d+")) {
                showAlert("Invalid Contact — digits only."); return;
            }
            if (tfBlood.getText().isBlank()) {
                showAlert("Blood group cannot be empty."); return;
            }
            manager.addDonor(
                    tfName.getText().trim(),
                    Integer.parseInt(tfAge.getText()),
                    tfContact.getText().trim(),
                    tfBlood.getText().trim()
            );
            loadDonors();
        });
    }

    private void loadDonors() {
        setTableRows(manager.getAllDonors());
    }

    private void searchDonorByBlood() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle("Search Donor");
        dlg.setHeaderText(null);
        dlg.setContentText("Enter Blood Group (e.g. A+, B-):");
        dlg.showAndWait().ifPresent(blood -> {
            String b = blood.trim();
            if (!b.isEmpty())
                setTableRows(manager.searchDonorByBloodGroup(b));
        });
    }

    private void deleteDonor() {
        String[] row = table.getSelectionModel().getSelectedItem();
        if (row == null) { showAlert("Select a row first."); return; }
        String name = row[0];
        manager.getAllDonors().removeIf(d -> d.getName().equalsIgnoreCase(name));
        Fileutil.saveData(manager.getAllDonors(), manager.getAllReceivers(), manager.getAllDonations());
        loadDonors();
    }

    // ─────────────────────────────────────────
    //  Receiver logic
    // ─────────────────────────────────────────
    private void addReceiver() {
        TextField tfName    = new TextField();
        TextField tfAge     = new TextField();
        TextField tfContact = new TextField();
        TextField tfBlood   = new TextField();

        GridPane form = buildForm(
                new String[]{"Name:", "Age:", "Contact:", "Blood Needed:"},
                new TextField[]{tfName, tfAge, tfContact, tfBlood});

        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
        dlg.setTitle("Add Receiver");
        dlg.setHeaderText(null);
        dlg.getDialogPane().setContent(form);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt != ButtonType.OK) return;
            if (!tfName.getText().matches("[a-zA-Z ]+")) {
                showAlert("Invalid Name — only letters allowed."); return;
            }
            if (!tfAge.getText().matches("\\d+")) {
                showAlert("Age must be a number."); return;
            }
            if (!tfContact.getText().matches("\\d+")) {
                showAlert("Invalid Contact — digits only."); return;
            }
            if (tfBlood.getText().isBlank()) {
                showAlert("Blood group cannot be empty."); return;
            }
            manager.addReceiver(
                    tfName.getText().trim(),
                    Integer.parseInt(tfAge.getText()),
                    tfContact.getText().trim(),
                    tfBlood.getText().trim()
            );
            loadReceivers();
        });
    }

    private void loadReceivers() {
        setTableRows(manager.getAllReceivers());
    }

    private void searchReceiverByBlood() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle("Search Receiver");
        dlg.setHeaderText(null);
        dlg.setContentText("Enter Blood Group (e.g. O+, B-):");
        dlg.showAndWait().ifPresent(blood -> {
            String b = blood.trim();
            if (!b.isEmpty())
                setTableRows(manager.searchReceiverByBloodGroup(b));
        });
    }

    private void deleteReceiver() {
        String[] row = table.getSelectionModel().getSelectedItem();
        if (row == null) { showAlert("Select a row first."); return; }
        String name = row[0];
        manager.getAllReceivers().removeIf(r -> r.getName().equalsIgnoreCase(name));
        Fileutil.saveData(manager.getAllDonors(), manager.getAllReceivers(), manager.getAllDonations());
        loadReceivers();
    }

    // ─────────────────────────────────────────
    //  Donate blood
    // ─────────────────────────────────────────
    private void donateBlood() {
        String[] selectedRow = table.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            showAlert("First click 'View Donors', then select a donor row.");
            return;
        }

        String donorName = selectedRow[0];
        Donor donor = null;
        for (Donor d : manager.getAllDonors()) {
            if (d.getName().equalsIgnoreCase(donorName)) { donor = d; break; }
        }
        if (donor == null) {
            showAlert("Selected row is not a donor.\nClick 'View Donors' first, then select a donor.");
            return;
        }

        Stage picker = new Stage();
        picker.initModality(Modality.APPLICATION_MODAL);
        picker.setTitle("Select Receiver for " + donor.getName());

        ObservableList<String[]> recData = FXCollections.observableArrayList();
        for (Receiver r : manager.getAllReceivers())
            recData.add(new String[]{r.getName(), r.getContact(), r.getBloodGroup()});

        TableView<String[]> recTable = new TableView<>(recData);
        recTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        String[] recHeaders = {"Name", "Contact", "Blood Group"};
        for (int i = 0; i < recHeaders.length; i++) {
            final int idx = i;
            TableColumn<String[], String> col = new TableColumn<>(recHeaders[i]);
            col.setCellValueFactory(cd ->
                    new SimpleStringProperty(cd.getValue()[idx]));
            recTable.getColumns().add(col);
        }

        final Donor finalDonor = donor;

        Button donateBtn = new Button("Donate");
        donateBtn.setStyle("-fx-background-color: #b40000; -fx-text-fill: white; -fx-font-weight: bold;");
        donateBtn.setMaxWidth(Double.MAX_VALUE);
        donateBtn.setOnAction(e -> {
            String[] recRow = recTable.getSelectionModel().getSelectedItem();
            if (recRow == null) { showAlert("Select a receiver first."); return; }

            String recName = recRow[0];
            Receiver receiver = null;
            for (Receiver r : manager.getAllReceivers()) {
                if (r.getName().equalsIgnoreCase(recName)) { receiver = r; break; }
            }
            if (receiver == null) { showAlert("Receiver not found!"); return; }

            if (!finalDonor.getBloodGroup().equalsIgnoreCase(receiver.getBloodGroup())) {
                showAlert("Blood groups do not match!\nDonor: "
                        + finalDonor.getBloodGroup()
                        + "   Receiver: " + receiver.getBloodGroup());
                return;
            }

            manager.donateBlood(finalDonor, receiver);
            showInfo("Blood donated successfully!");
            picker.close();
            tableData.clear();
        });

        BorderPane layout = new BorderPane(recTable);
        layout.setBottom(donateBtn);
        BorderPane.setMargin(donateBtn, new Insets(8));

        picker.setScene(new Scene(layout, 440, 330));
        picker.showAndWait();
    }

    // ─────────────────────────────────────────
    //  Donation history
    // ─────────────────────────────────────────
    private void showDonationHistory() {
        ArrayList<Donation> donations = manager.getAllDonations();
        if (donations.isEmpty()) { showInfo("No donations recorded yet."); return; }

        StringBuilder sb = new StringBuilder();
        for (Donation d : donations)
            sb.append(d.toString()).append("\n\n");

        TextArea ta = new TextArea(sb.toString().trim());
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setPrefSize(620, 300);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Donation History");
        alert.setHeaderText("All Donations (" + donations.size() + ")");
        alert.getDialogPane().setContent(ta);
        alert.getDialogPane().setPrefWidth(660);
        alert.showAndWait();
    }

    // ─────────────────────────────────────────
    //  Alert helpers
    // ─────────────────────────────────────────
    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    // ─────────────────────────────────────────
    //  Main
    // ─────────────────────────────────────────
    public static void main(String[] args) {
        launch(args);
    }
}