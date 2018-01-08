# ADS04 Android

## 수업 내용
- File I/O를 활용한 메모장을 학습

## Code Review

### ListActivity

```Java
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

    private ArrayList<Memo> loadData() {// 파일에 저장되어 있는 것을 불러오는 용도, 파일 or 데이터베이스의 목적은 바로 저장하고 불러오는거!
        ArrayList<Memo> result = new ArrayList<>();
        // 파일목록에서 파일을 하나씩 꺼낸 후에
        // Memo 클래스로 변환한 후 result에 담는다.
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
```
### ListAdapter

```Java
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
```


### WriteActivity

```Java
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
```
### DetailActivity

```Java
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
```

### Memo

```Java
public class Memo {

    private static final String DELIMETER ="//";
    private int no;
    private  String title;
    private  String author;
    private  String content;
    private  long datetime;

    public Memo(){

    }
    public Memo(String text){ // 생성자로 뭔가가 들어올때, parse()메소드가 호출됨.
        parse(text);
    }

    public void parse(String text){
        //1. 문자열을 줄("\n")단위로 분해
        String lines[] = text.split("\n");
        //2. 문자열을 행("위에 DELIMETER")단위로 분해
        for(String line : lines){
            String columns[] = line.split(DELIMETER);
            String key = "";
            String value = "";
            if(columns.length == 2){
                key = columns[0];
                value = columns[1];
            }else{
                key ="";
                value ="";
            }
            switch (key){
                case "no":
                    setNo(Integer.parseInt(value));
                    break;
                case "title":
                    setTitle(value);
                    break;
                case "author":
                    setAuthor(value);
                    break;
                case "datetime":
                    setDatetime(Long.parseLong(value));
                    break;
                case "content":
                    setContent(value);
                    break;
                default:
                    appendContent(value);
            }
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void appendContent(String value){ //이것의 구체적인 의미가 궁금금
       this.content += "\n" + value;
    }

    public String tosting(){
        StringBuilder result = new StringBuilder();
        result.append("no").append(DELIMETER).append(no).append("\n");
        result.append("title").append(DELIMETER).append(title).append("\n");
        result.append("author").append(DELIMETER).append(author).append("\n");
        result.append("datetime").append(DELIMETER).append(datetime).append("\n");
        result.append("content").append(DELIMETER).append(content).append("\n");
        return result.toString();
    }

    public byte[] toBytes(){
        return toString().getBytes(); //문자열을 바이트배열로 전환
    }
}
```

### FileUtil

```Java
public class FileUtil {

    public static String read(Context context, String filename) throws IOException {// 전역으로 따로 class를 만들었으므로 어디서건 사용가능
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = context.openFileInput(filename);
            // 버퍼를 달고
            bis = new BufferedInputStream(fis);
            // 한번에 읽어올 버퍼양을 설정
            byte buffer[] = new byte[1024];
            // 현재 읽은 양을 담는 변수설정
            int count = 0;
            while ((count = bis.read(buffer)) != -1) {
                String data = new String(buffer, 0, count); // public String(byte bytes[], int offset, int length), 즉 data란 변수의 정보를 설정하는 코드
                sb.append(data);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString(); // 이 메소드가 호출되면 다음과 같은 리턴타입을 돌려줌.
    }

    /**
     * 파일 쓰기 함수
     *
     * @param context
     * @param filename
     * @param content
     * @throws IOException
     */

    public static void write(Context context, String filename, String content) throws IOException {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null)
                try {
                    {
                        fos.close();
                    }
                } catch (Exception e) {
                    throw e;
                }
        }
    }
}
```


## 보충설명

### 앱의 구조

>> ListActivity - 메인 액티비티. 리스트뷰를 세팅하고, 데이터를 세팅하는 액티비티

>> DetailActivity - 상세 액티비티. 리스트뷰의 아이템을 클릭한 후에 넘어가는 액티비.

>> WriteActivity - 쓰기 액티비티. 이곳에서 작성하면 아이템이 추가됨.

>> ListAdapter class - 어댑터를 구현하는 클래스(+ viewHolder클래스)\
- 어댑터를 구현하고, 그안에 viewHolder를 통해 자주 사용하는 변수 및 기능을 넣어놓음.

>> FileUtil class - 파일관련 입출력을 관리하는 클래스
  - Util 패키지를 따로 만들어 객체지향적으로 사용할 수 있도록 구성

>> memo class - 메모관련 기본 변수 및 기본적인 기능들을 관리하는 클래스

