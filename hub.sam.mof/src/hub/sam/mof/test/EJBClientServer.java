package hub.sam.mof.test;

import hub.sam.mof.Repository;
import hub.sam.mof.reflection.client.ClientRepository;

public class EJBClientServer extends ClientServer {
	@Override
	protected ClientRepository getRepository() {
		try {
			return Repository.connectToRemoteRepository("jnp://localhost:1099");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
