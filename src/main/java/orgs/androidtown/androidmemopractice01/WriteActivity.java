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

    /**
     * 내용을 파일에 작성
     * - 파일 쓰기
     *  내부저장소 - Internal : 개별앱만 접근가능, 파일탐색기에서 보이지 않는다.
     *  외부저장소 - External : 모든앱이 접근 가능 > 권한 필요
     *
     */

    private Memo getMemoFromScreen(){
        Memo memo = new Memo();
        memo.setNo(1);
        memo.setTitle(editTitle.getText().toString());
        memo.setAuthor(editAuthor.getText().toString());
        memo.setContent(editContent.getText().toString());
        memo.setDatetime(System.currentTimeMillis());
        return memo; // write클래스에서 작성한 것들을 메모클래스에 set시켜서, 나중에 memo만 불러온다
    }

    private void write(Memo memo){// 파일에 저장하기 위함!!! 어떤 액티비티이든, 파일에 접근해서 저장하고, 불러오는 기능을 만들 수 있는게 객체지향의 장점!
        try {
            String filename = System.currentTimeMillis() + ".txt";//.txt로 저장
            FileUtil.write(this, filename, memo.tosting());

            setResult(RESULT_OK); // 나름 호출한 액티비티로 성공/실패 값을 넘겨준다.

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
                Memo memo = getMemoFromScreen(); // 화면에서 작성하는 것들을 객체에 담아서 저장시킴.
                write(memo); // 이를 파일에
            }
        });
    }
}