### 파일 입출력 전 알아둬야하는 사항

- 안드로이드 앱에서 파일을 읽고 쓰기 위해서는, 해당 디렉토리에 대한 읽기 권한 또는 쓰기 권한이 허용되어야 합니다. 만약 권한이 없어도 마음대로 파일을 읽고 쓸 수 있다면, 앱이 가진 고유한 데이터가 유출되거나 시스템 파일을 마음대로 바꿀 수 있게 되어 제대로 된 시스템 운영을 할 수 없게 됨.

- 안드로이드 앱이 기본적으로 읽고 쓸 수 있는 디렉토리 경로는 "/data/user/[USER-NO]/[PACKAGE]/files/"(또는 "/data/data/[PACKAGE-NAME]/files/") 입니다. 하지만 직접 전체 경로 이름을 지정하여 파일에 접근하는 것은 바람직하지 않고, 대신 Context(android.content.Context)의 getFilesDir() 함수를 사용하여 해당 경로를 얻어와야 합니다.

```Java
File file = new File(getFilesDir(), FILENAME) ;
```

- 바이트(byte) 단위로 처리되는 바이너리 데이터는, 각 바이트 값이 사용되는데 있어 딱히 용도가 구분되어 있지 않기 때문에, "XX 값이 나오기 전까지의 데이터를 읽는 함수"가 존재하지 않습니다. 그래서 데이터를 읽는 read() 함수에는 반드시 읽어들일 데이터의 크기를 명시하게 되어 있죠.

```Java
    int read() ;                           // 1 바이트 데이터 읽기.
    int read(byte[] b, int off, int len) ; // off 위치에서 len 만큼 b 버퍼에 읽기.
    int read(byte[] b) ;                   // b.length만큼 b 버퍼에 읽기.

```

- 하지만 문자(char) 단위로 처리되는 텍스트 데이터는, 화면에 문자로 표시되는 값 외에 "줄 바꿈" 이라는 조금은 특수한(?) 용도의 값이 구분되어 있어서, "'줄 바꿈'문자가 나올 때까지 데이터를 읽는 함수"가 존재합니다. 즉, 고정된 버퍼 크기가 아닌, 가변 길이의 줄 단위로 데이터를 읽는 것이 가능하다는 것이며, 이는 BufferedReader 클래스의 readLine() 함수가 그 역할을 수행합니다.

```Java
 String readLine() ;     // 한 줄 텍스트 데이터를 읽어 String 타입으로 리턴.

```

### File 입출력

- 기본적인 파일 입출력

![fileOutputStream](http://cfile10.uf.tistory.com/image/254F3A4F589C2DE2092029)
![fileInputStream](http://cfile26.uf.tistory.com/image/27455B3C589C2DE2081513)

- 버퍼를 사용한 향상된 파일 입출력

![fileOutputStreamWithBuffer](http://cfile6.uf.tistory.com/image/22056736589C5068168355)
![fileInputStreamWithBuffer](http://cfile1.uf.tistory.com/image/2212A335589C506819185F)

### Intent StartActivityForResult

![StartActivityForResult](http://3.bp.blogspot.com/-asLQgz6JJQM/VadaXG4YIUI/AAAAAAAAAuw/PV98GstvEB8/s1600/2.jpg)

액티비티 B가 종료되기 전에 setResult() 메소드를 호출하면 액티비티 A에게 응답을 보낼 수 있다. 액티비티 B에서 보낸 응답은 부모 액티비티의 onActivityResult() 메소드에서 받을 수 있다. 만약 액티비티가 알 수 없는 이유로 끝나 버리거나 비정상 종료되면Activity.RESULT_CANCELED 값이 전달

### getTag,setTag

- 일종의 바코드 
- 아이템에 대한 추가적인 정보를 보관하기 위해
- 커스텀 리스트 아이템의 UI 객체들을 미리 찾아서 Tag 에 넣어두고 getView() 에서 일일이 findview 로 찾는게 아니라 미리 찾아둔 tag 에서 꺼내 쓰는 식

#### 출처

- 출처: http://recipes4dev.tistory.com/114 [개발자를 위한 레시피]
- 출처: http://sioh59.blogspot.kr/2015/07/android_21.html

## TODO

- Intent의 추가적인 기능학습
- util성 클래스에 대한 학습
- 객체를 만드는 클래스 DAO에 대한 개념

## Retrospect

- 게시판에 추가적인 기능(삭제, 수정 등) 만들어 볼것.

## Output
- 추후 올릴 예정
