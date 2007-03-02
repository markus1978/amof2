package StateAutomaton.Semantics;


/**
 * <b>SingleAutomatonInstance</b>
 */
public interface SingleAutomatonInstance 
{

    /**
     * <b>instanceSet</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Semantics.AutomatonInstance getInstanceSet();

    public void setInstanceSet(StateAutomaton.Semantics.AutomatonInstance value);

    /**
     * <b>senderId</b>, multiplicity=(1,1)
     */
    public int getSenderId();

    public void setSenderId(int value);

    /**
     * <b>offspringId</b>, multiplicity=(1,1)
     */
    public int getOffspringId();

    public void setOffspringId(int value);

    /**
     * <b>processId</b>, multiplicity=(1,1)
     */
    public int getProcessId();

    public void setProcessId(int value);

    /**
     * <b>actualState</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.State getActualState();

    public void setActualState(StateAutomaton.Syntax.State value);

    /**
     * <b>trigger</b>, multiplicity=(0,*), isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Transition> getTrigger();

    /**
     * <b>consume</b>, multiplicity=(1,1)
     */
    public int consume(StateAutomaton.Semantics.SignalInstance signal) ;

}

