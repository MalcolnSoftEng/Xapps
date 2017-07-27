package xapps.teste;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Malcoln on 26/07/2017.
 */

public class UserListAdapter extends SimpleAdapter {

    private Context mContext;
    public LayoutInflater inflater=null;
    public UserListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
        TextView textID = (TextView)vi.findViewById(R.id.id);
        String id = (String) data.get("id");
        textID.setText(id);
        TextView text = (TextView)vi.findViewById(R.id.first_name);
        String name = (String) data.get("first_name");
        name = name.substring(0,1).toUpperCase().concat(name.substring(1));
        text.setText(name);
        TextView textLast = (TextView)vi.findViewById(R.id.last_name);
        String last_name = (String) data.get("last_name");
        last_name = last_name.substring(0,1).toUpperCase().concat(last_name.substring(1));
        char letra;
        if (last_name.length()>1){
            letra = last_name.charAt(0);
            textLast.setText(letra+".");
        }

        ImageView image=(ImageView)vi.findViewById(R.id.avatar);
        String image_url = (String) data.get("avatar");
        Picasso.with(mContext).load(image_url).into(image);
        return vi;
    }
}