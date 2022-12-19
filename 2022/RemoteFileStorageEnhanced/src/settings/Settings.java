package settings;

public interface Settings {
    int SERVER_PORT = 8188;
    String SERVER_ADDRESS = "localhost";
    String REMOTE_STORAGE_PATH = "/home/kb/test/file-storage/remote";
    String LOCAL_STORAGE_PATH = "/home/kb/test/file-storage/local";

//    String CLIENT_TRANSPORT = "socket";
    String CLIENT_TRANSPORT = "socket_channel";

//        String MESSAGE_SERIALIZATION_METHOD = "serialized";
    String MESSAGE_SERIALIZATION_METHOD = "quick";

    int TRANSPORT_TIMEOUT_SECONDS = 5;

    String DEFAULT_USER = "user";
    String DEFAULT_PASSWORD = "pass";
    String DEFAULT_FILE_TO_DELETE = "movie.mkv";
    String DEFAULT_FILE_TO_UPLOAD = "/home/kb/test/file-storage/local/movie.mkv";
    String DEFAULT_FILE_TO_DOWNLOAD = "movie.mkv";
}
