package speaktechnical.com.githubtask;

/**
 * Created by avi on 03/05/2017.
 */

public class GitHubFeed {

    String author_name = "";
    String image_url = "";
    String commit_message = "";
    String date = "";
    String sha_code = "";

    public GitHubFeed(String author_name, String image_url, String commit_message, String sha_code, String date) {
        this.author_name = author_name;
        this.image_url = image_url;
        this.commit_message = commit_message;
        this.sha_code = sha_code;
        this.date = date;
    }

    public String getSha_code() {
        return sha_code;
    }

    public void setSha_code(String sha_code) {
        this.sha_code = sha_code;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCommit_message() {
        return commit_message;
    }

    public void setCommit_message(String commit_message) {
        this.commit_message = commit_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
