package message;

public class FileDownloadRequest extends TokenizedMessage {
    private final String filename;

    public FileDownloadRequest(String token, String filename) {
        super(token);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
