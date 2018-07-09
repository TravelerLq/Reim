package com.jci.vsd.view.widget.treelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.jci.vsd.view.widget.treelist.bean.Node;

import java.util.List;

/**
 * @author lq
 * @创建时间：2017/2/15 14:06
 * @项目名称：firecommand_git
 * @所在包：com.kesen.fire.views.widget.treelist
 * @类说明：
 * @since JDK 1.8.0_112
 */
public abstract class TreeMenuBaseAdapter<T> extends BaseAdapter {
    public Context mContext;

    /**
     * 存储所有可见的节点
     */
    private List<Node> mVisibleNodes;

    /**
     * 存储所有的节点
     */
    private List<Node> mAllNodes;

    protected LayoutInflater mLayoutInflater;

    public void setTreeNodeClickListener(OnTreeNodeClickListener treeNodeClickListener) {
        mTreeNodeClickListener = treeNodeClickListener;
    }
    public void setTreeNodeLongClickListener(OnTreeNodeLongClickListener treeNodeLongClickListener) {
        mTreeNodeLongClickListener = treeNodeLongClickListener;
    }

    /**
     * 节点点击时的回调
     */
    public OnTreeNodeClickListener mTreeNodeClickListener;

    public OnTreeNodeLongClickListener mTreeNodeLongClickListener;

    /**
     * 设置树形菜单是否可点击展开
     * true可以点击展开，否则为false
     */
    public boolean isListExpand = true;

    public interface OnTreeNodeClickListener {
        void onNodeClick(Node node, int position);
    }
    public interface OnTreeNodeLongClickListener {
        void onNodeLongClick(Node node, int position);
    }

    public TreeMenuBaseAdapter(ListView listView, Context context, List<T> data, int defaultLevel) throws IllegalAccessException {
        mContext = context;
        mAllNodes = TreeMenuUtils.getSortNode(data, defaultLevel);

        mVisibleNodes = TreeMenuUtils.getVisibleNodesByAll(mAllNodes);
        mLayoutInflater = LayoutInflater.from(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Log.d("tag","collapse");
                expandOrCollapse(position);//展开或关闭
                if (mTreeNodeClickListener != null) {
                    mTreeNodeClickListener.onNodeClick(mVisibleNodes.get(position), position);
                }
            }
        });
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

    }

    public void setListExpand(boolean isListExpand) {
        this.isListExpand = isListExpand;
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    private void expandOrCollapse(int position) {
        if (isListExpand) {
            Node node = mVisibleNodes.get(position);
            if (node != null) {
                if (!node.isLeafNode()) {
                    node.setExpand(!node.isExpand());
                  //  TreeMenuUtils.setNodeIcon(node);
                    mVisibleNodes = TreeMenuUtils.getVisibleNodesByAll(mAllNodes);
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public int getCount() {
        if (mVisibleNodes != null && mVisibleNodes.size() > 0)
            return mVisibleNodes.size();
        return 0;
    }

    @Override
    public Node getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mVisibleNodes.get(position);
        TreeMenuUtils.setNodeIcon(node);
        convertView = getConvertView(node, position, convertView, parent);

        //根据层级设置内边距
        // convertView.setPadding (node.getNodeLevel () * 50, 3, 3, 3);//
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);
}
