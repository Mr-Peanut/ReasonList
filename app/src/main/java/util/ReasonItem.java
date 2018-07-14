package util;

public class ReasonItem {
    private String reasonCode;
    private String reasonContent;
    private Boolean originalChecked;
    private Boolean isChecked;

    public ReasonItem(String reasonCode, String reasonContent,Boolean originalChecked) {
        this.reasonCode = reasonCode;
        this.reasonContent = reasonContent;
        this.isChecked=false;
        this.originalChecked=originalChecked;
    }


    public Boolean getChecked() {
        return isChecked;
    }

    public Boolean getOriginalChecked() {
        return originalChecked;
    }

    public void setOriginalChecked(Boolean originalChecked) {
        this.originalChecked = originalChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonContent() {
        return reasonContent;
    }

    public void setReasonContent(String reasonContent) {
        this.reasonContent = reasonContent;
    }
}
