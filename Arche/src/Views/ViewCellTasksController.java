package Views;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;

import Models.ModelControl;
import Models.Task;
import Models.TaskType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

public class ViewCellTasksController implements Initializable{
	@FXML private JFXTabPane tabPane;
	@FXML private Tab tableTab, infoTab;
	
	//information tab
	@FXML private Label label;
	@FXML private TextField name;
	@FXML private ChoiceBox<String> className;
	@FXML private ChoiceBox<String> taskType;
	@FXML private TextArea description;
	@FXML private DateTimePicker dueDate, scheduledStartTime, scheduledEndTime;
	@FXML private JFXButton confirmInfo, cancelInfo;
	@FXML private TextField noticePeriod;
	@FXML private TextField hours, minutes;		//timeToComplete
	
	//table tab
	@FXML private JFXButton confirmAll, cancelAll;
	@FXML private JFXButton add, edit, delete;
	@FXML private TableView<Task> newTasksTable;
	@FXML private TableColumn<Task, String> nameColumn, descriptionColumn;
	@FXML private TableColumn<Task, Boolean> scheduledColumn;
	
	private ObservableList<Task> viewableTaskList;
	private ArrayList<Task> taskList;		//for initialization
	private Task taskTemp;
	private Date taskLastClickTime;
	private boolean isOnAdd;			//test if got to confirmInfo from edit or add button
	private int sIndex;
	
	//for registering changes made to tasks and confirm them once confirm button clicked
	private ArrayList<Task> addQueue = new ArrayList<Task>();
	private ArrayList<Task> updateQueue = new ArrayList<Task>();
	private ArrayList<Task> deleteQueue = new ArrayList<Task>();
	
