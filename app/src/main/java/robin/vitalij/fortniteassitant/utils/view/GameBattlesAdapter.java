package robin.vitalij.fortniteassitant.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import robin.vitalij.fortniteassitant.R;
import robin.vitalij.fortniteassitant.model.DetailStatisticsModel;

public class GameBattlesAdapter extends BaseAdapter {
    public List<DetailStatisticsModel> detailStatisticsModels;
    private LayoutInflater inflate;
    private Context context;

    public GameBattlesAdapter(Context applicationContext, List<DetailStatisticsModel> fruit) {
        this.detailStatisticsModels = fruit;
        this.context = applicationContext;
        inflate = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return detailStatisticsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflate.inflate(R.layout.spinner_server_layout, null);
        ImageView icon = view.findViewById(R.id.imageView);
        icon.setImageResource(detailStatisticsModels.get(i).getGameType().getImageRes());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.spinner_server_dropdown_layout,
                    null);
        }

        ImageView icon = convertView.findViewById(R.id.imageView);
        icon.setImageResource(detailStatisticsModels.get(position).getGameType().getImageRes());
        return convertView;
    }
}
