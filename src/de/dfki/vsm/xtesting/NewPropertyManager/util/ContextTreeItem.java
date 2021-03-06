package de.dfki.vsm.xtesting.NewPropertyManager.util;

import de.dfki.vsm.xtesting.NewPropertyManager.model.AbstractTreeEntry;
import de.dfki.vsm.xtesting.NewPropertyManager.model.EntryAgent;
import de.dfki.vsm.xtesting.NewPropertyManager.model.EntryPlugin;
import de.dfki.vsm.xtesting.NewPropertyManager.util.events.ContextEvent;
import de.dfki.vsm.xtesting.NewPropertyManager.util.events.DeleteContextEventAgent;
import de.dfki.vsm.xtesting.NewPropertyManager.util.events.DeleteContextEventPlugin;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.LinkedList;

/**
 * Created by alvaro on 5/14/16.
 */
public class ContextTreeItem extends AbstractTreeItem implements TreeObservable{
    private LinkedList<TreeObserver> observers = new LinkedList<>();
    private String contextValue = "Agent";
    private String pluginName;
    private AbstractTreeEntry entryItem;
    public ContextTreeItem(String name) {
        this.setValue(name);
    }
    public static int agentCounter = 1;

    private String getContextValueName(){
        String name = contextValue + agentCounter;
        return name;
    }

    public ContextTreeItem(AbstractTreeEntry item) {
        entryItem = item;
        this.setValue(entryItem.getName());
    }

    public AbstractTreeEntry getEntryItem(){
        return entryItem;
    }

    @Override
    public ContextMenu getMenu(){

        ContextMenu menu = new ContextMenu();
        if(entryItem instanceof EntryPlugin) {
            MenuItem addNewAgent = getAddNewAgentItem();
            menu.getItems().add(addNewAgent);
        }
        //TODO: Delete Option
        MenuItem deleteItem = getDeleteItem();
        menu.getItems().add(deleteItem);
        return menu;

    }

    private MenuItem getAddNewAgentItem(){
        MenuItem addNewAgent = new MenuItem("Add new agent");
        addNewAgent.setOnAction(new EventHandler() {
            public void handle(Event t) {
                EntryAgent agent = new EntryAgent(getContextValueName());
                BoxTreeItem newBox = new BoxTreeItem(agent);
                getChildren().add(newBox);
                agentCounter++;
                notifyObserver(agent);
                newBox.getParent().setExpanded(true);
            }
        });
        return  addNewAgent;
    }

    private MenuItem getDeleteItem(){
        MenuItem deleteItem = new MenuItem("Delete " + entryItem.getName());
        deleteItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                AbstractTreeEntry item = getEntryItem();
                if(item instanceof EntryAgent){
                    notifyObserverOnDeleteAgent(item);
                }else if(item instanceof EntryPlugin){
                    notifyObserverOnDeletePlugin(item);
                }
            }
        });
        return  deleteItem;
    }


    @Override
    public void registerObserver(TreeObserver object) {
        observers.add(object);
    }

    @Override
    public void unregisterObserver(TreeObserver object) {
        observers.remove(object);
    }

    @Override
    public void notifyObserver() {
        for (TreeObserver observer:observers) {
            observer.update(new ContextEvent(getContextValueName(), this.getValue().toString(), entryItem));
        }
    }

    public void notifyObserver(AbstractTreeEntry entry) {
        for (TreeObserver observer:observers) {
            observer.update(new ContextEvent(getContextValueName(), this.getValue().toString(), entry));
        }
    }

    private void notifyObserverOnDeleteAgent(AbstractTreeEntry entry) {
        for (TreeObserver observer:observers) {
            observer.update(new DeleteContextEventAgent(entry));
        }
    }

    private void notifyObserverOnDeletePlugin(AbstractTreeEntry entry) {
        for (TreeObserver observer:observers) {
            observer.update(new DeleteContextEventPlugin(entry));
        }
    }

    public String getPluginName() {
        return pluginName;
    }
}



