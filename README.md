# hanghaeboard

## API 명세

|기능|Method|URL|Request Header|Request|Response|Response Header|
|:------|:---|:---|:---|:---|:---|:---|
|메인페이지|GET|/|-|index.html|
|회원가입|POST|/api/user/signup|-|{"username":"godjin12","password":"hyeonjin12"}|{"result": true,"message": "회원가입 성공","status": "OK","data": null}|z|
|로그인|POST|/api/user/login|-|{"username":"godjin12","password":"hyeonjin12"}|{"result": true,"message": "로그인 성공","status": "OK","data": null}|Authorization:BearereyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|
|작성|POST|/api/boards|Authorization:BearereyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|{"title" : "아싸1","contents" : "과제성공1"}|{"result": true,"message": "write success","status": "OK","data": {"createdAt": "2023-04-17T19:57:42.767257","modifiedAt": "2023-04-17T19:57:42.767257","id": 1,"title": "아싸1","contents": "과제성공1","user": {"id": 1,"username": "godjin12","password": "hyeonjin12"}}}|z|
|목록|GET|/api/boards|-|-|{"result": true,"message": "list success","status": "OK","data": [{"createdAt": "2023-04-17T19:58:58.469346","modifiedAt": "2023-04-17T19:58:58.469346","id": 2,"title": "아싸2","contents": "과제성공2","user": {"id": 1,"username": "godjin12","password": "hyeonjin12"}},{"createdAt": "2023-04-17T19:57:42.767257","modifiedAt": "2023-04-17T19:57:42.767257","id": 1,"title": "아싸1","contents": "과제성공1","user": {"id": 1,"username": "godjin12","password": "hyeonjin12"}}]}|-|
|조회|GET|/api/boards/{id}|-|-|{"result": true,"message": "listOne success","status": "OK","data": {"createdAt": "2023-04-17T19:57:42.767257","modifiedAt": "2023-04-17T19:57:42.767257","id": 1,"title": "아싸1","contents": "과제성공1","user": {"id": 1,"username": "godjin12","password": "hyeonjin12"}}}|-|
|수정|PUT|/api/boards/{id}|Authorization:BearereyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|{"title" : "아싸12","contents" : "과제성공12"}|{"result": true,"message": "update success","status": "OK","data": {"createdAt": "2023-04-17T19:57:42.767257","modifiedAt": "2023-04-17T20:03:46.523136","id": 1,"title": "아싸12","contents": "과제성공12","user": {"id": 1,"username": "godjin12","password": "hyeonjin12"}}}|-|
|삭제|DELETE|//api/boards/{id}|Authorization:BearereyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|-|{"result": true,"message": "delete success","status": "OK","data": null}|-|

## 유스케이스
![항해과제2 (1)](https://user-images.githubusercontent.com/101760007/232492400-70190ca2-d950-4ad4-955b-a546c9399f9b.png)

## ERD 설계

![블로그_erd](https://user-images.githubusercontent.com/47537803/232467599-e293bdc5-34dd-4334-9ae1-f9822d8c0876.PNG)
