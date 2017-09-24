package orgs.androidtown.androidmemopractice01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import orgs.androidtown.androidmemopractice01.domain.Memo;
import orgs.androidtown.androidmemopractice01.util.FileUtil;

public class WriteActivity extends AppCompatActivity {

    private Button btnPost;
    private EditText editTitle;
    private EditText editAuthor;
    private EditText editContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
        initListener();
    }

    private Memo getMemoFromScreen(){
        Memo memo = new Memo();
        memo.setNo(1);
        memo.setTitle(editTitle.getText().toString());
        memo.setAuthor(editAuthor.getText().toString());
        memo.setContent(editContent.getText().toString());
        memo.setDatetime(System.currentTimeMillis());
        return memo;
    }

    private void write(Memo memo){
        try {
            String filename = System.currentTimeMillis() + ".txt";
            FileUtil.write(this, filename, memo.tosting());
            setResult(RESULT_OK);
            Toast.makeText(this, "등록되었습니다!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "에러:"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void initView(){
        btnPost = (Button) findViewById(R.id.btnPost);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
    }

    private void initListener(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Memo memo = getMemoFromScreen();
                write(memo);
            }
        });
    }
}
