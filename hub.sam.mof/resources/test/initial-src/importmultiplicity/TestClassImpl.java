package importmultiplicity;


public class TestClassImpl extends hub.sam.mof.reflection.ObjectImpl implements TestClass
{
    public TestClassImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public TestClassImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getTestProperty() {
        java.lang.Object value = get("testProperty");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "testProperty");
        }
    }

}

