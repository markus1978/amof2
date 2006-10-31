package qualifier;


public class TargetDlg extends hub.sam.mof.reflection.ObjectDlg implements Target
{
    protected Target self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Target)self;
    }

    public qualifier.Source getSource() {
        return (qualifier.Source)(java.lang.Object)self.getSource();
    }

    public void setSource(qualifier.Source value) {
        self.setSource(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

