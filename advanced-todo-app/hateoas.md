# HATEOAS (Hypermedia as the Engine of Application State)

## 정의
REST 아키텍쳐의 중요한 제약 조건 중 하나로, 애플리케이션의 상태가 하이퍼미디어를 통해 전이되고 결정되는 방식.

즉 클라이언트가 서버로부터 받은 응답에 포함된 링크들을 통해 다음에 수행할 수 있는 동작을 동적으로 파악할 수 있게끔 하고,
API와 상호작용하도록 하는 메커니즘

## 핵심 개념
기존의 REST API의 경우에는 클라이언트가 특정 기능을 수행하기 위해서는 필요한 URI를 미리 알고 하드코딩해야 했다.(`/users/{id}`, postman 등)

근데 HATEOAS를 적용한 API는 응답 데이터에 관련된 다른 리소스에 접근할 수 있는 링크가 애초에 포함되어 있다.
이 링그들은 `rel`(relation) 속성을 통해 어떤 종류의 동작인지 설명하며, `href` 속성으로 해당 동작을 수행할 수 있는 URI를 명시해준다.

```json
// HATEOAS가 적용되지 않은 응답
{
  "id": 1,
  "name": "mike",
  "email": "mike@naver./com",
}
```

```json
// HATEOAS가 적용된 않은 응답
{
  "id": 1,
  "name": "mike",
  "email": "mike@naver./com",
  "_links": {
      "self": {
        "href": "http://localhost:8080/api/todos/2"
      },
      "todo": {
        "href": "http://localhost:8080/api/todos/2"
      },
      "edit": {
        "href": "http://localhost:8080/api/todos/2"
      },
      "delete": {
        "href": "http://localhost:8080/api/todos/2"
      }
    }
}
```

이상의 예시에서 `_links` 객체를 통해서 이하의 정보를 항 수 있다.

- self: 현재 리소스의 URI
- edit: 이 사용자의 정보를 수정할 수 있는 URI
- delete: 이 사용저의 정보를 삭제할 수 있는 URI

## 장점
1. 클라이언트-서버 간의 결합도 감소
  - 클라이언트가 더이상 API의 URI 구조를 미리 알 필요 없다.
  - 서버에서 URI가 변경되더라도 `_links`에 제공되는 링크만 따라가면 되므로 코드 수정이 줄어든다.

2. API의 자체 설명 기능 강화
  - 응답에 포함된 링크들과 `_links` 객체의 키를 확인하는 것만으로 API 사용법을 더 잘 알 수 있다.

3. RESTful API를 구축하기 위한 편의사항이 제공된다.

## 단점
1. HATEOAS를 사용하면 응답 데이터의 크기(`_links`)가 커진다.

2. 구현상의 복잡도 증가
  - 서버와 클라이언트가 모두 링크를 참조하기 때문에 동적으로 상호작용하도록 구현하는 데 추가적인 노력이 필요하다.

# URL vs. URI

URL(Uniform Resource Locater)     / 통합 자원 위치      - 위치(주소)를 나타냄
URI(Uniform Resource Identifier)  / 톱합 자원 식별자    - 인터넷 상에서의 자원을 고유하게 식별

웹 상의 자원을 나태나는 문자열이라는 측면에서 유사하다.

URI가 URL을 포함하는 상위 개념.

URI의 경우에는 고유한 식별이 가능하기만 하면 되는 반면에,
URL은 통신 프로토콜을 통해 실제 위치에 도달할 수 있어야 한다.

## 예시
https://www.google.com/search?q=busan


- URL : https://www.google.com/search
  - https 방식으로 www.google.com이라는 서버에 접속해서 /search라는 경로의 자원을 찾아가라는 `위치 정보`를 표시.
  - 그래서 URL이면서 URI이기도 하다.
- URI : https://www.google.com/search?q=busan
  - 이상의 주소는 q=busan이라는 매개변수를 포함하여 'busan을 검색한 결과'라는 특정 자원을 고유하게 식별.
  - 만약 q=seoul이라면 다른 자원을 식별하게 되므로 URI라고 할 수 있다.

