package Java;

import cmof.exception.ModelException;

public class CallFrameCustom extends CallFrameDlg {

    @Override
    public Slot slotForVariable(Variable var) {
        for (Slot local: self.getLocalVariable()) {
            if (local.getMetaClassifierVariable().equals(var)) {
                return local;
            }
        }

        for (Slot member: self.getThis().getMemberVariable()) {
            if (member.getMetaClassifierVariable().equals(var)) {
                return member;
            }
        }

        for (Slot classVar: self.getThis().getMetaClassifierClass().getClassVariable()) {
            if (classVar.getMetaClassifierVariable().equals(var)) {
                return classVar;
            }
        }

        throw new ModelException("Cannot resolve variable.");
    }
}
