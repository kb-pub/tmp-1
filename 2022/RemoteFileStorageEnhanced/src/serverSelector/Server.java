package serverSelector;

import serverSelector.protocol.Protocol;
import serverSelector.service.FileService;
import serverSelector.service.UserService;
import serverSelector.transport.Transport;
import serverSelector.transport.TransportFactory;
import settings.Settings;
import util.Util;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Server {
    ServerSocketChannel serverChannel;
    Selector selector;
    UserService userService = new UserService();
    FileService fileService = new FileService();

    private Set<SelectionKey> currentSelectedKeysForInformer = Set.of();

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        startInformer();

        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open()) {
            this.serverChannel = serverChannel;
            this.selector = selector;
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress("localhost", Settings.SERVER_PORT));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                currentSelectedKeysForInformer = new HashSet<>(selector.selectedKeys());
                var selectedKeysIterator = selector.selectedKeys().iterator();
                while (selectedKeysIterator.hasNext()) {
                    try {
                        var key = selectedKeysIterator.next();
                        handle(key);
                    } finally {
                        selectedKeysIterator.remove();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void handle(SelectionKey key) {
        if (key.isValid() && key.isAcceptable()) {
            accept();
        }

        if (key.isValid() && key.isReadable()) {
            ((Transport) key.attachment()).inbound();
        }

        if (key.isValid() && key.isWritable()) {
            ((Transport) key.attachment()).outbound();
        }
    }

    private void accept() {
        Util.rethrow(() -> {
            var clientChannel = serverChannel.accept();
            if (clientChannel != null) {
                clientChannel.configureBlocking(false);
                var clientKey = clientChannel.register(selector, 0);
                var transport = TransportFactory.get(clientKey);
                clientKey.attach(transport);
                var protocol = new Protocol(transport, userService, fileService);
                transport.receive(protocol::handle);
            }
        });
    }

    private void startInformer() {
        var informer = new Thread(() -> {
            while (true) {
                Util.rethrow(() -> Thread.sleep(1000));
                if (selector != null) {
                    if (!selector.isOpen()) {
                        System.out.println("selector closed");
                    }
                    else {
                        System.out.println("===== " + LocalDateTime.now());
                        for (var key : currentSelectedKeysForInformer) {
                            System.out.println(key);
                        }
                    }
                }
            }
        });
        informer.setDaemon(true);
        informer.start();
    }
}
