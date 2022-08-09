package technologies.akkas.ageguess.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInPOJO {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private Integer data;
@SerializedName("msg")
@Expose
private String msg;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Integer getData() {
return data;
}

public void setData(Integer data) {
this.data = data;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

}