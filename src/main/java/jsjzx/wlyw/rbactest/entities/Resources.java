package jsjzx.wlyw.rbactest.entities;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@TableName("t_resources")
public class Resources extends Model<Resources> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer pId;
    private String url;
    private String icon;

    //////////////////更改的内容///////////////////
//////////////////更改的内容///////////////////
//////////////////更改的内容///////////////////
//////////////////更改的内容///////////////////
    //此节点是否展开
    @TableField(exist = false)
    private boolean open = true;
    //节点的 checkBox / radio 的 勾选状态
    @TableField(exist = false)
    private boolean checked = false;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    /////////////////////////////////////
    /////////////////////////////////////
    /////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Resources{" +
                ", id=" + id +
                ", name=" + name +
                ", pId=" + pId +
                ", url=" + url +
                ", icon=" + icon +
                "}";
    }
}
