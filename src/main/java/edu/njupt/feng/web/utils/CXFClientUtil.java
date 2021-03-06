package edu.njupt.feng.web.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class CXFClientUtil {

    public static final int CXF_CLIENT_CONNECT_TIMEOUT = 60 * 1000;
    public static final int CXF_CLIENT_RECEIVE_TIMEOUT = 20 * 60 * 1000;

    public static void configTimeout(Object service) {
        Client proxy = ClientProxy.getClient(service);
        proxy.getEndpoint().put("org.apache.cxf.stax.maxChildElements", "50000000");
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(CXF_CLIENT_CONNECT_TIMEOUT);
        policy.setReceiveTimeout(CXF_CLIENT_RECEIVE_TIMEOUT);
        conduit.setClient(policy);
    }

}