
# 간단한 블로그 API 기능을 포함한 프로젝트
- Spring Boot 3 (Spring 6) & JDK 17
- Single Project 
- (to do list) Multi Module Project (블로그 조회 기능, 인기 검색어 조회/저장 ) 
- 기본적으로 카카오 검색을 우선으로 하며 카카오 검색이 되지 않는 경우 네이버 검색으로 전환한다. 이외에도 검색이 되지 않는다면 에러가 발생하도록 조치한다

- 외부와의 서비스 통신 (2가지 방법 모두 구현)
  - RestTemplate (Spring 5.x 이후, deprecated로 비권장으로 주석처리함)
  - WebClient (Spring 5.x 이후 출시, Webflux, 동기/비동기 모두 접근 가능)

# 환경정보
- JAVA 17 이상 (스프링 6 / 스프링부트 3.x 버전)


# 기능 설명
## 1. 블로그 조회 기능
## 2. 최고 검색 건수 키워드 10선



# Swagger
http://localhost:9999/swagger-ui/index.html

# API 명세
## 공통
### 공통 응답 코드
| Code   | Description |
|--------|-------------|
| 200    | 성공        |
| 400    | 파라미터 오류 |
| 450    | 네트워크 오류 |
| 500    | 서버 오류    |

### Response 구조
| Name | Type   | Description   |
|------|--------|---------------|
| code | String | 응답 코드         |
| msg  | String | 응답 메시지        |
| data |        | 하기 Response 참고 |

```json
{
  "code": "code",
  "message":"msg",
  "data": {
    "sample": "data"
  }
}
```

## Swagger (OpenAPI)
'Select a definition' 에서 영역을 선택할 수 있음

### [바로가기] BLOG API
http://localhost:9999/swagger-ui/index.html?urls.primaryName=BLOG%20API

### [바로가기] KEYWORD API
http://localhost:9999/swagger-ui/index.html?urls.primaryName=KEYWORD%20API

# 기능 설명

## 1. /blog/blogs : 블로그 검색

### Parameter
| Name  | Type    | Description                                               | Required |
|-------|---------|-----------------------------------------------------------|----------|
| query | String  | 검색을 원하는 질의어      | O        |
| sort  | String  | 결과 문서 정렬 방식<br/>- accuracy: 정확도순(기본값)<br/> - recency: 최신순 | X        |
| page  | Integer | 결과 페이지 번호<br/>- 1~50 사이의 값(기본 값 1)                        | X        |
| size  | Integer | 한 페이지에 보여질 문서 수<br/>- 1~50 사이의 값(기본 값 1)                  | X        |

### Response Data
| Name      | Type     | Description          |
|-----------|----------|----------------------|
| meta      | Meta     | 블로그 조회 메타정보 |
| documents | [Array]  | 블로그 조회결과      |

#### Response Data (Detail) - meta [Meta]

| Name          | Type    | Description            |
|---------------|---------|------------------------|
| totalCount    | Integer | 검색된 문서 수               |
| isEnd         | Boolean | 현재 페이지가 마지막 페이지 여부     |

#### Response Data (Detail) - documents [Array]

| Name      | Type | Description                     |
|-----------|------|---------------------------------|
| title     | String | 블로그 글 제목                        |
| contents  | String | 블로그 글 요약                        |
| url       | String | 블로그 글 URL                       |
| blogname  | String | 블로그의 이름                         |
| thumbnail | String | 검색 시스템에서 추출한 대표 미리보기 이미지 URL    |
| registrationDate  | Date | 블로그 글 작성시간 ex: 2023-03-19 |

### Sample

#### Request curl

```curl
curl -X 'GET' \
  'http://localhost:9999/blog/blogs?query=%EC%9C%A0%EB%9F%BD&sort=accuracy&page=1&size=10' \
  -H 'accept: */*' \
  -H 'Accept-Language: KO_KR'

curl -X 'GET' \
  'http://localhost:9999/blog/blogs?query=%EC%8A%A4%ED%94%84%EB%A7%81&sort=accuracy&page=1&size=10' \
  -H 'accept: */*' \
  -H 'Accept-Language: KO_KR'
```

#### Response JSON

```json
{
  "code": "200",
  "message": "Success",
  "data": {
    "meta": {
      "totalCount": 12332955,
      "isEnd": false
    },
    "documents": [
      {
        "title": "커플 선글라스 맛집 <b>유럽</b>안경",
        "contents": "커플 선글라스 맛집 ! 구매이벤트 진행중인 <b>유럽</b>안경 (젠몬테 구매시 렌즈50%할인!) 환절기라 날씨가 아침저녁으로 싸늘해서 센치해지는 계절이에요. 빨리 리프레쉬 차원에서 휴가를 떠나야 할 것만 같아요. 그런 의미에서 저는 여자친구와 일본으로 휴가일정을 잡아놓았습니다. 그런데 커플티는 맞췄는데 선글라스가...",
        "url": "http://badseesunggong.tistory.com/12",
        "blogname": "40살전 아파트 구매",
        "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/85mxp0Yg16u",
        "registrationDate": "2023-03-02"
      },
      {
        "title": "대전 선글라스 <b>유럽</b>안경",
        "contents": "대전 선글라스 전문점 : 23시즌 젠몬 신상입고된 <b>유럽</b>안경 23년도가 찾아오고야 말았어요~~ 날씨가 연일 너무 춥고 건조해서 일하다보면 금방 지치네요. ㅠㅠ 빨리 리프레쉬 차원에서 휴가를 떠나야 할 것만 같아요. 저는 월말에 제주도로 휴가일정을 잡아놓았습니다. 그런데, 여행지에서 이쁘게 사진찍으려면 선글라스...",
        "url": "http://jasusungga.tistory.com/9",
        "blogname": "자수성가",
        "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/GDk62XHLj6h",
        "registrationDate": "2023-01-27"
      },
      ...
    ]
  }
}
```

## 2. /keyword/keywords: 인기 검색어 조회

### Response Data
| Name      | Type                         | Description |
|-----------|------------------------------|-------------|
| KeywordDTO | [Array] | 인기검색어 정보    |

#### Response Data (Detail)
| Name    | Type    | Description |
|---------|---------|-------------|
| keyword | String  | 검색어         |
| cnt     | Integer | 검색 건수       |

### Sample
#### Request curl
```curl
curl -X 'GET' \
  'http://localhost:9999/keyword/keywords' \
  -H 'accept: */*' \
  -H 'Accept-Language: KO_KR'
```

#### Response JSON
```json
{
  "code": "200",
  "message": "Success",
  "data": [
    {
      "keyword": "유럽",
      "cnt": 1
    },
    {
      "keyword": "스프링",
      "cnt": 1
    }
  ]
}
```

# Spring Actuator
## 스프링 부트 애플리케이션을 모니터링하고 관리하는 데 도움이 되는 여러 가지 추가 기능이 포함
### health : 응용 프로그램 상태 정보를 표시하는 데 사용됩니다
http://localhost:9999/actuator/health

### info : 임의 응용 프로그램 정보를 표시하는 데 사용됩니다.
http://localhost:9999/actuator/info

### loggers : 응용 프로그램에서 로거의 구성을 표시하고 수정하는 데 사용됩니다.
http://localhost:9999/actuator/info


# jar 파일 만드는 방법과 실행하는 방법

``` shell
> ./gradlew clean build --stacktrace --info --refresh-dependencies -x test
> java -jar build/libs/totoblog-1.0.0.jar
```

