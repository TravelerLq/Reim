package com.jci.vsd.view.widget.treelist.bean;



import java.util.ArrayList;
import java.util.List;

/**
 * @author lq
 * @创建时间：2017/2/15 11:15
 * @项目名称：firecommand_git
 * @所在包：com.kesen.fire.bean
 * @类说明：树形导航菜单节点实体类
 * @since JDK 1.8.0_112
 */
public class Node {
    public int getTextSize () {
        return mTextSize;
    }

    public void setTextSize (int textSize) {
        mTextSize = textSize;
    }

    /**
     * 设置节点字体大小
     */
    private int mTextSize;
    /**
     * 当前节点的ID
     */
    private String mNodeID;

    /**
     * 当前节点的父节点ID
     */
    private String mParentID;

    /**
     * 当前节点的名字
     */
    private String mNodeName;

    /**
     * 节点的级别
     */
    private int mNodeLevel;

    /**
     * 是否默认展开
     * <p>
     * false为默认关闭，否则为true
     * <p>
     * 在 {@link TreeMenuUtils#addNode(List, Node, int, int)}
     * 方法中 默认设置当前节点展开的级别为5，如果要设置当前树形列表为默认全部展开，那么
     * 传入的 defaultExpandLevel大于5即可，如果要默认打开时默认关闭状态，那么传入
     * 的defaultExpandLevel小于5即可
     */
    private boolean isExpand = false;

    /**
     * 当前节点所含的子节点
     */
    private List<Node> mChildrenNodes = new ArrayList<>();

    /**
     * 当前节点的父节点
     */
    private Node mParentNode;

    /**
     * 当前节点的图标
     */
    private int mIcon;

    /**
     * 当前节点的坐标

     */
    private String lat;
    private String lng;
    private String combatequipmentname;//单兵名字
    private String equipmentid;//视频设备编号


    public Node (String id, String pid, String name) {
        mNodeID = id;
        mParentID = pid;
        mNodeName = name;
    }
    //实例化node带gps位置
    public Node (String id, String pid, String name, String lat, String lng) {
        mNodeID = id;
        mParentID = pid;
        mNodeName = name;
        this.lat = lat;
        this.lng = lng;
    }
    //实例化node带gps位置
    public Node (String id, String pid, String name, String combatequipmentname, String lat, String lng) {
        mNodeID = id;
        mParentID = pid;
        mNodeName = name;
        this.combatequipmentname = combatequipmentname;
        this.lat = lat;
        this.lng = lng;
    }
    //实例化node带gps位置
    public Node (String id, String pid, String name, String combatequipmentname, String equipmentid, String lat, String lng) {
        mNodeID = id;
        mParentID = pid;
        mNodeName = name;
        this.combatequipmentname = combatequipmentname;
        this.equipmentid = equipmentid;
        this.lat = lat;
        this.lng = lng;
    }
    public boolean isExpand () {
        return isExpand;
    }
    /**
     * @param isExpand 当前节点是否关闭，如果关闭，那么其子节点也要关闭,false关闭
     */
    public void setExpand (boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : mChildrenNodes) {
                node.setExpand (isExpand);
            }
        }
    }

    /**
     * 判断当前节点是否是根节点
     *
     * @return 如果返回true则表示是根节点，否则返回false
     */
    public boolean isRootNode () {
        return mParentNode == null;
    }


    /**
     * 判断当前节点是否是叶子节点
     *
     * @return 如果返回true则表示是叶子节点，否则返回false
     */
    public boolean isLeafNode () {
        return mChildrenNodes.size () == 0;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand () {
        if (mParentNode == null) {
            return false;
        }
        return mParentNode.isExpand ();
    }

    public String getNodeID () {
        return mNodeID;
    }

    public void setNodeID (String nodeID) {
        mNodeID = nodeID;
    }

    public String getParentID () {
        return mParentID;
    }

    public void setParentID (String parentID) {
        mParentID = parentID;
    }

    public String getNodeName () {
        return mNodeName;
    }

    public void setNodeName (String nodeName) {
        mNodeName = nodeName;
    }

    public int getNodeLevel () {
        return mParentNode == null ? 0 : mParentNode.getNodeLevel () + 1;
    }

    public void setNodeLevel (int nodeLevel) {
        mNodeLevel = nodeLevel;
    }



    public List<Node> getChildrenNodes () {
        return mChildrenNodes;
    }

    public void setChildrenNodes (List<Node> childrenNodes) {
        mChildrenNodes = childrenNodes;
    }

    public Node getParentNode () {
        return mParentNode;
    }

    public void setParentNode (Node parentNode) {
        mParentNode = parentNode;
    }

    public int getIcon () {
        return mIcon;
    }

    public void setIcon (int icon) {
        mIcon = icon;
    }

    //
    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {

        return lat;
    }

    public String getCombatequipmentname() {
        return combatequipmentname;
    }

    public void setCombatequipmentname(String combatequipmentname) {
        this.combatequipmentname = combatequipmentname;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getLng() {
        return lng;
    }
}
