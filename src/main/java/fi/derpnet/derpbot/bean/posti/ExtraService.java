
package fi.derpnet.derpbot.bean.posti;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraService {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("additionalInfo")
    @Expose
    private Object additionalInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Object additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
