# 자리톡 소통 커뮤니티 API
이 API는 임차인, 임대인, 공인중개사 및 외부이용자가
자유롭게 이용 할 수 있는 커뮤니티의 API입니다.

***

### 사용가능한 API 목록<br />
(외부 이용자는 글, 댓글 등록을 하실 수 없습니다.)

    게시글 목록 취득
    게시글 상세 획득
    게시글 등록
    게시글 수정
    게시글 삭제
    게시글 좋아요 누르기
    댓글 쓰기
    댓글 수정
    댓글 삭제
    닉네임 등록

API 상세는 하단 참조

***
### API 사용법
*전제조건*<br>
이 API는 HTTP Header의 Authorization의 정보를 설정하지 않으면 외부 사용자로 판단되어,<br>
글 목록과 글 상세보기만 가능한 상태가 됩니다. OAuth2.0의 Header Prefix와 Token을 설정이 필요합니다.<br>
ex) curl -H "Authorization: Lessor 21"<br>
<br>
① 닉네임 등록 API로 닉네임 등록을 하게 되면, 커뮤니티용 회원으로 등록 됩니다.<br>
커뮤니티의 회원은 임대인(LESSOR), 공인중개사(REALTOR), 임차인(LESSEE)만 등록 가능합니다.<br>
<br>
② 닉네임을 등록하게 되면<br>
글 등록, 수정, 삭제 / 댓글 등록, 수정, 삭제 / 좋아요 누르기의 API를 사용 할 수 있습니다.<br>
닉네임 등록을 하지 않으면 외부 사용자와 같은 취급을 하기때문에, 반드시 닉네임 등록을 선행해주세요.


***
### 요구사항 외 추가한 기능에 대한 설명

- 댓글의 등록 수정 삭제 기능<br>
댓글에 대한 기술 요구사항은 없었으나, '댓글 목록 화면에서는 작성한 사용자의 계정 타입을 표시'라는 기능 요구사항이 있었기에<br>
댓글을 표시하기 위해서는 댓글에 대한 API가 필요하다고 판단하여 작성하였습니다.

- 닉네임 등록 기능 <br>
HttpHeader의 authorization를 이용해도 사용자 구분은 가능하지만,<br>
기능 요구사항의 글과 댓글에 표시하는 이름 부분에, 사용자의 계정타입까지 표시하는 요구사항과<br>
기술 요구사항의 임대인, 임차인, 공인중개사는 커뮤니티용 별도의 테이블을 사용한다는 요구사항을 만족하기 위해<br>
기존 자리톡 이용자가 커뮤니티를 이용하기 위해서는 닉네임 등록을 하도록 하고,<br>
그 닉네임 등록을 통해 커뮤니티용 구성원 테이블에 신규등록을 한 후, 그 정보를 활용 할 수 있도록 만들었습니다.

***
### 추가한 라이브러리

- lombok<br>
일부 아노테이션을 사용하기 위해 추가

- h2database<br>
테스트 데이터베이스를 사용하기 위해 추가

  

***
### API 상세
##### 게시글 목록 취득
서비스URL : /api/board/(글 번호) <br>
요청 형식 : GET <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드,
  "message": 응답 메세지,
  "data": {
    "id" : 글 고유 id,
    "title" : 글 제목,
    "writer" : 글쓴이,
    "favoriteFlag" : 좋아요 여부,
    "favoriteCount" : 좋아요 수
  }
}
```

***
##### 게시글 상세 취득
서비스URL : /api/board/글 번호 <br>
요청 형식 : POST <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드,
  "message": 응답 메세지,
  "data": {
        "id" : 글 고유 id,
        "title" : 글 제목,
        "content": 글 내용,
        "writer" : 글쓴이,
        "createdDate" : 글 쓴 시간,
        "modifiedDate" : 글의 마지막 수정시간,
        "deleteDate" : 글의 삭제시간,
        "favoriteFlag" : 좋아요 여부,
        "favoriteCount" : 좋아요 수,
        "favoriteCheckerList": 글에 좋아요를 한 사람 리스트,
        "boardCommentList": 글에 달린 댓글 리스트 {
              "commentId": 댓글 id,
              "commentWriter": 댓글 쓴 사람의 닉네임,
              "content": 댓글 내용
        }
    }
}
```
***
##### 게시글 등록
서비스URL : /api/board/ <br>
요청 형식 : POST <br>
요청 변수
```yaml
{
  "title" : "글 제목" (문자열)
  "content" : "글 내용" (문자열)
}
```
출력 결과

```yaml
{
  "status": Http 응답코드,
  "message": 응답 메세지,
  "data": null
}
```
***
##### 게시글 수정
서비스URL : /api/board/댓글 번호 <br>
요청 형식 : PUT <br>
요청 변수
```yaml
{
  "title" : "글 제목" (문자열)
  "content" : "글 내용" (문자열)
}
```
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 게시글 삭제
서비스URL : /api/board/댓글 번호 <br>
요청 형식 : DELETE <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 댓글 등록
서비스URL : /api/comment/글 번호 <br>
요청 형식 : POST <br>
요청 변수
```yaml
{
  "content" : "댓글 내용" (문자열)
}
```
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 댓글 수정
서비스URL : /api/comment/댓글 번호 <br>
요청 형식 : PUT <br>
요청 변수
```yaml
{
  "content" : "댓글 내용" (문자열)
}
```
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 댓글 삭제
서비스URL : /api/comment/댓글 번호 <br>
요청 형식 : DELETE <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 좋아요 등록
서비스URL : /api/favorite/글 번호 <br>
요청 형식 : GET <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
***
##### 닉네임 등록
서비스URL : /api/member/닉네임 <br>
요청 형식 : GET <br>
요청 변수 : 없음 <br>
출력 결과

```yaml
{
  "status": Http 응답코드
  "message": 응답 메세지
  "data": null
}
```
