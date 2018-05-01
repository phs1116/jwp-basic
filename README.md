#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 톰캣 서버가 실행 되면 우선 @WebListener 어노테이션으로 정의되어있는 클래스를 통해 컨테이너를 초기화한다
* 그 후 정의된 클래스패스 (이 경우 톰캣 클래스패스)에 @WebServlet으로 정의되어있는 클래스를 찾는다.
* loadOnStartup에 정의된 순서 대로 서블릿 클래스의 인스턴스를 생성하여 주소값과 매핑한다.
(별도 loadOnStartup에 대해 정의된 값이 없다면 해당 주소값에 접근 할 때 인스턴스를 생성한다.)
* 인스턴스를 생성할 때 오버라이딩한 init 함수를 호출하여 초기화한다.
* 이 프로젝트의 프론트 MVC의 경우 DispatcherSerlvet에서 RequestMapping을 생성해, 
작성한 Controller와 주소값을 매핑하여 맵의 형태로 저장한다.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 모든 URL을 대상으로 매핑되어있는 DispatcherServlet에 접근한다
* HttpServletRequest에 요청온 URL로, DispatcherServlet에 정의되어있는 
RequestMapping에서 해당 요청을 수행 할(주소가 매핑되어있) Controller를 찾는다.
* 작업을 수행할 Controller를 호출한다.
* 작성된 코드를 실행한다. QuestionDao을 생성하고, JdbcTemplate을 통해 DB에 전체 조회 쿼리를 보내 데이터를 조회한다.
* 리턴된 전체 질문 리스트를 화면의 뷰와(홈 화면), 화면에 노출될 데이터를 관리하는 ModelAndView에 세팅하고 리턴한다.
* 이러한 ModelAndView를 반환받은 DispatcherServlet은 ModelAndView에 설정된 값으로 리다이렉트, 혹은 foward,
 혹은 바로 화면을 출력(json, excel 등등)에 브라우져에 돌려준다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* ShowController는 싱글톤의 개념으로 단 하나만 존재하는 인스턴스이며, 요청들어오는 모든 리퀘스트는 ShowDatabase에 접근한다.
이러한 싱글톤 개념의 클래스는 상태를 가지면 안된다. 
왜냐하면 다른 리퀘스트의 처리 도중 다른 리퀘스트에 의해 값이 변경될 위험이 있기 때문이다.(thread-safe하지 않다.)
따라서 question과 answers는 필드로 두면 안되고 메서드 내에 지역 변수로 선언하여 사용해야한다. 
