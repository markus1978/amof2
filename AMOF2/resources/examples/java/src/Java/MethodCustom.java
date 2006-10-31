package Java;

import cmof.exception.ModelException;
import hub.sam.mof.util.ListImpl;

public class MethodCustom extends MethodDlg {

    @Override
    public void run(Object thisPointer, CallFrame callingFrame) {
        System.out.println("Method " + self.getName() + " was called.");

        // setup call frame
        CallFrame callFrame = self.metaCreateCallFrame();
        callFrame.setCallingFrame(callFrame);
        callFrame.setThis(thisPointer);
        if (self.getScope() == Scope.MEMBER && callFrame.getThis() == null) {
            throw new ModelException("Member variable without this pointer.");
        }
        for (Variable localVar: self.getVariable()) {
            callFrame.getLocalVariable().add(localVar.metaCreateSlot());
        }

        // run body
        for (Statement statement: self.getBody()) {
            if (statement instanceof Assignment) {
                callFrame.slotForVariable(((Assignment)statement).getAssignTo()).setValue(
                        ((Assignment)statement).getAssignWith().evaluate(callFrame));
            } else if (statement instanceof MethodCall) {
                Method method = ((MethodCall)statement).getCalledMethod();
                if (method.getScope() == Scope.MEMBER) {
                    method.run(((MethodCall)statement).getTarget().evaluate(callFrame), callFrame);
                }
            }
        }

        // destroy call frame
        for (Slot slot: new ListImpl<Slot>((callFrame.getLocalVariable()))) {
            slot.metaDelete();
        }
        callFrame.metaDelete();
    }
}
