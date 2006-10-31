package SDL;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.SetImpl;

import java.util.Collection;
import java.util.Vector;

public class SdlAgentInstanceCustom extends SdlAgentInstanceDlg {

    @Override
    public void initialize() {
        ((SdlInstance)getSuper(SdlInstance.class)).initialize();
        self.initializeBehavior();
    }

    @Override
    public void createSlots() {
        ((SdlInstance)getSuper(SdlInstance.class)).createSlots();
        for(SdlAgent agent : self.getMetaClassifierSdlAgentType().getAgent()) {
            self.setAgentInstanceSet(agent, (SdlAgentInstanceSet)agent.instanciate());
        }
    }

    @Override
    public void initializeBehavior() {
        SdlBehavior behavior = getMetaClassifierSdlAgentType().getBehavior();
        if (behavior == null) {
            return;
        }
        self.setBehavior(((SdlCompositeState)behavior).getType().metaCreateSdlCompositeStateInstance());
        self.getBehavior().initialize();
    }

    @Override
    public void run() {
        for(SdlAgent agent: self.getMetaClassifierSdlAgentType().getAgent()) {
            for (SdlAgentInstance agentInstance: self.getAgentInstanceSet(agent).getValue()) {
                agentInstance.run();
            }
        }
        SdlCompositeStateInstance behavior = self.getBehavior();
        if (behavior != null) {
            behavior.start();
            behavior.run();
        }
    }

    @Override
    public SdlInstance getContainingInstance() {
        return self.getOwningInstanceSet().getAgentInstance();
    }

    private static SdlAgentType getAgentTypeForPath(SdlChannelPath path, SdlAgentInstance instance) {
        SdlAgentType agent = path.getTarget().iterator().next().getAgent().getType();
        if (agent == null) {
            agent = instance.getOwningInstanceSet().getAgentInstance().getMetaClassifierSdlAgentType();
        }
        return agent;
    }

    /**
     * Queries a set of SdlChannelPaths for a path. The result contains all ChannelPaths that source
     * in the paths target end point.
     *
     * @param path A SdlChannelPath
     * @return A set of SdlChannelPaths
     */
    @Override
    public ReflectiveCollection<? extends SdlChannelPath> continuesWith(SdlChannelPath path) {
        ReflectiveCollection<? extends SdlChannelPath> result = new SetImpl<SdlChannelPath>();
        SdlAgentType context = getAgentTypeForPath(path, self);

        SdlGate gate = path.getTarget().iterator().next().getGate();
        if (gate == null) {
            return result;
        }

        // context.channel->collect(c|gate = c.source->first().gate)
        // SdlChannelValue n = new SdlChannelValue(null);
        // SdlChannelPathValue m = new SdlChannelPathValue(null);
        // new SdlAgentTypeValue(context).getChannel().collect(n,
        //        n.getPath().collect(m,
        //                new SdlGateValue(gate).oclEquals(m.getSource().asSequence().first().getGate())));                
        for (SdlChannel channel: context.getChannel()) {
            for (SdlChannelPath continuingPath: channel.getPath()) {
                SdlGate continueingGate = continuingPath.getSource().iterator().next().getGate();
                if (gate.equals(continueingGate)) {
                    result.add(continuingPath);
                }
            }
        }
        return result;
    }

    @Override
    public void dispatchSignal(SdlSignalInstance s, SdlGate via) {
        SdlAgentInstanceSet dispatchedTo = null;
        SdlSignal signal = s.getMetaClassifierSdlSignal();
        ReflectiveCollection<? extends SdlAgentInstanceSet> possibleReceiver = collectAgentInstanceSets(s, via, self);
        loop:
        for (SdlAgentInstanceSet receiver: possibleReceiver) {
            if (s.getReceiver() != null) {
                if (receiver.getValue().contains(s.getReceiver())) {
                    dispatchedTo = receiver;
                    receiver.getInputQueue().add(s);
                    receiver.update();
                    break loop;
                }
            } else {
                dispatchedTo = receiver;
                receiver.getInputQueue().add(s);
                receiver.update();
                break loop;
            }
        }
        // TODO dispatch to environment
        if (dispatchedTo == null) {
            System.out.println("signal dropped");
        } else {
            System.out.println("signal of type " + signal + " dipatched to instance of agent " +
                    dispatchedTo.getMetaClassifierSdlAgent());
        }
    }

    private static ReflectiveCollection<? extends SdlAgentInstanceSet> collectAgentInstanceSets(
            SdlSignalInstance s, SdlGate via, SdlAgentInstance self) {
        return collectAgentInstanceSets(s.getMetaClassifierSdlSignal(), via, self,
                self.getOwningInstanceSet().getAgentInstance(), false);
    }

    /**
     * Collect all AgentInstanceSets that the signal can be send to using a gate. The send is supposed to be
     * performed from within self.
     * @param s The signal type.
     * @param gate The gate to use.
     * @param from  The instance that wants to dispatch the signal.
     * @param to The instance that is supposed to receive the signal.
     * @return The possible receveing sets.
     */
    private static ReflectiveCollection<? extends SdlAgentInstanceSet> collectAgentInstanceSets(
            SdlSignal s, SdlGate gate, SdlAgentInstance from, SdlAgentInstance to, boolean in) {
        ReflectiveCollection<? extends SdlAgentInstanceSet> result = new SetImpl<SdlAgentInstanceSet>();
        // select all paths that originate in gate and are not owned by this instance
        for(SdlChannelPath path: selectPathsFromChannels(from.getOwningInstanceSet().getMetaClassifierSdlAgent(),
                gate, to.getMetaClassifierSdlAgentType().getChannel(), in, s)) {
            // find the target instanceset
            SdlAgent targetAgent = path.getTarget().iterator().next().getAgent();
            if (targetAgent == null) {
                result.addAll(collectAgentInstanceSets(s, path.getTarget().iterator().next().getGate(), to,
                        to.getOwningInstanceSet().getAgentInstance(), false));
            } else {
                SdlAgentInstanceSet targetSet = to.getAgentInstanceSet(targetAgent);
                if (targetAgent.getType().getKind() == SdlAgentKind.PROCESS) {
                    result.add(targetSet);
                } else {
                    for (SdlAgentInstance targetInstance: targetSet.getValue()) {
                        result.addAll(collectAgentInstanceSets(s, path.getTarget().iterator().next().getGate(),
                                to, targetInstance, true));
                    }
                }
            }
        }
        return result;
    }

    private static Iterable<SdlChannelPath> selectPathsFromChannels(SdlAgent originatingAgent, SdlGate originatingGate,
                                                                    Iterable<? extends SdlChannel> channels,
                                                                    boolean in, SdlSignal signal) {
        Collection<SdlChannelPath> result = new Vector<SdlChannelPath>();
        for(SdlChannel channel: channels) {
            innerLoop:
            for(SdlChannelPath path: channel.getPath()) {
                if (!path.getSignal().contains(signal)) {
                    continue innerLoop;
                }
                for(SdlChannelEnd end: path.getSource()) {
                    SdlAgent endAgent = end.getAgent();
                    if (endAgent == null) {
                        // a THIS end
                        if (!in) {
                             continue innerLoop;
                        }
                    } else  if (!endAgent.equals(originatingAgent)) {
                        // it is not the originatingAgent
                        continue innerLoop;
                    }
                    if (originatingGate != null && !originatingGate.equals(end.getGate())) {
                        // if there is a gate, it should be used
                        continue innerLoop;
                    }
                    result.add(path);
                }
            }
        }
        return result;
    }
}
