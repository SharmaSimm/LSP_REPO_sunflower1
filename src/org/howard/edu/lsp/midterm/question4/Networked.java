package org.howard.edu.lsp.midterm.question4;

/**
 * Network capability for devices that can connect/disconnect.
 */
public interface Networked {
    void connect();
    void disconnect();
    boolean isConnected();
}
