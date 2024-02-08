## ���������� �� ������ � ������� �������
��� ������ � ������� ������ Spring Boot ���������� ��� ����������� ��������� ����:
- �������� �������� � �������� ���������� ������ ������� � ��������� ��������� ������� ��� ������ ������ ����������:  
***./gradlew build***  
��� ������� ������� ��� ������ � ������� ����������� JAR-���� � ���������� build/libs.
- ����� �������� ������ �� ������ ��������� ����������, ��������� ��������� �������:  
***java -jar build/libs/library-service-0.0.1-SNAPSHOT.jar***
## ���� �� ������ ��������� ���������� ����� Docker
- ��������� ��������� ������� � ���������, �������� � �������� ���������� ������ �������, ����� ������� Docker-����� ������ ����������:  
***docker build -t library-service .***
- ����� �������� ������ ������ ��������� ��������� � ����� �����������, ��������� ��������� �������:  
***docker run -p 8080:8080 library-service***