# java-was-2022
Java Web Application Server 2022

## 요구사항 1: 정적인 html 파일 응답

> [http://localhost:8080/index.html](http://localhost:8080/index.html)
로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
>

### 로직 순서

1. BufferedReader로 InputStream 값을 한 줄(readLine()을 사용해)씩 읽어가며 로직을 처리한다.
2. header의 첫번째 라인을 읽는 경우, httpMethod URL HTTP/버전 순으로 출력되기 때문에 split을 사용해 첫 번째 URL을 가져온다.
3. 해당 URL에 해당하는 파일을 response에 담아 처리한다.

```java
BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
DataOutputStream dos = new DataOutputStream(out);

byte[] body = new byte[8192];
String line = "";
int lineCount = 0;

logger.debug("--------Request Header--------");

while (true) {
    line = br.readLine();
    if (line == null || "".equals(line)) break;

    logger.debug("Line: {}", line);

    if (lineCount == 0) {
        String path = line.split(" ")[1];
        logger.debug("Path: {}", path);

        body = getBytesFromStaticFile(path);
    }

    lineCount++;
}
```

```java
private byte[] getBytesFromStaticFile(String path) throws IOException {
		byte[] bytes = new byte[8192];
		try {
		    bytes = Files.readAllBytes(new File("./webapp" + path).toPath());
		} catch (Exception e) {
		    bytes = Files.readAllBytes(new File("./webapp" + "/error_not_found.html").toPath());
		}
		
		return bytes;
}
```

### BufferedReader

키보드 입력시 한 글자마다 프로그램에 전송되는 Scanner와 달리 입력시 한 글자를 버퍼에 담아두고 개행 문자가 존재하거나 버퍼가 가득차는 경우에 프로그램으로 전달하는 형태를 가진다. 따라서 Scanner보다 빠르다는 장점을 가지고 있다.

### 개선점

1. 첫번째 라인을 확인하기 위해 lineCount라는 변수를 사용했는데, 0일 때만 확인해서, 첫번째 url 추출하는 부분은 따로 처리하는 게 나은지에 대한 방법 고안해야 한다.

## 요구사항 2: ****GET으로 회원가입 기능 구현****

> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입 폼을 표시한다. 이 폼을 통해서 회원가입을 할 수 있다.회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
>

```
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

> HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
>

### 로직

1. 기존 코드에서 HttpRequest 클래스를 구현해 request에 관한 값을 처리한다.
2. 이 때 해당 인스턴스를 만들기 위해 이를 파싱해주는 RequestParser를 사용한다.
3. 인스턴스가 생성되면 해당 내부에 있는 필드 값인 path값을 통해 User 클래스에 값을 저장한다.

```java
// RequestHandler.java
HttpRequest httpRequest = RequestParser.getHttpRequestFromInput(br.readLine());
byte[] body = processRequest(httpRequest);
```

```java
private byte[] processRequest(HttpRequest httpRequest) throws IOException {

	if (httpRequest.getPath().equals("/user/create")){
	    String userId = httpRequest.getParameters().get("userId");
	    String password = httpRequest.getParameters().get("password");
	    String name = httpRequest.getParameters().get("name");
	    String email = httpRequest.getParameters().get("email");
	
	    User user = new User(userId, password, name, email);
	
	    logger.debug("CreateUserRequest UserInfo: {}", user);
	}
	
	return getBytesFromFilePath(httpRequest.getPath());
	}
```

### 개선점

1. 뭔가 지저분해보인다. 변수 변경 등이 필요하다.