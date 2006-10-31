package trace;

public class ProcessCustom extends ProcessDlg {
    @Override
    public String getLabel() {
        StringBuffer result = new StringBuffer();
        result.append(self.getProcessTypeName()).append(":").append(self.getProcessId());
        return result.toString();        
    }
}
