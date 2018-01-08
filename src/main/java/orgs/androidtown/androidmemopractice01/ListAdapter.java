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
    // 1. 저장소
    public static ArrayList<Memo> data;
    //2. 생성자 정의
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
        if (view == null) { // 뷰가 최초 생성될 경우
            // 1. 뷰 생성
            view = LayoutInflater.from(context).inflate(R.layout.itemlist, null); //LayoutInflater를 통해 itemlist를 사용할 수 있다
            // 2. 홀더 생성
            holder = new Holder(view);
            // 3. 뷰에 홀더를 붙인다.
            view.setTag(holder);
        } else {// 뷰가 두번 째 호출되면(즉, 한화면이 넘어가면)
            // 1. 뷰에 붙인 홀더를 꺼낸다.
            holder = (Holder) view.getTag();
        }
        // 값을 세팅
        Memo memo = data.get(i); // 1. 컬렉션 구종의 저장소로부터 객체단위로 꺼내두는게 사용하기 편함.
                                //2. 홀더의 위젯에 데이터를 입력한다.
        holder.setTextNo(memo.getNo()); //메모에서 받아온 메서드들을 holder에서 set해준다, 그러면 홀더에서 지정해놓은 item_list 변수들에 이 값들이 들어간다?
        holder.setTextTitle(memo.getTitle());
        holder.setTextDate(memo.getDatetime());
        holder.setPosition(i);
        holder.setAuthor(memo.getAuthor());
        holder.setContent(memo.getContent());
        return view;
    }
}

class Holder {// 리스트뷰라는 뷰 그룹안에 있는 뷰(아이템)을 관리하는 클래스

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
                Intent intent = new Intent(view.getContext(), DetailActivity.class); //Detail activity로 인텐트 데이터를 보낸다.
                intent.putExtra("position", position);
//                intent.putExtra("title", textTitle.getText());
//                intent.putExtra("author", author);
//                intent.putExtra("content", content);
//                intent.putExtra("datetime", textDate.getText());
                view.getContext().startActivity(intent); //view에서 컨텍스트를 받았음.

            }
        });
    }

    public void setPosition(int position) {
        this.position = position;
    } //set의 의미??

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTextNo(int no) {// 인자값이 변한이유?
        textNo.setText(no + "");
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
