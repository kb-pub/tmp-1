package settings;

public interface Settings {
    int SERVER_PORT = 8188;
    String SERVER_ADDRESS = "localhost";
    String REMOTE_STORAGE_PATH = "/home/kb/test/file-storage/remote";

//    String CLIENT_TRANSPORT = "socket";
    String CLIENT_TRANSPORT = "socket_channel";

//        String MESSAGE_SERIALIZATION_METHOD = "serialized";
    String MESSAGE_SERIALIZATION_METHOD = "quick";
}
