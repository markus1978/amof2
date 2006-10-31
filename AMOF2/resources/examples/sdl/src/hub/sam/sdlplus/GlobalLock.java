package hub.sam.sdlplus;

public class GlobalLock {

    private static final GlobalLock singelton = new GlobalLock();

    public static GlobalLock getGlobalLock() {
        return singelton;
    }

    private Object identity = null;

    private boolean getLock(Object identity) {
        //if (this.identity == null) {
        //    this.identity = identity;
        //    return true;
        //} else {
        //    return this.identity == identity;
        //}
        return true;
    }

    public void releaseLock(Object identity) {
        //if (this.identity != null && this.identity == identity) {
        //    this.identity = null;
        //    identity.notifyAll();
        //}
    }

    public synchronized void waitForLock(Object identity) {
        //while(!getLock(identity)) {
        //    try {
        //        identity.wait(1);
        //        this.wait(1);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
    }
}
