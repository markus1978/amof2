package core.abstractions.elements;


public class ElementDlg extends hub.sam.mof.reflection.ObjectDlg implements Element
{
    protected Element self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Element)self;
    }

}

