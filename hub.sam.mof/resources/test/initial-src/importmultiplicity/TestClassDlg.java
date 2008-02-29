package importmultiplicity;


public class TestClassDlg extends hub.sam.mof.reflection.ObjectDlg implements TestClass
{
    protected TestClass self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (TestClass)self;
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getTestProperty() {
        return self.getTestProperty();
    }

}

