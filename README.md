# hanghaeboard

## API 명세

|기능|Method|URL|Request|Response|
|:------|:---|:---|:---|:---|
|메인페이지|GET|/|-|index.html|
|작성|POST|/api/write|{"username":"qw12","password":"1234","title":"title","contents":contents}|{"result": true,"message": "success","date": {"createdAt": "2023-04-15T20:26:55.0299781","modifiedAt": "2023-04-15T20:26:55.0299781","id": 1,"username":"song","password": "1234","title": "안녕","contents": "반가워"}}|
|전체|GET|/api/boards|-|{"result": true,"message": "success","date":[{"createdAt": "2023-04-15T20:32:30.750136","modifiedAt": "2023-04-15T20:32:30.750136","id": 2,"username": "qw12","password": "1234","title": "title","contents": "contents"},{"createdAt": "2023-04-15T20:26:55.029978","modifiedAt": "2023-04-15T20:26:55.029978","id": 1,"username": "song","password": "1234","title": "안녕","contents": "반가워"}]}|
|조회|GET|/api/boards/{id}|{"id":2}|{"result": true,"message": "success","date":{"createdAt": "2023-04-15T20:32:30.750136","modifiedAt": "2023-04-15T20:32:30.750136","id": 2,"username": "qw12","password": "1234","title": "title","contents": "contents"}}|
|수정|PUT|/api/boards/{id}|{"username":"qw34","password":"1234","title":"title1","contents":contents1}|{"result": true, "message": "success","date": {"createdAt": "2023-04-15T20:32:30.750136","modifiedAt": "2023-04-16T04:42:39.7152063","id": 2,"username": "qw34","password": "1234","title": "title1", "contents": "contents1"}}|
|삭제|DELETE|/api/boards/{id}|{"password" : "1234"}|{"result": true,"message": "success","date": null}|

## 유스케이스

![과제1 drawio](https://user-images.githubusercontent.com/101760007/232275151-b2bdbf79-4491-478a-900f-52360527ec58.png)

## ERD 설계

![블로그_erd](https://user-images.githubusercontent.com/47537803/232467599-e293bdc5-34dd-4334-9ae1-f9822d8c0876.PNG)
