package de.dfki.vsm.runtime.project;

import de.dfki.vsm.model.acticon.ActiconConfig;
import de.dfki.vsm.model.project.ProjectConfig;
import de.dfki.vsm.model.gesticon.GesticonConfig;
import de.dfki.vsm.model.project.PlayerConfig;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.sceneflow.SceneFlow;
import de.dfki.vsm.model.scenescript.SceneScript;
import de.dfki.vsm.model.visicon.VisiconConfig;
import de.dfki.vsm.runtime.dialogact.DialogActInterface;
import de.dfki.vsm.runtime.dialogact.DummyDialogAct;
import de.dfki.vsm.runtime.player.Player;
import de.dfki.vsm.runtime.plugin.Plugin;
import de.dfki.vsm.util.log.LOGDefaultLogger;
import de.dfki.vsm.util.xml.XMLUtilities;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Gregor Mehlmann
 */
public class RunTimeProject {

    // The singelton logger instance
    protected final LOGDefaultLogger mLogger
            = LOGDefaultLogger.getInstance();

    // The sceneflow of the project
    private final SceneFlow mSceneFlow = new SceneFlow();
    // The scenescript of the project
    private final SceneScript mSceneScript = new SceneScript();
    // The project configuration of the project
    private final ProjectConfig mProjectConfig = new ProjectConfig();
    // The acticon configuration of the project
    private final ActiconConfig mActiconConfig = new ActiconConfig();
    // The visicon configuration of the project
    private final VisiconConfig mVisiconConfig = new VisiconConfig();
    // The gesticon configuration of the project
    private final GesticonConfig mGesticonConfig = new GesticonConfig();

    // The plugins maintained within this project
    private final HashMap<String, Plugin> mPluginMap = new HashMap<>();
    // The players maintained within this project
    private final HashMap<String, Player> mPlayerMap = new HashMap<>();

    // TODO:  Refactor The Dialog Act Stuff
    // Maybe use a configuration file for that
    private final DialogActInterface mDialogueAct = new DummyDialogAct();

    // Construct an empty runtime project
    public RunTimeProject() {
        // Print some information
        mLogger.warning("Creating a new empty runtime project");
    }

    // Construct a project from a directory
    public RunTimeProject(final File file) {
        // Call the local parsing method
        parse(file);
    }

    // Get the name of the project's configuration
    public final String getProjectName() {
        return mProjectConfig.getProjectName();
    }

    // Set the name in the project's configuration
    public final void setProjectName(final String name) {
        mProjectConfig.setProjectName(name);
    }

    // Get a specific player from the map of players
    public final Player getPlayer(final String name) {
        return mPlayerMap.get(name);
    }

    // Get a specific plugin from the map of plugins
    public final Plugin getPlugin(final String name) {
        return mPluginMap.get(name);
    }

    // Get the sceneflow of the project
    public final SceneFlow getSceneFlow() {
        return mSceneFlow;
    }

    // Get the scenescript of the project
    public final SceneScript getSceneScript() {
        return mSceneScript;
    }

    // Get the acticon of the project
    public final ActiconConfig getActicon() {
        return mActiconConfig;
    }

    // Get the visicon of the project
    public final VisiconConfig getVisicon() {
        return mVisiconConfig;
    }

    // Get the gesticon of the project
    public final GesticonConfig getGesticon() {
        return mGesticonConfig;
    }

    // TODO: refactor this
    // Get the default dialog act taxonomy of the project
    public final DialogActInterface getDialogAct() {
        return mDialogueAct;
    }

    // Parse the project data from a directory
    public boolean parse(final File file) {
        // Check if the file is null
        if (file == null) {
            // Print an error message
            mLogger.failure("Error: Cannot parse runtime project from a bad file");
            // Return false at error
            return false;
        }
        // Get the absolute file for this directory
        final File base = file.getAbsoluteFile();
        // Check if the project directory does exist
        if (!base.exists()) {
            // Print an error message
            mLogger.failure("Error: Cannot find runtime project directory '" + base + "'");
            // Return false at error
            return false;
        }
        // Parse the project from the base directory
        return (parseProjectConfig(base)
                && parseSceneFlow(base)
                && parseSceneScript(base)
                && parseActiconConfig(base)
                && parseVisiconConfig(base)
                && parseGesticonConfig(base));
    }

    // Write the project data to a directory
    public boolean write(final File file) {
        // Check if the file is null
        if (file == null) {
            // Print an error message
            mLogger.failure("Error: Cannot write runtime project into a bad file");
            // Return false at error
            return false;
        }
        // Get the absolute file for the directory
        final File base = file.getAbsoluteFile();
        // Check if the project directory does exist
        if (!base.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating a new runtime project directory '" + base + "'");
            // Try to create a project base directory
            if (!base.mkdir()) {
                // Print an error message
                mLogger.failure("Failure: Cannot create a new runtime project directory '" + base + "'");
                // Return false at error
                return false;
            }
        }
        // Save the project to the base directory
        return (writeProjectConfig(base)
                && writeSceneFlow(base)
                && writeSceneScript(base)
                && writeActiconConfig(base)
                && writeVisiconConfig(base)
                && writeGesticonConfig(base));
    }

