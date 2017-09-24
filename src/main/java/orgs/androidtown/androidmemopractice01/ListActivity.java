package orgs.androidtown.androidmemopractice01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import orgs.androidtown.androidmemopractice01.domain.Memo;
import orgs.androidtown.androidmemopractice01.util.FileUtil;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
        initListener();
        init();


    }

    private void init() { //onCreate시 기본 세팅
        ArrayList<Memo> list = loadData(); // 데이터 세팅(loadData 메서드에 있는 데이터를 토대로!)
        ListAdapter adapter = new ListAdapter(this, list); //리스트뷰에 데이터를 넣어서 객체 생성
        listView.setAdapter(adapter);// 리스트뷰에 어댑터를 달아서 세팅해준다!

    }

    private ArrayList<Memo> loadData() {// 데이터 메서드
        ArrayList<Memo> result = new ArrayList<>();
        for (File item : getFilesDir().listFiles()) {
            try {
                String text = FileUtil.read(this, item.getName());
                Memo memo = new Memo(text);
                result.add(memo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static final int WRITE_ACTIVITY = 12345;

    private void initListener() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivityForResult(intent, WRITE_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WRITE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    init();
                }
                break;
        }
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        btnPost = (Button) findViewById(R.id.btnPost);
    }
}