	public ViewCellTasksController(ArrayList<Task> tasks) {
		taskList = tasks;
		viewableTaskList = FXCollections.observableArrayList();
		updateTaskList();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dueDate.setFormat("MM/dd/yyyy hh:mm");
		scheduledStartTime.setFormat("MM/dd/yyyy hh:mm");
		scheduledEndTime.setFormat("MM/dd/yyyy hh:mm");
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
		scheduledColumn.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isScheduled()));
		scheduledColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		
		nameColumn.setStyle("-fx-alignment: CENTER;");
		descriptionColumn.setStyle("-fx-alignment: CENTER;");
		scheduledColumn.setStyle("-fx-alignment: CENTER;");
		
		//initialize choiceboxes
		ArrayList<Models.Class> allClasses = ModelControl.getClasses();
		ArrayList<TaskType> allTaskTypes = ModelControl.getTaskTypes();
		
		ObservableList<String> tts = FXCollections.observableArrayList();
		ObservableList<String> cls = FXCollections.observableArrayList();
		for(int count = 0;count<allTaskTypes.size();count++) {
			tts.add(allTaskTypes.get(count).getName());
		}
		for(int count = 0;count<allClasses.size();count++) {
			cls.add(allClasses.get(count).getAbbreviation());
		}
		taskType.getItems().clear();
		taskType.setItems(tts);
		className.getItems().clear();
		className.setItems(cls);
		
		//needs to be false in order for deleteButtonClicked method to work
		//as well as select indexing after completed operation
		nameColumn.setSortable(false);
		descriptionColumn.setSortable(false);
		scheduledColumn.setSortable(false);
		
		initializeCloseEventProperty();
		newTasksTable.setItems(viewableTaskList);
		resetTabPane();
	}
	@FXML
	private void addButtonClicked() {
		isOnAdd = true;
		tableTab.setDisable(true);
		infoTab.setDisable(false);
		infoTab.setText("Information");
		label.setText("New Task");
		tabPane.getSelectionModel().select(1);
	}
	@FXML
	private void editButtonClicked() {
		Task selected = newTasksTable.getSelectionModel().selectedItemProperty().get();
		sIndex = newTasksTable.getSelectionModel().getSelectedIndex();
		
		name.setText(selected.getName());
		description.setText(selected.getDescription());
		
		noticePeriod.setText(Integer.toString(selected.getNoticePeriod()));
		int amountOfTime = selected.getTimeToComplete();
		hours.setText((amountOfTime/60)+"");
		minutes.setText((amountOfTime%60)+"");
		
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(selected.getDueDate()), 
                TimeZone.getDefault().toZoneId()); 
		dueDate.setDateTimeValue(dateTime);
		if(selected.isScheduled()) {
			dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(selected.getScheduledStartTime()), 
	                TimeZone.getDefault().toZoneId()); 
			scheduledStartTime.setDateTimeValue(dateTime);
			dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(selected.getScheduledEndTime()), 
	                TimeZone.getDefault().toZoneId()); 
			scheduledEndTime.setDateTimeValue(dateTime);
		}
		
		int classIndex = -1;
		int taskIndex = -1;
		ArrayList<Models.Class> allClasses = ModelControl.getClasses();
		ArrayList<TaskType> allTaskTypes = ModelControl.getTaskTypes();
		
		//find where in list our values are for default choicebox initialization
		for(int count = 0;count<allClasses.size();count++) {
			if(allClasses.get(count).getAbbreviation().equals(selected.getClassAbr())) {
				classIndex = count;
				break;
			}
		}
		for(int count = 0;count<allTaskTypes.size();count++) {
			if(allTaskTypes.get(count).getName().equals(selected.getType())) {
				taskIndex = count;
				break;
			}
		}
		//move names and abbreviations into observable lists
		ObservableList<String> tts = FXCollections.observableArrayList();
		ObservableList<String> cls = FXCollections.observableArrayList();
		for(int count = 0;count<allTaskTypes.size();count++) {
			tts.add(allTaskTypes.get(count).getName());
		}
		for(int count = 0;count<allClasses.size();count++) {
			cls.add(allClasses.get(count).getAbbreviation());
		}
		//fill choiceboxes
		taskType.getItems().clear();
		taskType.setItems(tts);
		className.getItems().clear();
		className.setItems(cls);
		//make default values the one we found earlier, which is one for passed in task
		taskType.getSelectionModel().select(taskIndex);
		className.getSelectionModel().select(classIndex);
		
		isOnAdd = false;
		tableTab.setDisable(true);
		infoTab.setDisable(false);
		infoTab.setText("Information");
		label.setText("Edit Task");
		tabPane.getSelectionModel().select(1);
	}
	@FXML
	private void deleteButtonClicked() {
		Task t = newTasksTable.getSelectionModel().getSelectedItem();
		taskList.remove(t);
		updateTaskList();
		newTasksTable.setItems(viewableTaskList);
		deleteQueue.add(t);
	}
	@FXML
	private void confirmAllButtonClicked() {
		//execute queues
		for(int count=0;count<addQueue.size();count++) {
			ModelControl.addTask(addQueue.get(count));
		}
		for(int count=0;count<updateQueue.size();count++) {
			ModelControl.updateTask(updateQueue.get(count));
		}
		for(int count=0;count<deleteQueue.size();count++) {
			ModelControl.deleteTask(deleteQueue.get(count));
		}
		closeWindow();
	}
	@FXML
	private void cancelAllButtonClicked() {
		closeWindow();
	}
	@FXML
	private void confirmInfoButtonClicked() {
		Task t;
		if(isOnAdd) {				//got here from addButton
			int ttc = (Integer.parseInt(hours.getText())*60) + Integer.parseInt(minutes.getText());
			t = new Task(name.getText(), description.getText(),
					dueDate.getDateTimeValue().atZone(ZoneId.
					systemDefault()).toInstant().toEpochMilli(),
					false, taskType.getSelectionModel().getSelectedItem(),
					className.getSelectionModel().getSelectedItem(),
					Integer.parseInt(noticePeriod.getText()), ttc);
			if(scheduledStartTime.getDateTimeValue() != null && scheduledStartTime.getDateTimeValue() != null) {
				t.setScheduledStartTime(scheduledStartTime.getDateTimeValue().atZone(ZoneId.
						systemDefault()).toInstant().toEpochMilli());
				t.setScheduledEndTime(scheduledEndTime.getDateTimeValue().atZone(ZoneId.
						systemDefault()).toInstant().toEpochMilli());
			}
			taskList.add(t);
			updateTaskList();
			newTasksTable.setItems(viewableTaskList);
			newTasksTable.getSelectionModel().select(newTasksTable.getItems().size());
			addQueue.add(t);
		}
		else {						//got here from editButton
			t = viewableTaskList.get(newTasksTable.getSelectionModel().getSelectedIndex());		//TODO: check
			
			t.setName(name.getText());
			t.setType(taskType.getSelectionModel().getSelectedItem());
			t.setClassAbr(className.getSelectionModel().getSelectedItem());
			t.setDueDate(dueDate.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			t.setDescription(description.getText());
			int amountOfTime = (Integer.parseInt(hours.getText())*60) + Integer.parseInt(minutes.getText());
			t.setTimeToComplete(amountOfTime);
			t.setNoticePeriod(Integer.parseInt(noticePeriod.getText()));
			if(scheduledStartTime.getDateTimeValue() != null || scheduledStartTime.getDateTimeValue() != null) {
				t.setScheduledStartTime(scheduledStartTime.getDateTimeValue().atZone(ZoneId.
						systemDefault()).toInstant().toEpochMilli());
				t.setScheduledEndTime(scheduledEndTime.getDateTimeValue().atZone(ZoneId.
						systemDefault()).toInstant().toEpochMilli());
			}
			updateTaskList();
			newTasksTable.setItems(viewableTaskList);
			newTasksTable.getSelectionModel().select(sIndex);
			updateQueue.add(t);
		}
		resetTabPane();
	}
	@FXML
	private void cancelInfoButtonClicked() {
		resetTabPane();
	}
	//open view window on double click
	@FXML
	private void handleTaskClick() {
	    Task row = newTasksTable.getSelectionModel().getSelectedItem();
	    if(row == null) return;
	    if(row != taskTemp){
	        taskTemp = row;
	        taskLastClickTime = new Date();
	    } else if(row == taskTemp) {
	        Date now = new Date();
	        long diff = now.getTime() - taskLastClickTime.getTime();
	        if (diff < 300){ //another click registered in 300 millis
	             editButtonClicked();
	        } else {
	            taskLastClickTime = new Date();
	        }
	    }
	}
	@FXML
	private void setCloseEvent() {
		Stage window = (Stage) description.getScene().getWindow();
		window.setOnCloseRequest(e -> {
			e.consume();
			if(newTasksTable.getItems().size() > 0) {
				closeWindow(ConfirmExitView.display("Are you sure you want to exit without updating your tasks?"));
			}
			else {
				closeWindow();
			}
		});
	}
	private void initializeCloseEventProperty() {
		viewableTaskList.addListener(new ListChangeListener<Task>(){
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Task> pChange) {
                while(pChange.next()) {
                    setCloseEvent();
                }
            }
        });
	}
	@FXML
	private void handleKeyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			if(tableTab.isSelected()) {
				confirmAllButtonClicked();
			}
			else {
				confirmInfoButtonClicked();
			}
		}
	}
	private void updateTaskList() {
		viewableTaskList.clear();
		for(int count=0;count<taskList.size();count++) {
			viewableTaskList.add(taskList.get(count));
		}
	}
	private void resetTabPane() {
		tabPane.getSelectionModel().select(0);
		tableTab.setDisable(false);
		infoTab.setDisable(true);
		infoTab.setText("");
		name.setText("");
		className.getSelectionModel().clearSelection();
		taskType.getSelectionModel().clearSelection();
		description.setText("");
		dueDate.setDateTimeValue(null);
		noticePeriod.setText("");
		hours.setText("");
		minutes.setText("");
		scheduledStartTime.setDateTimeValue(null);
		scheduledEndTime.setDateTimeValue(null);
	}
	private void closeWindow(boolean answer) {
		if(answer == true) {
			closeWindow();
		}
	}
	private void closeWindow() {
		Stage stage = (Stage) confirmAll.getScene().getWindow();
		stage.close();
	}
}