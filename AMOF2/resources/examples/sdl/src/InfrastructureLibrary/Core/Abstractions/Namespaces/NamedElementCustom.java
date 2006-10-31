package InfrastructureLibrary.Core.Abstractions.Namespaces;

public class NamedElementCustom extends NamedElementDlg {
    @Override
    public String getQualifiedName() {
        String result = getName();
        Namespace ns = self.getNamespace();
        while (ns != null) {
            result = ns.getName() + "." + result;
            ns = ns.getNamespace();
        }
        return result;
    }
}
