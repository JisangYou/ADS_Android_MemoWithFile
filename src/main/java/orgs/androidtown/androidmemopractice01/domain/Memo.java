package orgs.androidtown.androidmemopractice01.domain;

/**
 * Created by Jisang on 2017-09-23.
 */

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
