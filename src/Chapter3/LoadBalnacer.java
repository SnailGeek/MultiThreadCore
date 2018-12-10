package Chapter3;

import javax.xml.ws.Endpoint;

public interface LoadBalnacer {
    void updateCandidate(final Candidate candidate);
    Endpoint nextEndpoint();
}
