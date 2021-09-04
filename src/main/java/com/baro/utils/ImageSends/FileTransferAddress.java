package com.baro.utils.ImageSends;

import java.net.InetSocketAddress;
public class FileTransferAddress extends InetSocketAddress {
    private static final long serialVersionUID = 1L;
    public FileTransferAddress(String hostname, int port) {
        super(hostname, port);
    }
    public int getPortnumber(){
        return super.getPort();
    }
}
