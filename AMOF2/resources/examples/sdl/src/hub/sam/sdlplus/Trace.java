package hub.sam.sdlplus;

import cmof.reflection.Extent;
import cmof.common.ReflectiveSequence;
import trace.TraceModel;
import trace.traceFactory;
import trace.ProcessNode;
import trace.Process;
import trace.Interaction;
import trace.Behavior;
import trace.LifeLineSequment;
import trace.Graph;
import trace.Edge;
import hub.sam.mof.Repository;
import SDL.SdlCompositeStateInstance;
import SDL.SdlSignalInstance;
import SDL.SdlDataValue;
import SDL.SdlGeneralValue;
import SDL.PidValue;
import SDL.SdlParameter;

import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;


public class Trace {

    private static final Extent traceModel = TraceModel.createModel();
    private final traceFactory theTraceFactory;
    private final Behavior behavior;

    private final Map<SdlCompositeStateInstance, Process> processes = new HashMap<SdlCompositeStateInstance, Process>();

    public Trace() {
        super();
        Extent traceExtent = Repository.getLocalRepository().createExtent("trace");
        theTraceFactory = (traceFactory)Repository.getLocalRepository().createFactory(
                traceExtent,
                (cmof.Package)traceModel.query("Package:trace"));
        behavior = theTraceFactory.createBehavior();
    }

    private Process getProcessForProcess(SdlCompositeStateInstance process) {
        Process result = processes.get(process);
        if (result == null) {
            result = theTraceFactory.createProcess();
            result.setProcessTypeName(process.getOwningInstance().getMetaClassifierSdlAgentType().getName());
            result.setProcessId(Integer.toString(process.hashCode()));
            behavior.getSubgraph().add(result);
            processes.put(process, result);
        }
        return result;
    }

    private void calculatePredecessor(Process process) {
        ReflectiveSequence<? extends ProcessNode> nodes = process.getProcessNode();
        if (nodes.size() >= 2) {
            LifeLineSequment lifeLine = theTraceFactory.createLifeLineSequment();
            lifeLine.setSource(nodes.get(nodes.size() - 2));
            lifeLine.setTarget(nodes.get(nodes.size() - 1));
            process.getLifeLines().add(lifeLine);
        }
    }

    public synchronized void addCommunication(SdlCompositeStateInstance source, SdlCompositeStateInstance target,
                                 SdlSignalInstance signal) {
    	/*
        ProcessNode sourceNode = addProcessNode(source);
        ProcessNode targetNode = addProcessNode(target);

        Interaction interaction = theTraceFactory.createInteraction();
        interaction.setSource(sourceNode);
        interaction.setTarget(targetNode);
        interaction.setInteractionTypeName(signal.getMetaClassifierSdlSignal().getName());
        for(SdlParameter parameter: signal.getMetaClassifierSdlSignal().getParameter()) {
            SdlDataValue argument = signal.getVariable(parameter).getValue().iterator().next();
            if (argument instanceof SdlGeneralValue) {
                interaction.getArguments().add(((SdlGeneralValue)argument).getValue().toString());
            } else if (argument instanceof PidValue) {
                interaction.getArguments().add(((PidValue)argument).getValue().getMetaClassifierSdlAgentType() +
                    "[instance]");
            }
        }
        behavior.getCommunication().add(interaction);
        */
    }

    public Graph getGraph() {
        return behavior;
    }

    private ProcessNode addProcessNode(SdlCompositeStateInstance process) {
        ProcessNode node = theTraceFactory.createProcessNode();
        getProcessForProcess(process).getProcessNode().add(node);
        calculatePredecessor(getProcessForProcess(process));
        return node;
    }

    private int unique = 0;
    private final Map<Object,String> ids = new HashMap<Object,String>();

    private String getId(Object identity) {
        String result = ids.get(identity);
        if (result == null) {
            result = Integer.toString(unique++);
            ids.put(identity, result);
        }
        return result;
    }

    public synchronized void generateDot(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(new File(fileName)));
        generateDorForGraph(behavior, false, out);
        out.close();
    }

    private void generateDorForGraph(Graph graph, boolean sub, PrintWriter out) throws IOException {
        if (sub) {
            out.println("subgraph cluster_" + getId(graph) + " {");
        } else {
            out.println("digraph " + getId(graph) + " {");
        }
        if (sub) {
            out.println("node [style=filled];");
        }
        out.println("    label = \"" + graph.getLabel() + "\";");
        for(Graph subgraph: graph.getSubgraph()) {
            generateDorForGraph(subgraph, true, out);
        }
        boolean first = true;
        for(Edge edge: graph.getEdge()) {
            if (sub) {
                if (first) {
                    first = false;
                    out.print(getId(edge.getSource()));
                }
                out.print(" -> ");
                out.print(getId(edge.getTarget()));
            } else {
                out.println("subgraph " + getId(edge) + " {");
                out.println("    rank = same;");
                out.println("    " + getId(edge.getSource()) + ";");
                out.println("    " + getId(edge.getTarget()) + ";");
                out.println("}");
                out.println("    " + getId(edge.getSource()) + " -> " + getId(edge.getTarget()) + ";");
            }
        }
        if (sub) {
            if (!first) {
                out.println(";");
            }
            out.println("color=blue;");
        }
        out.println("}");
    }
}
