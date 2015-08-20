Please add below said entry to your web project in web.xml file

	<servlet>
		<servlet-name>JUnitEETestServlet</servlet-name>
		<servlet-class>org.junitee.servlet.JUnitEEServlet</servlet-class>
		<init-param>
			<param-name>searchResources</param-name>
			<param-value></param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>JUnitEETestServlet</servlet-name>
		<url-pattern>/TestServlet/*</url-pattern>
	</servlet-mapping>
	
	
	Please read before test case is executed.
	
	In order to execute web based test case, 
	1) make sure your application is deployed 
	2) All your test case class extends junit.framework.TestCase
	3) testCase.txt located under web-inf/ contains test case file names with 
	   package declaration. Note if you alter or add any test case to testCase.txt,
	   make sure the test case file is defined with package name, not necessary to 
	   add file extension
	   for eg:-   
	   com.dummy.HelloWorldTest
    4) Invoke below url in a browser   http://<host>:<port>/<your web context>/TestServlet
       for eg:- http://localhost:8080/juniteeweb/TestServlet
    5) Further help please refer: http://www.junitee.org/tutorial.html   	   
	      