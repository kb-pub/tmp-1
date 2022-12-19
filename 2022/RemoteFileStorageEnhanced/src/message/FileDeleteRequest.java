package message;

public class FileDeleteRequest extends TokenizedMessage {
    private final String filename;

    public FileDeleteRequest(String token, String filename) {
        super(token);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
