package com.jci.vsd.view.widget.treelist;



import com.jci.vsd.R;
import com.jci.vsd.bean.SingleVideoBean;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.treelist.bean.Node;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeCombatequipmentname;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeEquipID;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeID;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeLat;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeLng;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeName;
import com.jci.vsd.view.widget.treelist.bean.TreeNodePID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lq
 * @创建时间：2018/2/15 11:13
 * @项目名称：firecommand_git
 * @所在包：com.kesen.fire.utils
 * @类说明：树形导航菜单工具类
 * @since JDK 1.8.0_112
 */
public class TreeMenuUtils {

    /**
     * 过滤出所有可见的Node
     *
     * @param allNodes 所有节点集合
     * @return 可见节点集合
     */
    public static List<Node> getVisibleNodesByAll (List<Node> allNodes) {
        List<Node> visibleNodes = new ArrayList<>();
        for (Node node : allNodes) {
            // 如果为跟节点，或者上层目录为展开状态 当前节点可见
            if (node.isRootNode () || node.isParentExpand ()) {
                visibleNodes.add (node);
                setNodeIcon (node);
            }
        }
        return visibleNodes;
    }

    /**
     * 根据传入的类<T>转化为排序后的Node
     *
     * @param data         数据集合
     * @param defaultLevel 默认的展开级别{@link Node#isExpand}
     * @return
     */
    public static <T> List<Node> getSortNode (List<T> data, int defaultLevel) throws IllegalAccessException {
        List<Node> sortedNodes = new ArrayList<>();
        List<Node> data2Node;
        //判断下data的类型，再用不同的解析方式。
        T classType =data.get(0);
        if(classType instanceof SingleVideoBean){//如果以后有其他类型，也添加在这里判断
            data2Node = convertDataToNodeSingelVideo (data);
        }else {
            data2Node = convertData2Node (data);
        }
        //List<Node> data2Node = convertDataToNodeWithGps (data);
        List<Node> rootNodes = getRootNodes (data2Node);
        for (Node node : rootNodes) {
            addNode (sortedNodes, node, defaultLevel, 1);
        }
        return sortedNodes;
    }

    private static void addNode (List<Node> sortedNodes, Node node, int defaultLevel, int currentLevel) {
        sortedNodes.add (node);
       // 设置列表进入的时候全部关闭或者打开
        if (defaultLevel >= currentLevel) {
            node.setExpand (true);
        }
        if (node.isLeafNode ())
            return;
        int size = node.getChildrenNodes ().size ();
        for (int i = 0; i < size; i++) {
            addNode (sortedNodes, node.getChildrenNodes ().get (i), defaultLevel, currentLevel + 1);
        }

    }

    private static List<Node> getRootNodes (List<Node> data2Node) {
        List<Node> rootNode = new ArrayList<>();
        for (Node node : data2Node) {
            if (node.isRootNode ()) {
                rootNode.add (node);
            }
        }
        return rootNode;
    }

    /**
     * 将数据转化为树的节点
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> List<Node> convertData2Node (List<T> data) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node;
        for (T t : data) {
            String id = null;
            String pid =null;
            String name = null;
            Class<? extends Object> clazz = t.getClass ();
            Field[] declaredFields = clazz.getDeclaredFields ();
            for (Field f : declaredFields) {
                if (f.getAnnotation (TreeNodeID.class) != null) {
                    f.setAccessible (true);
                    id = (String) f.get (t);
                }
                if (f.getAnnotation (TreeNodePID.class) != null) {
                    f.setAccessible (true);
                    pid = (String) f.get (t);
                }
                if (f.getAnnotation (TreeNodeName.class) != null) {
                    f.setAccessible (true);
                    name = (String) f.get (t);
                }
                if (id!= null && pid != null && name != null) {
                    break;
                }
            }
            node = new Node (id, pid, name);
            nodes.add (node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        int size = nodes.size ();
        for (int i = 0; i < size; i++) {
            Node n = nodes.get (i);
            for (int j = i + 1; j < size; j++) {
                Node m = nodes.get (j);
                if (n.getNodeID () .equals (m.getParentID ()) ) {
                    n.getChildrenNodes ().add (m);
                    m.setParentNode (n);
                } else if (n.getParentID ().equals (m.getNodeID ()) ) {
                    n.setParentNode (m);
                    m.getChildrenNodes ().add (n);
                }
            }
        }
        for (Node n : nodes) {
            if(n.isRootNode ()){
                n.setTextSize (15);
            }else if(n.getChildrenNodes ().size ()>0){
                n.setTextSize (14);
            }else {
                n.setTextSize (13);
            }
            setNodeIcon (n);
        }
        return nodes;
    }

    /**
     * 将数据转化为树的节点有经纬度，单兵图像模块
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> List<Node> convertDataToNodeSingelVideo (List<T> data) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node;
        for (T t : data) {
            String id = null;
            String pid =null;
            String name = null;
            String combatequipmentname =null;//
            String equipmentid=null;
            String lat = null;//
            String lng = null;//
            Class<? extends Object> clazz = t.getClass ();
            Field[] declaredFields = clazz.getDeclaredFields ();
            for (Field f : declaredFields) {
                if (f.getAnnotation (TreeNodeID.class) != null) {
                    f.setAccessible (true);
                    id = (String) f.get (t);
                }
                if (f.getAnnotation (TreeNodePID.class) != null) {
                    f.setAccessible (true);
                    pid = (String) f.get (t);
                }
                if (f.getAnnotation (TreeNodeName.class) != null) {
                    f.setAccessible (true);
                    name = (String) f.get (t);
                }
                if (f.getAnnotation(TreeNodeCombatequipmentname.class)!=null){
                    f.setAccessible(true);
                    combatequipmentname = (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodeEquipID.class)!=null){
                    f.setAccessible(true);
                    equipmentid = (String) f.get(t);
                }

                if(f.getAnnotation(TreeNodeLat.class)!=null){
                    f.setAccessible(true);
                    lat = (String) f.get(t);
                }
                if(f.getAnnotation(TreeNodeLng.class)!=null){
                    f.setAccessible(true);
                    lng = (String) f.get(t);
                }

                if (id!= null && pid != null && name != null) {
                    break;
                }
            }
            node = new Node(id,pid,name,combatequipmentname,equipmentid,lat,lng);
            nodes.add (node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        int size = nodes.size ();
        for (int i = 0; i < size; i++) {
            Node n = nodes.get (i);
            for (int j = i + 1; j < size; j++) {
                Node m = nodes.get (j);
                if (n.getNodeID () .equals (m.getParentID ()) ) {
                    n.getChildrenNodes ().add (m);
                    m.setParentNode (n);
                } else if (n.getParentID ().equals (m.getNodeID ()) ) {
                    n.setParentNode (m);
                    m.getChildrenNodes ().add (n);
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon (n);
        }
        return nodes;
    }


    /**
     * 设置节点的图标
     *
     * @param node 当前的节点
     */
    public static void setNodeIcon (Node node) {
//        Loger.e("--child"+node.getChildrenNodes().size());
//        Loger.e("--isExpand"+node.isExpand());

        if (node.getChildrenNodes ().size () > 0 && node.isExpand ()) {


            node.setIcon (R.drawable.es_listview_expand);
        } else if (node.getChildrenNodes ().size () > 0 && !node.isExpand ()) {
            node.setIcon (R.drawable.es_listview_coll);
        } else {
           // Loger.e("--node.setIcon (-1)");
            node.setIcon (-1);
        }
    }

}
