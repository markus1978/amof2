package Java;

public class UmlClassCustom extends UmlClassDlg {

    @Override
    public void runMain() {
        for (Method method: getMethod()) {
            if ("main".equals(method.getName())) {
                method.run(null, null);
                return;
            }
        }
        System.err.println("No main method found in Class " + self.getName());
    }
}
