/***********************************************************************
 * State Automaton Language
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.stateautomaton;

import hub.sam.stateautomaton.model.Automaton;
import hub.sam.stateautomaton.model.AutomatonRuntime;
import hub.sam.stateautomaton.model.FinalState;
import hub.sam.stateautomaton.model.InitialState;
import hub.sam.stateautomaton.model.State;
import hub.sam.stateautomaton.model.Transition;
import hub.sam.stateautomaton.model.modelFactory;
import hub.sam.mas.execution.MasExecutionHelper;
import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.MasXmiFiles;
import hub.sam.mas.management.SimpleMasXmiFiles;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.MofModelManager;
import hub.sam.mof.ocl.OclObjectEnvironment;

public class StateAutomaton {

    public void execute() throws Exception {
        Repository repository = Repository.getLocalRepository();
        Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
        Repository.getConfiguration().setGenerousXMI(true);
        
        // load xmi files for syntax and semantic from a mas context file
        MasXmiFiles xmiFiles = new SimpleMasXmiFiles("resources/", "StateAutomaton.masctx");

        // create a new mas model container
        MasModelContainer masModelContainer = new MasModelContainer(repository);
        
        // load mas model (semantic)
        masModelContainer.loadMasModel(xmiFiles.getMasFile());
        
        // load state automaton meta-model (syntax)
        masModelContainer.loadSyntaxModelForExecution(xmiFiles.getSyntaxFile(), "Package:model");
        masModelContainer.getSyntaxModel().addJavaPackagePrefix("hub.sam.stateautomaton");
        
        // now we can create a mas context
        MasContext masContext = MasRepository.getInstance().createMasContext(masModelContainer);
        
        // create a test model:        
        // here the mof model manager concept can be used to create a test model
        // as instance of the syntax model.
        MofModelManager testManager = new MofModelManager(repository);
        testManager.setM2Model(masModelContainer.getSyntaxModel());
        MofModel testModel = testManager.createM1Model("test");
        modelFactory testFactory = (modelFactory) testModel.getFactory();
        
        MasExecutionHelper.prepareRun(repository, masContext, testModel);
        
        //Automaton automaton = createSimpleTestModel(testFactory);
        //automaton.instantiate().run("baba");
        
        Automaton automaton = createLargeTestModel(testFactory);
        AutomatonRuntime automatonRuntime = automaton.instantiate();
        automatonRuntime.run("dbdecacf");
        automatonRuntime.remove();
    }
    
    public static boolean evaluateInvariant(String invariant, cmof.reflection.Object forObject) {
        Object result = forObject.getAdaptor(OclObjectEnvironment.class).execute(invariant);
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        System.out.println("invariant \"" + invariant + "\" evaluated to FALSE");
        return false;
    }
    
    public static void main(String[] args) throws Exception {
        new StateAutomaton().execute();
    }
    
    private Automaton createLargeTestModel(modelFactory factory) {
        
        // main automaton X
        
        Automaton automatonX = factory.createAutomaton();
        automatonX.setName("X");
        
        InitialState startX = factory.createInitialState();
        startX.setName("startX");
        State stateA = factory.createState();
        stateA.setName("A");
        State stateB = factory.createState();
        stateB.setName("B");
        FinalState stopX = factory.createFinalState();
        stopX.setName("stopX");
        
        automatonX.setInitialState(startX);
        automatonX.getState().add(stateA);
        automatonX.getState().add(stateB);
        automatonX.getFinalState().add(stopX);
        
        Transition tstartX = factory.createTransition();
        Transition ta = factory.createTransition();
        ta.setInput("a");
        Transition tb = factory.createTransition();
        tb.setInput("b");
        Transition tf = factory.createTransition();
        tf.setInput("f");
        
        automatonX.getTransition().add(tstartX);
        automatonX.getTransition().add(ta);
        automatonX.getTransition().add(tb);
        automatonX.getTransition().add(tf);

        tstartX.setSource(startX);
        tstartX.setTarget(stateA);
        tb.setSource(stateA);
        tb.setTarget(stateB);
        ta.setSource(stateB);
        ta.setTarget(stateA);
        tf.setSource(stateA);
        tf.setTarget(stopX);
        
        // inner automaton Y
        
        Automaton automatonY = factory.createAutomaton();
        automatonY.setName("Y");
        
        stateA.setSubAutomaton(automatonY);
        stateB.setSubAutomaton(automatonY);
        
        InitialState startY = factory.createInitialState();
        startY.setName("startY");
        
        State stateC = factory.createState();
        stateC.setName("C");
        
        State stateD = factory.createState();
        stateD.setName("D");
        
        FinalState stopY = factory.createFinalState();
        stopY.setName("stopY");
        
        automatonY.setInitialState(startY);
        automatonY.getState().add(stateC);
        automatonY.getState().add(stateD);
        automatonY.getFinalState().add(stopY);
        
        Transition tstartY = factory.createTransition();
        Transition td = factory.createTransition();
        td.setInput("d");
        Transition tc = factory.createTransition();
        tc.setInput("c");
        Transition te = factory.createTransition();
        te.setInput("e");
        
        automatonY.getTransition().add(tstartY);
        automatonY.getTransition().add(td);
        automatonY.getTransition().add(tc);
        automatonY.getTransition().add(te);
        
        tstartY.setSource(startY);
        tstartY.setTarget(stateC);
        td.setSource(stateC);
        td.setTarget(stateD);
        tc.setSource(stateD);
        tc.setTarget(stateC);
        te.setSource(stateD);
        te.setTarget(stopY);
        
        return automatonX;
    }
    
    private Automaton createSimpleTestModel(modelFactory factory) {
        // main automaton X
        Automaton automaton = factory.createAutomaton();
        automaton.setName("X");
        
        // states
        InitialState start = factory.createInitialState();
        start.setName("start");
        
        State stateA = factory.createState();
        stateA.setName("A");

        FinalState stop = factory.createFinalState();
        stop.setName("stop");
        
        // transitions
        Transition tstart = factory.createTransition();

        Transition ta = factory.createTransition();
        ta.setInput("a");

        // add states and transitions to automatons
        automaton.setInitialState(start);
        automaton.getState().add(stateA);
        automaton.getFinalState().add(stop);
        automaton.getTransition().add(tstart);
        automaton.getTransition().add(ta);
        
        // connect states and transitions
        tstart.setSource(start);
        tstart.setTarget(stateA);
        ta.setSource(stateA);
        ta.setTarget(stop);
        
        // inner automaton Y
        Automaton automatonY = factory.createAutomaton();
        automatonY.setName("Y");
        stateA.setSubAutomaton(automatonY);
        
        // states
        InitialState startY = factory.createInitialState();
        startY.setName("startY");
        
        State stateB = factory.createState();
        stateB.setName("B");

        FinalState stopY = factory.createFinalState();
        stopY.setName("stopY");
        
        // transitions
        Transition tstartY = factory.createTransition();

        Transition tb = factory.createTransition();
        tb.setInput("b");
        
        // add states and transitions to automatons
        automatonY.setInitialState(startY);
        automatonY.getState().add(stateB);
        automatonY.getFinalState().add(stopY);
        automatonY.getTransition().add(tstartY);
        automatonY.getTransition().add(tb);
        
        // connect states and transitions
        tstartY.setSource(startY);
        tstartY.setTarget(stateB);
        tb.setSource(stateB);
        tb.setTarget(stopY);
        
        return automaton;
    }
    
    private Automaton createVerySimpleTestModel(modelFactory factory) {
        Automaton automaton = factory.createAutomaton();
        
        // states
        
        InitialState start = factory.createInitialState();
        start.setName("start");
        
        State stateA = factory.createState();
        stateA.setName("A");

        FinalState stop = factory.createFinalState();
        stop.setName("stop");
        
        // transitions
        
        Transition tstart = factory.createTransition();

        Transition ta = factory.createTransition();
        ta.setInput("a");

        // add states and transitions to automatons

        automaton.setInitialState(start);
        automaton.getState().add(stateA);
        automaton.getFinalState().add(stop);
        automaton.getTransition().add(tstart);
        automaton.getTransition().add(ta);
        
        // connect states and transitions
        
        tstart.setSource(start);
        tstart.setTarget(stateA);
        ta.setSource(stateA);
        ta.setTarget(stop);
        
        return automaton;
    }    

}
