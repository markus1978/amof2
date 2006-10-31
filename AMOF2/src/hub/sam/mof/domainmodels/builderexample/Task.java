package hub.sam.mof.domainmodels.builderexample;

public abstract class Task {

    /**
     * Uses reflection to resolve the Task with the given name and instantiate and return it.
     */
    public static Task resolveTask(String name) {
        return null;
    }

    /**
     * Uses reflection to set the task parameter with the given name by calling the setter with the
     * given name.
     */
    public void setParameter(String name, String value) {

    }

    /**
     * Callback method to execute the task.
     * @return Sucess
     */
    public abstract boolean execute();
}
