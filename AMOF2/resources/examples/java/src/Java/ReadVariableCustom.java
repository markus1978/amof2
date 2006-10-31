package Java;

public class ReadVariableCustom extends ReadVariableDlg {
    
    @Override
    public Object evaluate(CallFrame context) {
        return context.slotForVariable(self.getVariable()).getValue();
    }
}
