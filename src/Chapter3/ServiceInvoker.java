package Chapter3;

import com.sun.org.apache.xpath.internal.operations.Variable;
import org.omg.CORBA.Request;

import javax.xml.ws.Endpoint;

public class ServiceInvoker
{
    private static final ServiceInvoker INSTANCE = new ServiceInvoker();
    private volatile LoadBalnacer loadBalnacer;

    private ServiceInvoker(){}
    public static ServiceInvoker getInstance()
    {
        return INSTANCE;
    }

    public void dispatchRequest(Request request)
    {
        Endpoint endpoint = getLoadBalnacer().nextEndpoint();
        if (null == endpoint)
        {
            return;
        }
//        dispatchRequest();
    }


//    public void dispatchToDwonstream(Request request, EndPoint)
    public LoadBalnacer getLoadBalnacer()
    {
        return loadBalnacer;
    }
}
