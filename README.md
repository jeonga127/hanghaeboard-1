# hanghaeboard

## API 명세

|기능|Method|URL|Request Header|Request|Response|Response Header|
|:------|:---|:---|:---|:---|:---|:---|
|메인페이지|GET|/|-|index.html|-|-|
|회원가입|POST|/api/user/signup|-|{"username":"godjin12","password":"hyeonjin12"}|{"message": "회원가입 성공","status": "OK","data": null}|-|
|로그인|POST|/api/user/login|-|{"username":"godjin12","password":"hyeonjin12"}|{"message": "로그인 성공","status": "OK","data": null}|Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|
|작성|POST|/api/boards|Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|{"username" : "Song12","title" : "아싸1","contents" : "과제성공1"}|{"id": 2,"username": "Song12","title": "아싸12","contents": "과제성공12","createdAt": "2023-04-18T10:40:28.2176221","modifiedAt": "2023-04-18T10:40:28.2176221"}|-|
|목록|GET|/api/boards|-|-|{"message": "list success","status": "OK","data": [{"id": 2,"username": "Song12","title": "아싸12","contents": "과제성공12","createdAt": "2023-04-18T10:40:28.217622","modifiedAt": "2023-04-18T10:40:28.217622"},{"id": 1,"username": "Song12","title": "아싸1","contents": "과제성공1","createdAt": "2023-04-18T10:40:21.280084","modifiedAt": "2023-04-18T10:40:21.280084"}]}|-|
|조회|GET|/api/boards/{id}|-|-|{"id": 1,"username": "Song12","title": "아싸1","contents": "과제성공1","createdAt": "2023-04-18T10:40:21.280084","modifiedAt": "2023-04-18T10:40:21.280084"}|-|
|수정|PUT|/api/boards/{id}|Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|{"username" : "Song123","title" : "아싸123","contents" : "과제성공123"}|{"id": 1,"username": "Song123","title": "아싸123","contents": "과제성공123","createdAt": "2023-04-18T10:26:09.692325","modifiedAt": "2023-04-18T10:26:09.692325"}|-|
|삭제|DELETE|/api/boards/{id}|Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RqaW4xMiIsImV4cCI6MTY4MTczMjYwNCwiaWF0IjoxNjgxNzI5MDA0fQ.3_-Pgu2TIWP8tTt9il9sEluF7u6S3uipVN-_HyDzVYo|-|{"message": "delete success","status": "OK","data": null}|-|

## 유스케이스
![사용자인증](https://user-images.githubusercontent.com/101760007/232858291-4563bf6b-1b35-4009-a879-ba39690b9203.png)

## ERD 설계

![블로그_erd](https://user-images.githubusercontent.com/47537803/232467599-e293bdc5-34dd-4334-9ae1-f9822d8c0876.PNG)
