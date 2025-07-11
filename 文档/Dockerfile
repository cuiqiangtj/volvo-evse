# 使用官方OpenJDK作为基础镜像
FROM openjdk:17
 
# 将本地文件复制到Docker镜像中的工作目录下
COPY ./app.jar /usr/src/app/app.jar
 
# 设置工作目录
WORKDIR /usr/src/app
 
# 暴露端口，如果你的应用需要监听某个端口，例如8080
EXPOSE 8081
 
# 运行Java应用程序
CMD ["java", "-jar", "app.jar"]

192.168.50.130