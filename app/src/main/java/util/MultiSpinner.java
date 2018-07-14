package util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.guans.reasonlist.R;

import java.util.List;

public class MultiSpinner {
    private Context context;
    private View parentView;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private View contentView;
    private ContentAdapter mAdapter;
    private List<ReasonItem> reasonsList;
    private OnCancelButtonClickedListener cancelButtonClickedListener;
    private OnOkButtonClickedListener okButtonClickedListener;

    public void setCancelButtonClickedListener(OnCancelButtonClickedListener cancelButtonClickedListener) {
        this.cancelButtonClickedListener = cancelButtonClickedListener;
    }

    public void setOkButtonClickedListener(OnOkButtonClickedListener okButtonClickedListener) {
        this.okButtonClickedListener = okButtonClickedListener;
    }

    public List<ReasonItem> getReasonsList() {
        return reasonsList;
    }

    private void setReasonsList(List<ReasonItem> reasonsList) {
        this.reasonsList = reasonsList;
    }

    public MultiSpinner(final View parentView, List<ReasonItem> list) {
        this.parentView = parentView;
        setReasonsList(list);
        context = parentView.getContext();
        layoutInflater = LayoutInflater.from(context);
        initPopupWindow();
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
                popupWindow.showAsDropDown(parentView, 0, 0, Gravity.BOTTOM);
            }
        });
    }

    private void initPopupWindow() {
        if (contentView == null)
            contentView = layoutInflater.inflate(R.layout.multi_spinner_layout, null, false);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (mAdapter == null)
            mAdapter = new ContentAdapter(context);
        mAdapter.setList(reasonsList);
        RecyclerView recyclerView = contentView.findViewById(R.id.content_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        Button cancel = contentView.findViewById(R.id.cancel);
        final Button okButton = contentView.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ReasonItem item : reasonsList) {
                    item.setOriginalChecked(item.getChecked());
                    popupWindow.dismiss();
                }
                okButtonClickedListener.onOkClicked(reasonsList, parentView);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ReasonItem item : reasonsList) {
                    item.setChecked(false);
                    popupWindow.dismiss();
                }
                cancelButtonClickedListener.onCancelClicked(reasonsList, parentView);
            }
        });
    }

    public boolean isPopuWindowOnShow() {
        return popupWindow != null && popupWindow.isShowing();
    }

    public void cancelPopWindow() {
        if (isPopuWindowOnShow()) {
            for (ReasonItem item : reasonsList) {
                item.setChecked(false);
                popupWindow.dismiss();
            }
        }
    }

    public interface OnOkButtonClickedListener {
        void onOkClicked(List<ReasonItem> list, View view);
    }

    public interface OnCancelButtonClickedListener {
        void onCancelClicked(List<ReasonItem> list, View view);
    }
}
