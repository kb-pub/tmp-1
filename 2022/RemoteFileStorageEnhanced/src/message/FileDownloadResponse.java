package message;

public class FileDownloadResponse extends Message {
    private final long filesize;

    public FileDownloadResponse(long filesize) {
        this.filesize = filesize;
    }

    public long getFilesize() {
        return filesize;
    }
}
