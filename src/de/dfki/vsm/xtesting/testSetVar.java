package de.dfki.vsm.xtesting;

import de.dfki.vsm.editor.EditorInstance;
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.editor.project.ProjectEditor;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Int;
import de.dfki.vsm.model.sceneflow.definition.VarDef;
import de.dfki.vsm.runtime.interpreter.Environment;
import de.dfki.vsm.runtime.interpreter.Evaluator;
import de.dfki.vsm.runtime.interpreter.Interpreter;
import de.dfki.vsm.runtime.interpreter.error.InterpreterError;
import de.dfki.vsm.runtime.project.RunTimeProject;

import java.io.File;

/**
 * Created by alvaro on 5/19/16.
 */
public class testSetVar {

    public static void main(String[] args) {
        final EditorInstance instance = EditorInstance.getInstance();
        instance.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (Exception exc) {
            // Do something here
        }
        //instance.hideWelcomeStickman();
        final EditorProject project = new EditorProject();
        project.parse(new File("/home/alvaro/Documents/WorkHiwi/VSM_stable/VisualSceneMaker/res/tutorials/4-TestStickman").getPath());


        final ProjectEditor editor = instance.showProject(project);
        try {
            Thread.sleep(5000);
        } catch (Exception exc) {
            // Do something here
        }
        VarDef v = new VarDef("testing", "String", new Int(13));
        RunTimeProject rt = project;
        rt.getSceneFlow().addVarDef(v);

        //rt.
        Interpreter interpreter = new Interpreter(rt);
        Environment env = new Environment();
        env.push();

        Evaluator evaluator = interpreter.getEvaluator();


            //evaluator.declare(v, env);




        project.launch();
        project.start();
        boolean a  = rt.setVariable("test2", "Hello World");

        //boolean a  = project.setVariable("testing", "Hello");


        System.err.println("Tes");

        //instance.hideProject(editor);
    }
}