    // Load the runtime objects of the project
    public final boolean load() {
        return (loadPlayers() && loadPlugins());
    }

    // Launch the runtime objects of the project
    public final boolean launch() {
        return (launchPlayers() && launchPlugins());
    }

    // Unload the runtime objects of the project
    public final boolean unload() {
        return (unloadPlayers() && unloadPlugins());
    }

    private boolean parseProjectConfig(final File base) {
        // Create the project configuration file
        final File file = new File(base, "project.xml");
        // Check if the  configuration does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find project configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the project configuration file
        if (!XMLUtilities.parseFromXMLFile(mProjectConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse project configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Loaded project configuration file '" + file + "':\n" + mProjectConfig);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeProjectConfig(final File base) {
        // Create the project configuration file
        final File file = new File(base, "project.xml");
        // Check if the configuration does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new project configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a project configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new project configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the project configuration file
        if (!XMLUtilities.writeToXMLFile(mProjectConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write project configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved project configuration file '" + file + "':\n" + mProjectConfig);
        // Return success if the project was saved
        return true;
    }

    private boolean parseSceneFlow(final File base) {
        // Create the sceneflow configuration file
        final File file = new File(base, "sceneflow.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find sceneflow configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the sceneflow configuration file
        if (!XMLUtilities.parseFromXMLFile(mSceneFlow, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse sceneflow configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Perform all the postprocessing steps
        mSceneFlow.establishStartNodes();
        mSceneFlow.establishTargetNodes();
        mSceneFlow.establishAltStartNodes();
        // Print an information message in this case
        mLogger.message("Loaded sceneflow configuration file '" + file + "':\n" + mSceneFlow);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeSceneFlow(final File base) {
        // Create the sceneflow configuration file
        final File file = new File(base, "sceneflow.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new sceneflow configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a sceneflow configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new sceneflow configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the sceneflow configuration file
        if (!XMLUtilities.writeToXMLFile(mSceneFlow, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write sceneflow configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved sceneflow configuration file '" + file + "':\n" + mSceneFlow);
        // Return success if the project was saved
        return true;
    }

    private boolean parseSceneScript(final File base) {
        // Create the scenescript configuration file
        final File file = new File(base, "scenescript.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find scenescript configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the scenescript configuration file
        if (!XMLUtilities.parseFromXMLFile(mSceneScript, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse scenescript configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Loaded scenescript configuration file '" + file + "':\n" + mSceneScript);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeSceneScript(final File base) {
        // Create the scenescript configuration file
        final File file = new File(base, "scenescript.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new scenescript configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a scenescript configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new scenescript configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the scenescript configuration file
        if (!XMLUtilities.writeToXMLFile(mSceneScript, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write scenescript configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved scenescript configuration file '" + file + "':\n" + mSceneScript);
        // Return success if the project was saved
        return true;
    }

    private boolean parseActiconConfig(final File base) {
        // Create the acticon configuration file
        final File file = new File(base, "acticon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find acticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the acticon configuration file
        if (!XMLUtilities.parseFromXMLFile(mActiconConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse acticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Loaded acticon configuration file '" + file + "':\n" + mActiconConfig);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeActiconConfig(final File base) {
        // Create the acticon configuration file
        final File file = new File(base, "acticon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new acticon configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a acticon configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new acticon configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the acticon configuration file
        if (!XMLUtilities.writeToXMLFile(mActiconConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write acticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved acticon configuration file '" + file + "':\n" + mActiconConfig);
        // Return success if the project was saved
        return true;
    }

    private boolean parseGesticonConfig(final File base) {
        // Create the gesticon configuration file
        final File file = new File(base, "gesticon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find gesticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the gesticon configuration file
        if (!XMLUtilities.parseFromXMLFile(mGesticonConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse gesticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Loaded gesticon configuration file '" + file + "':\n" + mGesticonConfig);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeGesticonConfig(final File base) {
        // Create the gesticon configuration file
        final File file = new File(base, "gesticon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new gesticon configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a gesticon configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new gesticon configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the gesticon configuration file
        if (!XMLUtilities.writeToXMLFile(mGesticonConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write gesticon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved gesticon configuration file '" + file + "':\n" + mGesticonConfig);
        // Return success if the project was saved
        return true;
    }

    private boolean parseVisiconConfig(final File base) {
        // Create the visicon configuration file
        final File file = new File(base, "visicon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot find visicon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Parse the visicon configuration file
        if (!XMLUtilities.parseFromXMLFile(mVisiconConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot parse visicon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Loaded visicon configuration file '" + file + "':\n" + mVisiconConfig);
        // Return success if the project was loaded
        return true;
    }

    private boolean writeVisiconConfig(final File base) {
        // Create the visicon configuration file
        final File file = new File(base, "visicon.xml");
        // Check if the configuration file does exist
        if (!file.exists()) {
            // Print a warning message in this case
            mLogger.warning("Warning: Creating the new visicon configuration file '" + file + "'");
            // Create a new configuration file now
            try {
                // Try to create a new configuration file
                if (!file.createNewFile()) {
                    // Print an error message in this case
                    mLogger.warning("Warning: There already exists a visicon configuration file '" + file + "'");
                }
            } catch (final IOException exc) {
                // Print an error message in this case
                mLogger.failure("Failure: Cannot create the new visicon configuration file '" + file + "'");
                // Return failure if it does not exist
                return false;
            }
        }
        // Write the visicon configuration file
        if (!XMLUtilities.writeToXMLFile(mVisiconConfig, file)) {
            // Print an error message in this case
            mLogger.failure("Error: Cannot write visicon configuration file '" + file + "'");
            // Return failure if it does not exist
            return false;
        }
        // Print an information message in this case
        mLogger.message("Saved visicon configuration file '" + file + "':\n" + mVisiconConfig);
        // Return success if the project was saved
        return true;
    }

    // Load the plugins of the project
    private boolean loadPlugins() {
        for (final PluginConfig config : mProjectConfig.getPluginConfigList()) {
            // Get the class and plugin name
            final String className = config.getClassName();
            final String pluginName = config.getPluginName();
            try {
                // Find the plugin class by name
                final Class clazz = Class.forName(className);
                // Get the initialization method
                final Method method = clazz.getMethod("getInstance", RunTimeProject.class, PluginConfig.class);
                // Call the initialization method
                final Plugin plugin = (Plugin) method.invoke(null, this, config);
                // Check if plugin has been created
                if (plugin == null) {
                    // Print an error message 
                    mLogger.failure("Failure: Cannot load plugin of class '" + className + "'");
                    // Return false at error
                    return false;
                } else {
                    // Set the default scene player then
                    mPluginMap.put(pluginName, plugin);
                    // Print an information message here
                    mLogger.message("Loading plugin '" + plugin + "' with plugin config:\n" + config);
                }
            } catch (final Exception exc) {
                // Print an error message
                mLogger.failure("Failure: Cannot load plugin of class '" + className + "'");
                // Print an error message
                mLogger.failure(exc.toString());
            }
        }
        // Return true at success
        return true;
    }

    // Load the players of the project
    private boolean loadPlayers() {
        //
        for (final PlayerConfig config : mProjectConfig.getPlayerConfigList()) {
            // Get the class and plugin name
            final String className = config.getClassName();
            final String playerName = config.getPlayerName();
            try {
                // Find the plugin class by name
                final Class clazz = Class.forName(className);
                // Get the initialization method
                final Method method = clazz.getMethod("getInstance", RunTimeProject.class, PlayerConfig.class);
                // Call the initialization method
                final Player player = (Player) method.invoke(null, this, config);
                // Check if plugin has been created
                if (player == null) {
                    // Print an error message 
                    mLogger.failure("Failure: Cannot load player of class '" + className + "'");
                    // Return false at error
                    return false;
                } else {
                    // Set the default scene player then
                    mPlayerMap.put(playerName, player);
                    // Print an information message here
                    mLogger.message("Loading player '" + player + "' with plugin config:\n" + config);
                }
            } catch (final Exception exc) {
                // Print an error message
                mLogger.failure("Failure: Cannot load player of class '" + className + "'");
                // Print an error message
                mLogger.failure(exc.toString());
            }
        }
        // Return true at success
        return true;
    }

    // Launch the players of the project
    private boolean launchPlayers() {
        // Initialize the result flag
        boolean success = true;
        // Try launching all the players
        for (final Player player : mPlayerMap.values()) {
            if (!player.launch()) {
                success = false;
            }
        }
        return success;
    }

    // Launch the plugins of the project
    private boolean launchPlugins() {
        // Initialize the result flag
        boolean success = true;
        // Try launching all the plugins
        for (final Plugin plugin : mPluginMap.values()) {
            if (!plugin.launch()) {
                success = false;
            }
        }
        return success;
    }

    // Unload the players of the project
    private boolean unloadPlayers() {
        // Initialize the result flag
        boolean success = true;
        // Try unloading all the players
        for (final Player player : mPlayerMap.values()) {
            if (!player.unload()) {
                success = false;
            }
        }
        return success;
    }

    // Unload the plugins of the project
    private boolean unloadPlugins() {
        // Initialize the result flag
        boolean success = true;
        // Try unloading all the plugins
        for (final Plugin plugin : mPluginMap.values()) {
            if (!plugin.unload()) {
                success = false;
            }
        }
        return success;
    }

//        //%%
//        if (mDialogActClassName != null) {
//            try {
//                Class daClass = Class.forName(mDialogActClassName);
//
//                mDialogueAct = (DialogActInterface) daClass.getConstructor().newInstance();
//            } catch (Exception exc) {
//
//                // do nothing
//            }
//        } //else {
//
//        //}
    // Get the hash code of the project
    protected synchronized int getHashCode() {
        int hashCode = ((mSceneFlow == null)
                ? 0
                : mSceneFlow.getHashCode());
        // TODO: Why Is The Hash Computed
        // Only Based On The SceneFlow's 
        // Hash And Not Based Also On The 
        // Other Project Data Structures?
        return hashCode;
    }
}
