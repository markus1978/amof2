package qualifier;


public class ATargetDlg extends hub.sam.mof.reflection.ObjectDlg implements ATarget
{
    protected ATarget self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ATarget)self;
    }

    public qualifier.ASource getAsource() {
        return (qualifier.ASource)(java.lang.Object)self.getAsource();
    }

    public void setAsource(qualifier.ASource value) {
        self.setAsource(value);
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

