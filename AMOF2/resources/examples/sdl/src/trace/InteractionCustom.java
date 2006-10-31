package trace;

public class InteractionCustom extends InteractionDlg {
    @Override
    public String getLabel() {
        StringBuffer result = new StringBuffer();
        result.append(self.getInteractionTypeName()).append("(");
        boolean first = true;
        for (String arg: self.getArguments()) {
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(arg);
        }
        result.append(")");
        return result.toString();
    }
}
