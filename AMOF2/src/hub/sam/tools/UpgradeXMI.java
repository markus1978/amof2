package hub.sam.tools;

import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.xmi.Xmi1Reader;

public class UpgradeXMI {

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            exit(args);
        }

        String fromTypeStr = args[0];
        String toTypeStr = args[2];
        Xmi1Reader.XmiKind fromKind = null;
        Xmi1Reader.XmiKind toKind = null;
        for(Xmi1Reader.XmiKind kind: Xmi1Reader.XmiKind.class.getEnumConstants()) {
            if (fromTypeStr.equals(kind.toString())) {
                fromKind = kind;
            }
            if (toTypeStr.equals(kind.toString())) {
                toKind = kind;
            }
        }
        if ((fromKind == null) || (toKind == null)) {
            exit(args);
        }

        Repository repo = Repository.getLocalRepository();
        Extent m3 = repo.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmof = (Package)m3.query("Package:cmof");

        Extent m2 = repo.createExtent("m2");
        repo.loadXmiIntoExtent(m2, cmof, args[1], fromKind);
        Tools.setOppositeValues(m2);
        Tools.changeFromPackagedElement(m2);
        repo.writeExtentToXmi(args[3], cmof, m2, toKind);
    }

    private static void exit(String[] args) {
        System.err.println("Wrong args.");
        System.err.println(args);
        System.exit(1);
    }

}
