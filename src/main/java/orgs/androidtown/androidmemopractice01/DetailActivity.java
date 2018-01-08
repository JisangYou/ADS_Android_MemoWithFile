package orgs.androidtown.androidmemopractice01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import orgs.androidtown.androidmemopractice01.domain.Memo;

public class DetailActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textDate;
    private TextView textAuthor;
    private TextView textContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        init();
    }

    private void initView() {
        textTitle = (TextView) findViewById(R.id.textTitle);
        textDate = (TextView) findViewById(R.id.textDate);
        textAuthor = (TextView) findViewById(R.id.textAuthor);
        textContent = (TextView) findViewById(R.id.textContent);
    }

    public void init() {
        Intent intent = getIntent(); // 리스트 어댑터에서 보낸 인텐트들을 받는다.
        int position = intent.getIntExtra("position", -1);
//
//        String title = intent.getStringExtra("title");
//        String author = intent.getStringExtra("author");
//        String content = intent.getStringExtra("content");
//        String datetime = intent.getStringExtra("datetime"); // 인텐트로 하나씩 다 보내도 된다. 그러나 이미 Memo라는 객체에 저장이 되어 있기 때문에 Memo에서 빼서 써도 된다.

        Memo memo = ListAdapter.data.get(position);// 인텐트로 받아온 position값으로 아이템을 구분할 수 있다. 즉, 특정 아이템의 메모!

        textTitle.setText(memo.getTitle());
        textDate.setText(memo.getDatetime()+"");
        textAuthor.setText(memo.getAuthor());
        textContent.setText(memo.getContent());
        // 세팅하는 작업업
    }
}