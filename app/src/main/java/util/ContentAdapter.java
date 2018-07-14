package util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.guans.reasonlist.R;
import java.util.List;

class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ItemHolder> {
    private Context mContext;
    private List<ReasonItem> list;
    private final  static String REASON_TAG="REASON_TAG";
    public void setList(List<ReasonItem> list) {
        this.list = list;
    }
    public ContentAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override

    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_view_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        CheckBox itemCheckBox=holder.checkBox;
        itemCheckBox.setChecked(list.get(position).getOriginalChecked());
        LinearLayout itemRoot=holder.itemRoot;
        EditText editText = itemRoot.findViewWithTag(REASON_TAG);
        if(position!=list.size()-1){
            if(editText!=null){
                itemRoot.removeView(editText);
            }
            itemCheckBox.setText(list.get(position).getReasonContent());
        }else {
            itemCheckBox.setText("其他，请填写");
            if(itemCheckBox.isChecked()){
               initEditView(holder,position);
            }
        }
        list.get(position).setChecked(list.get(position).getOriginalChecked());
        itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) {
                   list.get(position).setChecked(true);
                   if(position==list.size()-1){
                      initEditView(holder,position).setVisibility(View.VISIBLE);
                   }
               }
               else{
                   list.get(position).setChecked(false);
                   if(position==list.size()-1){
                       initEditView(holder,position).setVisibility(View.GONE);
                   }
               }
            }
        });
    }
    private EditText initEditView(@NonNull ItemHolder holder, final int position){
        LinearLayout itemRoot=holder.itemRoot;
        EditText editText = itemRoot.findViewWithTag(REASON_TAG);
        if(editText==null){
            editText = new EditText(mContext);
            ViewGroup.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80);
            editText.setTag(REASON_TAG);
            editText.setText(list.get(position).getReasonContent());
            itemRoot.addView(editText,layoutParams);
        }
        String reason=list.get(position).getReasonContent();
        if(reason.equals("其他，请填写")){
            editText.setHint("请填写其他原因");
        }else {
            editText.setText(reason);
        }
        return editText;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        LinearLayout itemRoot;
        ItemHolder(View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.item);
            itemRoot=itemView.findViewById(R.id.itemRoot);
        }
    }
}

