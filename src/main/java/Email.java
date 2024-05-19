public class Email {
    private String content;
    private boolean isSpam;

    public Email(String content, boolean isSpam) {
        this.content = content;
        this.isSpam = isSpam;
    }

    public String getContent() {
        return content;
    }

    public boolean isSpam() {
        return isSpam;
    }

}
