package hub.sam.mof.mopatree;

import cmof.reflection.ObjectEventHandler;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Mof2TreeNode implements TreeNode {

    private static final Map<cmof.reflection.Object, TreeNode> cache = new HashMap<cmof.reflection.Object, TreeNode>();
    private ObjectEventHandler handler = new ObjectEventHandler() {
        public void handleDelete(cmof.reflection.Object object) {
            for(TreeNode child: new Vector<TreeNode>(children)) {
                if (child.getElement().equals(object)) {
                    children.remove(child);
                }
            }
        }
    };

    public static TreeNode createNode(cmof.reflection.Object forObject) {
        TreeNode result = cache.get(forObject);
        if (result == null) {
            result = new Mof2TreeNode(forObject);
            cache.put(forObject, result);
        }
        return result;
    }

    public static Collection<TreeNode> createNodes(Iterable<? extends cmof.reflection.Object> objects) {
        Collection<TreeNode> result = new Vector<TreeNode>();
        for (cmof.reflection.Object obj: objects) {
            result.add(createNode(obj));
        }
        return result;
    }

    private final cmof.reflection.Object element;
    private final Collection<TreeNode> children;

    private Mof2TreeNode(cmof.reflection.Object obj) {
        super();
        this.element = obj;
        this.children = createNodes(obj.getComponents());
        for(TreeNode child: children) {
            ((cmof.reflection.Object)child.getElement()).addObjectEventHandler(handler);
        }
    }

    public TreeNode getParent() {
        return createNode(element.container());
    }

    public Iterable<TreeNode> getChildren() {
        return children;
    }

    @SuppressWarnings("unchecked")
    public Iterable<TreeNode> getReferences(String arg0) {
        java.lang.Object getResult = element.get(arg0);
        Collection<cmof.reflection.Object> results = new Vector<cmof.reflection.Object>();
        if (getResult instanceof cmof.common.ReflectiveCollection) {
            for (java.lang.Object element: (cmof.common.ReflectiveCollection)getResult) {
                if (element instanceof cmof.reflection.Object) {
                    results.add((cmof.reflection.Object)element);
                } else {
                    return Collections.EMPTY_LIST;
                }
            }
        } else {
            if (getResult instanceof cmof.reflection.Object) {
                results.add((cmof.reflection.Object)getResult);
            }
        }
        return createNodes(results);
    }

    public Object getElement() {
        return element;
    }

    public boolean match(String arg) {
        return false;
    }
}
