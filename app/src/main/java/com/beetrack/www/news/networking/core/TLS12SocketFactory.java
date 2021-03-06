package com.beetrack.www.news.networking.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

@SuppressWarnings("WeakerAccess")
public class TLS12SocketFactory extends SSLSocketFactory {

    private static final String[] TLS_V12_ONLY = {"TLSv1.2"};

    final SSLSocketFactory delegate;

    public TLS12SocketFactory(SSLSocketFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public String[] getDefaultCipherSuites() {
//        return new String[0];
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
//        return new String[0];
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
//        return null;
        return patch(delegate.createSocket(s,host,port,autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
//        return null;
        return patch(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
//        return null;
        return patch(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
//        return null;
        return patch(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
//        return null;
        return patch(delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket patch(Socket socket) {
        if(socket instanceof SSLSocket){
            ((SSLSocket) socket).setEnabledProtocols(TLS_V12_ONLY);
        }

        return socket;
    }
}
