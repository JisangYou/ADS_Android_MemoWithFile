package orgs.androidtown.androidmemopractice01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import orgs.androidtown.androidmemopractice01.domain.Memo;

/**
 * Created by Jisang on 2017-09-24.
 */

public class ListAdapter extends BaseAdapter {

    Context context;
   public static ArrayList<Memo> data;

    public ListAdapter(Context context, ArrayList<Memo> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.itemlist, null);
            holder = new Holder(view);
            view.setTag(holder);
        }else{
            holder=(Holder)view.getTag();
        }
        Memo memo = data.get(i); // 컬렉션 구종의 저장소로부터 객체단위로 꺼내두는게 사용하기 편함.
        holder.setTextNo(memo.getNo());
        holder.setTextTitle(memo.getTitle());
        holder.setTextDate(memo.getDatetime());
        holder.setPosition(i);
        holder.setAuthor(memo.getAuthor());
        holder.setContent(memo.getContent());
        return view;
    }
}

class Holder {
    private int position;
    private String author;
    private String content;
    private TextView textNo;
    private TextView textTitle;
    private TextView textDate;

    public Holder(View view) {
        textNo = view.findViewById(R.id.textNo);
        textTitle = view.findViewById(R.id.textTitle);
        textDate = view.findViewById(R.id.textDate);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("position", position);
//                intent.putExtra("title", textTitle.getText());
//                intent.putExtra("author", author);
//                intent.putExtra("content", content);
//                intent.putExtra("datetime", textDate.getText());
                view.getContext().startActivity(intent);

            }
        });
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTextNo(int no) {// 인자값이 변한이유?
       textNo.setText(no+"");
    }

    public void setTextTitle(String title) { //여기도  TextView에서 변함.
        textTitle.setText(title);
    }

    public void setTextDate(Long datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String result = sdf.format(datetime);
        textDate.setText(result);
    }
}
