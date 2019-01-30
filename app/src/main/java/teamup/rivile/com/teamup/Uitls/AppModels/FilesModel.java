package teamup.rivile.com.teamup.Uitls.AppModels;

import android.net.Uri;

public class FilesModel {
    static int id;
    Uri fileUri;
    String fileName;

    public FilesModel(Uri fileUri) {
        this.id ++;
        this.fileUri = fileUri;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public static int getId() {
        return id;
    }

    public Uri getFileUri() {
        return fileUri;
    }
}
