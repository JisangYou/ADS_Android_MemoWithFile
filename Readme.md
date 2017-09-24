# AndroidMemo01
## 리스트뷰와 File I/O를 활용

### 액티비티 및 클래스 구성

1. ListActivity - 메인 액티비티. 리스트뷰를 세팅하고, 데이터를 세팅하는 액티비티

2. DetailActivity - 상세 액티비티. 리스트뷰의 아이템을 클릭한 후에 넘어가는 액티비.

3. WriteActivity - 쓰기 액티비티. 이곳에서 작성하면 아이템이 추가됨.

4. ListAdapter class - 어댑터를 구현하는 클래스(+ viewHolder클래스)\
- 어댑터를 구현하고, 그안에 viewHolder를 통해 자주 사용하는 변수 및 기능을 넣어놓음.

5. FileUtil class - 파일관련 입출력을 관리하는 클래스
  - Util 패키지를 따로 만들어 객체지향적으로 사용할 수 있도록 구성

6. memo class - 메모관련 기본 변수 및 기본적인 기능들을 관리하는 클래스
- 기본적인 메모 변수 및 메서드들을 implements해서 구성함.
