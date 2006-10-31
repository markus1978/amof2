package Pattern.Communication;

import SDL.SdlCompositeStateInstance;
import SDL.SdlStateStatus;

public class ContextCustom extends ContextDlg {
    @Override
    public void update() {
        if (self.getPendingEvents().size() > 0) {
            Event nextEvent = self.getPendingEvents().iterator().next();
            self.getPendingEvents().remove(nextEvent);
            boolean save = false;

            loop:
            for (Process process: self.getProcess()) {
                if (((SdlCompositeStateInstance)process).getStatus() == null) {
                    process.start();
                }
                if (((SdlCompositeStateInstance)process).getStatus() == SdlStateStatus.STARTED) {
                    save = true;
                } else {
                    for (Listener input: process.getInput()) {
                        if (input.listensTo(nextEvent)) {
                            if (process.consume(input, nextEvent)) {
                                nextEvent = null;
                                break loop;
                            } else {
                                save = true;
                            }
                        }
                    }
                }
            }
            if (save && nextEvent != null) {
                self.getPendingEvents().add(nextEvent);
                System.out.println("saved an event of type " + nextEvent.getMetaClassifierEventType());
            } else if (nextEvent != null) {
                System.out.println("droped event of type " + nextEvent.getMetaClassifierEventType());
                nextEvent.metaDelete();
            }
        }
    }
}
